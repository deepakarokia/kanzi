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

package function

import (
	"errors"
	"fmt"
	"kanzi/transform"
)

// Utility class to compress/decompress a data block
// Fast reversible block coder/decoder based on a pipeline of transformations:
// Forward: Burrows-Wheeler -> Move to Front -> Zero Length
// Inverse: Zero Length -> Move to Front -> Burrows-Wheeler
// The block size determines the balance between speed and compression ratio

// Stream format: Header (m bytes) Data (n bytes)
// Header: mode (8 bits) + BWT primary index (8, 16 or 24 bits)
// mode: bit 7 is unused for now
//       bits 6-4 (contains the size in bits of the primary index - 1) / 4
//       bits 3-0 4 highest bits of primary index
// primary index: remaining bits (up to 3 bytes)       
//
// EG: Mode=0bx.100.xxxx primary index is (4+1)*4=20 bits long
//     Mode=0bx.000.xxxx primary index is (0+1)*4=4 bits long

const (
	MAX_BLOCK_HEADER_SIZE = 3
	MAX_BLOCK_SIZE        = 16*1024*1024 - MAX_BLOCK_HEADER_SIZE // 16 MB (24 bits)
)

type BlockCodec struct {
	buffer []byte
	mtft   *transform.MTFT
	bwt    *transform.BWT
	size   uint
}

func NewBlockCodec(size uint) (*BlockCodec, error) {
	if size > MAX_BLOCK_SIZE {
		errMsg := fmt.Sprintf("The block size must be at most %d\n", MAX_BLOCK_SIZE)
		return nil, errors.New(errMsg)
	}

	var err error
	this := new(BlockCodec)
	this.bwt, err = transform.NewBWT(0)

	if err != nil {
		return nil, err
	}

	this.mtft, err = transform.NewMTFT(0)

	if err != nil {
		return nil, err
	}

	this.size = size
	this.buffer = make([]byte, size)
	return this, nil
}

func (this *BlockCodec) Size() uint {
	return this.size
}

func (this *BlockCodec) SetSize(sz uint) bool {
	if sz > MAX_BLOCK_SIZE {
		return false
	}

	this.size = sz
	return true
}

func (this *BlockCodec) Forward(src, dst []byte) (uint, uint, error) {
	if src == nil {
		return 0, 0, errors.New("Input buffer cannot be null")
	}

	if dst == nil {
		return 0, 0, errors.New("Output buffer cannot be null")
	}

	if &src == &dst {
		return 0, 0, errors.New("Input and output buffers cannot be equal")
	}

	blockSize := this.size

	if this.size == 0 {
		blockSize = uint(len(src))
	}

	if blockSize > MAX_BLOCK_SIZE {
		errMsg := fmt.Sprintf("Block size is %v, max value is %v", blockSize, MAX_BLOCK_SIZE)
		return 0, 0, errors.New(errMsg)
	}

	if blockSize > uint(len(src)) {
		errMsg := fmt.Sprintf("Block size is %v, input buffer length is %v", blockSize, len(src))
		return 0, 0, errors.New(errMsg)
	}

	if blockSize > uint(len(this.buffer)) {
		this.buffer = make([]byte, blockSize)
	}

	// Use a buffer to preserve input data
	copy(this.buffer, src[0:blockSize])

	// Apply Burrows-Wheeler Transform
	this.bwt.SetSize(blockSize)
	this.bwt.Forward(this.buffer)
	primaryIndex := this.bwt.PrimaryIndex()

	// Apply Move-To-Front Transform
	this.mtft.SetSize(blockSize)
	this.mtft.Forward(this.buffer)

	pIndexSizeBits := uint(4)

	for 1<<pIndexSizeBits <= primaryIndex {
		pIndexSizeBits += 4
	}

	headerSizeBytes := (4 + pIndexSizeBits + 7) >> 3
	zlt, err := NewZLT(blockSize)

	if err != nil {
		return 0, 0, err
	}

	// Apply Zero Length Encoding 
	iIdx, oIdx, err := zlt.Forward(this.buffer, dst[headerSizeBytes:])

	if err != nil {
		return 0, 0, err
	}
	
	oIdx += headerSizeBytes

	if (oIdx > uint(len(dst))) || (oIdx > blockSize) {
		return 0, 0, errors.New("ZLT failed: output buffer too small")
	}

	if iIdx < blockSize {
		return 0, 0, errors.New("ZLT failed: input buffer too small")
	}

	// Write block header (mode + primary index)
	// 'mode' contains size of primaryIndex in bits (bits 6 to 4)
	// the size is divided by 4 and decreased by one
	mode := byte(((pIndexSizeBits >> 2) - 1) << 4)
	shift := pIndexSizeBits

	if (shift & 7) == 4 {
		shift -= 4
		mode |= byte((primaryIndex >> shift) & 0x0F)
	}

	dst[0] = mode

	for i := uint(1); i < headerSizeBytes; i++ {
		shift -= 8
		dst[i] = byte((primaryIndex >> shift) & 0xFF)
	}

	return iIdx, oIdx, nil
}

func (this *BlockCodec) Inverse(src, dst []byte) (uint, uint, error) {
	compressedLength := this.size

	if compressedLength == 0 {
		return 0, 0, nil
	}

	// Read block header (mode + primary index)
	// 'mode' contains size of primaryIndex in bits (bits 6 to 4)
	// the size is divided by 4 and decreased by one
	mode := src[0]
	pIndexSizeBits := uint(((mode&0x70)>>4)+1) << 2
	headerSizeBytes := (4 + pIndexSizeBits + 7) >> 3
	compressedLength -= headerSizeBytes
	shift := pIndexSizeBits
	primaryIndex := uint(0)

	if (shift & 7) == 4 {
		shift -= 4
		primaryIndex |= uint(mode & 0x0F) << shift
	}	

	// Extract BWT primary index
	for i := uint(1); i < headerSizeBytes; i++ {
		shift -= 8
		primaryIndex |= uint(src[i]) << shift
	}

	// Apply Zero Length Decoding 
	zlt, err := NewZLT(compressedLength)

	if err != nil {
		return 0, 0, err
	}

	iIdx, oIdx, err := zlt.Inverse(src[headerSizeBytes:], dst)   
   iIdx += headerSizeBytes
   
	if err != nil {
		return iIdx, oIdx, err
	}

	// If buffer is too small, return error 
	if iIdx < compressedLength {
		errMsg := fmt.Sprintf("ZLT failed: input buffer length: %d, required: %d", iIdx, compressedLength)
		return iIdx, oIdx, errors.New(errMsg)
	}

	blockSize := oIdx
	
	if blockSize > uint(len(dst)) {
		errMsg := fmt.Sprintf("ZLT failed: output buffer length: %d, required: %d", len(dst), blockSize)
		return iIdx, oIdx, errors.New(errMsg)
	}

	// Apply Move-To-Front Inverse Transform
	this.mtft.SetSize(blockSize)
	this.mtft.Inverse(dst)

	// Apply Burrows-Wheeler Inverse Transform
	this.bwt.SetPrimaryIndex(primaryIndex)
	this.bwt.SetSize(blockSize)
	this.bwt.Inverse(dst)

	return iIdx, oIdx, nil
}
