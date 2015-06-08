package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

/*

 Implement regular expression matching with support for '.' and '*'.

 '.' Matches any single character.
 '*' Matches zero or more of the preceding element.

 The matching should cover the entire input string (not partial).

 The function prototype should be:
 bool isMatch(const char *s, const char *p)

 Some examples:
 isMatch("aa","a") return false
 isMatch("aa","aa") return true
 isMatch("aaa","aa") return false
 isMatch("aa", "a*") return true
 isMatch("aa", ".*") return true
 isMatch("ab", ".*") return true
 isMatch("aab", "c*a*b") return true
 */
public class RegularExpressionMatch {

	public boolean isMatch(String text, String pattern) {
		return isMatch(text, 0, pattern, 0);
	}

	public boolean isMatch(String text, int offset, String pattern,
			int patternOffset) {

		// text is consumed.
		if (offset >= text.length()) {
			if (patternOffset >= pattern.length()) {
				return true;
			} else {
				return false;
			}
		}
		if (patternOffset >= pattern.length()) {
			if (offset < text.length()) {
				return false;
			}
		}

		// only one character remaining.
		if (pattern.length() - patternOffset == 1) {

			if (text.length() - offset != 1) {
				return false;
			}
			if (pattern.charAt(patternOffset) == '.') {
				return true;
			}
			if (pattern.charAt(patternOffset) == text.charAt(offset)) {
				return true;
			}
			return false;
		}

		if (pattern.charAt(patternOffset + 1) != '*') {
			if (pattern.charAt(patternOffset) == '.'
					|| pattern.charAt(patternOffset) == text.charAt(offset)) {
				return isMatch(text, offset + 1, pattern, patternOffset + 1);
			} else {
				return false;
			}
		} else {
			char c = pattern.charAt(patternOffset);
			while (true) {
				// do first match without consume any character
				if (isMatch(text, offset, pattern, patternOffset + 2)) {
					return true;
				}
				if (offset<text.length() && (c == '.' || c == text.charAt(offset))) {
					offset++;
				} else {
					return false;
				}
			}
		}

	}

	@Test
	public void testMatch() {
		RegularExpressionMatch matcher = new RegularExpressionMatch();
		assertTrue(matcher.isMatch("", ""));
		assertFalse(matcher.isMatch("aa", "a"));
		assertTrue(matcher.isMatch("aa", "aa"));
		assertFalse(matcher.isMatch("aaa", "aa"));
		assertTrue(matcher.isMatch("aa", "a*"));
		assertTrue(matcher.isMatch("aa", ".*"));
		assertTrue(matcher.isMatch("ab", ".*"));
		assertTrue(matcher.isMatch("aab", "c*a*b"));
	}

}
