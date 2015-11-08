package jason.algorithm.select;

import static org.junit.Assert.assertEquals;
import jason.algorithm.Shuffler;
import jason.algorithm.sort.QuickSort;

import java.util.Date;
import java.util.Random;

import org.junit.Test;


/*
 * http://www.programcreek.com/2014/02/leetcode-majority-element-java/
 * 
 * 
 Given an array of size n, find the majority element. The majority element is the element that
  appears more than ⌊ n/2 ⌋ times.
You may assume that the array is non-empty and the majority element always exist in the array.
This is the quick select problem. 
We are looking for the n/2 th element.


 */

public class QuickSelect {
	
	
	public static int select(int[] input, int k) {
		Random random=new Random(new Date().getTime());
		
		//first element is 1th.
		return select(input, 0, input.length-1, k-1, random);
		
	}

	/**
	 * Select the kth elements for subarray from index start to end. The input array is altered in the process. 
	 * kth element is moved to kth position.
	 * @param input
	 * @param start
	 * @param end inclusive
	 * @param k the index of kth element. First element is 0th 
	 * @return
	 */
	public static int select(int[] input, int startIndex, int endIndex, int k, Random random) {
		int pivotalPosition=QuickSort.partition(input, startIndex, endIndex, random);	
		if (k==pivotalPosition) {
			return pivotalPosition;
		}
		if (k<pivotalPosition) {
			//we should go to left partiton.
			return select(input, startIndex, pivotalPosition-1, k, random);
		} else {
			//we should go to right partition
			//left +pivotal has (pivotalPosition+1-start) elements
			return select(input, pivotalPosition+1, endIndex, k, random);
		}
		
		
		
	}
	
	@Test
	public void test() {
		//1,2,3,4,5,6
		int[] input={2,5,7,1,3,4,6};
		int index=QuickSelect.select(input, 4);
		assertEquals(input[index], 4);
	}
	
	@Test
	public void testSelect() {
		int[] input=new int[100];
		for (int i=0; i<input.length; i++) {
			input[i]=i+1;
		}
		
		//check 100 times.
		for (int i=0; i<100; i++) {
			Shuffler.shuffle(input);
			int index=QuickSelect.select(input, 50);
			assertEquals(input[index], 50);
			assertEquals(index, 49);
			
			
			index=QuickSelect.select(input, 30);
			assertEquals(index, 29);
			assertEquals(input[index], 30);
			
		}
	}
	
	
}
