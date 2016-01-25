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
		int i = 0, j = -1;
		int m = pattern.length;
		int[] b = new int[m + 1];
		b[i] = j;
		while (i < m) {
			while (j >= 0 && pattern[i] != pattern[j]) {
				j = b[j];
			}
			i++;
			j++;
			b[i] = j;
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
