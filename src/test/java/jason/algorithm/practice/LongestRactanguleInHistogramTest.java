package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class LongestRactanguleInHistogramTest {
	
	@Test
	public void test(){
		
		int[] input={1,2,3,4,5,6};
		//bar 4 with 3 four area
		assertEquals(12, LongestRactanguleAreaInHistogram.find(input));
		
		int[] input1={2,3,2,4};
		//2*4=8
		assertEquals(8, LongestRactanguleAreaInHistogram.find(input1));
		
		int[] input2={1,3,2,4};
		//2*3=6
		assertEquals(6, LongestRactanguleAreaInHistogram.find(input2));
		
		int[] input3={2,3,4, 1,1,4,1};
		//1*7=7
		assertEquals(7, LongestRactanguleAreaInHistogram.find(input3));
		
		int[] input4={2,2,1,1,1};
		//1*7=7
		assertEquals(5, LongestRactanguleAreaInHistogram.find(input4));
	}
	@Test
	public void test1(){
		
		int[] input={1,2,3,4,5,6};
		//bar 4 with 3 four area
		assertEquals(12, LongestRactanguleAreaInHistogram.largestRectangleArea(input));
		
		int[] input1={2,3,2,4};
		//2*4=8
		assertEquals(8, LongestRactanguleAreaInHistogram.largestRectangleArea(input1));
		
		int[] input2={1,3,2,4};
		//2*3=6
		assertEquals(6, LongestRactanguleAreaInHistogram.largestRectangleArea(input2));
		
		int[] input3={2,3,4, 1,1,4,1};
		//1*7=7
		assertEquals(7, LongestRactanguleAreaInHistogram.largestRectangleArea(input3));
		int[] input4={2,2,1,1,1};
		//1*7=7
		assertEquals(5, LongestRactanguleAreaInHistogram.largestRectangleArea(input4));
	}
	
	
	

}
