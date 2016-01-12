package jason.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Biconnected extends ConnectivityGraph {
	
	

	public Biconnected(int v) {
		super(v);
	}
	
	
	BitSet visited;
	int[] parent; 
	int[] low;
	int[] disc;

	
	
	//stack 
	Stack<String> temp;
	
	
	List<List<String>> result;
	
	public void addEdgeAsString(int u, int v){
		int s=Math.min(u, v);
		int b=Math.max(u, v);
		temp.push(s+"-"+b);
	}
	
	public void bccUtil(int u){
		
		
		visited.set(u);
		
		low[u]=disc[u]=++time;
		
		int children=0;
		
		for (int v: adj[u]){
			if (!visited.get(v)){
				children++;
				//add this edge;
				addEdgeAsString(u, v);
				
				String uvEdge=temp.peek();
				parent[v]=u;
				bccUtil(v);
				low[u]=Math.min(low[u], low[v]);
				
				
				boolean isAP=false;
				
				if (parent[u]==-1 && children>1){
					//U root is AP
					isAP=true;
					
				} else{
					if (low[v]>=disc[u]){
						//u is AP
						isAP=true;
					}
				}
				if (!isAP){
					continue;
				}
				List<String> oneBcc=new LinkedList<>();
				while (!temp.peek().equals(uvEdge)){
					oneBcc.add(temp.pop());
				}
				if (low[v]==disc[u]){
					//edge UV should be the bcc in subtree
					oneBcc.add(uvEdge);
					temp.pop();
				} 
				if (!oneBcc.isEmpty()){
					result.add(oneBcc);
				}
				if (low[v]>disc[u]){
					//bridge edge.
					oneBcc=new LinkedList<>();
					oneBcc.add(uvEdge);
					result.add(oneBcc);
					temp.pop();
				}
			} else{
				//visited
				if (parent[u]!=v){ //not parent, one edge isn't used twice.
					low[u]=Math.min(low[u], disc[v]);
					if (disc[u]>disc[v]){
						//backward edge
						addEdgeAsString(u, v);
					}
				
				}
			}
		}
		
		//first child of the root node.
		if (parent[u]==-1 && !temp.isEmpty()){
			List<String> oneBcc=new LinkedList<>();
			oneBcc.addAll(temp);
			result.add(oneBcc);
		}
	}
	
	public List<List<String>> findBcc(){
		visited=new BitSet(V);
		parent=new int[V];
		Arrays.fill(parent, -1); //-1 no parent;
		low=new int[V];
		disc=new int[V];
		result=new LinkedList<>();
		temp=new Stack<>();
		time=0;
		for (int u=0; u<V; u++){
			if (!visited.get(u)){
				bccUtil(u);
			}
		}
		return result;
	}

	
	
	
}
