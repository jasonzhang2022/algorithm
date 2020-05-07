+ antiparallel pattern: add vertex
+ vertex capacity: split vertex: see the Escape problem
+ parallel path: add vextex
+ super source for multiple sources
+ **edge connectivity: each edge has a capacity of 1. No edge can be reused, Every path is distinct.**
  
  **This is important. Assign a capacity of 1 to make sure no edge is reused **
  

# review
+ 26.2-13: https://www.coursehero.com/file/p1k8ft3/2-each-edge-has-a-capacity-of-1-and-uv-E-implies-vu-6-E-it-follows-that-VE-has/
+ relabel-to-front


#maximum flow, minimum cut
+ flow: f(S->T)-f(T->S)
+ cut: C(S->T) . There is no **C(T->S)** component
+ The maximmum flow and mimimum cut theorem
  + three equivalent statements (each is the necessary and sufficient condition of the other).
  + f is the maximum flow
  + there is no augmenting path
  + |f|=C(S->T) for come cut (S,T) of the G. This means  1) **all edges in this cut from S to T are saturated**. 2) **all edges in this cut from T to S are not used at all (flow is zero)**. 
+ f be a flow in flow network G => net flow cross any S, T is |f|
+ f is bounded from above by the capacity of any cut in G (S, T)

# Bipartile graph
[check whether graph is bipartite or not](https://www.geeksforgeeks.org/bipartite-graph/)

#bipartite matching
+ [reference](https://lucatrevisan.wordpress.com/2011/02/23/cs261-lecture14-algorithms-in-bipartite-graphs/)
+ push relabel implementation?
  

#proof Koning theorem
**Koning theorem**: 	For bipartite graph, the cardinality of maximum matching equals to the minimum of vertex cover

Proof

 First we know that maximum matching is equivalently maximum flow. Given a graph **G=(V, E)**. Divide V into **L (left)** and **R (right vertex)**.  Add two vertices s(source) and t(target), run a maximum flow algorithm,  obtain the residual graph.  After this we obtain a matching with cardinality |M|=|Lm|=|Rm|. 
 
 + M: matching edges, 
 + Lm: incident vertices at left side. 
 + Rm: incident vertices are right side.  
 
 All vertices incident on matching edges are **matching vertices**. Other vertices are **free vertices**.

## There is no cover that is less than |M|.
If there is one, some edges in M can't be covered. If we could find a cover with |M| vertices, it is minimal.
 
 We define two subgraph:
 
 + S:  All vertices and the edges in residual graph reachable from s
 + T: the remaining vertices and their edges. 
 
## subgraph S
 
 Let **L1**(left vertices) and **R1**(right vertices) denote the vertices reachable from s in residual graph. It should be clear that any free vertex at left side are reachable from s and is included in L1.  Divide L1 in two sets: **L11**( free vertices) and **L12**( matching vertices).  For matched vertices L12, all its matched vertices at right side should be in R1 since L12 are reached from right from residual graph(not from s since that edge is not in residual graph). There is no free vertex at right side in R1. If there is one free vertex at R1, we can have one additional augment path. This contradicts that matching is maximal.  **So R1 is the set of matched vertices for L12**. In another word: R1 is a subset of Rm.  **All edges in this subgraph S are covered by R1**.
 
##subgraph T

Let **L2**(L-L1) and **R2**(R-R1) denote the remaining vertices at left side and right side.  All vertices in L2 should be in matching set since All free vertices(L11) are included in L1.  All its matching vertices at right side are included in R2. Similarly we divide R2 into two sets : **R21**(free vertices), and **R22**(matching vertices). **All edges in this subgraph T are covered by L2**.
 
##cross edges
   Here we divide matching edges into two separates sets: L12<->R1, L2<->R22. There are no cross matching edges: L12<->R2, L2<->R1 
 
All cross edges are non matching edges. 
Possible cross edges:
+ L2->R1
+ L1->R2
Since the graph is bipartite graph, there is no edge between L1 and L2 or R1 and R2.

**Case L2 -> R1 **
Possible.  These edges are covered by L2.

**Case L1 -> R2 **
Not possible since we could include those R vertices into L1 in first place.

##Conclusion
All edges are covered by R1 and L2.
+ |M|=|R1|+|R22|=|L12|+|L2|
+ |R1|=|L12|, |R22|=|L2|
+ **|M|=|R1|+|L2|**: The number of cover vertices equals cardinality of  maximum matching. The cover is minimum.


**Cross edges and cut property**
It should be clear from the above analysis that the cut between S and T are the mini cut. The cut consists of the edges: S->L2, and R1->T. Edge L2->R1 are possible. They are by definition are not part of the capacity of the  cut. This is clear that cut is defined as a property of directed graph. Edges in the reverse direction is not considered as part of the cut. Cut S->L2, and R1->T are indeed disconnect the graph. No vertex in S can reaches T after the cut  Vertices in T can still reaches the those in S through L2->R1.



#Minimum Path cover for DAG
This can be solved like this.
For original graph G, create a new graph G' with 
+ vertex: Split each vertex u into two vertex u<sub>l</sub>(u at left) and u<sub>r</sub>(u at right). 
+ edge :If  there is one edge u->v, add one edge ul->vr. 

Suppose there is one path u1->u2...->uk in the minimum path cover.
This path will be mapped to u1<sub>l</sub>->u2<sub>r</sub>, u2<sub>l</sub>->u3<sub>r</sub>, ..., ->uk<sub>r</sub>. This is k-1 matching.
Suppose we have p paths in the minimum path cover. Mapping all x paths to matching, give us |V|-p matching. 
Similarly we can convert matching to path cover so that |M|=|V|-p. The more |M| is, the less p is. When |M| reaches maximum, p reaches minimum, we have minimum cover.    



TODO
+ problem 26-3 algorithm consulting page 761
http://yinyanghu.github.io/files/clrs_prev.pdf




 
 
 