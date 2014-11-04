package jason;

import static org.junit.Assert.*;
import jason.datastructure.rmq.RMQ;
import jason.datastructure.rmq.SelectionRMQ;
import jason.solution.palindrome.DPPalindrome;
import jason.solution.palindrome.SuffixPalindrome;

import java.util.Arrays;

import org.junit.Test;

public class SuffixTreeTest {

	@Test
	public void testSuffix() {
		char delimiter='#';
		RMQ rmq=new SelectionRMQ();
		
		
		assertEquals("a", SuffixPalindrome.longPalindrome("ab",  new SelectionRMQ(), delimiter));
		assertEquals("a", SuffixPalindrome.longPalindrome("abc",  new SelectionRMQ(), delimiter));
		//assertEquals("", SuffixPalindrome.longPalindrome("", new SelectionRMQ(), delimiter));
		System.out.println("-----------------------");
		//special case, really no palindrome.
		//assertEquals("a", SuffixPalindrome.longPalindrome("a", new SelectionRMQ(), delimiter));
		assertEquals("a", SuffixPalindrome.longPalindrome("abcdef",  new SelectionRMQ(), delimiter));
		
		//odd case: basic
		assertEquals("aba", SuffixPalindrome.longPalindrome("aba",  new SelectionRMQ(), delimiter));
		//odd case" basic
		assertEquals("aba", SuffixPalindrome.longPalindrome("xabay",  new SelectionRMQ(), delimiter));
		//make sure we find the longest one, not arbitrary one.
		assertEquals("abcba", SuffixPalindrome.longPalindrome("xyzyabcbax",  new SelectionRMQ(), delimiter));
		
		
		//even case
		assertEquals("aa", SuffixPalindrome.longPalindrome("aa",  new SelectionRMQ(), delimiter));
		//odd case" basic
		assertEquals("abba", SuffixPalindrome.longPalindrome("abba",  new SelectionRMQ(), delimiter));
		//make sure we find the one in middle
		assertEquals("abba", SuffixPalindrome.longPalindrome("xabbayz",  new SelectionRMQ(), delimiter));
		//make sure we find the longest one 
		assertEquals("abccba", SuffixPalindrome.longPalindrome("xabbayzabccbats",  new SelectionRMQ(), delimiter));
	
	}
	
	@Test
	public void testDP() {
	
		
		assertEquals("a", DPPalindrome.longPalindrome("ab"));
		assertEquals("a", DPPalindrome.longPalindrome("abc"));
		//assertEquals("", DPPalindrome.longPalindrome("", new SelectionRMQ(), delimiter));
		System.out.println("-----------------------");
		//special case, really no palindrome.
		//assertEquals("a", DPPalindrome.longPalindrome("a", new SelectionRMQ(), delimiter));
		assertEquals("a", DPPalindrome.longPalindrome("abcdef"));
		
		//odd case: basic
		assertEquals("aba", DPPalindrome.longPalindrome("aba"));
		//odd case" basic
		assertEquals("aba", DPPalindrome.longPalindrome("xabay"));
		//make sure we find the longest one, not arbitrary one.
		assertEquals("abcba", DPPalindrome.longPalindrome("xyzyabcbax"));
		
		
		//even case
		assertEquals("aa", DPPalindrome.longPalindrome("aa"));
		//odd case" basic
		assertEquals("abba", DPPalindrome.longPalindrome("abba"));
		//make sure we find the one in middle
		assertEquals("abba", DPPalindrome.longPalindrome("xabbayz"));
		//make sure we find the longest one 
		assertEquals("abccba", DPPalindrome.longPalindrome("xabbayzabccbats"));
	
	}
	
	
}
