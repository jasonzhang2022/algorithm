Give a size
how to create a segment tree with proper size. a lot of bit manipulation.

input size: ***size***

  position of most significant bit: ***msb***= Math.floor(Math.log(size)/Math.log(2))
  
  All values are stored at leaf nodes.
  if (1<<msb < size) {
    //we have a full tree. we don't need to expand leaf node.
    msb +=1
  }
  
  1<<msb(2^msb) will be the number of leaf nodes. 
  
  The internal node will be ***number of leaf node -1***
  
  This is a complete tree.
  
  leafNodes = 1<<msb
  internalNodes = (1<<msb) -1
  tree = new int[leadNodes + internalnodes] 
  
  The tree can accommodate leafNodes of value although only size of them are needed. 
  
  
