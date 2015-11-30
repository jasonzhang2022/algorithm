package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
//http://www.geeksforgeeks.org/dynamic-programming-set-4-longest-common-subsequence/
public class LongestCommonSequence {
	public static int longestCommonSequence(char[] left, char[] right ){
		int[][] lcs=new int[left.length+1][right.length+1];
		//lcs[i][j] represent the lcs for left[i+1] and right[j+1];
		for (int i=0; i<left.length+1; i++){
			Arrays.fill(lcs[i], -1);
		}
		
		return longestCommonSequence(left, right, left.length, right.length, lcs);
	}
	
	
	public static int longestCommonSequence(char[] left, char[] right, int leftEndIndex, int rightEndIndex, int[][] lcs){
		if (leftEndIndex==0 || rightEndIndex==0){
			return 0;
		}
		
		if (lcs[leftEndIndex][rightEndIndex]!=-1){
			return lcs[leftEndIndex][rightEndIndex];
		}
		
		int length=Integer.MIN_VALUE;
		if (left[leftEndIndex-1]==right[rightEndIndex-1]){
			length=Math.max(length, longestCommonSequence(left, right, leftEndIndex-1, rightEndIndex-1, lcs)+1);
		} else {
			int temp1=Math.max(length, longestCommonSequence(left, right, leftEndIndex, rightEndIndex-1, lcs));
			int temp2=Math.max(length, longestCommonSequence(left, right, leftEndIndex-1, rightEndIndex, lcs));
			length=Math.max(length, temp1);
			length=Math.max(length, temp2);
		}
		lcs[leftEndIndex][rightEndIndex]=length;
		return length;
	}
	
	@Test
	public void testRecursion(){
		String a="ABCBDAB";
		String b="BDCABA";
		
		int expected=4;
		
		
		assertEquals(expected, longestCommonSequence(a.toCharArray(), b.toCharArray()));
	}
	
	
	//-----------------------bottom up version
	
	public static int longestCommonSequenceIterative(char[] left, char[] right ){
		int n=left.length+1;
		int m=right.length+1;
		int[][] lcs=new int[n][m];
		for (int i=0; i<m; i++){
			lcs[0][i]=0;
		}
		for (int i=0; i<n; i++){
			lcs[i][0]=0;
		}
		
		for (int row=1; row<n; row++){
			for (int col=1; col<m; col++){
				int temp=Integer.MIN_VALUE;
				if (left[row-1]==right[col-1]){
					temp=Math.max(temp, lcs[row-1][col-1]+1);
				} else {
					temp=Math.max(temp, lcs[row-1][col]);
					temp=Math.max(temp, lcs[row][col-1]);
				}
				lcs[row][col]=temp;
			}
		}
		return lcs[n-1][m-1];
		
	}
	
	@Test
	public void testNonRecursion(){
		String a="ABCBDAB";
		String b="BDCABA";
		
		int expected=4;
		
		
		assertEquals(expected, longestCommonSequenceIterative(a.toCharArray(), b.toCharArray()));
	}
	
}
