#review

+ adjacent matrix: can find in-degree quickly. Can check whether an edge exist or not in constant time. Compact bitset representation if edge is not weighted.
+ adjacent list: ** good for sparse graph**. Can loop out degree quickly. 
In reality, most graph are sparse graph.  Adjacent graph can be augmented to provide the benefit of adjacent matrix. 
  + Maintain a indgree list beside out degree list
  + Maintain a map X_Y -> edge.
  
+ universal Sink
+ BSF for shortest path of unweighted graph
+ bipartite graph coloring BSF


#DFS
dfs is a very useful tool exploring graph topology.

During DFS, edge can be classified as
+ tree edge: edge followed by DFS
+ backward edge: this edge will form a loop together with tree edge.
+ forward edge: an alternative path for tree edge
+ cross edge: all other edges

The official DFS in introduction of algorithm color the node into 3 colors: 
+ white: not touched yet.
+ grey: just discovered,  its descendants are not fully processed, but its descedant is in process to be processed.
+ black: its descendants and itself are fully traversed.
We can decide the edge type by vertices color.

In real java implementation of DFS, we only use two colors: true(black)/false(white). If the node is black and in queue, it is like grey. If the node is black and not in queue, it is real black.

#Topological Sorting
DFS is used to do this. The idea is adding descendant to list first, then add the parent to the beginning of the list.

#Strongly connected components.
[reference](http://www.geeksforgeeks.org/strongly-connected-components)
This is another application of DFS. We separated graph into strongly connected component graph
DFS traversal follows the topological sorting order of SCC.
In DFS, the start time is marked when node is first visited. Its finish time is marked when node is touched during back track. So the parent always has a bigger finish time than descendants. Root has the biggest finish time in a forest.
We can add the node to stack during back track. By this way, the nodes are sorted by finish time. The root is always the top node in the stack.
During second DFS, we actually start with the same set of nodes as the first DFS. But the edge cross SCC is reversed. We are not be able to reach other SCC anymore
Moreover during second DFS, we visit from the reverse order SCC in G(T: transpose). So the SCC in the beginning of SCC topological sorting are removed first.


