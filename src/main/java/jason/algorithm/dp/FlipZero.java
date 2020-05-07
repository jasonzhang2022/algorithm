package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

//http://www.geeksforgeeks.org/find-zeroes-to-be-flipped-so-that-number-of-consecutive-1s-is-maximized/
// slide window mechanism
public class FlipZero {

	public static int findCount(int[] input, int n){

		int fromZeroIndex = -1;

		int tempCount =0;
		int lastZeroIndex =-1;
		while (tempCount <n+1 && lastZeroIndex <input.length){
			lastZeroIndex = findNextZero(input, lastZeroIndex+1);
			tempCount++;
		}

		int maxLen = lastZeroIndex -fromZeroIndex -1;
		while (lastZeroIndex<input.length){
			fromZeroIndex = findNextZero(input, fromZeroIndex+1);
			lastZeroIndex = findNextZero(input, lastZeroIndex+1);
			maxLen = Math.max(maxLen, lastZeroIndex - fromZeroIndex-1);
		}
		return maxLen;
	}

	private static int findNextZero(int[] input, int from){

		while (from<input.length && input[from]!=0){
			from++;
		}
		return from;
	}

	public static class TestCase {
		@Test
		public void testCount() {
			assertEquals(4, findCount(new int[]{0, 0, 0, 1}, 4));
			assertEquals(5, findCount(new int[]{1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1}, 1));
			assertEquals(8, findCount(new int[]{1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1}, 2));
		}

		@Test
		public void testEdge() {
			assertEquals(1, findCount(new int[]{0, 0, 0, 1}, 0));

			assertEquals(4, findCount(new int[]{0, 1, 0, 1}, 10));
		}
	}
}
