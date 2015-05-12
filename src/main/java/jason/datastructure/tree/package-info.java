/**
 * This package contains the different implementation of self-balanced tree.
 * Self-balanced tree is used to implement associative array.
 * The standard implementation is AVL Tree, and Red-Black Tree.  Generally speaking, RedBlack Tree is the best one.
 * 
 * AVL Tree is more rigidly balanced than Red balck tree, leading to slower insertion and deletion, and faster retrieval. 
 * It leads faster retrieval since it could has lower height than redblack tree.
 * 
 * Treap and SkipList are randomized treelike structure. Treap is Binary search Tree with a Priority. Treap is 
 * also heap-ordered by priority. The tree reshape during insertion is easier to implement than Red-Black Tree. 
 * 
 * 	Randomized tree-like structure has a amortized O(logN) performance. For a particular operation, the performance could
 *  be O(N). Worst-case can be avoided in Strict BST like Red Black tree and AVL, but not in randomized tree structure
 *  
 *  SkipList performs much better than Red Black Tree in concurrent environment. That is why we do not have ConcurrentTreeMap, but 
 *  a ConcurrentSkipListMap. (We DO have ConcurrentHashMap)
 * 
 * 
 * There are others BST like AA Tree, Scapegoat Tree. 
 * 
 * @see {http://stackoverflow.com/questions/7467079/difference-between-avl-trees-and-splay-trees }
 * Another interesting tree is Splay Tree which shifts node up upon each access. This puts the most frequently-accessed node 
 * on the top of tree so the lookup is fast. Several implication: 1) Tree shape is modified in search operation. It is bad for concurrent 
 * access, 2) More work is done at lookup, 3) Bad if there is no lookup pattern. 
 * 
 * 
 * @author jason.zhang
 *
 */
package jason.datastructure.tree;