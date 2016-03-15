package jason.datastructure.tree;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.junit.Test;

import jason.datastructure.EnglishDictTrie;

public class TrieTest {

	@Test
	public void testBasic(){
		EnglishDictTrie root=new EnglishDictTrie(' ');
		root.children=new EnglishDictTrie[26];
		root.addWord("dict".toCharArray(), 0);
		root.addWord("dig".toCharArray(), 0);
		root.addWord("dog".toCharArray(), 0);
		root.addWord("cat".toCharArray(), 0);
		
		int[] count={0};
		Consumer<String> consumer=s->{count[0]++;};
		root.findWord("dig".toCharArray(), 0, new StringBuilder(), consumer);
		assertEquals(count[0], 1);
		
		count[0]=0;
		root.findWord("dug".toCharArray(), 0, new StringBuilder(), consumer);
		assertEquals(count[0], 0);
	}
	
	@Test
	public void testOneMissingLetter(){
		EnglishDictTrie root=new EnglishDictTrie(' ');
		root.children=new EnglishDictTrie[26];
		root.addWord("dict".toCharArray(), 0);
		root.addWord("dig".toCharArray(), 0);
		root.addWord("dog".toCharArray(), 0);
		root.addWord("cat".toCharArray(), 0);
		
		int[] count={0};
		Consumer<String> consumer=s->{count[0]++ ; System.out.println(s);};
		root.findMissingLetterWord("duog".toCharArray(), 0, consumer);
		assertEquals(count[0], 1);
		
		count[0]=0;
		root.findMissingLetterWord("diog".toCharArray(), 0, consumer);
		assertEquals(count[0], 2);
	}
}
