package jason.datastructure.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class EulerTourUndirectedSet {

	int n;
	int[][]matrix;

	public EulerTourUndirectedSet(int n) {
		this.n = n;
		matrix=new int[n][n];
	}

	public void addEdge(int u, int v) {
		matrix[u][v]++;
		matrix[v][u]++;
	}

	public static class Node {
		int v;
		Node prev;
		Node next;
		Node root;

		// self loop
		public Node(int v) {
			super();
			this.v = v;
			prev = this;
			next = this;
		}

		public void addNode(Node n) {
			this.next.prev = n;
			n.next = this.next;

			n.prev = this;
			this.next = n;
		}
	}

	public Node[] nodes;
	
	public String tour() {
		nodes =new Node[n];
	

		// loop all edges
		int startVertex = -1;
		while ((startVertex = searchStartVextex()) != -1) {
			addOneCircle(startVertex);
		}

		
		Node startNode = nodes[0];
		return outputChain(startNode);
		
	}
	
	
	public String outputChain(Node startNode){
		IntStream.Builder builder=IntStream.builder();
		
		builder.accept(startNode.v);
		Node prevNode = startNode;
		while (prevNode.next != startNode) {
			prevNode = prevNode.next;
			builder.accept(prevNode.v);
		}
		builder.accept(startNode.v);
		return builder.build().mapToObj(i->String.valueOf(i)).collect(Collectors.joining("->"));
	}

	//find one edge;
	public int searchStartVextex() {
		for (int u = 0; u < n; u++) {
			for (int v=0; v<n; v++){
				if (matrix[u][v]>0){
					return u;
				}
			}
		}
		return -1;
	}


	public Node findRoot(Node n){
		if (n.root==n){
			return n;
		}
		n.root=findRoot(n.root);
		return n.root;
	}
	
	public void addOneCircle(int start){
		List<Node> merges=new LinkedList<>();
		Node newRoot=new Node(start);
		newRoot.root=newRoot;
		if (nodes[start]==null){
			nodes[start]=newRoot;
		} else {
			merges.add(newRoot);
		}
		
		Node prevNode=newRoot;
		int u=prevNode.v;
		
		outer:
		while (true) {
			for (int v=0; v<n; v++){
				if (matrix[u][v]>=1){
					matrix[u][v]--;
					matrix[v][u]--;
					if (v==start){
						break outer;
					}
					
					Node nextNode=new Node(v);
					nextNode.root=newRoot;
					prevNode.addNode(nextNode);
					if (nodes[v]==null){
						nodes[v]=nextNode;
					} else{
						merges.add(nextNode);
					}
					prevNode=nextNode;
					u=v;
					break;
				}
			}
		}
		
		for (Node v: merges){
			mergeLoop(v);
		}

	}
	

	public void mergeLoop(Node newNode) {
		
		Node oldRoot=findRoot(nodes[newNode.v]);
		if (oldRoot==newNode.root){
			return;
		}
		oldRoot.root=newNode.root;
		
		Node n=newNode;
		
		Node oldn = nodes[n.v];
		Node oldNext = oldn.next;
		Node newNext = n.next;

		oldn.next = newNext;
		newNext.prev = oldn;

		n.next = oldNext;
		oldNext.prev = n;

	}

	public static class TestCase {
		@Test
		public void testEulerThreeCirclesShareOnePointUndirected() {
			// Create ArticulationPoints given in above diagrams
			EulerTourUndirectedSet g1 = new EulerTourUndirectedSet(7);
			g1.addEdge(0, 1);
			g1.addEdge(1, 2);
			g1.addEdge(2, 0);

			g1.addEdge(1, 5);
			g1.addEdge(5, 6);
			g1.addEdge(6, 1);

			g1.addEdge(1, 3);
			g1.addEdge(3, 4);
			g1.addEdge(4, 1);
			String result = g1.tour();
			String expected = "0->1->5->6->1->3->4->1->2->0";
			assertThat(result, equalTo(expected));
		}

		@Test
		public void testEulerThreeCirclesUndirected() {
			// Create ArticulationPoints given in above diagrams
			EulerTourUndirectedSet g1 = new EulerTourUndirectedSet(7);
			g1.addEdge(0, 1);
			g1.addEdge(1, 2);
			g1.addEdge(2, 0);

			g1.addEdge(4, 5);
			g1.addEdge(5, 6);
			g1.addEdge(6, 4);

			g1.addEdge(1, 3);
			g1.addEdge(3, 4);
			g1.addEdge(4, 1);
			String result = g1.tour();
			String expected = "0->1->3->4->5->6->4->1->2->0";
			assertThat(result, equalTo(expected));
		}

		@Test
		public void testEulerTourSquareUndirected() {
			// Create ArticulationPoints given in above diagrams
			EulerTourUndirectedSet g1 = new EulerTourUndirectedSet(4);
			g1.addEdge(0, 1);
			g1.addEdge(1, 2);
			g1.addEdge(2, 3);
			g1.addEdge(3, 0);

			String result = g1.tour();
			String expected = "0->1->2->3->0";
			assertThat(result, equalTo(expected));
		}

		@Test
		public void testEulerTwoTrianglesUndirected() {
			// Create ArticulationPoints given in above diagrams
			EulerTourUndirectedSet g1 = new EulerTourUndirectedSet(5);
			g1.addEdge(0, 1);
			g1.addEdge(1, 2);
			g1.addEdge(2, 0);
			g1.addEdge(1, 3);
			g1.addEdge(3, 4);
			g1.addEdge(4, 1);

			String result = g1.tour();
			String expected = "0->1->3->4->1->2->0";
			assertThat(result, equalTo(expected));
		}
		
		@Test
		public void TwoCircleCrossed() {
			// Create ArticulationPoints given in above diagrams
			EulerTourUndirectedSet g1 = new EulerTourUndirectedSet(5);
			g1.addEdge(0, 1);
			g1.addEdge(1, 2);
			g1.addEdge(2, 0);
			g1.addEdge(2, 1);
			g1.addEdge(1, 3);
			g1.addEdge(3, 2);


			String result = g1.tour();
			String expected = "0->1->2->3->1->1->0";
			assertThat(result, equalTo(expected));
		}
	}
}
