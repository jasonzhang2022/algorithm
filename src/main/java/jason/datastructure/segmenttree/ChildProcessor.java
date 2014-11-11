package jason.datastructure.segmenttree;

public interface ChildProcessor {
	int process(int left, int right);
	
	int outRangeValue();
}