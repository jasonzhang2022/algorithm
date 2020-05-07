package jason.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bridge extends ConnectivityGraph {
	
	

	public Bridge(int v) {
		super(v);
	}
	
	
	BitSet visited;
	int[] parent; 
	int[] low;
	int[] disc;
	List<String> bridges;
	
	public void bridgeUtil(int u){
		
		
		visited.set(u);
		
		low[u]=disc[u]=++time;
		
		for (int v: adj[u]){
			if (!visited.get(v)){
				
				parent[v]=u;
				bridgeUtil(v);
				low[u]=Math.min(low[u], low[v]);
				
				if (low[v]>disc[u]){
					//we findUsingArray a bridge
					bridges.add(Math.min(u, v)+"-"+ Math.max(u, v));
				}
			} else{
				//visited
				if (parent[u]!=v){
					low[u]=Math.min(low[u], disc[v]);
				}
			}
		}
	}
	
	public List<String> findBridge(){
		visited=new BitSet(V);
		parent=new int[V];
		Arrays.fill(parent, -1); //-1 no parent;
		low=new int[V];
		disc=new int[V];
		bridges=new ArrayList<>();
		time=0;
		
		for (int u=0; u<V; u++){
			if (!visited.get(u)){
				bridgeUtil(u);
			}
		}
		return bridges;
	}

	
	
	
}
