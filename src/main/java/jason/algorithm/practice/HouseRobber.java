package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class HouseRobber {

	public static int maxValue(int[] input) {

		if (input.length == 0) {
			return 0;
		}
		if (input.length == 1) {
			return input[0];
		}
		if (input.length == 2) {
			return Math.max(input[0], input[1]);
		}

		// maxValues[i] is the max values a robber can get when input ends at
		// index i;
		int[] maxValues = new int[input.length];
		maxValues[0] = input[0];
		maxValues[1] = Math.max(input[0], input[1]);

		for (int index = 2; index < input.length; index++) {
			// for this particular index.
			// 1. this index is included in final selection
			int case1 = maxValues[index - 2] + input[index];

			// 2. this index is not included in final selection.
			int case2 = maxValues[index - 1];
			maxValues[index] = Math.max(case1, case2);

		}
		System.out.println(Arrays.toString(maxValues));
		return maxValues[input.length - 1];
	}

	@Test
	public void test() {

		assertEquals(3, maxValue(new int[] { 3 }));
		assertEquals(4, maxValue(new int[] { 3, 4 }));
		assertEquals(8, maxValue(new int[] { 3, 4, 5 }));
		assertEquals(10, maxValue(new int[] { 3, 4, 5, 6 }));

		assertEquals(29, maxValue(new int[] { 20, 5, 6, 9 }));
	}

	public static int maxValue1(int[] input) {

		if (input.length == 0) {
			return 0;
		}
		if (input.length == 1) {
			return input[0];
		}
		if (input.length == 2) {
			return Math.max(input[0], input[1]);
		}

		int p2 = input[0];
		int p1 = Math.max(input[0], input[1]);

		for (int index = 2; index < input.length; index++) {
			// for this particular index.
			// 1. this index is included in final selection
			int case1 = p2 + input[index];

			// 2. this index is not included in final selection.
			int case2 = input[index - 1];

			p2 = p1;
			p1 = Math.max(case1, case2);
		}

		return p1;
	}

	@Test
	public void test1() {

		assertEquals(3, maxValue1(new int[] { 3 }));
		assertEquals(4, maxValue1(new int[] { 3, 4 }));
		assertEquals(8, maxValue1(new int[] { 3, 4, 5 }));
		assertEquals(10, maxValue1(new int[] { 3, 4, 5, 6 }));

		assertEquals(29, maxValue1(new int[] { 20, 5, 6, 9 }));
	}
}
