package jason.datastructure.graph;

import java.util.Arrays;
import java.util.List;


public class AjacenctMatrix extends Graph{

	
	Edge[][] matrix=null;
	
	
	public void initGraph() {
		matrix=new Edge[vertices.verticesArray.size()][vertices.verticesArray.size()];
	}
	public void addEdge(String from, String to, int distance) {
		Vertex fromVertex=vertices.getVertexByName(from);
		Vertex toVertex=vertices.getVertexByName(to);
		Edge edge=new Edge(fromVertex.index, toVertex.index, distance);
		matrix[fromVertex.index][toVertex.index]=edge;
	}
	
	@Override
	public List<Edge> getEdges(int index) {
		return Arrays.asList(matrix[index]);
	}

	
}
