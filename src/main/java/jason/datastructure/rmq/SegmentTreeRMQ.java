package jason.datastructure.rmq;

import jason.datastructure.segmenttree.MinimumProcessor;
import jason.datastructure.segmenttree.SegmentTree;

public class SegmentTreeRMQ implements RMQ {

	SegmentTree segmentTree;
	@Override
	public int minimum(int i, int j) {
		return segmentTree.queryRange(i, j);
	}

	@Override
	public void init(int[] inpput) {
		segmentTree=new SegmentTree(inpput, new MinimumProcessor());
		
	}

}
