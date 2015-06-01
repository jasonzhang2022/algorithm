package jason.algorithm.practice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

//http://www.programcreek.com/2013/02/longest-substring-which-contains-2-unique-characters/
public class LongestSubstringWithKUniqueCharacter {

		
	
	
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

	
}
