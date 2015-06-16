package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//http://www.programcreek.com/2013/02/leetcode-maximum-subarray-java/
/*
 * we maintain two stat:
 * Maximum sub array so far, 
 * Maximum sub array ends with last elements.
 */
public class MaximumSubArray {

	public static int maximumSubarray(int[] input){
		if (input==null || input.length==0){
			return 0;
		}
	
		int maxSofar=input[0];
		int maxEndWithLastElement=input[0];
		
		for (int i=1; i<input.length; i++){
			//maintain maximum ends with last elements.
			//There are two cases:
			//case 1: only last elements.
			//case 2: the last one and previous end maximum
			maxEndWithLastElement=Math.max(maxEndWithLastElement+input[i], input[i]);
			if (maxEndWithLastElement>maxSofar){
				maxSofar=maxEndWithLastElement;
			}
		}
		
		return maxSofar;
	}
	
	@Test
	public void test(){
		
		assertEquals(6, maximumSubarray(new int[]{-2, 1,-3,4,-1,2,1,-5,4}));
		
		assertEquals(3, maximumSubarray(new int[]{3, -2, 1}));
		
		assertEquals(4, maximumSubarray(new int[]{3, -2, 1, 1,1}));

	}
	
	
}
