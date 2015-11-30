package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
//http://www.geeksforgeeks.org/dynamic-programming-set-24-optimal-binary-search-tree/
public class OptimalBinaryTree {
	/**
	 * We separate dummyFreq from freq because we want dummyFreq to be leave nodes all the time.
	 * @param freq
	 * @param dummyFreq
	 * @return
	 */
	public static  double optimalTree(double[] freq, double[] dummyFreq){
		double[][] cost=new double[freq.length][freq.length];
		for(int i=0; i<freq.length; i++){
			Arrays.fill(cost[i], -1);
		}
		return optimalTreeSub(freq, dummyFreq, 0, freq.length-1, cost);
	}
	
	public static double optimalTreeSub(double[] freq, double[] dummyFreq, int i, int j, double[][] cost){
		if (j==i-1){
			return dummyFreq[i];
		}
		if (i==j+1){
			return dummyFreq[j+1];
		}
		
		if (cost[i][j]!=-1){
			return cost[i][j];
		}
		
		double min=Double.MAX_VALUE;
		
		for (int k=i; k<=j; k++){
			double left=optimalTreeSub(freq, dummyFreq, i, k-1, cost);
			double right=optimalTreeSub(freq, dummyFreq, k+1, j, cost);
			
			min=Math.min(left+right,  min);
		}
		for (int k=i; k<=j; k++){
			min+=freq[k];
			min+=dummyFreq[k];
		}
		min+=dummyFreq[j+1];
		cost[i][j]=min;
		return min;
	}
	
	
	@Test
	public void testRecursion(){
		double[] freq={0.15, 0.10, 0.05, 0.10, 0.20};
		double[] dummyFreq={0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
	
		double expected=2.75;
		
		assertTrue(expected==optimalTree(freq, dummyFreq));
	}
	
	
	//---------------------------------------------norecursive solution
	public static  double optimalTreeNoRecursive(double[] freq, double[] dummyFreq){
		int n=freq.length;
		int costLen=n+2;
		double[][] cost=new double[costLen][costLen];
		for(int i=0; i<costLen; i++){
			Arrays.fill(cost[i], -1);
		}
		
		for (int i=1; i<costLen; i++){
			cost[i][i-1]=dummyFreq[i-1];  //prepresent node dummyFreq[i-1]
		}
		
		for (int len=0; len<n; len++){
			for (int start=1; start<=n-len; start++){
				int end=start+len; 
				
				double min=Double.MAX_VALUE;
				for (int k=start; k<=end; k++){
					double left=cost[start][k-1];
					double right=cost[k+1][end];
					min=Math.min(left+right, min);
				}
				for (int k=start; k<=end; k++){
					min+=freq[k-1];
					min+=dummyFreq[k-1];
				}
				min+=dummyFreq[end];
				cost[start][end]=min;
				
			}
		}
		
		return cost[1][n];
	}
	

	@Test
	public void testNonRecursion(){
		double[] freq={0.15, 0.10, 0.05, 0.10, 0.20};
		double[] dummyFreq={0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
	
		double expected=2.75;
		
		assertTrue(expected==optimalTreeNoRecursive(freq, dummyFreq));
	}

}
