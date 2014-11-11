package jason.datastructure.segmenttree;

//Full binary tree can be represented by an array.
public class SegmentTree {
	/*
	 *use an array to represent the segment tree.
	 *For a node at index i, the left child is 2*i+1, the right child is 2*i+2, the parent is floor((i-1)/2) 
	 *leave node contains the elements from input array. All internal node contains the summary information
	 *
	 *How can we know the internal a node represents for.
	 *We do not: we use the same logic in the building tree process so that we calculate the internal when we walk down the 
	 *tree.
	 *
	 */
	int[] tree;
	ChildProcessor processor;
	int inputLen=0;
	
	
	public SegmentTree(int[] input, ChildProcessor processor) {
		inputLen=input.length;
		//We need to allocate enough memory for the tree.
		//suppose we have n node, the Tree height will be h=Ceil(log2(n)).
		//A full tree will have 2^h+2^-1 nodes.
		//But some array element will has empty value.
		int h=(int) Math.ceil(Math.log((double)input.length)/Math.log(2.0d));
		tree=new int[ (1<<(2*h))-1 ];
		buildSegmentTree(input, 0, input.length-1, tree, 0, processor);
		this.processor=processor;
	}
	
	public int queryRange(int start, int end) {
		return queryRange(tree, 0, 0, inputLen-1, start, end);
	}
	
	/**
	 * Complexity: We are effectively walk down the edge of the range. So we have logN.
	 * @param tree
	 * @param nodeIndex
	 * @param segmentStart
	 * @param segmentEnd
	 * @param queryStart
	 * @param queryEnd
	 * @return
	 */
	protected int queryRange(int[] tree, int nodeIndex, int segmentStart, int segmentEnd, int queryStart, int queryEnd) {
		if (segmentEnd<queryStart) {
			//out our range
			return processor.outRangeValue();
		}
		
		if (segmentStart>queryEnd) {
			return processor.outRangeValue();
		}
		
		//we have overlap.
		//segment in our range.
		if (segmentStart>=queryStart && segmentEnd<=queryEnd) {
			return tree[nodeIndex];
		}
		
		int leftValue=queryRange(tree, nodeIndex*2+1, segmentStart, (segmentStart+segmentEnd)/2, queryStart, queryEnd);
		int rightValue=queryRange(tree, nodeIndex*2+2, (segmentStart+segmentEnd)/2+1, segmentEnd, queryStart, queryEnd);
		
		return processor.process(leftValue, rightValue);
		
	}
	
	
	
	
	/**
	 * Build a tree for segments from start to end inclusive.  Store the value at tree[nodeIndex]
	 * @param input
	 * @param start
	 * @param end
	 * @param tree
	 * @param nodeIndex
	 * @param processor
	 * @return processed value for the whole tree
	 * 
	 * Complexity: We walk down the tree once: Which is logarithmic.
	 */
	protected int buildSegmentTree(int[] input, int start, int end, int[] tree, int nodeIndex, ChildProcessor processor) {
		
		if (start==end) {
			//we are build segments for a node.
			tree[nodeIndex]=input[start];
			return input[start];
		}
		
		int leftTreeValue=buildSegmentTree(input, start, (start+end)/2, tree, nodeIndex*2+1, processor);
		int rightTreeValue=buildSegmentTree(input, (start+end)/2+1, end, tree, nodeIndex*2+2, processor);
		int nodeValue=processor.process(leftTreeValue, rightTreeValue);
		tree[nodeIndex]=nodeValue;
		return nodeValue;
	}
}
