package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class FindLargestRectanguleTest {

	@Test
	public void test() {
		int[][] matrix={
				{0,	0,	1,	1,	0},
				{1,	1,	1,	1,	0},
				{1,	1,	1,	1,	0},
				{0,	1,	1,	0,	0},
				{0,	0,	0,	0,	0},
		};
		
		FindLargestRectangule finder=new FindLargestRectangule();
		assertEquals(8, finder.maximalRectangle(matrix));
		
	}

}
