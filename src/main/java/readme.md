+ recursion
+ combinatoric
+ fractional Knapsack, selection
+ CountSortApp, Bucket sort example: radix sorting

+ Suffix Tree, Suffix Array
+ Trie
+ Segment Tree

+ data structure
  + bit: least significant bit index&-index
  + segment tree: tree size calculation, recursion with segment size
  + disjoint set
+ use IdentityHashMap for array. Think about ChordIntersect
+ Eluer cycle/Path for directed graph: has to be Strongly connected component first. Then in degree equals to that out degree.
  + print directed tour: mark **visited edge using map** instead of vertex.
+ Euler undirected: even number of node with odd degree. All other node has even degree.

Grid walking can't use grapH algorithm effectively since the path is not predefined. 
DAG: longest path or shortest path for negative weight

+ array scan: maximum subarray sum, maximum sub array product. How to produce new results ending at current index 
  from result ending with previous index.

+  maximum flow/minumum cut problem. Need to be able to solve the special case
   + Edge is used once(disjoint): Edge has capacity one
   + vertex is used once(disjoint): vertex has capacity one.
   + bipartite matching: capacity is one. 
     +  Each maximum flow paths earch is a DFS. For DFS, we need to keep a colors[]  to record which vertex is visited
     + BFS simplified algorithm. Queue for BFS
     + use to solve **assignment** problem. (tasks to agent, people to job position)
     + vertex cover: minimal set of vertices covering all edge
       + Z all vertices reached from source through alternating path 
       + Minial cover (L exclude Z) union (Z intersect with R)
       + good application: https://tryalgo.org/en/matching/2016/08/05/konig
+ DP
  + **OptimalBinaryTree** the array is sorted. **Huffman Code**?
  + **Traveling salesman problem**: all possible permutation : use bitset to representation a set of permutation.
  + **Knapsack 0/1 problem**: all possible combination.
    + for both Traveling salesmane, and knapsack, we need an efficient way to represent a subset. 
  + **Knapsack with repeat**: combination with repeat.
+ Greedy
  + Need more practice on huffman code
  + TaskSchedule1: each unit task has a deadline, penalty. We want to schedule tasks to minimize the penalty
    + **Two conflicting requirements**. We need to satisfy one of them first. Then using priority queue/stack/queue
      to adjust in each turn to satisfy the other requirement.
      + You can only satisfy one requirement first. Then second one.
      + Disjoint solution: like offline Extract. Occupy a position first.
  + TaskSchedule: Each task has (start, end, weight). We'd like to maximize the weight. (DP, or longest DAG)
  
+ Interval: Each interval has four kinds of overlap intervals. 
  + If we search a set of dynamic intervals for overlapping. We need to keep a BST tree using start, and a BST for end. 
    we also need to consider equals endpoints
+ quick select: linear time. select start, end, pivotal
  + partition walk condition: start<=end. Final condition, start >=end.
  + swap pivotal to start. Start becomes middle. Middel has definite order with left and right. 
  + avoid infinite loop when recurses
+ Recursion
  + Build BST from sorted linked list. Clever using recursion and iterator.
  
+ **Tree Builder**
  + build **balanced** BST from sorted array. Build a tree from an array fragment recursively.
  + **NOTE** Build **balacned** BST from sorted linked list. Very similarly to sorted array. But using iterator and length as a guidance.
  + build tree from string format a(b, c). Recursion, continue children, end children tree.
  + **huffman code**.Not a BST tree. Only leaf node has value. Very special algorithm. 
  + **Optimal Binary Search Tree**. Don't need to be balanced. Different from huffman code. Internal node have value.  
    Two conflict condition: Satify one condition first. DP solution.
+  Practice
  + LongestRectangularAreaInHistogram. 
    + use stack. Late element decides the final answert for element in the stack, but not itself.
    + how to handle equal height.
  + rain water, candy problem: **ascending/descending fragment. Scan from both directions**
    + 2D rainwater.

  
  
 

