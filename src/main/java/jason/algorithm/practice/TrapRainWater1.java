package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//http://www.programcreek.com/2014/06/leetcode-trapping-rain-water-java/
public class TrapRainWater1 {
	
	
	
	public static int trapWater(int[] input){
		
		if (input==null || input.length<=2){
			return 0;
		}
		
		//leftMax[i]: for index i, the peak element on left.
		int[] leftMax=new int[input.length];
		int[] rightMax=new int[input.length];
		
		int len=input.length;
		
		
		int max=input[0];
		leftMax[0]=max;
		for (int i=1; i<len; i++){
			if (input[i]>max){
				max=input[i];
			}
			leftMax[i]=max;
		}
		
		max=input[len-1];
		rightMax[len-1]=max;
		for (int i=len-2; i>=0; i--){
			if (input[i]>max){
				max=input[i];
			}
			rightMax[i]=max;
		}
		
		int total=0;
		for (int i=0; i<len; i++){
			total+=Math.min(leftMax[i], rightMax[i])-input[i];
		}
		
		return total;
	}
	
	@Test
	public void test(){
		assertEquals(0, trapWater(new int[]{}));
		assertEquals(0, trapWater(new int[]{1}));
		assertEquals(0, trapWater(new int[]{1,2,3}));
		assertEquals(0, trapWater(new int[]{1,2,3,4,4,3,2,1}));
		assertEquals(1, trapWater(new int[]{3,1, 2}));
		assertEquals(1, trapWater(new int[]{3,3,1,2}));
		assertEquals(6, trapWater(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
		
	}
}
