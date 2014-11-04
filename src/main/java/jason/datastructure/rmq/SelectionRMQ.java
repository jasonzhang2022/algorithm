package jason.datastructure.rmq;

public class SelectionRMQ implements RMQ {

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

	/**
	 * The minimal value from input[i] to input[j] inclusive.
	 * i can be less than j, i!=j
	 * @param i 
	 * @param j
	 * @return
	 */
	public int maximum(int i, int j) {
		int start=i;
		int end=j;
		if (i>j) {
			start=j;
			end=i;
		}
		int max=Integer.MIN_VALUE;
		for (int k=start; k<=end && k<input.length; k++) {
			if (input[k]>max) {
				max=input[k];
			}
		}
		return max;
	}
}
