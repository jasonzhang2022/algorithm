#what is a sorted structure?
+LinkedList
+ArrayList
+**BST** Don't forget this. Comparing with array, it is dynamic. It is a compromise between linkedList and ArrayList. It also provides a map function. So it can be viewed as a compromise among linkedList, ArrayList and Map. It can access/modify sorted structure in logN time. Moreover, it can be augmented to provide summary information about a tree segment.


#Problem
Suppose we have N numbers. The value is between 0 and k.
We will answer these questions repeatedly.
	give two integers a and b. How many numbers in N are between a and b.
You can preprocess the N numbers.

+ if the range of 0 and k is small, see the CourtSortApp. 
  + preprocess time is linear
  + query time is O(1)
+ If the range is not small, use BST.
  + preprocess time is NlogN
  + query time is logN

#review  
+ Binary search 
+ Count sort: assumption range is small 
+ CourtSort App
+ Bucket sort. assumption. value is uniformly distribute 
