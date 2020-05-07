package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
/*
 * Different from http://www.geeksforgeeks.org/dynamic-programming-set-24-optimal-binary-search-tree/
 * 
 * The solution tries to solve Optimal Binary search tree at page 397
 *
 * THE ARRAY is SORTED ARRAY
 * 
 */
public class OptimalBinaryTree {
	/**
	 * We separate dummyFreq from freq because we want dummyFreq to be leave nodes all the time.
	 * @param freq
	 * @param dummyFreq frequent for query falling between existing nodes. These nodes have to be leaf nodes. Why? One can  tell key is not found only when leaf is reached.
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
	
	//assume that we have at least one key.
	public static  double optimalTree1(double[] freq, double[] dummyFreq){
		double[][] cost=new double[freq.length][freq.length];
		for(int i=0; i<freq.length; i++){
			Arrays.fill(cost[i], -1);
		}
		return optimalTreeSub1(freq, dummyFreq, 0, freq.length-1, cost);
	}
	public static double optimalTreeSub1(double[] freq, double[] dummyFreq, int i, int j, double[][] cost){
		if (i==j){
			//if we reach a single node, it has two dummyFreq as leaf nodes[i, i+1]
			return dummyFreq[i]*2+freq[i]+dummyFreq[i+1]*2;
		}
		if (cost[i][j]!=-1){
			return cost[i][j];
		}
		double min=Double.MAX_VALUE;
		for (int k=i; k<=j; k++){
			double oneMin=0;
			
			//left
			if (k==i){
				//First node is root, left can only be dummy node.
				oneMin=dummyFreq[i];
			} else{
				//k>i
				oneMin=optimalTreeSub1(freq, dummyFreq, i, k-1, cost);
			}
			
			//right
			if (k==j){
				//last node is root. Right can only be dummy node.
				oneMin+=dummyFreq[j+1];
			} else{
				//k <j
				oneMin+=optimalTreeSub1(freq, dummyFreq, k+1, j, cost);
			}
			min=Math.min(min, oneMin);
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
	public void testRecursion1(){
		double[] freq={0.15, 0.10, 0.05, 0.10, 0.20};
		double[] dummyFreq={0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
	
		double expected=2.75;
		
		assertTrue(expected==optimalTree1(freq, dummyFreq));
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
