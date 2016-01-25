#FractionalKnapsackLinear
Two approaches
1. greedy. we pick the the element with most value ratio first. **Heap** problem. All elements are picked in sorted order.
1. partition. Better. The picked elements are not even in sorted order. We partition them basing on segments property. 

#WeightedMedian.
We can partition element.
1. quick sort: partition based on the number elements on each side.
1. weighted median: partition based on the total sum of each segment.
 