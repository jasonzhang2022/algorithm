package jason.algorithm.sort;

import static jason.algorithm.Swaper.swap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.Shuffler;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
/**
 * The idea is this:
 * When do partition, 
 * 1) leave pivot to the end of array. 
 * 2) the array is processed from both end: If one value is smaller than pivot, the front index is increased by one.
 *   If one value is bigger than pivot, swap and the end index is decreased by one. 
 * 3) Then we come to the final stage: front index is equals to end index. 
 *     If the value is less than pivot, swap it with pivot (end of array)
 *     Otherwise swap pivot with the one after this value.
 * 4) We actually divide the array into three fragments: 
 * 		the beginning fragment with all value less than pivot
 *      The pivot itself-----------This is something we forget sometime. This guarantee we always reduce the array by one.
 *      The end fragment with all values larger than or equal to pivot.
 *   
 *  5) we process beginning fragment and end fragment recursively.
 * @author jason
 *
 */
public class QuickSort extends TestSetup{
	public static int[] sort(int[] input) {
		Random random = new Random(new Date().getTime());
		sort(input, 0, input.length - 1, random);
		return input;
	}

	/**
	 * Partition the subarray from startIndex to endIndex(inclusive) into two
	 * section.
	 * 
	 * @param input
	 * @param startIndex
	 * @param endIndex
	 *            inclusive
	 * @param random
	 *            the index of pivotal value;
	 * @return
	 */
	public static int partition(int[] input, int startIndex, int endIndex, Random random) {
		if (endIndex==startIndex) {
			//one element
			return startIndex;
		}
		int pivotalIndex = random.nextInt(endIndex + 1 - startIndex) + startIndex;
		int pivotal = input[pivotalIndex];

		// move pivotal to end to avoid interferenece
		swap(input, pivotalIndex, endIndex);

		int begin = startIndex;
		int end = endIndex - 1;

		while (begin != end) {
			if (input[begin] > pivotal) {
				swap(input, begin, end);
				end--;
			} else {
				begin++;
			}
		}

		// move pivotal back to its position.
		int pivotalPosition;
		if (input[begin] > pivotal) {
			swap(input, begin, endIndex);
			pivotalPosition = begin;
		} else {
			swap(input, begin + 1, endIndex);
			pivotalPosition = begin + 1;
		}
		return pivotalPosition;
	}

	private static void sort(int[] input, int startIndex, int endIndex, Random random) {
		// base case:
		if (startIndex >= endIndex) {
			return;
		}
		if (endIndex - startIndex <= 20) {
			// for short sequenece using insert sort.
			InsertionSort.sort(input, startIndex, endIndex);
			return;
		}
		
		int pivotalPosition=partition(input, startIndex, endIndex, random);

		sort(input, startIndex, pivotalPosition - 1, random);
		sort(input, pivotalPosition + 1, endIndex, random);
	}
	
	

	@Test
	public void testQuickSort() {
		
		for (int j=0; j<20; j++) {
			Shuffler.shuffle(input);
			long start=System.currentTimeMillis();
			int[] output=QuickSort.sort(input);
			long end=System.currentTimeMillis();
			System.out.println("\nQuick Sort time :"+(end-start));
			for (int i=0; i<inputLen; i++) {
				assertThat(output[i], equalTo(i));
			}
		}
		
	}
}
