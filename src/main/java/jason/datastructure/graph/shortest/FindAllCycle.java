package jason.datastructure.graph.shortest;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import jason.datastructure.graph.ConnectivityGraph;


public class FindAllCycle  extends ConnectivityGraph{

	public FindAllCycle(int v) {
		super(v);
	}
	
	List<String> cycles;
	public List<String> find(){
		int[] color=new int[V];
		int[] parent=new int[V];
		cycles=new LinkedList<>();
		for (int i=0; i<V; i++){
			if (color[i]==0){
				DFSUtil(i, color, parent);
			}
		}
		return cycles;
	}
	
	public void DFSUtil(int u, int[] color, int[] parent){
		
		color[u]=1; //grey
		for (int v : adj[u]){
			if (color[v]==1){
				//back edge, we have a cycle
				cycles.add(outputCycle(u, v, parent));
				
				
			} else if (color[v]==2){
				//cross edge or forward edge
				
			} else {
				parent[v]=u;
				DFSUtil(v, color, parent);
			}
		}
		
		color[u]=2;
	}
 	
	
	
	public String outputCycle(int u, int v, int[] parent){
		LinkedList<Integer> cycle=new LinkedList<Integer>();
		cycle.addFirst(v);
		int current=u;
		while (current!=v){
			cycle.addFirst(current);
			current=parent[current];
		}
		cycle.addFirst(v);
		return cycle.stream().map(x->x.toString()).collect(Collectors.joining("->"));
		
	}
	

}
