package jason.algorithm.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//http://www.programcreek.com/2012/12/leetcode-word-ladder/
/*
 * Given two words (start and end), and a dictionary, findUsingArray the length of shortest transformation sequence from start to end, such that only one letter can be changed at a time and each intermediate word must exist in the dictionary. For example, given:

start = "hit"
end = "cog"
dict = ["hot","dot","dog","lot","log"]
One shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog", the program should return its length 5.

Note:
Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.

start and end are not required to be dict.

We could build a tree and findUsingArray the shortest path from 'from' to 'end'.
But the issue is this: a tree representation consuming memory.  Given a Set<String> dict, the edge is implicit given the translation rule.

 */
public class LadderWord {

	
	/*
	 * we perform the breadth walking, the first one should be the shallow one
	 */
	public static List<String> findLadderWord(String from, String to, Set<String> dict){
		
		Queue<String> processedWords=new LinkedList<String>();
		processedWords.offer(from);
		
		/**
		 * Map where each word comes from.
		 */
		Map<String, String> sources=new HashMap<String, String>();
		
		outer:
		while (!processedWords.isEmpty()){
			
			String current=processedWords.poll();
			System.out.println("current is "+current);
			
			//walk current word
			for (int i=0; i<current.length(); i++){
				char[] chars=current.toCharArray();
				//walk letter i
				for (char a='a'; a<='z'; a++){
					chars[i]=a;
					if (String.valueOf(chars).equals(to)){
						//we found one.
						// end case.
						System.out.println("walk to "+String.valueOf(chars)+" from "+current);
						sources.put(to, current);
						
						break outer;
					}
					//there is a path.
					if (dict.contains(String.valueOf(chars))){
						System.out.println("walk to "+String.valueOf(chars)+" from "+current);
						processedWords.offer(String.valueOf(chars));
						sources.put(String.valueOf(chars), current);
						//in graph traversal, we ususally use a boolean to mark a node is walked or not to avoid loop.
						dict.remove(String.valueOf(chars)); //avoid loop.
					}
				}
			}
		}
		if (!sources.containsKey(to)){
			return null;
		}
		
		LinkedList<String> path=new LinkedList<String>();
		String prevKey=to;
		while (prevKey!=null){
			path.addFirst(prevKey);
			
			prevKey=sources.get(prevKey);
			
		}
		return path;
				
		
	}
	
}
