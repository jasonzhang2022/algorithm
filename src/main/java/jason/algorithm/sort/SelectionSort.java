package jason.algorithm.sort;

import jason.algorithm.Swaper;

public class SelectionSort {

	public static int[] sort(int[] input) {
		
		for (int i=0; i<input.length; i++){
			int minIndex=findMin(input, i);
			if (minIndex!=i){
				Swaper.swap(input, i, minIndex);
			}
		}
		return input;
	}
	
	/**
	 * Find the minimal value from start to end.
	 * @param input
	 * @param start
	 * @return the index of minimal value
	 */
	public static int findMin(int[] input, int start){
		int minIndex=start;
		int minValue=input[start];
		for (int i=start+1; i<input.length; i++){
			if (input[i]<minValue){
				minIndex=i;
				minValue=input[i];
			}
		}
		return minIndex;
	}
}
