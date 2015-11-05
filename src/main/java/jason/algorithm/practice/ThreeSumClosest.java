package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

//http://www.programcreek.com/2013/02/leetcode-3sum-closest-java/
public class ThreeSumClosest {
	
	
	public static int closest(int[] input, int target){
		Arrays.sort(input); //ONlogN
		
		/*
		 * After sorting
		 * Suppose the sum=input[i-2]+input[i-1]+input[i]
		 * and sum<target
		 * 
		 * item i-2, i-1, i are the three biggest item for input[0...i]
		 * 
		 * So OtherThreeSum < sum < target.
		 * This sum is the one closest to target for three sum of input[0...i]
		 * 
		 *  So we will search the i which is the closest to target using binary search algorithm.
		 */
		
		
		int index=searchIndex(input, 0, input.length-3, target);
		if (index==0){
			return input[0]+input[1]+input[2];
		}
		if (index>input.length-3){
				int length=input.length;
				return input[length-3]+input[length-2]+input[length-1];
		}
		
		
		//in the middle and index valye less tnan targetValue
		int largeSum=input[index]+input[index+1]+input[index+2];
		int lessSum=input[index-1]+input[index]+input[index+1];
		int lessDiff=Math.abs(lessSum-target);
		int largeDiff=Math.abs(largeSum-target);
		if (lessDiff<largeDiff){
			return lessSum;
		} else{
			return largeSum;
		}
	}
	
	
	private static int searchIndex(int[] input, int start, int end, int target){
		
		if (end<=start) {
			int startValue=input[start]+input[start+1]+input[start+2];
			if (startValue<target){
				return start+1;
			}  else{
				return start;
			}
		}
		
		
		
		int middle=start+ (end-start)/2;
		
		int middleValue=input[middle]+input[middle+1]+input[middle+2];
		if (middleValue==target){
			return middle;
		}
		if (middleValue<target){
			return searchIndex(input, middle+1, end, target);
		} else{
			return searchIndex(input, start, middle-1, target);
		}
		
	}
	
	
	@Test
	public void test(){
		assertEquals(12, closest(new int[]{1,2,3,4,5,6,7,8,9}, 11));
		assertEquals(9, closest(new int[]{1,2,3,4,5,6,7,8,9}, 10));
		
		assertEquals(6, closest(new int[]{1,2,3,4,5,6,7,8,9}, 5));
		
		assertEquals(24, closest(new int[]{1,2,3,4,5,6,7,8,9}, 27));
		
		//equal case
		assertEquals(9, closest(new int[]{1,2,3,4,5,6,7,8,9}, 9));
		
		assertEquals(6, closest(new int[]{1,2,3,4,5,6,7,8,9}, 6));
		assertEquals(24, closest(new int[]{1,2,3,4,5,6,7,8,9}, 24));
	}

}
