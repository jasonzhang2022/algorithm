package jason.algorithm.string;

import java.util.Arrays;

//explanation in wiki is wrong
//http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/bmen.htm
public class BoryeMoore {
	public static int ALPHABET_SIZE = 256;

	public static int[] bmInitocc(char[] pattern) {
		int[] table = new int[ALPHABET_SIZE];

		Arrays.fill(table, -1);

		for (int j = 0; j < pattern.length; j++) {
			table[pattern[j]] = j;
		}
		return table;
	}

	/*
	 * To understand the logic here, we have to understand  two theorem about border and what is border.
	 * The information is here: http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm#section2
	 * 
	 * A border of Text is a subsequence(S) at both end of string, S=prefix=suffix.
	 * 
	 *  Theorem 1: For a String P(pattern) with widest border S, the NEXT widest border for P if the widest border of S.
	 * 
	 */
	
	/**
	 * 
	 * @param pattern
	 * @return an array s[i]= if mismatch occurs at i-1 (matched pattern is
	 *         i:end), how much should we shift so that another pattern like
	 *         [i:end] can be aligned with [i:end]
	 */
	public static int[] bmPreprocess1(char[] pattern) {
		int m=pattern.length;
		
		
		int[] f = new int[m + 1];
		int[] s = new int[m+ 1];
		f[m]=m+1;
		f[m-1]=m;
		int i = m-1, j =m;

		/*
		 * The algorithm move from the end of pattern to beginning.
		 *  P: pattern
		 *  f[i]:
		 *   	the starting position of the widest border for suffix P[i, end].
		 *   	String we are talking suffix from i: P[i, end]
		 *  	It is the starting position for the border at the end.
		 *  	The starting position for the border at the beginning is i;
		 *  
		 *  Suppose we know f[i], 
		 *  we want to move one step to left and calculate f[i-1]
		 *  
		 *  How can we extend the border,
		 * -----------Match case: we can extend the border 
		 *  if P[j-1]==f[i-1], the border can be extended by one character: 
		 *  f[i-1]=j-1 ( i--; j--; f[i]=j;) 
		 *  
		 * ---------- Mismatch case
		 *  If P[j-1]!=f[i-1], Theorem 1 tells us the next widest border we should try is 
		 *  the border for current border: that is f[j];
		 *  
		 *  At the same time, The mismatch case is the case 1 of good suffix rule.
		 *  we want to record that when there is a mismatch at this position
		 *   we shift j-i position.
		 *  
		 */

		while (i > 0) {
			//j<=m so that j-1 is a valid index
			//and the potential border has at least one character.
			while (j <= m && pattern[i - 1] != pattern[j - 1]) {
				if (s[j] == 0) {
					/*
					 * 
					 *this is the case 1 of good suffix.
					 * if P[j-1] does not match with character in Text, 
					 * but the suffix starting with j exists some where before,
					 * we shift the previous identical string to current position.
					 * 
					 * Notice there P[j-1] does not match which character in text
					 * and P[j-1] does not match with P[i-1].
					 * It is possible that P[i-1] after shift will match with the character at position.
					 * 
					 *	
					 * 
					 * However, the case when a border cannot be extended to the
					 * left is also interesting, since it leads to a promising
					 * shift of the pattern if a mismatch occurs. Therefore, the
					 * corresponding shift distance is saved in an array s –
					 * provided that this entry is not already occupied. The
					 * latter is the case when a shorter suffix has the same
					 * border.
					 * 
					 * Shift distance if mismatch occurs at j-1
					 */
					s[j] = j - i;
				}
				/*
				
				Theorem 1: For a String P with widest border S, the NEXT widest border for P if the widest border of S.
				
				Current border can not be extended since there is a mismatch.
				We go next one. According to theorem 1, we need to go the widest border of current border.
				
				The starting position for current border is j. The border is S=pattern[j, end].
				We need to findUsingArray the starting position of border for String S=pattern[j, end] which is f[j].
				
				*/
				j = f[j];
			}
			
			/**
			 * Two cases:
			 * 1. matched. 
			 * 2. no matched so j=m+1. we set f[i-1]=m;
			 */
			f[i-1] = j-1;
			i--;
			j--;
			
		}

		// prefix pattern
		i = 0;
		j = 0;
		j = f[0]; //the starting position of widest border for whole pattern (suffix starting 0)
		
		
		/*
		 * This is the case 2:
		 * for case 2: we only move when there is a border (prefix=suffix), not SUBSEQUENCE in middle of pattern=suffix
		 * Notice the prefix starting with zero, so we shift j-0=j: shift position zero to position j.
		 */
		for (i = 0; i <= pattern.length; i++) {
			if (s[i] == 0) {//no shift is set.
				/*
				 * s[i]==0 means when mismatch occurs at p[i-1], suffix[1:end] does not exists in the left.
				 */
				s[i] = j; 
			}
				
			if (i == j) {
				j = f[j];
			}
		}
		return s;
	}

	
	public static int indexOf(char[] text, char[] pattern) {
		int[] occ=bmInitocc(pattern);
		int[] s=bmPreprocess1(pattern);
		int i=0, j;
	    while (i<=text.length-pattern.length)
	    {
	        j=pattern.length-1;
	        while (j>=0 && pattern[j]==text[i+j]) {
	        	j--;
	        }
	        
	        if (j<0)
	        {
	        	//we have a match.
	            return i;
	        }   else {
	        	//we shift by increasing i.
	        	//in KMP we shift by decreasing j
	        	
	        	//a mismatch occurs at pattern[j] or text[i+j]
	        	 i+=Math.max(s[j+1], j-occ[text[i+j]]);
	        }
	           
	    }
	    return -1;
	}
}
