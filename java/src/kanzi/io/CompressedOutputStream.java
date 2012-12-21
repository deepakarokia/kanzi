/*
Copyright 2011, 2012 Frederic Langlet
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
you may obtain a copy of the License at

                http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package kanzi.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import kanzi.ByteFunction;
import kanzi.EntropyEncoder;
import kanzi.IndexedByteArray;
import kanzi.OutputBitStream;
import kanzi.bitstream.DefaultOutputBitStream;
import kanzi.entropy.EntropyCodecFactory;
import kanzi.function.FunctionFactory;
import kanzi.util.MurMurHash3;



// Implementation of a java.io.OutputStream that encodes a stream
// using a 2 step process:
// - step 1: a ByteFunction is used to reduce the size ogf the input data
// - step 2: an EntropyEncoder is used to entropy code the results of step 1
public class CompressedOutputStream extends OutputStream
{
   private static final int DEFAULT_BLOCK_SIZE       = 1024 * 1024; // Default block size
   private static final int BITSTREAM_TYPE           = 0x4B414E5A; // "KANZ"
   private static final int BITSTREAM_FORMAT_VERSION = 1;
   private static final int DEFAULT_BUFFER_SIZE      = 32768;
   private static final int COPY_LENGTH_MASK         = 0x0F;
   private static final int SMALL_BLOCK_MASK         = 0x80;
   private static final int SKIP_FUNCTION_MASK       = 0x40;
   private static final int MIN_BLOCK_SIZE           = 1024;
   private static final int MAX_BLOCK_SIZE           = (16*1024*1024) - 4;
   private static final int SMALL_BLOCK_SIZE         = 15;

   private final int blockSize;
   private final MurMurHash3 hasher;
   private final IndexedByteArray iba1;
   private final IndexedByteArray iba2;
   private final char entropyType;
   private final char transformType;
   private final OutputBitStream  obs;
   private final PrintStream ds;
   private boolean initialized;
   private boolean closed;
   private int blockId;


   public CompressedOutputStream(String entropyCodec, String functionType, OutputStream os)
   {
      this(entropyCodec, functionType, os, DEFAULT_BLOCK_SIZE, false, null);
   }


   // debug print stream is optional (may be null)
   public CompressedOutputStream(String entropyCodec, String functionType,
               OutputStream os, int blockSize, boolean checksum, PrintStream debug)
   {
      if (entropyCodec == null)
         throw new NullPointerException("Invalid null entropy encoder type parameter");

      if (os == null)
         throw new NullPointerException("Invalid null output stream parameter");

      if (blockSize > MAX_BLOCK_SIZE)
           throw new IllegalArgumentException("The block size must be at most "+MAX_BLOCK_SIZE);

      if (blockSize < MIN_BLOCK_SIZE)
         throw new IllegalArgumentException("The block size must be at least "+MIN_BLOCK_SIZE);

      this.obs = new DefaultOutputBitStream(os, DEFAULT_BUFFER_SIZE);

      // Check entropy type validity (throws if not valid)
      char type = entropyCodec.toUpperCase().charAt(0);
      String name = new EntropyCodecFactory().getName((byte) type);

      if (entropyCodec.equalsIgnoreCase(name) == false)
         throw new IllegalArgumentException("Unsupported entropy type: " + entropyCodec);

      this.entropyType = type;

      // Check transform type validity (throws if not valid)
      type = functionType.toUpperCase().charAt(0);
      name = new FunctionFactory().getName((byte) type);

      if (functionType.equalsIgnoreCase(name) == false)
         throw new IllegalArgumentException("Unsupported function type: " + functionType);

      this.transformType = type;
      this.blockSize = blockSize;
      this.hasher = (checksum == true) ? new MurMurHash3(BITSTREAM_TYPE) : null;
      this.iba1 = new IndexedByteArray(new byte[blockSize], 0);
      this.iba2 = new IndexedByteArray(new byte[0], 0);
      this.ds = debug;
   }


   protected void writeHeader() throws IOException
   {
      if (this.initialized == true)
         return;

      if (this.obs.writeBits(BITSTREAM_TYPE, 32) != 32)
         throw new kanzi.io.IOException("Cannot write header", Error.ERR_WRITE_FILE);

      if (this.obs.writeBits(BITSTREAM_FORMAT_VERSION, 7) != 7)
         throw new kanzi.io.IOException("Cannot write header", Error.ERR_WRITE_FILE);

      if (this.obs.writeBit((this.hasher != null) ? 1 : 0) == false)
         throw new kanzi.io.IOException("Cannot write header", Error.ERR_WRITE_FILE);

      if (this.obs.writeBits(this.entropyType & 0x7F, 7) != 7)
         throw new kanzi.io.IOException("Cannot write header", Error.ERR_WRITE_FILE);

      if (this.obs.writeBits(this.transformType & 0x7F, 7) != 7)
         throw new kanzi.io.IOException("Cannot write header", Error.ERR_WRITE_FILE);

      if (this.obs.writeBits(this.blockSize, 26) != 26)
         throw new kanzi.io.IOException("Cannot write header", Error.ERR_WRITE_FILE);
   }


   /**
    * Writes the specified byte to this output stream. The general
    * contract for <code>write</code> is that one byte is written
    * to the output stream. The byte to be written is the eight
    * low-order bits of the argument <code>b</code>. The 24
    * high-order bits of <code>b</code> are ignored.
    * <p>
    * Subclasses of <code>OutputStream</code> must provide an
    * implementation for this method.
    *
    * @param      b   the <code>byte</code>..
    */
   @Override
   public void write(int b) throws IOException
   {
      this.iba1.array[this.iba1.index++] = (byte) (b & 0xFF);

      // If the buffer is full, time to encode
      if (this.iba1.index >= this.iba1.array.length)
         this.processBlock();
   }


   /**
    * Flushes this output stream and forces any buffered output bytes
    * to be written out. The general contract of <code>flush</code> is
    * that calling it is an indication that, if any bytes previously
    * written have been buffered by the implementation of the output
    * stream, such bytes should immediately be written to their
    * intended destination.
    * <p>
    * If the intended destination of this stream is an abstraction provided by
    * the underlying operating system, for example a file, then flushing the
    * stream guarantees only that bytes previously written to the stream are
    * passed to the operating system for writing; it does not guarantee that
    * they are actually written to a physical device such as a disk drive.
    * <p>
    * The <code>flush</code> method of <code>OutputStream</code> does nothing.
    *
    */
   @Override
   public void flush()
   {
      // Let the bitstream of the entropy encoder flush itself when needed
   }


   /**
    * Closes this output stream and releases any system resources
    * associated with this stream. The general contract of <code>close</code>
    * is that it closes the output stream. A closed stream cannot perform
    * output operations and cannot be reopened.
    * <p>
    *
    * @exception  IOException  if an I/O error occurs.
    */
   @Override
   public synchronized void close() throws IOException
   {
      if (this.closed == true)
         return;

      this.closed = true;

      if (this.iba1.index > 0)
         this.processBlock();

      // End block of size 0
      this.obs.writeBits(SMALL_BLOCK_MASK, 8);
      this.obs.close();
      this.iba1.array = new byte[0];
      super.close();
   }


   private void processBlock() throws IOException
   {
      if (this.iba1.index == 0)
         return;

      if (this.initialized == false)
      {
         this.writeHeader();
         this.initialized = true;
      }

      try
      {
         final int sz = this.iba1.index;
         this.iba1.index = 0;
         final int encoded = this.encode(this.iba1, sz);

         if (encoded < 0)
            throw new kanzi.io.IOException("Error in transform forward()", Error.ERR_PROCESS_BLOCK);

         this.iba1.index = 0;
         this.blockId++;
      }
      catch (Exception e)
      {
         throw new kanzi.io.IOException(e.getMessage(), Error.ERR_UNKNOWN);
      }
   }


   public long getWritten()
   {
      return (this.obs.written() + 7) >> 3;
   }


   // Return -1 if error, otherwise the number of encoded bytes
   private int encode(IndexedByteArray data, int blockLength)
   {
      EntropyEncoder ee = null;

      try
      {
         if (this.iba2.array.length < blockLength*5/4)
             this.iba2.array = new byte[blockLength*5/4];

         ByteFunction transform = new FunctionFactory().newFunction(blockLength,
                 (byte) this.transformType);

         this.iba2.index = 0;
         byte mode = 0;
         int dataSize = 0;
         int compressedLength = blockLength;
         int checksum = 0;

         if (blockLength <= SMALL_BLOCK_SIZE)
         {
            // Just copy
            System.arraycopy(data.array, data.index, this.iba2.array, 0, blockLength);
            data.index += blockLength;
            this.iba2.index = blockLength;
            mode = (byte) (SMALL_BLOCK_MASK | (blockLength & COPY_LENGTH_MASK));
         }
         else
         {
            // Compute block checksum
            if (this.hasher != null)
               checksum = this.hasher.hash(data.array, data.index, blockLength);

            final int savedIdx = data.index;

            // Forward transform
            if ((transform.forward(data, this.iba2) == false) || (this.iba2.index >= blockLength))
            {
               // Transform failed or did not compress, skip and copy block
               data.index = savedIdx;
               System.arraycopy(data.array, data.index, this.iba2.array, 0, blockLength);
               data.index += blockLength;
               this.iba2.index = blockLength;
               mode |= SKIP_FUNCTION_MASK;
            }

            compressedLength = this.iba2.index;
            dataSize++;

            for (int i=0xFF; i<compressedLength; i<<=8)
               dataSize++;

            // Record size of 'block size' in bytes
            mode |= (dataSize & 0x03);
         }

         // Each block is encoded separately
         // Rebuild the entropy encoder to reset block statistics
         ee = new EntropyCodecFactory().newEncoder(this.obs, (byte) this.entropyType);

         // Write block 'header' (mode + compressed length)
         final OutputBitStream bs = ee.getBitStream();
         final long written = bs.written();
         bs.writeBits(mode, 8);

         if (dataSize > 0)
            bs.writeBits(compressedLength, 8*dataSize);

         // Write checksum (unless small block)
         if ((this.hasher != null) && ((mode & SMALL_BLOCK_MASK) == 0))
            bs.writeBits(checksum, 32);

         // Entropy encode block
         final int encoded = ee.encode(this.iba2.array, 0, compressedLength);

         if (this.ds != null)
         {
            this.ds.print("Block "+this.blockId+": "+
                   blockLength + " => " + encoded + " => " +
                  ((bs.written()-written)/8L)+" ("+
                  ((bs.written()-written)*100L/(long)(blockLength*8))+"%)");

            if ((this.hasher != null) && ((mode & SMALL_BLOCK_MASK) == 0))
               this.ds.print("  [" + Integer.toHexString(checksum) + "]");

            this.ds.println();
         }

         return encoded;
      }
      catch (Exception e)
      {
         return -1;
      }
      finally
      {
         if (ee != null)
           ee.dispose();
      }
   }


}
