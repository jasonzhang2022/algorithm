package jason;

import static org.junit.Assert.*;
import jason.datastructure.graph.AjacenctMatrix;
import jason.datastructure.graph.AjacentGraph;
import jason.datastructure.graph.Graph;
import jason.datastructure.graph.Vertex;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AjacentGraphTest {

	
	public void positiveTest(Graph graph) {
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.initGraph();
		
		graph.addEdge("A", "B", 9);
		graph.addEdge("A", "C", 8);
		graph.addEdge("B", "D", 1);
		graph.addEdge("C", "D", 10);

		List<Vertex> path = graph.dijkstraShortestPath("A", "D");
		
		String pathString=Arrays.deepToString(path.toArray());
		System.out.println(pathString);
		assertEquals("[A, B, D]", pathString);;
	}
	
	public void negativeTest(Graph graph) {
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.initGraph();
		graph.addEdge("A", "B", 9);
		graph.addEdge("A", "C", 8);
		graph.addEdge("D", "E", 1);
	

		List<Vertex> path  = graph.dijkstraShortestPath("A", "D");
		assertNull(path);
	}
	@Test
	public void testAjacentList() {
		positiveTest(new AjacentGraph());
		negativeTest(new AjacentGraph());
	}
	
	@Test
	public void testAjacentMatrix() {
		positiveTest(new AjacenctMatrix());
		negativeTest(new AjacenctMatrix());
	}

}
