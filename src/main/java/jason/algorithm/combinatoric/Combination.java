package jason.algorithm.combinatoric;

import static org.junit.Assert.assertEquals;


import java.util.function.Consumer;

import org.junit.Test;
/**
 * The algorithm is very similar to 0/1 knapsack.
 * @author jason
 *
 */
public class Combination {
	
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


		// we skip nOffset
		subCombination(n, k, result, resultOffset, nOffset+1, consumer);

		// we include
		result[resultOffset]=nOffset;
		subCombination(n, k-1, result, resultOffset+1, nOffset+1, consumer);
	}
	
	public static class Count {
		int count;
	}

	public static  class TestCase {
		@Test
		public void TestCombination() {
			String input = "ABCDEFGH";
			int n = input.length();
			int k = 3;

			int expectedCombination = n * (n - 1) * (n - 2) / 3 / 2;
			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int index : a) {
					System.out.print(input.charAt(index));
				}
				System.out.print("\n");
				c.count++;
			};

			Combination.combination(n, k, consumer);
			assertEquals(expectedCombination, c.count);
		}
	}

}
