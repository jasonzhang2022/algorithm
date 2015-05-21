package jason.algorithm.combinatoric;

import java.util.function.Consumer;

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
		
		//all element with nOffset
		result[resultOffset]=nOffset;
		subCombination(n, k-1, result, resultOffset+1, nOffset+1, consumer);
		//result[resultOffset]=-1;
		
		//all element withouyt nOffset
		subCombination(n, k, result, resultOffset, nOffset+1, consumer);
		
		
	}
	

}
