#Analysis
knapsack problem is basically a combination problem.
Suppose the target weight is W, and we have N items. We establish a NXW matrix M.

## one flow: we include item one by one
M[i, j] represents the maximal value for target weight j considering only item 0 to i.
We right now consider item i+1.

For each target weight w (from 0 to W-1), **how many item i+1 can we have?**
  1. we can have zero
  2. we can have 1. The weight of this item is less than or equal to W.
  3. we can have 2 ... k **if reuse is allowed**. The total weight for these copies are less than or equal to W.
  Suppose for each cases, suppose the total weight from copies of i<sup>th</sup element is x.
  We pick maximal value of M[i, w-x] plus value from copies of  i<sup>th</sup element as the value for M[i+1, w]

See how similar this solution is to combination problem. To solve this kind of problem, we need to be pretty familar to combination routine.

##alternative flow: we increase weight one pound by one pound.
W[j] represents the maximal value for target weight j. 
Items[j] records what the last item included here.
Let use consider weight j+1,
For each item, we have two cases

+ yes  if its weight is less than j+1
+ no.

Totally we have 2N cases.
We uses the maximal value for the 2N case as W[j+1];
We set items[j+1] to item index which gives the maximal value.

[This reference](http://www.mathcs.emory.edu/~cheung/Courses/323/Syllabus/DynProg/knapsack1.html) introduces the concept of constrained optimization problem.

