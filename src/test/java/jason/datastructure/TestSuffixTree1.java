package jason.datastructure;

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
	
		//verifySuffix("$");

		//verifySuffix("a$");
		//verifySuffix("aa$");
		//verifySuffix("abc$");
		//verifySuffix("xabxa$");
		//verifySuffix("abcabxabcd$");
		//verifySuffix("geeksforgeeks$");
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
}
