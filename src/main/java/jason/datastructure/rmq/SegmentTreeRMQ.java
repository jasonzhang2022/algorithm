package jason.datastructure.rmq;

import jason.datastructure.segmenttree.MinimumProcessor;
import jason.datastructure.segmenttree.SegmentTree;

import java.util.Arrays;

public class SegmentTreeRMQ implements RMQ {

	SegmentTree segmentTree;
	@Override
	public int minimum(int i, int j) {
		return segmentTree.queryRange(i, j);
	}

	@Override
	public void init(int[] inpput) {
		segmentTree=new SegmentTree(inpput.length, new MinimumProcessor());
		Arrays.fill(segmentTree.tree, Integer.MAX_VALUE);
		for (int i=0; i<inpput.length; i++){
			segmentTree.update(i, inpput[i]);
		}
		
	}

}
