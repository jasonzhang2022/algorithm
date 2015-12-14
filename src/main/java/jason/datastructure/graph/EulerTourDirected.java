package jason.datastructure.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EulerTourDirected extends ConnectivityGraph {

	//singly-linked cycle
	public static class VertexLink {
		int u;
		VertexLink after;
		public VertexLink(int u){
			this.u=u;
			after=this;
			
		}
		
		//v->u
		public void insertAfter(VertexLink v){
			v.after=this.after;
			this.after=v;
		}
		
	}
	
	public EulerTourDirected(int v) {
		super(v);
	}
	
	
	
	public boolean isEulerianCycle(){
		
		//in degree equal out degree.
		int[] indegree=new int[V];
		for (int i=0; i<V; i++){
			for (int v: adj[i]){
				indegree[v]++;
			}
		}
		
		for (int i=0; i<V; i++){
			if (adj[i].size()!=indegree[i]){
				return false;
			}
		}
		
		
		//all edges are connected.
		int[] color=new int[V];
		Arrays.fill(color, 0);
		//find the first vertex with some edge
		int firstVertex=0;
		for (int i=0; i<V; i++){
			if (adj[i].size()>0){
				firstVertex=i;
				break;
			}
		}
		
		DFSUtil(firstVertex, color);
		
		//all vertices with edge are touched.
		for (int i=0; i<V; i++){
			if (adj[i].size()>0 && color[i]==0){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isEulerianPath(){
		
		//in degree equal out degree.
		int[] indegree=new int[V];
		for (int i=0; i<V; i++){
			for (int v: adj[i]){
				indegree[v]++;
			}
		}
		
		int firstUnequal=-1;
		int secondUnequal=-1;
		//can only have two vertex with unequal degree.
		for (int i=0; i<V; i++){
			if (adj[i].size()!=indegree[i]){
				if (firstUnequal==-1){
					firstUnequal=i;
				} else if (secondUnequal==-1){
					secondUnequal=i;
				} else{
					return false;
				}
			}
		}
		
		if (adj[firstUnequal].size()-indegree[firstUnequal]!=-1 && adj[firstUnequal].size()-indegree[firstUnequal]!=1){
			return false;
		}
		if (adj[secondUnequal].size()-indegree[secondUnequal]!=-1 && adj[secondUnequal].size()-indegree[secondUnequal]!=1){
			return false;
		}
		
		if (adj[firstUnequal].size()-indegree[firstUnequal] + adj[secondUnequal].size()-indegree[secondUnequal]!=0){
			return false;
		}
		
		int startVextex=firstUnequal;
			//second has 
		if (adj[secondUnequal].size()>indegree[secondUnequal]){
			startVextex=secondUnequal;
		}
		
		
		
		//all edges are connected.
		int[] color=new int[V];
		Arrays.fill(color, 0);
		
		
		DFSUtil(startVextex, color);
		
		//all vertices with edge are touched.
		//Should be Strongly connected
		for (int i=0; i<V; i++){
			if (adj[i].size()>0 && color[i]==0){
				return false;
			}
		}
		
		return true;
	}
	
	
	public void DFSUtil(int u, int[] color){
		color[u]=1; //grey;
		
		for (int v: adj[u]){
			if (color[v]==0){
				DFSUtil(v, color);
			}
		}
		
		color[u]=2; //black
	}
	
	
	public void outputTour(VertexLink v){
		
		VertexLink current=v;
		do {
			System.out.printf("%d->", current.u);
			current=current.after;
		} while (current!=v);
		System.out.println("");
	}
	
	/**
	 * indices[u] hold the next edge to be visited for vertext U
	 */
	int[] indices;
	
	
	/**
	 * Start from firstVextex, add one loop
	 * @param firstVextex
	 * @param links
	 */
	public void addOneLoop(int firstVextex, VertexLink[] links){
		int u=firstVextex;
		//hold all vertex in current loop
		VertexLink[] newlinks=new VertexLink[V];
		VertexLink linkPoint=null;
		VertexLink prev=null;
		
		do {
			VertexLink newVertex=new VertexLink(u);
			
			//only add the vertex to newlinks first time a vextex is found.
			if (newlinks[u]==null){
				newlinks[u]=newVertex;
			}
			//also add it to links if this is the first time vertex is reached
			if (links[u]==null){
				links[u]=newVertex;
				
			} else if (newlinks[u]!=links[u]) {
				//if we touch the vertex in both old loop and new loop, we can merge loop 
				linkPoint=newVertex;
			}
			if (prev!=null){
				prev.insertAfter(newVertex);
			}
			if (indices[u]==adj[u].size()){
				//no further vertex
				u=-1;
			} else{
				u=adj[u].get(indices[u]++);
			}
			prev=newVertex;
			
			//no further edge or we have a loop.
		} while (u>=0 && u!=firstVextex);
		
		//outputTour(prev);
		if (linkPoint!=null){
			//merge
			VertexLink oldVertex=links[linkPoint.u];
			VertexLink newAfter=linkPoint.after;
			VertexLink oldAfter=oldVertex.after;
			
			linkPoint.after=oldAfter;
			oldVertex.after=newAfter;
		}
		
	}
	public String tour(){
		indices=new int[V];
		Arrays.fill(indices, 0);
		VertexLink[] links=new VertexLink[V];
		
		for (int i=0; i<V; i++){
			while (indices[i]<adj[i].size()){
				addOneLoop(i, links);
			}
		}

		
		IntStream.Builder builder=IntStream.builder();
		
		VertexLink current=links[0];
		VertexLink first=links[0];
		while (current.after!=first){
			builder.add(current.u);
			current=current.after;
		}
		builder.add(current.u);
		boolean connectedToFirst=false;
		int u=current.u;
		for (int v: adj[u]){
			if (v==0){
				connectedToFirst=true;
				break;
			}
		}
		if (connectedToFirst){
			builder.add(0);
		}
		
		return builder.build().mapToObj(String::valueOf).collect(Collectors.joining("->"));

	}

}
