##review
+ multiway tree representation
+ iterative tree traversal: please see jason.algorithm.recursion for analysis
+ VLSL database| Voice Mixer
+ ChordIntersect to familiarize the geometry
+ Josephus permutation can be solved like this
  + Given a red black tree with size of N,  we find **k%N** element (counting from 1) from root, record and delete it. We give the deleted index number **d**. The runtime is logN.  The next element we want to delete is **d+k**=**(d+k)/n-1**. Here we have a recursive formula.
  	The next element we want to delete is **(d+k)/size**. d is the position of last deleted element. size is the new tree size.  The crucial part of the tree order is maintained automatically after we delete one item. So Red black tree is really flexible than LinkedList/ArrayList. **dynamic** maintains the order -> **rb tree**
  	