package jason.solution.palindrome;

import java.util.LinkedList;
import java.util.Queue;

public class DPPalindrome {

	public static String longPalindrome(String str) {

		FoundPalindrome longtest = new FoundPalindrome(0, 1);
		Queue<FoundPalindrome> foundPalindromes = new LinkedList<>();
		// initialize single letter
		for (int i = 0; i < str.length(); i++) {
			foundPalindromes.offer(new FoundPalindrome(i, 1));
		}
		// initialize two letters
		for (int i = 0; i < str.length() - 1; i++) {
			if (str.charAt(i) == str.charAt(i + 1)) {
				FoundPalindrome palindrome=new FoundPalindrome(i, 2);
				if (longtest.length < palindrome.length) {
					longtest = palindrome;
				}
				foundPalindromes.offer(palindrome);
			}

		}

		FoundPalindrome current = null;
		// grow palindrome
		while ((current = foundPalindromes.poll()) != null) {
			if (current.startPosition == 0 || current.startPosition + current.length >= str.length()) {
				continue; // reach the beginnning or end, can not grow anymore.
			}
			if (str.charAt(current.startPosition - 1) == str.charAt(current.startPosition + current.length)) {
				// grow
				current.startPosition = current.startPosition - 1;
				current.length = current.length + 2;
				if (longtest.length < current.length) {
					longtest = current;
				}
				// add back to queue
				foundPalindromes.offer(current);
			}
		}

		return str.substring(longtest.startPosition, longtest.startPosition+longtest.length);
	}

	public static class FoundPalindrome {
		int startPosition;
		int length;

		public FoundPalindrome(int startPosition, int length) {
			super();
			this.startPosition = startPosition;
			this.length = length;
		}

	}

}