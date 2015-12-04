package jason.datastructure.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CycleDetectionOfUndirectedGraph {
	

	public static class VertexNode {
		int parent;
		int rank;
	}
	
	VertexNode[] nodes=null;
	public void makeSet(int i){
		nodes[i]=new VertexNode();
		nodes[i].parent=i;
	}
	
	public int find(int i){
		if (nodes[i].parent!=i){
			nodes[i].parent=find(nodes[i].parent);
		}
		return nodes[i].parent;
	}
	
	public void union(int i, int j){
		int iroot=find(i);
		VertexNode inode=nodes[iroot];
		int jroot=find(j);
		VertexNode jnode=nodes[jroot];
		if (inode.rank<jnode.rank){
			inode.parent=jroot;
		} else if (inode.rank>jnode.rank){
			jnode.parent=iroot;
		} else{
			inode.parent=jroot;
			inode.rank++;
		}
	}
	
	public boolean disjoinsetCycle(AjacentGraph graph){
		
		nodes=new VertexNode[graph.ajacentList.size()];
		for (int i=0; i<nodes.length; i++){
			makeSet(i);
		}
		
		
		for (int i=0; i<nodes.length; i++){
			int root=find(i);
			for (Edge edge: graph.getEdges(i)){
				int to=find(edge.to);
				if (to==root){
					//already in the same set
					return true;
				}
				union(root, to);
			}
		}
		return false;
	}

	@Test 
	public void testAdjacentList(){
		AjacentGraph graph=new AjacentGraph();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.initGraph();
		
		graph.addEdge("A", "B", 1);
		graph.addEdge("A", "C", 1);
		graph.addEdge("C", "B", 1);
		graph.addEdge("C", "D", 1);
		graph.addEdge("D", "E", 1);
		
		assertTrue(disjoinsetCycle(graph));
		
	}
	
	@Test 
	public void testAdjacentListFalse(){
		AjacentGraph graph=new AjacentGraph();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.initGraph();
		
		graph.addEdge("A", "B", 1);
		graph.addEdge("B", "C", 1);
		graph.addEdge("C", "D", 1);
		graph.addEdge("D", "E", 1);
		
		assertFalse(disjoinsetCycle(graph));
		
	}
}
