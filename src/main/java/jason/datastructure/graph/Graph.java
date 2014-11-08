package jason.datastructure.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Graph {

	Vertices vertices=new Vertices();
	

	public void addVertex(String name) {
		vertices.addVertex(name);
	}
	public abstract void initGraph();
	public abstract void addEdge(String from, String to, int distance);
	
	public abstract List<Edge> getEdges(int index) ;
	
	public static class TempStruct implements Comparable<TempStruct>{
		public int selfVertex;
		public int from;
		public int distance;
		@Override
		public int compareTo(TempStruct o) {
			if (distance<o.distance) {
				return -1;
			}
			if (distance>o.distance) {
				return 1;
			}
			return 0;
		}
		public TempStruct(int selfVertex, int distance) {
			super();
			this.selfVertex = selfVertex;
			this.distance = distance;
		}
		
		
	}
	public List<Vertex> dijkstraShortestPath(String from, String to){
		
		PriorityQueue<TempStruct> unvisited=new PriorityQueue<>();
		int fromIndex=vertices.getVertexByName(from).index;
		int toIndex=vertices.getVertexByName(to).index;
	
		
		TempStruct[] tempStructs=new TempStruct[vertices.verticesArray.size()];
		for (int i=0; i<vertices.verticesArray.size(); i++) {
			TempStruct tempStruct;
			if (i==fromIndex) {
				tempStruct=new TempStruct(i, 0);
				tempStruct.from=-1;
			} else {
				tempStruct=new TempStruct(i, Integer.MAX_VALUE);
				
			}
			unvisited.add(tempStruct);
			tempStructs[i]=tempStruct;
		}

		
		TempStruct tempStruct;
		while ((tempStruct=unvisited.poll())!=null) {
			if (tempStruct.distance==Integer.MAX_VALUE) {
				//smallest one has infinite one, we quit.
				break;
			}
			if (tempStruct.selfVertex==toIndex) {
				break;
			}
			for (Edge edge: getEdges(tempStruct.selfVertex)) {
				if (edge==null) {
					continue;
				}
				TempStruct targetStruct=tempStructs[edge.to];
				if (targetStruct.distance>tempStruct.distance+edge.distance) {
					unvisited.remove(targetStruct);
					targetStruct.distance=tempStruct.distance+edge.distance;
					targetStruct.from=tempStruct.selfVertex;
					unvisited.add(targetStruct);
				}
			}

		}
		if (tempStruct.distance==Integer.MAX_VALUE) {
			return null;
		}
		LinkedList<Vertex> path=new LinkedList<>();
		TempStruct lastOne=tempStruct;
		while (true) {
			path.addFirst(vertices.verticesArray.get(lastOne.selfVertex));
			if (lastOne.from!=-1) {
				lastOne=tempStructs[lastOne.from];
			} else {
				break;
			}
			
		} 
		
		
		return path;
		
	}
}
