package jason.datastructure.rmq;

public interface RMQ {
	
	/**
	 * The minimal value from input[i] to input[j] inclusive.
	 * i can be less than j, i!=j
	 * @param i 
	 * @param j
	 * @return
	 */
	public int minimum(int i, int j);
	
	/**
	 * The minimal value from input[i] to input[j] inclusive.
	 * i can be less than j, i!=j
	 * @param i 
	 * @param j
	 * @return
	 */
	public int maximum(int i, int j);
	
	/*
	 * Query will operate on this input
	 */
	public void init(int[] inpput);
}
