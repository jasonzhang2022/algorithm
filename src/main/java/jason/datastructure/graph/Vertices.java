package jason.datastructure.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vertices {

	public Map<String, Vertex> verticeMaps=new HashMap<String, Vertex>();
	public ArrayList<Vertex> verticesArray=new ArrayList<>();
	
	public Vertex addVertex(String vertex) {
		Vertex vertex2=new Vertex(vertex, verticesArray.size());
		verticesArray.add(vertex2);
		verticeMaps.put(vertex, vertex2);
		return vertex2;
	}
	
	public Vertex getVertexByName(String name) {
		return verticeMaps.get(name);
	}
	
	public Vertex getVertexByIndex(int index) {
		return verticesArray.get(index);
	}
	
}
