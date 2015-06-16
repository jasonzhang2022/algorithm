package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//http://www.programcreek.com/2013/12/edit-distance-in-java/
/*
 * In computer science, edit distance is a way of quantifying how dissimilar two strings (e.g., words) are to one another by counting the minimum number of operations required to transform one string into the other.

There are three operations permitted on a word: replace, delete, insert. For example, the edit distance between "a" and "b" is 1, the edit distance between "abc" and "def" is 3. 
This post analyzes how to calculate edit distance by using dynamic programming.
 */
public class EditDistance {

	
	
	public static int editDistance(String word1, String word2){
		int n=word1.length();
		int m=word2.length();
		int[][] DP=new int[m+1][n+1];
		
		
		//THE index here is string length instead of index.
		//base case: the word2 is empty
		for (int w1=0; w1<=n; w1++){
			DP[0][w1]=w1; //edit an empty sequence into a string with length w1, we need w1 number of insertion.
		}
		
		for (int w2=0; w2<=m; w2++){
			DP[w2][0]=w2; //edit an empty sequence into a string with length w2, we need w2 number of insertion.
		}
		
		for (int word2Len=1; word2Len<=m; word2Len++){
			char lastCharinWord2=word2.charAt(word2Len-1);
			
			for (int word1Len=1; word1Len<=n; word1Len++){
				char lastCharinWord1=word1.charAt(word1Len-1);
				
				if (lastCharinWord2==lastCharinWord1){
					//last charatcer is same, we do not need edit any thing.
					//we only need extending string
					DP[word2Len][word1Len]=DP[word2Len-1][word1Len-1];
					continue;
				} 
				
				
				//append lastCharinWord2 to word1.
				int append=DP[word2Len-1][word1Len]+1;
				int delete=DP[word2Len][word1Len-1]+1;
				int replace=DP[word2Len-1][word1Len-1]+1;
				
				DP[word2Len][word1Len]=Math.min(replace, Math.min(append, delete));
			}
		}
		return DP[m][n];
	}
	
	
	
	@Test
	public void test(){
		
		assertEquals(0, editDistance("a", "a"));
		assertEquals(1, editDistance("a", "b"));
		
		assertEquals(3, editDistance("abc", "def"));
		
		
	}
}
