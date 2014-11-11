package jason.datastructure.segmenttree;

public class MinimumProcessor implements ChildProcessor {

	@Override
	public int process(int left, int right) {
		return Math.min(left, right);
	}

	@Override
	public int outRangeValue() {
		return Integer.MAX_VALUE;
	}
	

}
