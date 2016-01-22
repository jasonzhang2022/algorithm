package jason.algorithm.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jason.algorithm.util.Shuffler;
import jason.algorithm.util.Swaper;

public class SelectionSort extends TestSetup{

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
	
	
	

	@Test
	public void testSelectionSort() {
		inputLen=100;
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
		int[] output=SelectionSort.sort(input);
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}
}
