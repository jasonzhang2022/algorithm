package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

import org.junit.Test;

//http://www.programcreek.com/2013/02/longest-substring-which-contains-2-unique-characters/
public class LongestSubstringWithKUniqueCharacter {

		
	public static String find1(String input, int k){
		
		if (input==null || input.length()==0){
			return "";
		}
		int[] charTable=new int[256];
		Arrays.fill(charTable, -1);
		
		
		int maxCount=Integer.MIN_VALUE;
		int maxStartIndex=-1;
		
		charTable[input.charAt(0)]=0;
		int startIndex=0;
		int uniqueCount=1;
		
		
		for (int i=1; i<input.length(); i++){
			//this character is not seen before.
			if (charTable[input.charAt(i)]==-1){ 
				
				if (uniqueCount==k){
				
					//we need to start a new sequence.
					
					//keep the sequence index
					int newMaxCount=i-startIndex;
					if (newMaxCount>maxCount){
						maxCount=newMaxCount;
						maxStartIndex=startIndex;
					}
					
					//remove one character;
					while (charTable[input.charAt(startIndex)]!=startIndex){
						startIndex++;
					}
					//remove one character from begin and increase startIndex by one.
					charTable[startIndex++]=-1;
					
					//add this newly found character.
					charTable[input.charAt(i)]=i;
					//no need to adjust uniqueK since we remove one. and add one at the same time.
					
				} else {
					//this character is not seen before
					charTable[input.charAt(i)]=i;
					uniqueCount++;
				}
				
			} else {
				//this character exists before.
				charTable[input.charAt(i)]=i; //only keep the left most index
			}
		}
		
		
		//handle the end condition.
		if(input.length()-startIndex>maxCount && uniqueCount==k){
			maxCount=input.length()-startIndex;
			maxStartIndex=startIndex;
		}
		
		if (maxStartIndex==-1){
			return null;
		}
		return input.substring(maxStartIndex, maxStartIndex+maxCount);
	}
	
	
	public static String find(String input, int k){
		
		HashMap<Character, Integer> map=new HashMap<Character, Integer>(k);
	
		int lastStringStart=0;
		//----------------------initialization
		int i=0;
		while (map.size()<k && i<input.length()){
			map.put(input.charAt(i), i++); //we want to the keep the latest index.
		}
		
		if (i==input.length() && map.size()<k){
			return null; //no such string.
		}
		//-----------------extend and finish the first ine.
		while (i<input.length() && map.containsKey(input.charAt(i))){
			map.put(input.charAt(i), i++); //we want to the keep the latest index.
		}
		
		int lastStringEnd=i-1;
		int maxStringStart=lastStringStart;
		int maxStringEnd=lastStringEnd;
		
		while (i<input.length()){
			
			int currentStringStart=-1;
			int removeIndex=lastStringStart;
			
			//take away: don't try to optimize the, loop is fine. 
			//Make it work first.
			while (true){
				Character removedCharacter=input.charAt(removeIndex);
				if (map.get(removedCharacter)==removeIndex){
					map.remove(removedCharacter);
					currentStringStart=removeIndex+1;
					break;
				}
				removeIndex++;
			}
			
			//extend current string so that it has k character.
			while (map.size()<k && i<input.length()){
				map.put(input.charAt(i), i++); //we want to the keep the latest index.
			}
			if (i==input.length() && map.size()<k){
				//we end with a string that has less than k character
				break;
			}
			while (i<input.length() && map.containsKey(input.charAt(i))){
				map.put(input.charAt(i), i++); //we want to the keep the latest index.
			}
			
			//we finish one more substring.
			int currentStringEnd=i-1;
			
			if ((currentStringEnd-currentStringStart)>(maxStringEnd-maxStringStart)){
				maxStringStart=currentStringStart;
				maxStringEnd=currentStringEnd;
			}
			
			lastStringStart=currentStringStart;
			lastStringEnd=currentStringEnd;
			
			
			
		}
		
	
		return input.substring(maxStringStart, maxStringEnd+1);
	}
	
	//solution from here: http://www.programcreek.com/2013/02/longest-substring-which-contains-2-unique-characters/
	public static String maxSubStringKUniqueChars(String s, int k) {
		//declare a counter
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();		
		int start = 0;
		int maxLen = 0;
		String maxSubstring = null;
	 
		for (int i = 0; i < s.length(); i++) {
			//add each char to the counter
			char c = s.charAt(i);
			if(map.containsKey(c)){
				map.put(c, map.get(c)+1);
			}else{
				map.put(c, 1);
			}
	 
			if(map.size() == k+1){
				//get maximum
				int range = i-start;
				if(range > maxLen){
					maxLen = range;
					maxSubstring = s.substring(start, i);
				}
	 
				//move left cursor toward right, so that substring contains only k chars
				char first = s.charAt(start);
				while(map.size()>k){
					int count = map.get(first);
					if(count>1){
						map.put(first,count-1);
					}else{
						map.remove(first);
					}
					start++;
				}
			}
		}
	 
		if (map.size() == k && maxLen == 0) {
			return s;
		}
	 
		return maxSubstring;
	}
	
	@Test
	public void test() {
		assertNull(find("abc", 4));
		assertEquals("a", find("abc", 1));
		assertEquals("ab", find("abc", 2));
		assertEquals("abc", find("abc", 3));
		
		
		
		assertEquals("abac", find("abac", 3));
		assertEquals("ababacc", find("ababacc", 3));
		assertEquals("abac", find("abacd", 3));
		
		//this case can not pass.
		assertEquals("cadcacacaca", find("abcadcacacaca", 3));
	}
	
	@Test
	public void test1() {
		assertNull(find1("abc", 4));
		assertEquals("a", find1("abc", 1));
		assertEquals("ab", find1("abc", 2));
		assertEquals("abc", find1("abc", 3));
		
		
		
		assertEquals("abac", find1("abac", 3));
		assertEquals("ababacc", find1("ababacc", 3));
		assertEquals("abac", find1("abacd", 3));
		
		//this case can not pass.
		assertEquals("cadcacacaca", find1("abcadcacacaca", 3));
	}
	
	@Test
	public void testProvided() {
		assertNull(maxSubStringKUniqueChars("abc", 4));
		assertEquals("a", maxSubStringKUniqueChars("abc", 1));
		assertEquals("ab", maxSubStringKUniqueChars("abc", 2));
		assertEquals("abc", maxSubStringKUniqueChars("abc", 3));
		
		
		
		assertEquals("abac", maxSubStringKUniqueChars("abac", 3));
		assertEquals("ababacc", maxSubStringKUniqueChars("ababacc", 3));
		assertEquals("abac", maxSubStringKUniqueChars("abacd", 3));
		
		
		
		//this case can not pass.
		assertEquals("cadcacacaca", maxSubStringKUniqueChars("abcadcacacaca", 3));
	}
	

	
}
