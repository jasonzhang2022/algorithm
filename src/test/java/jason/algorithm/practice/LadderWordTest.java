package jason.algorithm.practice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

//http://www.programcreek.com/2012/12/leetcode-word-ladder/
/*
 * Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that only one letter can be changed at a time and each intermediate word must exist in the dictionary. For example, given:

start = "hit"
end = "cog"
dict = ["hot","dot","dog","lot","log"]
One shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog", the program should return its length 5.

Note:
Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.

start and end are not required to be dict.

We could build a tree and find the shortest path from 'from' to 'end'.
But the issue is this: a tree representation consuming memory.  Given a Set<String> dict, the edge is implicit given the translation rule.

 */
public class LadderWordTest {

	
	@Test
	public void testWordLadder (){
		Set<String> dict=new HashSet<>();
		String[] dictArray={"hot","dot","dog","lot","log"};
		dict.addAll(Arrays.asList(dictArray));
		
		List<String> path=LadderWord.findLadderWord("hit", "cog", dict);
		
		assertThat(path, contains("hit", "hot", "dot", "dog", "cog"));
		System.out.println("------------------------");
		
		dict.clear();
		dict.addAll(Arrays.asList(dictArray));
		dict.add("cog");
		
		path=LadderWord.findLadderWord("hit", "cig", dict);
		
		assertThat(path, contains("hit", "hot", "dot", "dog", "cog", "cig"));
		
		dict.clear();
		dict.addAll(Arrays.asList(dictArray));
		dict.add("cog");
		dict.add("hig");
		
		path=LadderWord.findLadderWord("hit", "cig", dict);
		
		assertThat(path, contains("hit", "hig", "cig"));
		
		
	}
}
