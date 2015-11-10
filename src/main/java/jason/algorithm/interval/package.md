To search an overlapping interval from a given set

+ If we query these intervals once, we can just loop the intervals one by one
+ If we query these intervals multiple times, we can pre-process the intervals.
  + If these intervals are fixed, we can sort them and use binary search to find the interval.
	Sorting takes O(NlgN) time. Binary search takes logN time. 
	The benefit of this approach is that we can find all overlapping interval vert quickly.
  + If these intervals are dynamic, we can use interval tree. 
	Construct interval tree takes NlogN time.
	Search/**insertion/deletion** takes logN time.


Application of interval tree
