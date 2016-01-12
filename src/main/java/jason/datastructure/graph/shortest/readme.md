#Bellman-Ford algorithm
Relax each edge |V| -1 times. Systematic solution. Works for negative edges: O(VE)

#Dijkstra algorithm
More controlled. Only work for edge with non-negative edge. Better performance O(VlogV+E) for sparse graph


# shortest path for DAG. 
Topology sorting first.

#Brute force all pair shortest path
Use the idea from Bellman-Ford algorithm. Have a shortest path with only one edge, extends the path by adding one more edge until we have |V|-1 edge.
Using adjacent matrix, we have O(V^4)
Using adjacent list, we have O(V^2E)

#Floyd-Warshall algorithm (work for negative cycle)
For all vertices, build a path through intermediate vertex from 1 to K.
 for K=1, .... N;

**Diagonal weight is negative if there is negative cycle**
 
#Johnson's algorhtm
+ add extra S to detect negative cycle first using Bellman-Ford.
+ If no negative cycle, Reweight edge, Apply Dijkstra n times.

+ All pair shortest path solution approach can be used to solve 
	all-pair, whole graph property like transitive closure

	
#Tricks
+ all sources, single destination: reverse edge direction
+ longest path. negate edge weight.
+ shortest path algorithm not only solves the 'shortest' path problem, but also solves the "reachablility" path problems: whether there is a path, how many pathes, etc.



#Nesting Boxes
Problem 24-2. Page 678.
[ solution reference](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&cad=rja&uact=8&ved=0ahUKEwjqzIjk0KPKAhUQxWMKHZxpAEoQFggjMAE&url=https%3A%2F%2Fpiazza.com%2Fclass_profile%2Fget_resource%2Fhbh2wad62yw1on%2Fhfo8blhcyet3e5&usg=AFQjCNHcFcZpnZbU-DwptFTXV0s4jXqcfw&sig2=KakAcTadGpuhR2zBy94Wvw)
**FOR DAG, we have a linear algorithm for shortest path**
**We are computing longest path using anti-relax rule**. The anti-relax rule doesn't work for positive cycle.

**insight: how do we compute longest path**
+ Negate all edge capacity. 
+ make sure we don't have negative cycle.
+ compute shortest path



#Arbitrage solution
Question: 24-3, page 679
R=R(i1,i2)*R(i2,i3), logR=logR=logR(i1,i2)+logR(i2, r3)
We can convert the rate table to logR table.  The product formula becomes sum formula.  **The key is taking logarithm of the product and convert it to summation**.  Then we want to find a **cycle** with value >0. To convert it to a shortest path problem, we negate the path value. The problem is transformed to a problem of finding a negative cycle.

[link to solution](http://math.stackexchange.com/questions/94414/an-algorithm-for-arbitrage-in-currency-exchange)

[maximum product subarray](http://www.programcreek.com/2014/03/leetcode-maximum-product-subarray-java/) using this approach
Find the contiguous subarray within an array (containing at least one number) which has the largest product.

For an array, a1,a2, a3, ...
we transform it to log(a1),log(a2), ...
The problem is transformed into [maximum subarray](http://www.programcreek.com/2013/02/leetcode-maximum-subarray-java/) question


#Bitonic Shortest Path
**Relax concept**
problem 24-6, page 682.
A sequence is bitonic if it monotonically increases and then monotonically decreases, or if by a circular shift it monotonically increases and then monotonically decreases. For example, the sequences (1, 4, 6, 8, 3, -2), (9, 2, -4, -10, -5), and (1, 2, 3, 4) are bitonic, but (1, 3, 12, 4, 2, 10) is not.

Suppose that we are given a directed graph G=(V,E)G=(V,E) with weight function w:E→Rw:E→R, where all edge weights are unique, and we wish to find single-source shortest paths from a source vertex s. We are given one additional piece of information: for each vertex v∈Vv∈V, the weights of the edges along any shortest path from ss to vv form a bitonic sequence.

**Path-relaxation property**" Edges in shortest path can be relaxed **in order**. If all edges are relaxed, then we obtain the shortest path. 



Given that the shortest is bitonic. The path can be classified into four categories
1. increasing from s.
2. decreasing from s
3. increasing first (I), then deceasing(D)
4. Decreasing first (D), then increasing(I)

Let us do this.

First, relax all edges in increasing order.  
  1. Paths in this category get their shortest path
  2. Paths in this category goes nowhere except the first vertex is relaxed.
  3. I segment are relaxed.
  4. Paths in this category goes nowhere except the first vertex is relaxed.
  
Second, relax all edge in decreasing order
  1. Paths in this category will not change. Once path reaches short path, the value will not change.
  2. Paths in this category gets their shortest path
  3. Segment D are relaxed.   Paths in this category gets their shortest path
  4. Segments D are relaxed. 
  
Third relax all edges in increasing order again.
  1.  Once path reaches short path, the value will not change.
  2.  Once path reaches short path, the value will not change.
  3. Once path reaches short path, the value will not change.
  4. Segment  I are relaxed. Path reaches short path




* all weight is unique.
We can relax all edge in increasing order once, then decreasing order once. All edges in shortest path should be relaxed.


