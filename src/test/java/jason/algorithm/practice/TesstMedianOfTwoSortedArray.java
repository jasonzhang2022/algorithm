package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesstMedianOfTwoSortedArray {

	@Test
	public void TestMedian(){
		int[] A={0, 1,2,3, 4,5};
		int[] B={6,7,8,9,10};
		
		assertTrue(MedianOfTwoSortedArray.median(A, B)==5.0d);
		
		
		int[] C={0, 1,2,3, 4, 5};
		int[] D={6,7,8,9,10,11};
		
		double median=MedianOfTwoSortedArray.median(C, D);
		double expected=(6+5)*0.5;
		assertTrue(median==expected);
	}
}
