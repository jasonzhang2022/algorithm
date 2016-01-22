To search an overlapping interval from a given set

+ If we query these intervals once, we can just loop the intervals one by one
+ If we query these intervals multiple times, we can pre-process the intervals.
  + If these intervals are fixed, we can sort them and use binary search to find the interval.
	Sorting takes O(NlgN) time. Binary search takes logN time. 
	The benefit of this approach is that we can find all overlapping interval very quickly.
  + If these intervals are dynamic, we can use interval tree. 
	Construct interval tree takes NlogN time.
	Search/**insertion/deletion** takes logN time.


Application of interval tree

How do you handle interval data.
	One way is handle interval as a single data structure. 
		InsertInterval sorts interval using start as key
		InternalTree constructs tree using start as key
	Another way to separate an internal into two points so that we can effectively scan them. 
		VoiceMixer is such an example. VoiceMixer is very similar to VSDL database(rectange overlap) questions.
		Both scans the input from left to right. Both decides a **set of live intervals**. The set of live interval is searched to decide the output
		