package jason.datastructure.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HamiltonianCycle  extends ConnectivityGraph {

	boolean directed=true;
	public HamiltonianCycle(int v) {
		super(v);
	}
	
	
	
	public boolean isDirected() {
		return directed;
	}



	public void setDirected(boolean directed) {
		this.directed = directed;
	}



	private String edgeKey(int u, int v){
		return Math.min(u, v)+"_"+Math.max(u, v);
	}
	int[] path;
	public boolean findCycle(){
		int[] color=new int[V];
		Arrays.fill(color, 0);
		path=new int[V];
		
		//start with 0. It doesn't matter what vertex to start if we have a cycle
		path[0]=0;
		Set<String> usedEdges=new HashSet<>();
		return DFSTraverse(0, color, path, usedEdges);
	}
	
	public int[] getPath(){
		return path;
	}
	
	
	
	private boolean DFSTraverse(int fromPos, int[] color, int[] path, Set<String> usedEdges){
		int u=path[fromPos];
		//base case
		if (fromPos==V-1){
			//can we reach zero from last vertex
			//do we have a path to zero
			for (int v: adj[u]){
				if (v==0){
					return true;
				}
			}
			return false;
		}
		
		
	
		color[u]=1; //grey
		
		boolean hasCycle=false;
		for (int v: adj[u]){
			if (color[v]==0 && (directed  ||  !usedEdges.contains(edgeKey(u, v)) )){ //no visited
				
				path[fromPos+1]=v;
				usedEdges.add(edgeKey(u, v));
				hasCycle=DFSTraverse(fromPos+1, color, path, usedEdges);
				if (hasCycle){
					return true;
				} else {
					usedEdges.remove(edgeKey(u, v));
				}
			}
		}
		
		//if code has here, we don't have a cycle
		color[u]=0; //relable U as unvisited.
		
		return false;
		
	}
	
	
	

}
