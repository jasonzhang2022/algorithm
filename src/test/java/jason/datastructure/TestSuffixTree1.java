package jason.datastructure;

import jason.algorithm.string.SubstringTest;
import jason.algorithm.string.SubstringTest.Result;
import jason.datastructure.SuffixTree1.SuffixTreeNode;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
public class TestSuffixTree1 {
	
	@Test
	public void suffixTree1Test(){
	
		verifySuffix("$");

		verifySuffix("a$");
		verifySuffix("aa$");
		verifySuffix("abc$");
		verifySuffix("xabxa$");
		verifySuffix("abcabxabcd$");
		verifySuffix("geeksforgeeks$");
		verifySuffix("AABAACAADAABAAABAA$");
		
	}

	
	
	
	public void verifySuffix(String s){
		SuffixTree1 tree=new SuffixTree1(s);
		ArrayList<Integer> indices=new ArrayList<Integer>(s.length());
		for (int i=0; i<SuffixTree1.MAX_CHAR; i++){
			if (tree.root.children[i]!=null){
				collectSuffixDFS(tree.root.children[i], "", s, indices);
			}
		}
		
		indices.sort((s1, s2)->s1.compareTo(s2));
		
		for (int i=0; i<s.length(); i++){
			assertEquals(indices.get(i).intValue(), i);
		}
		
		
	}
	public void collectSuffixDFS(SuffixTreeNode node, String parentLabel, String text, ArrayList<Integer> indices){
		
		String selfLabel=text.toString().substring(node.edgeStart, node.end.end+1);
		if (node.isLeaf()){
			String finalString=parentLabel+selfLabel;
			assertEquals(finalString, text.substring(node.suffixIndex));
			indices.add(node.suffixIndex);
			return;
		}
		for (int i=0; i<SuffixTree1.MAX_CHAR; i++){
			if (node.children[i]!=null){
				collectSuffixDFS(node.children[i], parentLabel+selfLabel, text, indices);
			}
		}
	}
	
	
	@Test 
	public void testPatternFind(){
		
		SubstringTest test=new SubstringTest();
		test.setup();
		
		for (String text: test.testData.keySet()) {
			Result result=test.testData.get(text);
			SuffixTree1 suffixTree=new SuffixTree1(text+"$");
			
			System.out.println(result.needle);
			int k=suffixTree.applicationHasPattern(result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
		}
		
		
		SuffixTree1 suffixTree=new SuffixTree1("ababc");
		int k=suffixTree.applicationHasPattern("ab".toCharArray());
		assertEquals(k, 0); //we found 0 instead of 2
		
	}
	
	
	@Test
	public void testLongestRepeatString(){
		
		assertNull(new SuffixTree1("$").applicationLongestRepeatedString());
		assertNull(new SuffixTree1("a$").applicationLongestRepeatedString());
		assertNull(new SuffixTree1("ab$").applicationLongestRepeatedString());
		assertNull(new SuffixTree1("abc$").applicationLongestRepeatedString());
		assertEquals(new SuffixTree1("aaaaa$").applicationLongestRepeatedString(), "aaaa");
		assertEquals(new SuffixTree1("abcdeabcdex$").applicationLongestRepeatedString(), "abcde");
	}

}
