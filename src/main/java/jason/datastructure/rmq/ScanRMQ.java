package jason.datastructure.rmq;

public class ScanRMQ implements RMQ {

	int[] input;
	
	public void init(int[] input) {
		this.input=input;
	}
	@Override
	public int minimum(int i, int j) {
	
		int start=i;
		int end=j;
		if (i>j) {
			start=j;
			end=i;
		}
		int min=Integer.MAX_VALUE;
		for (int k=start; k<=end && k<input.length; k++) {
			if (input[k]<min) {
				min=input[k];
			}
		}
		return min;
	}

	
}
