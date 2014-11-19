package jason.algorithm.sort;

import static jason.algorithm.Swaper.swap;

import java.util.Date;
import java.util.Random;

public class QuickSort {
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
}
