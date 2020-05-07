#review

+ adjacent matrix: can find in-degree quickly. Can check whether an edge exist or not in constant time. Compact bitset representation if edge is not weighted.
+ adjacent list: ** good for sparse graph**. Can loop out degree quickly. 
In reality, most graph are sparse graph.  Adjacent list can be augmented to provide the benefit of adjacent matrix. 
  + Maintain a indgree list beside out degree list
  + Maintain a map X_Y -> edge.
   
   Using an adjacency matrix representation of directed graph, 
   find if a universal sink exist(a node with in-degree of n-1 and outdegree of 0). 
   If it exists you must also be able to tell the node which is the sink
   
+ **universal Sink**
+ BSF for shortest path of unweighted graph
+ bipartite graph coloring BSF

**Review the MinimumHeightTree ** question.
This question is very similar to one of topological sorting algorithm. It uses **set** instead of List as vertices. Using set, it is faster to find whether a vertex is connected or not than list. It is also easier to remove an edge.

#DFS
dfs is a very useful tool **exploring graph topology**. For example, connectivity (connected component, biconnected, bridge, articulation point) and reachablility  are explored using DFS.

During DFS, edge can be classified as for **directed graph**
+ tree edge: edge followed by DFS
+ backward edge: this edge will form a loop together with tree edge.
+ forward edge: an alternative path for tree edge
+ cross edge: all other edges

For **undirected graph**
there are only two types of edges:
+ tree edges
+ backward edge

The official DFS in introduction of algorithm color the node into 3 colors: 
+ white: not touched yet.
+ grey: just discovered,  its descendants are not fully processed, but its descedant is in process to be processed.
+ black: its descendants and itself are fully traversed.
We can decide the edge type by vertices color.

In real java implementation of DFS, we only use two colors: true(black)/false(white). If the node is black and in queue, it is like grey. If the node is black and not in queue, it is real black.

##traversal properties
+ Color: white, grey, black
+ discover time, low, start, finish
+ parent

#Topological Sorting
DFS is used to do this. The idea is adding descendant to list first, then add the parent to the beginning of the list.

#Strongly connected components.
[reference](http://www.geeksforgeeks.org/strongly-connected-components)
This is another application of DFS. We separated graph into strongly connected component graph
DFS traversal follows the topological sorting order of SCC.
In DFS, the start time is marked when node is first visited. Its finish time is marked when node is touched during **back track**. So the parent always has a bigger finish time than descendants. Root has the biggest finish time in a forest.
We can add **the node to stack during back track**. By this way, the nodes are sorted by finish time. The root is always the top node in the stack.
During second DFS, we actually start with the same set of nodes as the first DFS. But the edge cross SCC is reversed. We are not be able to reach other SCC anymore
Moreover during second DFS, we visit from the reverse order SCC in G(T: transpose). So the SCC in the beginning of SCC topological sorting are removed first.


#SCC using low and disc (Tarjan's algorithm)
[flow and explanation](http://www.geeksforgeeks.org/tarjan-algorithm-find-strongly-connected-components/) .
Articulation point is allowed for SCC. Articulation point is a concept for undirected graph while SCC is a concept for directed graph

** A directed cycle is strongly connected and every nontrivial strongly connected component contains at least one directed cycle**

#review
+ Reachability: Transpose first, then DFS, page 623, 22-4
+ Euler Tour: cycle covering all edges. Pre-requirement, algorithm to print one. for directed and undirected graph
+ connectivity:  For undirected graph
  + Articulation point: if there is no articulation point, it means we have an alternative way from one vertex to another one.
  + relationship between bridge and Articulation Point. The two vertices of a bridge are articulation point. The converse is not true.
  + biconnected component for undirected graph.  Any two points should be in a simple cycle. There could be more edges than necessary edges required for simple cycle. One vertex can belong to more than one BCC(biconnected component).  Once we found an articulation point, we have a BCC. Bridge is BCC itself. We print all edges when we print BCC.
  
#How can we know an edge is used during traverse in Euler path
If we use ajacentMatrix, each time one edge is used we decrease edge count.
If we use adjcentList, we keep a map using u->v as key. The value is count. Each time, the edge is used, we decrease count. 
  
#cycle
+ cycle detection: DFS or Union find (undirected graph)
+ Hamiltonian cycle: a cycle including **all vertices**
+ Euler Tour, a path including **all edges**
+ Traveling salesman problem. Assume complete graph, find the shortest cycle
+ Bitonic euclidean traveling salesman problem

Euler Circuit Exercise: 
Given an array of strings, find if the given strings can be chained to form a circle. A string X can be put before another string Y in circle if the last character of X is same as first character of Y.
http://www.geeksforgeeks.org/given-array-strings-find-strings-can-chained-form-circle/




#connectivity
+ Articulation point, Vertex cut  
+ Bridge, edge cut, **k-edge connected(undirected graph)**, transitive closure, Strongly connected component.
+ give a graph: is there a path from u to v, from any u to any v? **If u to v is connected, how many distinct path from u to v?**
+ bipartite minimum vertex cover, path cover

How can we find **edge-disjoint path or vertex-disjoint path**.
	Disjoint means no edge or vertex are shared among different path
	The trick is givinbg edge or vextex a capacity of 1, running maximum flow algorithm. Once the capacity is consumed, the edge or vertex can not be resued again. Since we give a capacity of 1, the edge or vertex can only be used one.

**[Transitive closure](http://www.geeksforgeeks.org/transitive-closure-of-a-graph/)**
Given a directed graph, find out if a vertex j is reachable from another vertex i for all vertex pairs (i, j) in the given graph. Here reachable mean that there is a path from vertex i to j. The reach-ability matrix is called transitive closure of a graph.

#TODO
+ 22.3-13: directed graph is singly connected
For each vertext U: run a **new** DFS, If there is a forward edge. It is not singled connected. please also refere [polytree](https://en.wikipedia.org/wiki/Polytree)





 

