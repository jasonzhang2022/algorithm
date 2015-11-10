+ multiway tree representation: left child an dright sibling representation
+ select: weighted median. Different logic to decide which partition to select
+ count sort, assumption :small number in range
+ Binary Search
+ Bucket sort assumption: uniform distribution
+ BInary Search Tree Node 
+ BST iterative traversal: all stack and queue operations have this characterstic. Get one from stack/queue, process it and push more elements to stack or queue
 We get one element from stack, then we expand the stack by processing the element.
	 * We can pop it since we don't need it anymore.
	 * If we can't pop it (we need it later), we need to push further element and process the element at a later time

+ radix tree

VSDL database problem


Put a list of items into a sorted order. Then select one or more items with some characterstics, e.g minimal item, maximal  item, kth item, item with min gap. 	Sorting the items will take NlogN items. It is worth of sorting if we repeatedly querying items. If we only query the item once, we could use select algorithm if possible. There are three ways to store sorted list: linked list, array list, balanced BST(red black tree).

+ linked list is not a good choice since we are not able to do binary search efficient.
+ Array List is good since we can do binary search
+ Balanced BST is best since 1) we can do binary search. 2) we can add/remove items efficiently. This is something that arraylist can't provide. 3) we can augment the data structure. After that we can binary search the tree using the agument property like Rank, min gap, interval tree. This is something array list can't provide. The only down side of BST is it is not as memory efficient as array list.
+ heap. heap is good for minimum and maximum query and it supports dynamic insertion/deletion. Heap is implemented as array. It is more efficient than arraylist for this query
 
 
 
 



[Augmented RB tree](https://github.com/gzc/CLRS/blob/master/C14-Augmenting-Data-Structures/14.3.md)

[Augmented RB tree](http://ripcrixalis.blog.com/2011/02/08/clrs-14-3-interval-trees/)

#TODO

1 java collection
