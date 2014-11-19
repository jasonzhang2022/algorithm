package jason.algorithm.select;

import jason.algorithm.sort.QuickSort;

import java.util.Date;
import java.util.Random;

public class QuickSelect {
	
	public static int select(int[] input, int k) {
		Random random=new Random(new Date().getTime());
		return select(input, 0, input.length-1, k, random);
		
	}

	/**
	 * Select the kth elements for subarray from index start to end. The input array is altered in the process. kth element is moved to kth position.
	 * @param input
	 * @param start
	 * @param end inclusive
	 * @param k the index of kth element, k>0. This has to be k-1;
	 * @return
	 */
	public static int select(int[] input, int startIndex, int endIndex, int k, Random random) {
		//the index for kth element will be startIndex+k-1;
		int kthPosition=startIndex+k-1;
		
		int pivotalPosition=QuickSort.partition(input, startIndex, endIndex, random);	
		if (kthPosition==pivotalPosition) {
			return pivotalPosition;
		}
		if (kthPosition<pivotalPosition) {
			//we should go to left partiton.
			return select(input, startIndex, pivotalPosition-1, k, random);
		} else {
			//we should go to right partition
			//left +pivotal has (pivotalPosition+1-start) elements
			return select(input, pivotalPosition+1, endIndex, k-(pivotalPosition+1-startIndex), random);
		}
		
		
		
	}
	
	
	
}
