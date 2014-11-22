package jason.algorithm.string;

import java.util.Arrays;

//http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/sundayen.htm
public class Sunday {


	public static int ALPHABET_SIZE = 256;

	public static int[] sunTable(char[] pattern) {
		int[] table = new int[ALPHABET_SIZE];

		Arrays.fill(table, -1);

		for (int j = 0; j < pattern.length; j++) {
			table[pattern[j]] = j;
		}
		return table;
	}

	public static int indexOf(char[] text, char[] pattern) {

		int[] table = sunTable(pattern);
		int i = 0;
		while (i <= text.length - pattern.length) {
			int j = pattern.length - 1;
			while (j >= 0 && pattern[j] == text[i + j]) {
				j--;
			}
			if (j < 0) {
				return i;
			}
			i += pattern.length; //one after current window.
			if (i<text.length) {
				System.out.println("using character "+text[i]);
				i -= table[text[i]];	
			}
		}
		return -1;
	}
}
