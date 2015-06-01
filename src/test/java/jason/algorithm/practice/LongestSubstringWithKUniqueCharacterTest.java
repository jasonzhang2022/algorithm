package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class LongestSubstringWithKUniqueCharacterTest {

	@Test
	public void test() {
		assertNull(LongestSubstringWithKUniqueCharacter.find("abc", 4));
		assertEquals("a", LongestSubstringWithKUniqueCharacter.find("abc", 1));
		assertEquals("ab", LongestSubstringWithKUniqueCharacter.find("abc", 2));
		assertEquals("abc", LongestSubstringWithKUniqueCharacter.find("abc", 3));
		
		
		
		assertEquals("abac", LongestSubstringWithKUniqueCharacter.find("abac", 3));
		assertEquals("ababacc", LongestSubstringWithKUniqueCharacter.find("ababacc", 3));
		assertEquals("abac", LongestSubstringWithKUniqueCharacter.find("abacd", 3));
		
		
		
		//this case can not pass.
		assertEquals("cadcacacaca", LongestSubstringWithKUniqueCharacter.find("abcadcacacaca", 3));
	}
	
	@Test
	public void testProvided() {
		assertNull(LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abc", 4));
		assertEquals("a", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abc", 1));
		assertEquals("ab", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abc", 2));
		assertEquals("abc", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abc", 3));
		
		
		
		assertEquals("abac", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abac", 3));
		assertEquals("ababacc", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("ababacc", 3));
		assertEquals("abac", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abacd", 3));
		
		
		
		//this case can not pass.
		assertEquals("cadcacacaca", LongestSubstringWithKUniqueCharacter.maxSubStringKUniqueChars("abcadcacacaca", 3));
	}
	

}
