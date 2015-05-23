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
	 * http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm#section2
	 * 
	 * A border is a identical string at both end of the target string
	 * 
	 * Theorem 1: For a String P with widest border S, the NEXT widest border for P if the widest border of S.
	 * 
	 * if f[i] contains width of the widest border for P[0, i-1] (length i),
	 * we want to find out the widest border for P[0, i] (length i+1), the new character  is P[i]. 
	 * if P[i]=P[f[i]] (new character matches the character following widest border), the border for P[i] can be extended by one from that for P[i-1].
	 * If not, we need to find the next possible border to extend. According to theorem 1, It is the widest border for previous border: f[f[i]]
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
		int i = pattern.length, j = pattern.length + 1;
		int[] f = new int[pattern.length + 1];
		int[] s = new int[pattern.length + 1];
		f[i] = j;

		/*
		 * f[i] containing the starting position of widest border for suffix
		 * starting from i. The border is pattern[f[i], end];
		 * 
		 * j=f[i], In each loop, we try to caluclate the widest border for i-1;
		 * If the border can be extended: pattern[i-1]==pattern[j-1], then
		 * f[i-1]=j-1, if not, we trying the border from f[j];
		 */

		while (i > 0) {
			while (j <= pattern.length && pattern[i - 1] != pattern[j - 1]) {
				if (s[j] == 0) {
					/*
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
				We need to find the starting position of border for String S=pattern[j, end] which is f[j].
				
				*/
				j = f[j];
			}
			i--;
			j--;
			f[i] = j;
		}

		// prefix pattern
		i = 0;
		j = 0;
		j = f[0]; //the starting position of widest border for whole pattern (suffix starting 0)
		
		
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
	        while (j>=0 && pattern[j]==pattern[i+j]) {
	        	j--;
	        }
	        
	        if (j<0)
	        {
	            return i;
	        }   else {
	        	//a mismatch occurs at pattern[j] or text[i+j]
	        	 i+=Math.max(s[j+1], j-occ[text[i+j]]);
	        }
	           
	    }
	    return -1;
	}
}
