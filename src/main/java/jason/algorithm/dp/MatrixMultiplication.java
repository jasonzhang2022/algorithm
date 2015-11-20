package jason.algorithm.dp;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class MatrixMultiplication {

	public static int minimalMultiplicaiton(int[] dimensions){
		//matrix is numbered from 0 to n-1.
		//for matrix i, the dimension is dimensions[i]*dimension[i+1]
		int n=dimensions.length-1;
		int[][] cost=new int[n][n];
		
		for (int i=0; i<n; i++){
			cost[i][i]=0;
		}
		//memorize how to build a triangle 
		for (int len=1; len<n; len++){
			for (int start=0; start< n-len; start++){
				//compute cost[start][start[len]
				int endIndex =start+len;
				int mini=Integer.MAX_VALUE;
				for (int split=start; split<endIndex; split++){
					int tempCost=cost[start][split]
							+cost[split+1][endIndex]
								+dimensions[start]*dimensions[split+1]*dimensions[endIndex+1];
					mini=Math.min(tempCost, mini);
				}
				cost[start][endIndex]=mini;
				
			}
		}
		return cost[0][n-1];
	}
	
	
	
	
	public static int minimalMultiplicaitonRecursive(int[] dimensions){
		int n=dimensions.length-1;
		int[][] cost=new int[n][n];
		for(int  i=0; i<n; i++){
			Arrays.fill(cost[i], -1);
		}
		return recursiveMultiplicationSubroutine(dimensions, 0, n-1, cost);
	}
	
	public static int recursiveMultiplicationSubroutine(int[] dimensions, int i, int j, int[][] cost){
		if (i==j){
			return 0;
		}
		if (cost[i][j]!=-1){
			return cost[i][j];
		}
		int mini=Integer.MAX_VALUE;
		for (int split=i; split<j; split++){
			int temp=recursiveMultiplicationSubroutine(dimensions, i, split, cost)
					+recursiveMultiplicationSubroutine(dimensions, split+1, j, cost)
					+dimensions[i]*dimensions[split+1]*dimensions[j+1];
			mini=Math.min(temp, mini);
		}
		cost[i][j]=mini;
		return mini;
	}
	
	
	@Test
	public void testRecursion(){
		int[] dimensions={30, 35, 15,5,10,20, 25};
		int expected=15125;
		assertEquals(expected, minimalMultiplicaitonRecursive(dimensions));
	}
	
	@Test
	public void testBottomUp(){
		int[] dimensions={30, 35, 15,5,10,20, 25};
		int expected=15125;
		assertEquals(expected, minimalMultiplicaiton(dimensions));
	}
	
	

}
