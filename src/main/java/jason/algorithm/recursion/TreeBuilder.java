package jason.algorithm.recursion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jason.datastructure.disjointset.TarjanOfflineLCA.Node;

public class TreeBuilder {

	String specials= "(,)";
	int index=0;
	public MultiTreeNode buildTree(String input){
		index = 0;
		MultiTreeNode root=new MultiTreeNode(null, null);
		buildOneSubTree(input, root);
		if (root.children.isEmpty()){
			return null;
		}
		if (root.children.size() ==1) {
			return root.children.getFirst();
		}
		return root;
	}
	
	public void buildOneSubTree(String input, MultiTreeNode parent){

		while (index<input.length()) {
			// create one node.

			String name = collectName(input);
			MultiTreeNode child  = new MultiTreeNode(name, parent);
			parent.children.add(child);

			if (index<input.length() && input.charAt(index) =='(') {
				index++ ; //skip (
				// we have subtree.
				buildOneSubTree(input, child);

				// when we we back, we are at the position just  after ")
			} else if(index <input.length() && input.charAt(index) ==')')  {
				index ++; //walk over )
				return; //finish subtree construction.
				// This wil wind execution back to line 42.
			}

			// do we have next node
			if (index >=input.length() || input.charAt(index)!=','){
				break;
			} else {
				index++;
			}
		}

	}
	
	public String collectName(String input){
		StringBuilder sb = new StringBuilder();
		while (index<input.length() && specials.indexOf(input.charAt(index))<0){
			sb.append(input.charAt(index++));
		}
		return sb.toString();
	}

	public static class TestCase {
		@Test
		public void testBuildNodeWithChildrenSimple() {
			String input;
			MultiTreeNode node;
			input = "";
			node = new TreeBuilder().buildTree("");
			assertNull(node);
		}

		@Test
		public void testBuildNodeWithChildrenSimple1() {
			String input;
			MultiTreeNode node;
			input = "x";
			node = new TreeBuilder().buildTree("x");
			assertThat(node.toString(), equalTo(input));

		}

		@Test
		public void testBuildNodeWithChildrenSimple2() {
			String input;
			MultiTreeNode node;
			input = "a,b";
			node = new TreeBuilder().buildTree(input);
			assertThat(node.toString(), equalTo("a,b"));
		}

		@Test
		public void testBuildNodeWithChildrenSimple3() {
			String input;
			MultiTreeNode node;
			input = "a(a1,a2),b";
			node = new TreeBuilder().buildTree(input);
			assertThat(node.toString(), equalTo(input));
			assertThat(node.children, hasSize(2));
			assertThat(node.children.get(0).children, hasSize(2));
		}

		@Test
		public void testBuildNodeWithChildren() {
			String input;
			MultiTreeNode node;
			input = "a(a1,a2),b(b1, b2, b3), c";
			node = new TreeBuilder().buildTree(input);
			assertThat(node.toString(), equalTo(input));
			assertThat(node.children, hasSize(3));
			assertThat(node.children.get(0).children, hasSize(2));
			assertThat(node.children.get(1).children, hasSize(3));
			assertThat(node.children.get(2).children, hasSize(0));
		}

		@Test
		public void testBuildNodeWithChildrenNested1() {
			String input = "x(x1(x11,x12),x2(x21))";
			Node node = new TreeBuilder().buildTree(input).ToTarjanNode();
			assertThat(node.c, equalTo("x"));
			assertThat(node.children, hasSize(2));

			Node child1 = node.children.get(0);
			assertThat(child1.c, equalTo("x1"));

			Node child2 = node.children.get(1);
			assertThat(child2.c, equalTo("x2"));


			assertThat(child1.children, hasSize(2));
			assertThat(child1.children.get(0).c, equalTo("x11"));
			assertThat(child1.children.get(1).c, equalTo("x12"));

			assertThat(child2.children, hasSize(1));
			assertThat(child2.children.get(0).c, equalTo("x21"));
		}
	}

}
