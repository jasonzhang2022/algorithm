package jason.datastructure.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
/*
 * Let G = (V, E) be a directed graph in which each vertex u∈V is labeled with a unique
integer L(u) from the set {1, 2, …, |V|}. For each vertex u∈V, let R(u) = {v∈V| ∃ a path
from u to v in G}. Define min(u) to be the vertex in R(u) whose label is minimum, i.e.,
min(u) is the vertex v such that L(v) = min{L(w)|w∈R(u)}. Give an O(V+E)-time
algorithm that computes min(u) for all vertices u∈v. 

 * http://www.fongboy.com/classes/cs180/hw7-sol.pdf
 * First question 22-4
 * 
 * 1. what do we learn: transpose DFS instead of direct DFS
 * 2. linear counting sorting
 */
public class Reachability {

	int[] reach;
	int[] colors;
	Vertex[] labelToVertex;
	public int[] reach(AjacentGraph graph, int[] labels){
		labelToVertex=new Vertex[graph.size()+1];
		
		//sorting by label in linear time. Special counting sorting case.
		for (int i=0; i<graph.size(); i++){
			labelToVertex[labels[i]]=graph.vertices.getVertexByIndex(i);
		}
		
		reach=new int[graph.size()];
		colors=new int[graph.size()];
		Arrays.fill(colors, 0); //white
		
		AjacentGraph t=graph.transpose();
		for (int i=1; i<=graph.size(); i++){
			Vertex v=labelToVertex[i];
			if (colors[v.index]==0){
				//i is the label for v
				DFS(t, v.getIndex(),  i);
			} 
			
		}
		return reach;
	}
	
	
	public void DFS(AjacentGraph graph, int i, int minLabel){
		colors[i]=1; //grey
		for (Edge edge: graph.getEdges(i)){
			if (reach[edge.to]==0){
				//not set minLabel
				reach[edge.to]=minLabel; //no matter the children is touched or not, we set the minLabel.
			}
			if (colors[edge.to]==0){
				DFS(graph, edge.to,  minLabel);
			} 
		}
		colors[i]=2; //black
	}
	
	@Test 
	public void testReachbility(){
		AjacentGraph g=new AjacentGraph();
		int[] label={2,4,7,8,3,1,9,6,5};
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		g.addVertex("f");
		g.addVertex("g");
		g.addVertex("h");
		g.addVertex("i");
		
		g.initGraph();
		g.addEdge("a", "c", 1);
		g.addEdge("b", "e", 1);
		g.addEdge("c", "d", 1);
		g.addEdge("c", "h", 1);
		g.addEdge("d", "e", 1);
		g.addEdge("d", "f", 1);
		
		g.addEdge("e", "g", 1);
		g.addEdge("f", "c", 1);
		g.addEdge("f", "d", 1);
		g.addEdge("f", "g", 1);
		g.addEdge("g", "i", 1);
		g.addEdge("i", "h", 1);
	
		//example result from book
		//int[] expected={1,3,1,1,3,1,5,6,5};
		int[] expected={1,3,1,1,5,1,5,0,6};
		String resultStr=Arrays.stream(reach(g, label)).mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
		System.out.println(resultStr);
		String expectedStr=Arrays.stream(expected).mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
		assertThat(resultStr, equalTo(expectedStr));
		
		
	}
	
}
