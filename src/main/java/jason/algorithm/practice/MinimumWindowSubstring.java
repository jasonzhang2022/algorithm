package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

//http://www.programcreek.com/2014/05/leetcode-minimum-window-substring-java/
public class MinimumWindowSubstring {

	public static String find(String s, String t) {
		if (s==null || t==null || t.length()==0){
			return null;
		}
		
		LinkedList list;
		
		int[] charTable = new int[256];
		// -2 means such character does not exist in t,
		Arrays.fill(charTable, -2);
		int tCount = 0;
		for (int i = 0; i < t.length(); i++) {
			// -1 means character exis in t
			if (charTable[t.charAt(i)] != -1) {
				tCount++;
				charTable[t.charAt(i)] =-1;
			}
		}
		
		//special case 
		if (tCount==1 && s.indexOf(t.charAt(0))!=-1){
			return String.valueOf(t.charAt(0));
		}

		int minLen = Integer.MAX_VALUE;
		int minStart = -1;

		int count = 0;
		if (charTable[s.charAt(0)] >= -1) {
			charTable[s.charAt(0)] = 0;
			count = 1;
		}
		int startIndex = 0;

		for (int i = 1; i < s.length(); i++) {
			int c = s.charAt(i);
			if (charTable[c] == -2) {
				// not exist in t.
				continue;
			}

			if (charTable[c] == -1) {
				charTable[c] = i;
				count++;
				if (count == tCount) {
				
					//can we shrink the window at the same time to keep it valid?
					while (charTable[s.charAt(startIndex)] != startIndex) {
						startIndex++;
					}
					//right now, we still at a valid window before we remove character at startIndex, 
					if (i - startIndex + 1 < minLen) {
						minStart = startIndex;
						minLen = i - startIndex + 1;
					}
					
					charTable[s.charAt(startIndex)] = -1; // remove this character.
					startIndex++; // new StartIndex;
					count--; // we removed one character;
					
					//continue remove character not in t;
					while (charTable[s.charAt(startIndex)]==-2){
						startIndex++;
					}
				}

			} else {
				// exist before. keep rightmost index
				charTable[c] = i;
				// don't increase count;
			}

		}

		// no edge case.
		// if last character makes count==tCount, it is already processed.

		if (minStart==-1){
			return null;
		}
		return s.substring(minStart, minStart + minLen);

	}
	
	@Test
	public void test() {
		assertNull(find("abc", ""));
		assertNull(find("abc", "d"));
		assertEquals("a", find("abc", "a"));
		assertEquals("b", find("abc", "b"));
		assertEquals("c", find("abc", "c"));
		
		//we can handle end condition
		assertEquals("ba", find("xaacbcba", "ab"));
				
		//we can shrink the window aacb->acb
		assertEquals("acb", find("xaacbc", "ab"));
		
		//we can shrink unrelated character o: aoacb->acb
		assertEquals("acb", find("xaoacbc", "ab"));
		
		//we can shrink unrelated character o: aoacb->b
		assertEquals("ba", find("xaoacbac", "ab"));
		
		//aoboac->ac
		assertEquals("acb", find("xaoboacbef", "abc"));
	
	}
}
