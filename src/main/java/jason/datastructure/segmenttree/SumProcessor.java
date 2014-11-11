package jason.datastructure.segmenttree;

public class SumProcessor implements ChildProcessor {

	@Override
	public int process(int left, int right) {
		return left+right;
	}

	@Override
	public int outRangeValue() {
		return 0;
	}

}
