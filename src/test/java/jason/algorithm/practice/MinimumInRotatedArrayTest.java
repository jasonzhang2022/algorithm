package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class MinimumInRotatedArrayTest {

	@Test
	public void test() {
		int[] input={4,5,6,7,1,2,3};
		assertEquals(1, MinimumInRotatedArray.find(input) );
		
		int[] input1={10,11,12,13,14,15,16,17,3,4,5,6,7,8};
		assertEquals(3, MinimumInRotatedArray.find(input1) );
	}

}
