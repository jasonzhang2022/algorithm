package jason.algorithm.sort;

import static jason.algorithm.Swaper.swap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.Shuffler;

import org.junit.Test;

public class InsertionSort extends TestSetup {

	public static int[] sort(int[] input) {
		sort(input, 0, input.length-1);
		return input;
	}
	public static void sort(int[] input, int startIndex, int endIndex) {
		for (int i=startIndex+1; i<=endIndex; i++) {
			insertOneElement(input, startIndex, i);
		}
	}
	
	public static void insertOneElement(int[] input, int startIndex, int currentIndex) {
		
		int insertIndex=BinarySearch.binarySearch(input, startIndex, currentIndex-1, input[currentIndex]);
		
		while (currentIndex>insertIndex) {
			swap(input, currentIndex, currentIndex-1);
			currentIndex--;
		}
	}
	
	@Test
	public void testInsertionSort() {
		inputLen=100;
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
		int[] output=InsertionSort.sort(input);
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}
	
}
