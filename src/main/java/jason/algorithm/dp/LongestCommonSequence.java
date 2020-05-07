package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.IntStream;

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
		int row =left.length+1;
		int col = right.length+1;
		int[][] dp = new int[row][col];

		for (int r=1; r<row; r++){
			char lc = left[r-1];
			for (int c=1; c<col; c++){
				char rc=right[c-1];

				if (lc==rc){
					// extend the last character.
					dp[r][c] = dp[r-1][c-1]+1;
				} else {
					//discard both end character
					int max1 = dp[r-1][c-1];
					//discard last character at left. But keep character right.
					int max2 = dp[r-1][c];

					//discard last character at right. but keep last charatcer in left.
					int max3 = dp[r][c-1];

					dp[r][c] = IntStream.of(max1, max2, max3).max().getAsInt();
				}


			}
		}

		return dp[row-1][col-1];


	}

	public static class TestIterative {
		@Test
		public void testNonRecursion() {
			String a = "ABCBDAB";
			String b = "BDCABA";

			int expected = 4;


			assertEquals(expected, longestCommonSequenceIterative(a.toCharArray(), b.toCharArray()));
		}
	}
	
}
