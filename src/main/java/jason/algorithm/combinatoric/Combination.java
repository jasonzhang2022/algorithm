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
		if (n-nOffset==k){
			//we only have K elements, add call them and return;
			for (int i=0; i<k; i++){
				result[resultOffset+i]=nOffset+i;
			}
			consumer.accept(result);
			return;
			//clean up; not needed
			//Arrays.fill(result,  resultOffset, resultOffset+k, -1);
		}
		
		//combination with element at nOffset
		result[resultOffset]=nOffset;
		subCombination(n, k-1, result, resultOffset+1, nOffset+1, consumer);
		//result[resultOffset]=-1;
		
		//combination without element at nOffset
		subCombination(n, k, result, resultOffset, nOffset+1, consumer);
		
		
	}
	
	public static class Count {
		int count;
	}
	
	@Test
	public void TestCombination(){
		String input="ABCDEFGH";
		int n=input.length();
		int k=3;
		
		int expectedCombination=n*(n-1)*(n-2)/3/2;
		Count c=new Count();
		
		Consumer<int[]> consumer= a->{
			for (int index: a){
				System.out.print(input.charAt(index));
			}
			System.out.print("\n");
			c.count++;
		};
		
		Combination.combination(n, k, consumer);
		assertEquals(expectedCombination, c.count);
	}

}
