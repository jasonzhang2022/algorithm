package jason.algorithm.recursion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jason.datastructure.disjointset.TarjanOfflineLCA.Node;

public class TreeBuilder {
	
	public MultiTreeNode buildTree(String input){
		
		MultiTreeNode root=new MultiTreeNode(null, null);
		int[] offset={0};
		char[] chars=input.toCharArray();
		
		while (offset[0]<input.length()){
			MultiTreeNode child=buildOneSubTree(chars, offset);
			if (child!=null){
				child.parent=root;
				root.children.add(child);
				offset[0]++;
			}
		}
		if (root.children.size()==0){
			return null;
		}
		if (root.children.size()==1){
			return root.children.getFirst();
		}
		return root;
	}
	
	public MultiTreeNode buildOneSubTree(char[] chars, int[] offset){
		if (offset[0]>=chars.length){
			return null;
		}
		String name=collectName(chars, offset);		
		MultiTreeNode root=new MultiTreeNode(name);
		
		
		if (offset[0]==chars.length){
			//we reach the end of string
			return root;
		}
		//we don't have subtree.
		if (chars[offset[0]]==',' || chars[offset[0]]==')'){
			//we are leaf node.
			return root;
		}
		
		//The character is '(', we have subtree. 
		//construct the subtree.
		/*
		 * We can view '(' and ',' as prefix for a child node while ')' is a singal that we don't have child node any more.
		 */
		while (chars[offset[0]]!=')'){
			offset[0]++;
			MultiTreeNode child=buildOneSubTree(chars, offset);
			child.parent=root;
			root.children.add(child);
		}
		
		//strip off ')'
		offset[0]++;
		return root;
	}
	
	public String collectName(char[] chars, int[] index){
		StringBuilder sb=new StringBuilder();
		for (int i=index[0]; i<chars.length; i++){
			if (chars[i]!=')' && chars[i]!=',' && chars[i]!='('){
				sb.append(chars[i]);
			} else{
				index[0]=i;
				return sb.toString();
			}
		}
		index[0]=chars.length;
		return sb.toString();
	}
	
	@Test
	public void testBuildNodeWithChildrenSimple(){
		String input;
		MultiTreeNode node;
	
		node=buildTree("");
		assertNull(node);
	}
	
	@Test
	public void testBuildNodeWithChildrenSimple1(){
		String input;
		MultiTreeNode node;
		input="x";
		node=buildTree("x");
		assertThat(node.toString(),equalTo(input));
		
	}
	@Test
	public void testBuildNodeWithChildrenSimple2(){
		String input;
		MultiTreeNode node;
		input="a,b";
		node=buildTree(input);
		assertThat(node.toString(),equalTo("a,b"));
	}
	@Test
	public void testBuildNodeWithChildrenSimple3(){
		String input;
		MultiTreeNode node;
		input="a(a1,a2),b";
		node=buildTree(input);
		assertThat(node.toString(),equalTo(input));
		assertThat(node.children, hasSize(2));
		assertThat(node.children.get(0).children, hasSize(2));
	}
	@Test
	public void testBuildNodeWithChildren(){
		String input;
		MultiTreeNode node;
		input="a(a1,a2),b(b1, b2, b3), c";
		node=buildTree(input);
		assertThat(node.toString(),equalTo(input));
		assertThat(node.children, hasSize(3));
		assertThat(node.children.get(0).children, hasSize(2));
		assertThat(node.children.get(1).children, hasSize(3));
		assertThat(node.children.get(2).children, hasSize(0));
	}
	
	@Test
	public void testBuildNodeWithChildrenNested1(){
		String input="x(x1(x11,x12),x2(x21))";
		Node node=buildTree(input).ToTarjanNode();
		assertThat(node.c, equalTo("x"));
		assertThat(node.children, hasSize(2));
		
		Node child1=node.children.get(0);
		assertThat(child1.c, equalTo("x1"));
		
		Node child2=node.children.get(1);
		assertThat(child2.c, equalTo("x2"));
		
		
		assertThat(child1.children, hasSize(2));
		assertThat(child1.children.get(0).c, equalTo("x11"));
		assertThat(child1.children.get(1).c, equalTo("x12"));
		
		assertThat(child2.children, hasSize(1));
		assertThat(child2.children.get(0).c, equalTo("x21"));
	}

}
