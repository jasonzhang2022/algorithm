package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

//http://www.programcreek.com/2013/01/leetcode-distinct-subsequences-total-java/
/*
 * Given a string S and a string T, count the number of distinct subsequences of T in S.

A subsequence of a string is a new string which is formed from the original 
string by deleting some (can be none) of the characters without disturbing 
the relative positions of the remaining characters. (ie, "ACE" is a subsequence of
 "ABCDE" while "AEC" is not).

Here is an example:
S = "rabbbit", T = "rabbit"

Return 3.
 */


/*
 * We are going to use dynamic programming

Base case: 
Suppose S has length:0, ...n-1
Suppose t has length:0, ...m-1
We have a DP[t][s] matrix where 0<=0<m, 0<=s<n
where t=0; 
	for each character 
		S[s]=T[0], DP[0][s]=DP[0][s-1]+1;
		S[s]!=T[0], DP[0[s]=DP[0][s-1]
We grow t one by one
	for t>0,
		if S[s]!=T[t], DP[t][s]=DP[t][s-1]; 
			If the current character does not match the new character in t,
			the exists subsequence for T[0...t] in S[0...S] will be that T[0...t] in S[0...S-1] 
			since character S[s] does not contribute a new subsequence.
		if S[s]=T[t], DP[t][s]=DP[t-1][s-1]
			For any existing subsequence T[0...t-1] in S[0...s-1], there is a new subsequence
			T[0...t] in S[0...S]
 * 
 */
public class HowManySubsequence {

	
	public static int subsequence(String S, String T){
		int n=S.length();
		int m=T.length();
		int[][] DP=new int[m+1][n+1];

		// there is one way: delete all character.
		Arrays.fill(DP[0], 1);

		for (int t=1; t<=m; t++) {

			char tc = T.charAt(t-1);
			for (int s=1; s<=n; s++) {
				char ts = S.charAt(s-1);

				int num = 0;
				// keep ts so that it  matches with tc
				if (tc==ts){
					num = DP[t-1][s-1];
				}

				// delete ts. tc matches with one character before/
				num += DP[t][s-1];

				DP[t][s]= num;
			}
		}
		return DP[m][n];
	}
	
	
	@Test
	public void test(){
		
		assertEquals(3, subsequence("rabbbit", "rabbit"));
		
		assertEquals(4, subsequence("raara", "ra"));
	}

}
