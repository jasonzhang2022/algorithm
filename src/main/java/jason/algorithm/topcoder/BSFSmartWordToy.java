package jason.algorithm.topcoder;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

//http://community.topcoder.com/stat?c=problem_statement&pm=3935&rd=6532
public class BSFSmartWordToy {
	
	public static void processSingleForbiddenString(String[] tokens,Set<String> results){
		for (int i=0; i<tokens[0].length(); i++){
			for(int j=0; j<tokens[1].length(); j++){
				for (int k=0; k<tokens[2].length(); k++){
					for (int l=0; l<tokens[3].length(); l++){
						results.add(""+tokens[0].charAt(i)+tokens[1].charAt(j)+tokens[2].charAt(k)+tokens[3].charAt(l));
					}
				}
			}
		}
	}
	
	public static int minPresses(String start, String finish, String[] forbid){
		
		Set<String> forbiddens=new HashSet<>(26*26*26*26*2);
		for(String f: forbid){
			processSingleForbiddenString(f.split(" "), forbiddens);
		}
		
		if (forbiddens.contains(finish)){
			return -1;
		}
		Set<String> touchedWords=new HashSet<>(forbiddens);
		
		Queue<String> queue=new LinkedList<>();
		Queue<Integer> level=new LinkedList<>();
		
		if (start.equals(finish)){
			return 0;
		}
		
		
		queue.offer(start);
		level.offer(0);
		//always mark before queue
		touchedWords.add(start);
		while (!queue.isEmpty()){
					
			String current=queue.poll();
			int currentLevel=level.poll();
		
			//System.out.printf("level: %d, queue size:%d, touchedWords :%d\n", currentLevel, queue.size(), touchedWords.size());;
			
			
			for (int i=0; i<4; i++){
				char[] scratch=current.toCharArray();
				char c=scratch[i];
				String word1=null;
				String word2=null;
				if (c=='a'){
					scratch[i]='z';
					word1=String.valueOf(scratch);
					
					scratch[i]='b';
					word2=String.valueOf(scratch);
				} else if (c=='z'){
					scratch[i]='a';
					word1=String.valueOf(scratch);
					
					scratch[i]='y';
					word2=String.valueOf(scratch);
					
				} else {
					scratch[i]=(char) (c+1);
					word1=String.valueOf(scratch);
					
					scratch[i]=(char) (c-1);
					word2=String.valueOf(scratch);
				}
				
				if (word1.equals(finish) || word2.equals(finish)){
					return currentLevel+1;
				}
				
				if (!touchedWords.contains(word1)){
					queue.offer(word1);
					level.offer(currentLevel+1);
					touchedWords.add(word1);
				}
				if (!touchedWords.contains(word2)){
					queue.offer(word2);
					level.offer(currentLevel+1);
					touchedWords.add(word2);
				}
			}
		}
		
		return -1;
	}

	
	@Test 
	public void test(){
		assertEquals(4, minPresses("aaaa", "bbbb", new String[]{}));
		assertEquals(50, minPresses("aaaa", "mmnn", new String[]{}));
		assertEquals(8, minPresses("aaaa", "zzzz", new String[]{"a a a z", "a a z a", "a z a a", "z a a a", "a z z z", "z a z z", "z z a z", "z z z a"}));
		assertEquals(4, minPresses("aaaa", "bbbb", new String[]{}));
		
		
		assertEquals(-1, minPresses("aaaa", "zzzz", new String[]{"bz a a a", "a bz a a", "a a bz a", "a a a bz"}));
		
		assertEquals(6, minPresses("aaaa", "zzzz", new String[]{"cdefghijklmnopqrstuvwxyz a a a", 
				 "a cdefghijklmnopqrstuvwxyz a a", 
				 "a a cdefghijklmnopqrstuvwxyz a", 
				 "a a a cdefghijklmnopqrstuvwxyz"}));
		
		assertEquals(-1, minPresses("aaaa", "bbbb", new String[]{"b b b b"}));
		
		
		assertEquals(-1, minPresses("zzzz", "aaaa", new String[]{"abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk",
				 "abcdefghijkl abcdefghijkl abcdefghijkl abcdefghijk"}));
		
	}
}
