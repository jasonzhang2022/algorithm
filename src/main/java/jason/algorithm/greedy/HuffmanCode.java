package jason.algorithm.greedy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class HuffmanCode {

	public static class CodeNode {
		int freq;
		CodeNode left=null;
		CodeNode right=null;
		char c;
		public CodeNode(int freq, char c) {
			super();
			this.freq = freq;
			this.c = c;
		}
		
	}
	
	Queue<CodeNode> first;
	Queue<CodeNode> second;
	
	public CodeNode findMin(){
		if (first.isEmpty() && second.isEmpty()){
			return null;
		}
		if (first.isEmpty()){
			return second.poll();
		}
		if (second.isEmpty()){
			return first.poll();
		}
		if (first.peek().freq<second.peek().freq){
			return first.poll();
		}
		return second.poll();
		
	}
	
	
	public void traverse(CodeNode codeNode, StringBuilder sb, Map<Character, String> map){
		if (codeNode.left==null){
			//right also is null
			map.put(codeNode.c, sb.toString());
			return ;
		}
		
		int length=sb.length();
		//go left
		traverse(codeNode.left, sb.append("0"), map);
		sb.setLength(length);
		traverse(codeNode.right, sb.append("1"), map);
		sb.setLength(length);
	}

	/*
	 * http://www.geeksforgeeks.org/greedy-algorithms-set-3-huffman-coding-set-2/
	 * Like mergeSort
	 */
	public Map<Character, String> computeCode(int[] freq, char[] code){
		
		Map<Character, String> result=new HashMap<>();
		//assume that the fre is sorted.
		
		first=new LinkedList<>();
		second=new LinkedList<>();
		for (int i=0; i<freq.length; i++){
			first.add(new CodeNode(freq[i], code[i]));
		}
		
		CodeNode c1=findMin();
		CodeNode c2=findMin();
		while (c1!=null && c2!=null){
			CodeNode c3=new CodeNode(c1.freq+c2.freq, ' ');
			c3.left=c1;
			c3.right=c2;
			second.offer(c3);
			c1=findMin();
			c2=findMin();
		
		
		}
		
		CodeNode root=(c1==null?c2:c1);
		traverse(root, new StringBuilder(), result);
		return result;

	}
	@Test
	public void test() {
		char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
	    int freq[] = {5, 9, 12, 13, 16, 45};
	    
	    Map<Character, String> result=computeCode(freq, chars);
	    for (Character c: result.keySet()){
	    	System.out.printf("%c: %s\n", c, result.get(c));
	    }
	    assertThat(result.get('f'), equalTo("0"));
	    assertThat(result.get('a'), equalTo("1100"));
	    assertThat(result.get('c'), equalTo("100"));
	    
	}
	
}
