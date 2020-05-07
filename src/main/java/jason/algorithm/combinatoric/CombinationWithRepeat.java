package jason.algorithm.combinatoric;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.Test;
/**
 * The algorithm is very similar to 0/1 knapsack.
 *  Question: Does the final result have this element? 0/1 case
 * 
 * @author jason
 *
 */
public class CombinationWithRepeat {
	
	/*
	 * Select k elements from n without order
	 */
	public static void combination(int n, int k, Consumer<int[]> consumer){
		int[] results=new int[k];
		subCombination(n, k, results, 0, 0, consumer);
		
	}

	/*
	 * select k element from nOffset
	 */
	public static void subCombination(int n,  int k, int[] result, int resultOffset, int nOffset,Consumer<int[]> consumer){
		if (k==0){
			consumer.accept(result);
			return;
		}
		if (nOffset>=n){
			//invalid
			return;
		}

		//how many times can we repeats noffset
		for(int m=0; m<=k; m++){
			if (m!=0){
				result[resultOffset+m-1] =nOffset;
			}
			subCombination(n, k-m, result, resultOffset+m, nOffset+1, consumer);
		}
	}
	
	public static class Count {
		int count;
	}

	public static class TestCase {
		@Test
		public void TestCombination() {
			String input = "ABCDEFGH";
			int n = input.length();
			int k = 1;

			//formua=C(n+k-1, k)
			int expectedCombination = 8;
			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int index : a) {
					System.out.print(input.charAt(index));
				}
				System.out.print("\n");
				c.count++;
			};

			CombinationWithRepeat.combination(n, k, consumer);
			assertEquals(expectedCombination, c.count);
		}

		@Test
		public void TestCombinationTwo() {
			String input = "ABCDEFGH";
			int n = input.length();
			int k = 2;

			//formua=C(n+k-1, k)
			int expectedCombination = 9 * 8 / 2;
			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int index : a) {
					System.out.print(input.charAt(index));
				}
				System.out.print("\n");
				c.count++;
			};

			CombinationWithRepeat.combination(n, k, consumer);
			assertEquals(expectedCombination, c.count);
		}

		@Test
		public void TestCombinationThree() {
			String input = "ABCDEFGH";
			int n = input.length();
			int k = 3;

			//formua=C(n+k-1, k)
			int expectedCombination = 10 * 9 * 8 / 2 / 3;
			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int index : a) {
					System.out.print(input.charAt(index));
				}
				System.out.print("\n");
				c.count++;
			};

			CombinationWithRepeat.combination(n, k, consumer);
			assertEquals(expectedCombination, c.count);
		}
	}
}
