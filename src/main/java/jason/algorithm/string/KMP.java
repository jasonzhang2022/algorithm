package jason.algorithm.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KMP {
	// http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm
	/**
	 * 
	 * @param pattern
	 * @return b[i]= length of border for subpattern of length of i.  border is the longest prefix 
	 * which is also the suffix of the partial string
	 */
	static int[] kmpPreprocess(char[] pattern) {
		
		int m = pattern.length;
		int[] b = new int[m + 1];
		b[0] = -1;
		b[1] = 0;
		int i = 1, j = 0;
		
		/*
		 * i is substring length
		 * j is the border length.
		 * 
		 */
		while (i < m) {
			/*
			 * we try to calculate the value for i+1
			 * 
			 * The last character for substring of length of i+1 is pattern[i]
			 * We compare with j only if the potential border has at least length of one.
			 */
			
			while (j >= 0 && pattern[i] != pattern[j]) {
				j = b[j];
			}
			/*
			 * two cases.
			 * 1. matched, b[i+1==j+1;
			 * 2. no border so j==-1, we give b[i+1]=0;
			 */
			b[i+1] = j+1;
			
			i++;
			j++;
			
		}
		return b;
	}

	public static int indexOf(char[] text, char[] pattern) {
		int n = text.length;
		int m = pattern.length;
		int[] b = kmpPreprocess(pattern);
		int i = 0, j = 0;
		/**
		 * Suppose we already match
		 * pattern[0..., j-1]
		 * with 
		 * text[i-1-j-1, ...i-1]
		 * 
		 * We decide what index to match for i
		 */
		while (i < n) {
			/*
			 * Shift pattern to right until
			 * we find a match
			 * pattern[0, j]
			 * with text[i-j, i]
			 * During the shift process, we decrease j.
			 * 
			 * If there is no prefix can match
			 * j will be -1.
			 * 
			 */
			while (j >= 0 && text[i] != pattern[j]){
				j = b[j];
			}
			/*
			 * When code comes here, we got a match for i. The match could be -1;
			 */
			
			/*
			 * Prepare for next index.
			 */
			i++;
			j++;
			//we one index over the last index.
			if (j == m) {
				return (i - j);

			}
		}
		return -1;
	}

	@Test
	public void test() {
		assertEquals(2, indexOf("acacacaf".toCharArray(), "acacaf".toCharArray()));
	}
}
