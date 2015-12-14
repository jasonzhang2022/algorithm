package jason.datastructure.disjointset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jason.algorithm.recursive.TreeBuilder;
public class TarjanOfflineLCA {

	public static class Node {
		public String c;
		
		public Node parent; //used for disjoin dataset
		public int rank=0;
		
		
		public Node ancestor;
		public List<Node> children;
		
		boolean black=false;
		
	};
	
	public static class Pair {
		String u;
		String v;
		
		String lca;

		public Pair(String u, String v) {
			super();
			this.u = u;
			this.v = v;
		}
		
	}
	
	
	public Node find(Node n){
		if (n.parent!=n){
			n.parent=find(n.parent);
		}
		return n.parent;
	}
	
	public Node makeSet(Node n){
		n.parent=n;
		n.rank=0;
		return n;
	}
	
	public void union(Node x, Node y){
		Node xroot=find(x);
		Node yroot=find(y);
		if (xroot.rank>yroot.rank){
			yroot.parent=xroot;
		} else if (xroot.rank<yroot.rank){
			xroot.parent=yroot;
		} else if (xroot!=yroot) {
			yroot.parent=xroot;
			xroot.rank++;
		}
	}
	
	
	/*
	 *  Understand what is backtrack for tree here
	 * We process the node from left to right, bottom to top (backtrack)
	 * All processed node is put into one set (union) during backtrack.
	 * The ancestor is set during backtrack.
	 */
	
	Map<String, Node> nodes=null;
	public void lca(Node root, List<Pair> pairs){
		makeSet(root);
		//record ancestor so that when it is time to calculate LCA, we know the ancestor.
		root.ancestor=root;
		if (root.children!=null){
			for (Node child: root.children){
				lca(child, pairs);
				
				//merge the root and all its children processed so far in to one set.
				//Either root or one of descendant can be the representative node of the set.
				union(root, child);
				
				//The representative node represents the whole set.
				//we need to make root node as the ancestor for the whole tree
				
				//set the ancestor for all branch already visited.
				//a subtree can have parent as ancestor after all nodes in subtree are processed.
				
				//back track action on one branch
				find(root).ancestor=root;
			}
		}
		//makr node as visited;
		root.black=true;
		
		//backtrack action on whole tree
		for (Pair p: pairs){
			if (p.u.equals(root.c)){
				if (nodes.get(p.v).black){
					//if myself (root) is first time visted (the black is just set to true)
					//and the the other node of the pair(p.v) is already visited. 
					//we need to find the set's ancestor.
					p.lca=find(nodes.get(p.v)).ancestor.c;
				}
			} else if (p.v.equals(root.c)){
				if (nodes.get(p.u).black){
					p.lca=find(nodes.get(p.u)).ancestor.c;
				}
			}
		}
	}
	
	
	
	@Test
	public void testLCA(){
		String input="x(x1(x11,x12),x2(x21(x211,x212),x22))";
		int[] offset={0};
		Node node=TreeBuilder.buildNode1(input.toCharArray(), offset);
		nodes=new HashMap<>();
		assertThat(node.c, equalTo("x"));
		assertThat(node.children, hasSize(2));
		nodes.put(node.c, node);
		
		Node child1=node.children.get(0);
		assertThat(child1.c, equalTo("x1"));
		nodes.put("x1", child1);
		
		Node child2=node.children.get(1);
		assertThat(child2.c, equalTo("x2"));
		nodes.put("x2", child2);
		
		
		assertThat(child1.children, hasSize(2));
		assertThat(child1.children.get(0).c, equalTo("x11"));
		assertThat(child1.children.get(1).c, equalTo("x12"));
		nodes.put("x11", child1.children.get(0));
		nodes.put("x12", child1.children.get(1));
		
		assertThat(child2.children, hasSize(2));
		assertThat(child2.children.get(0).c, equalTo("x21"));
		assertThat(child2.children.get(1).c, equalTo("x22"));
		nodes.put("x21", child2.children.get(0));
		nodes.put("x22", child2.children.get(1));
		
		Node child21=child2.children.get(0);
		assertThat(child21.children, hasSize(2));
		assertThat(child21.children.get(0).c, equalTo("x211"));
		assertThat(child21.children.get(1).c, equalTo("x212"));
		nodes.put("x211", child21.children.get(0));
		nodes.put("x212", child21.children.get(1));
		
		
		List<Pair> pairs=new ArrayList<>(4);
		pairs.add(new Pair("x11", "x12")); //x1
		pairs.add(new Pair("x211", "x212")); //x21
		pairs.add(new Pair("x211", "x22")); //x2
		pairs.add(new Pair("x211", "x1")); //x
		
		lca(node, pairs);
		assertThat(pairs.get(0).lca, equalTo("x1"));
		assertThat(pairs.get(1).lca, equalTo("x21"));
		assertThat(pairs.get(2).lca, equalTo("x2"));
		assertThat(pairs.get(3).lca, equalTo("x"));
		
		
	}
}
