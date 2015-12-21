#Bellman-Ford algorithm
Relax each edge |V| -1 times. Systematic solution. Works for negative edges: O(VE)

#Dijkstra algorithm
More controlled. Only work for edge with non-negative edge. Better performance O(VlogV+E) for sparse graph


#Brute force all pair shorttest path
Use the idea from Bellman-Ford algorithm. Have a shortest path with only one edge, extends the path by adding one more edge until we have |V|-1 edge.
Using adjacent matrix, we have O(V^4)
Using adjacent list, we have O(V^2E)

#Floyd-Warshall algorithm (work for negative cycle)
For all vertices, build a path through intermediate vertex from 1 to K.
 for K=1, .... N;

Diagonal weight is negative if there is negative cycle
 
#Johnson's algorhtm
+ add extra S to detect negative cycle first using Bellman-Ford.
+ If no negative cycle, Reweight edge, Apply Dijkstra n times.

+ All pair shortest path solution approach can be used to solve 
	all-pair, whole graph property like transitive closure

	

