package jason.algorithm.recursive;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import jason.datastructure.disjointset.TarjanOfflineLCA.Node;

public class TreeBuilder {
	public static  Node buildNode1(char[] chars, int[] offset) {
		
		
		
		//strip , and space character
		/*
		while (chars[offset]==',' || chars[offset]==' '){
			offset++;
			continue;
		}*/
		Node node=new Node();
		node.children=new LinkedList<>();
		StringBuilder sb=new StringBuilder();
		while (offset[0]<chars.length && chars[offset[0]]!='(' && chars[offset[0]]!=')' && chars[offset[0]]!=','){
			sb.append(chars[offset[0]]);
			offset[0]++;
		}
		
		node.c=sb.toString();
		//nested children
		boolean hasChildren=false;
		if (offset[0]<chars.length && chars[offset[0]]=='('){
			hasChildren=true;
			while (chars[offset[0]]!=')'){
				offset[0]++;
				Node child=buildNode1(chars, offset);
				node.children.add(child);
			}
		}
		
		if ( offset[0]<chars.length && chars[offset[0]]==')' && hasChildren){
			offset[0]++;
		}
		return node;
	}
	@Test
	public void testBuildNodeSimple(){
		String input="x";
		int[] offset={0};
		Node node=buildNode1(input.toCharArray(), offset);
		assertThat(node.c, equalTo(input));
		assertTrue(node.children==null || node.children.isEmpty());
	}
	

	
	
	@Test
	public void testBuildNodeWithChildrenSimple(){
		String input="x(x1,x2)";
		int[] offset={0};
		Node node=buildNode1(input.toCharArray(), offset);
		assertThat(node.c, equalTo("x"));
		assertThat(node.children, hasSize(2));
		
		Node child1=node.children.get(0);
		assertThat(child1.c, equalTo("x1"));
		
		Node child2=node.children.get(1);
		assertThat(child2.c, equalTo("x2"));
		
		
		
	}
	
	@Test
	public void testBuildNodeWithChildrenNested() {
		String input="x(x1(x11,x12),x2(x21))";
		int[] offset={0};
		Node node=buildNode1(input.toCharArray(), offset);
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
	
	
public static  Node buildNode(char[] chars, int[] offset){
		
		Node node=new Node();
		StringBuilder key=new StringBuilder();
		int i=offset[0];
		for (; i<chars.length; i++){
			if (chars[i]=='(' || chars[i]==',' || chars[i]==')' ){
				break;
			}
			key.append(chars[i]);
		}
		node.c=key.toString();
		offset[0]=i;
		if (i<chars.length && chars[i]=='('){
			
			//we build children
			node.children=new LinkedList<>();
			do {
				offset[0]++;
				Node child=buildNode(chars, offset);
				node.children.add(child);
			} while (chars[offset[0]]==',' && offset[0]<chars.length);
			
			offset[0]++; //skip ")"
			//chars[] should be ")"
		} else {
			//don't skip ),
		}
		return node;
		
		
	}
	

	@Test
	public void testBuildNodeSimple1(){
		String input="x";
		int[] offset={0};
		Node node=buildNode(input.toCharArray(), offset);
		assertThat(node.c, equalTo(input));
		assertNull(node.children);
	}
	

	
	
	@Test
	public void testBuildNodeWithChildrenSimple1(){
		String input="x(x1,x2)";
		int[] offset={0};
		Node node=buildNode(input.toCharArray(), offset);
		assertThat(node.c, equalTo("x"));
		assertThat(node.children, hasSize(2));
		
		Node child1=node.children.get(0);
		assertThat(child1.c, equalTo("x1"));
		
		Node child2=node.children.get(1);
		assertThat(child2.c, equalTo("x2"));
		
		
		
	}
	
	@Test
	public void testBuildNodeWithChildrenNested1(){
		String input="x(x1(x11,x12),x2(x21))";
		int[] offset={0};
		Node node=buildNode(input.toCharArray(), offset);
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
