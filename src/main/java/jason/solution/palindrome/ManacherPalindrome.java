package jason.solution.palindrome;

import java.util.Arrays;

//http://tarokuriyama.com/projects/palindrome2.php
public class ManacherPalindrome implements LongestPalindromeFinder {

	char delimiter;

	public ManacherPalindrome(char delimiter) {
		super();
		this.delimiter = delimiter;
	}

	public void expand(int[] patLen, char[] chars, int center) {
		do {
			int currentPatLen = patLen[center];
			
			if (center - currentPatLen - 1>=0 && 
					center + currentPatLen + 1<chars.length &&
					(chars[center - currentPatLen - 1] == chars[center + currentPatLen + 1])  
					) {
				patLen[center] = currentPatLen + 1;
			} else {
				break;
			}
		} while (true);
	}

	/**
	 * The next position that should be checked for palindrome.
	 * 
	 * @param patLen
	 * @param chars
	 * @param center
	 * @return
	 */
	public int setRightValue(int[] patLen, char[] chars, int center) {
		int palLen = patLen[center];
		for (int i = 1; i <= palLen; i++) {
			int leftPosition = center - i;
			int rightPosition = center + i;
			int edgeDistance = palLen - i;

			int leftPalLen = patLen[leftPosition];
			if (leftPalLen < edgeDistance) {
				// right position has the same value
				patLen[rightPosition] = leftPalLen;
				// final decision, this position can be skipped from center.
			} else if (leftPalLen > edgeDistance) {
				// can not beigger than edgeDistance,
				// if we can, patLen[center] can be extended in first place.
				patLen[rightPosition] = edgeDistance;
				// final decision, this position can be skipped from center.
			} else {

				// patLen[rightPosition]>=edgeDistance. Finallly we find one
				// position we are not sure about this palindrome length,
				// move to that poistion to make final Decision
				patLen[rightPosition] = edgeDistance;
				return rightPosition;
			}

		}
		return center + palLen + 1;
	}

	@Override
	public String findLongestPalindrome(String input) {
		char[] chars = new char[input.length() * 2 + 1];
		for (int i = 0; i < input.length(); i++) {
			chars[i * 2] = delimiter;
			chars[i * 2 + 1] = input.charAt(i);
		}
		chars[chars.length - 1] = delimiter;

		int[] palLen = new int[chars.length];

		int currentCenter = 0;
		while (currentCenter < chars.length) {
			expand(palLen, chars, currentCenter);
			currentCenter = setRightValue(palLen, chars, currentCenter);
		}

		int maxPosition = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < palLen.length; i++) {
			if (palLen[i] > max) {
				maxPosition = i;
				max=palLen[i];
			}
		}

		char[] results = Arrays.copyOfRange(chars, maxPosition - max, maxPosition + max + 1);
		StringBuilder sBuilder = new StringBuilder();
		for (char c : results) {
			if (c != delimiter) {
				sBuilder.append(c);
			}
		}

		return sBuilder.toString();
	}

}
