/**
 * Selection Overview
 * @author jason.zhang
 * Selection by Sorting
 * 		If we have many selection, sort the input in O(nlogn), then select O(1)
 * 		Partial sorting: sort the first Kth element in O(n+klogk), then select O(1)
 * 		Unordered Partial Sorting. O(n), then select O(K) for element smaller than K. O(1) for kth element.
 * 			QuickSelect/Partition Select is the algorithm for unordered partial sorting, and is also partition Select algorithm
 * 			Binary heap can be built in O(n) time.  We can build a heap with K element with O(K) time, then replace the remaing lement
 * 			O( (N-K)logk). After that select can be done in O(k) time.
 *  Selection by partition:
 *  	Quick Select
 *  Sublinear selection
 *  	Without processing, the best run time for selection is O(n). If we want sublinear runtime, we need some data structure to support.
 * 			Sorted array: O(1) time
 *  		Heap: O(1) for min/max element.
 *  		Order statistics tree for BST: logn time.
 *  		Bucket sorting: divide the input into bucket/hash table. Suppose we have sqrt(N) bucket. The select will be sqrt(N) time.
 *  Online selection
 *  	Using binary Heap
 *  
 *  
 *  
 * Partial Sorting can be viewed as a equivalent of selection algorithm:
 * 		Unordered partial Sorting can be solved easily with quickselect(offline) or Binary heap(online/stream)
 * 		Binary heaps lead to an O(n + k log n) solution to partial sorting: partial heapsort. 
 * 			First "heapify", in linear time, the complete input array. Then extract the minimum of the heap k times.[1]
 *  		1)build Heap with N element, then extra K element. The K elements are in sorted order.---Offline mode.
 *  		2)Build Heap with K element, then delete/add element. The final K element in heap is not in any order.
 *  			Solve the stream sorting issue
 *  
 *  		
 *  		Algorithm 1: may be effecient.
 *  
 * 	
 *If a linear algorithm is needed, it is most likely quick selection
 * 
 * 
 */
package jason.algorithm.select;