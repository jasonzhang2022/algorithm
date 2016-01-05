+ antiparallel pattern: add vertex
+ vertext capacity: split vertex: see the Escape problem
+ parallel path: add vextex
+ super source for multiple sources
+ edge connectivity: each edge has a capacity of 1. No edge can be reused, Every path is distinct.

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


#bipartite matching
+ [reference](https://lucatrevisan.wordpress.com/2011/02/23/cs261-lecture14-algorithms-in-bipartite-graphs/)
+ push relabel implementation?
  

