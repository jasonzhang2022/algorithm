package jason.algorithm.sort;

import static jason.algorithm.Swaper.swap;

public class InsertionSort {

	public static int[] sort(int[] input) {
		sort(input, 0, input.length-1);
		return input;
	}
	public static void sort(int[] input, int startIndex, int endIndex) {
		for (int i=startIndex+1; i<=endIndex; i++) {
			insertOneElement(input, startIndex, i);
		}
	}
	
	
	/**
	 * Assume that input[startIndex] to input[endIndex] are sorted.
	 * @param input
	 * @param startIndex
	 * @param endIndex
	 * @param value
	 * @return index the value should be placed.
	 */
	public static int binarySearch(int[] input, int startIndex, int endIndex, int value) {
		int left=startIndex;
		int right=endIndex;
		
		
		
		while (left!=right) {
			int middle=(left+right)/2;
			//if right-left=1, we could have a infinite loop.
			if (right-left==1) {
				if (input[right]<=value) {
					return right+1;
				} else if (input[left]>value) {
					return left;
				} else {
					return left+1;
				}
			}
			
			
			if (input[middle]>value) {
				right=middle;
			} else {
				left=middle;
			}
		}
		
		if (input[left]<=value) {
			return left+1;
		}
		return left;
	}
	
	
	public static void insertOneElement(int[] input, int startIndex, int currentIndex) {
		
		int insertIndex=binarySearch(input, startIndex, currentIndex-1, input[currentIndex]);
		
		while (currentIndex>insertIndex) {
			swap(input, currentIndex, currentIndex-1);
			currentIndex--;
		}
	}
	
}
