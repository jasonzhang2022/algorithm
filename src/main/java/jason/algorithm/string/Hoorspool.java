package jason.algorithm.string;

import java.util.Arrays;

//http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/horsen.htm
/**
 * Different from BoryeMoore.
 * 
 * When there is mismatch, 
 * 	always align the character at the end of match with the right most one in the pattern except the last one.
 * 
 * There are several cases for last character at the end of match
 * 1. mismatch, and the character is not in the pattern. The whole pattern is shfit;
 * 2. mismatch and the character is in the pattern. The pattern is shift and aligned with the character.
 * 3. match, but character at the left does not match. This is the last character in the pattern.
 * 
 * The mismatched character does not take part in the calculation.
 * 
 * @author jason.zhang
 *
 */
public class Hoorspool {

	public static int ALPHABET_SIZE = 256;

	public static int[] horoTable(char[] pattern) {
		int[] table = new int[ALPHABET_SIZE];

		Arrays.fill(table, -1);

		for (int j = 0; j < pattern.length - 1; j++) {
			table[pattern[j]] = j;
		}
		return table;
	}

	public static int indexOf(char[] text, char[] pattern) {

		int[] table = horoTable(pattern);
		int i = 0;
		while (i <= text.length - pattern.length) {
			int j = pattern.length - 1;
			while (j >= 0 && pattern[j] == text[i + j]) {
				j--;
			}
			if (j < 0) {
				return i;
			}
			i += pattern.length - 1;
			
			System.out.println("using character "+text[i]);
			
			i -= table[text[i]];
		}
		return -1;
	}
}
