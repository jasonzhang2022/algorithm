package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinarySearchIndex {
	
	
	/*
	 * If found, return the index;
	 * If not found, return the index if it was in the sequence
	 */
	public static int binarySearch(int[] input, int start,  int end, int target){
		if (end<=start){
			if (target>input[start]){
				return start+1;
			} else {
				return start;
			}
		}
		
		int middle=start+ (end-start)/2;
		if (input[middle]==target){
			return middle;
		} 
		if (input[middle]<target){
			return binarySearch(input, middle+1, end, target);
		}
		return binarySearch(input, start, end-1, target);
	}

	@Test
	public void test(){
		assertEquals(2, binarySearch(new int[]{2,4,6,8,10,12}, 0, 5, 6));
		assertEquals(2, binarySearch(new int[]{2,4,6,8,10,12}, 0, 5, 5));
		assertEquals(0, binarySearch(new int[]{2,4,6,8,10,12}, 0, 5, 2));
		assertEquals(5, binarySearch(new int[]{2,4,6,8,10,12}, 0, 5, 12));
		assertEquals(6, binarySearch(new int[]{2,4,6,8,10,12}, 0, 5, 13));
		assertEquals(0, binarySearch(new int[]{2,4,6,8,10,12}, 0, 5, 1));
	}
}
