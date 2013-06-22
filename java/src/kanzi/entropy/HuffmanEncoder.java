/*
Copyright 2011-2013 Frederic Langlet
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

package kanzi.entropy;

import java.util.LinkedList;
import kanzi.OutputBitStream;
import kanzi.BitStreamException;
import kanzi.entropy.HuffmanTree.Node;
import kanzi.util.sort.DefaultArrayComparator;
import kanzi.util.sort.QuickSort;


public class HuffmanEncoder extends AbstractEncoder
{
    private static final int DEFAULT_CHUNK_SIZE = 1 << 16; // 64 KB by default
    
    private final OutputBitStream bitstream;
    private final int[] buffer;
    private final int[] codes;
    private final int[] sizes; // Cache for speed purpose
    private final int chunkSize;


    public HuffmanEncoder(OutputBitStream bitstream) throws BitStreamException
    {
       this(bitstream, DEFAULT_CHUNK_SIZE);
    }
    
    
    // The chunk size indicates how many bytes are encoded (per block) before 
    // resetting the frequency stats. 0 means that frequencies calculated at the
    // beginning of the block apply to the whole block.
    // The default chunk size is 65536 bytes.
    public HuffmanEncoder(OutputBitStream bitstream, int chunkSize) throws BitStreamException
    {
        if (bitstream == null)
           throw new NullPointerException("Invalid null bitstream parameter");

        if ((chunkSize != 0) && (chunkSize < 1024))
           throw new IllegalArgumentException("The chunk size must be a least 1024");

        if (chunkSize > 1<<30)
           throw new IllegalArgumentException("The chunk size must be a least most 2^30");

        this.bitstream = bitstream;
        this.buffer = new int[256]; 
        this.sizes = new int[256];
        this.codes = new int[256];
        this.chunkSize = chunkSize;
        
        // Default frequencies, sizes and codes
        for (int i=0; i<256; i++) 
        {
           this.buffer[i] = 1;
           this.sizes[i] = 8;
           this.codes[i] = i;
        }        
    }

    
    // Rebuild Huffman tree
    public boolean updateFrequencies(int[] frequencies) throws BitStreamException
    {              
        if ((frequencies == null) || (frequencies.length != 256))
           return false;

        // Create tree from frequencies
        createTreeFromFrequencies(frequencies, this.sizes);

        // Create canonical codes
        HuffmanTree.generateCanonicalCodes(this.sizes, this.codes);      
        int prevSize = 1;
        ExpGolombEncoder egenc = new ExpGolombEncoder(this.bitstream, true);
       
        // Transmit code lengths only, frequencies and codes do not matter
        // Unary encode the length difference
        for (int i=0; i<256; i++)
        {
           egenc.encodeByte((byte) (this.sizes[i] - prevSize));
           prevSize = this.sizes[i];
        }
        
        return true;
    }

    
    // Dynamically compute the frequencies for every chunk of data in the block
    @Override
    public int encode(byte[] array, int blkptr, int len)
    {
       if ((array == null) || (blkptr + len > array.length) || (blkptr < 0) || (len < 0))
          return -1;
        
       final int[] frequencies = this.buffer;
       final int end = blkptr + len;
       final int sz = (this.chunkSize == 0) ? len : this.chunkSize;
       int startChunk = blkptr;
       int sizeChunk = (startChunk + sz < end) ? sz : end - startChunk;
       int endChunk = startChunk + sizeChunk;

       while (startChunk < end)
       {
          for (int i=0; i<256; i++)
             frequencies[i] = 0;

          for (int i=startChunk; i<endChunk; i++)
             frequencies[array[i] & 0xFF]++;

          // Rebuild Huffman tree
          this.updateFrequencies(frequencies); 

          for (int i=startChunk; i<endChunk; i++)
          {
             if (this.encodeByte(array[i]) == false)
                return i - blkptr;
          }
                      
          startChunk = endChunk;
          sizeChunk = (startChunk + sz < end) ? sz : end - startChunk;
          endChunk = startChunk + sizeChunk;
       }
       
       return len;
    }

    
    // Frequencies of the data block must have been previously set
    @Override
    public boolean encodeByte(byte val)
    {
       final int idx = val & 0xFF;
       return (this.bitstream.writeBits(this.codes[idx], this.sizes[idx]) == this.sizes[idx]);
    }

           
    private static Node createTreeFromFrequencies(int[] frequencies, int[] sizes_)
    {
       int[] array = new int[256];
       int n = 0;

       for (int i=0; i<256; i++)
       {
          sizes_[i] = 0;
          
          if (frequencies[i] > 0)
             array[n++] = i;
       }
       
       // Sort by frequency
       QuickSort sorter = new QuickSort(new DefaultArrayComparator(frequencies));
       sorter.sort(array, 0, n);

       // Create Huffman tree of (present) symbols
       LinkedList<Node> queue1 = new LinkedList<Node>();
       LinkedList<Node> queue2 = new LinkedList<Node>();
       Node[] nodes = new Node[2];

       for (int i=n-1; i>=0; i--)
       {
          final int val = array[i];
          queue1.addFirst(new Node((byte) val, frequencies[val]));
       }

       while (queue1.size() + queue2.size() > 1)
       {
          // Extract 2 minimum nodes
          for (int i=0; i<2; i++)
          {
             if (queue1.size() == 0)
             {
                nodes[i] = queue2.removeFirst();
                continue;
             }

             if (queue2.size() == 0)
             {
                nodes[i] = queue1.removeFirst();
                continue;
             }

             if (queue1.getFirst().weight <= queue2.getFirst().weight)
                nodes[i] = queue1.removeFirst();
             else
                nodes[i] = queue2.removeFirst();
          }

          // Merge minimum nodes and enqueue result
          final Node left = nodes[0];
          final Node right = nodes[1];
          final Node merged = new Node(left.weight + right.weight, left, right);
          queue2.addLast(merged);
       }

       final Node rootNode = ((queue1.isEmpty()) ? queue2.removeFirst() : queue1.removeFirst());
       fillTree(rootNode, 0, sizes_);
       return rootNode;
    }


    // Fill sizes
    private static void fillTree(Node node, int depth, int[] sizes_)
    {
       if ((node.left == null) && (node.right == null))
       {
          sizes_[node.symbol & 0xFF] = depth;
          return;
       }

       if (node.left != null)
          fillTree(node.left, depth + 1, sizes_);

       if (node.right != null)
          fillTree(node.right, depth + 1, sizes_);
    }
    

    @Override
    public void dispose()
    {
       this.bitstream.flush();
    }


    @Override
    public OutputBitStream getBitStream()
    {
       return this.bitstream;
    }
}
