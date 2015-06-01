package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class MinimumPathSumTest {

	@Test
	public void test() {
		int[][] matrix={
				{0,	100,	1,	1,	1},
				{1,	1,		1, 100,	1},
				{100,100,	100,100, 1}, 
				{100, 100, 100, 100, 1},
				{100, 100, 100, 100, 3}
		};
		
		assertEquals(12, MinimumPathSum.minimumPathSum(matrix));
	}
	
	@Test
	public void testWeb() {
		int[][] matrix={
				{0,	100,	1,	1,	1},
				{1,	1,		1, 100,	1},
				{100,100,	100,100, 1}, 
				{100, 100, 100, 100, 1},
				{100, 100, 100, 100, 3}
		};
		
		assertEquals(12, MinimumPathSum.minPathSum(matrix));
	}

}
