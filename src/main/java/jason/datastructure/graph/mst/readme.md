#MST Properties
+ Grow MST by selecting light edge of cut.
+ But it assumes 1) It has  a set of edges of A which belongs to  a MST. 2) the cut respects A. So where does the A comes in first place
  + edge of minimum weight is in MST
  + edge of maximum weight in a cycle is not in MST (this is the reason behind Kruskal's algorithm)
  + unique MST if every cut has one unique light edge
  + List edges by weight in sorted order in all MST. The weight order identical. 
  + We have different MSTs because some cut has multiple light edges with the same weights.

#Review: use cycle properties
+ 23.1-11: one edge not in MST is decreased
+ 23.1-5 : cycle property : edge of maximum weight of a cycle can't be MST
+ 23.2-7: update MST by adding new vertex and incidence edges
+ 23.1-10: decrease one edge in MST by K, the MST is still MST with the new weight function
+ 23.1-8: MST in sorted order is the same.
+ 24.6 bitonic shortest path



  