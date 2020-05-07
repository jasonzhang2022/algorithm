package jason.datastructure.graph.mst;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


import org.junit.Test;

public class MinimumSpanningTree {
	
	public static class Edge {
		int vertex1;
		int vertext2;
		int weight;
		public Edge(int vertex1, int vertext2, int weight) {
			super();
			this.vertex1 = vertex1;
			this.vertext2 = vertext2;
			this.weight = weight;
		}
		
	}
	
	
	public static class DisjoinSet{
		int[] parent;
		int[] rank;
		
		public DisjoinSet(int n){
			parent=new int[n];
			rank=new int[n];
			for (int i=0; i<n; i++){
				parent[i]=i;
				rank[i]=1;
			}
		}
		
		public int find(int i){
			if (parent[i]==i){
				return i;
			}
			int p=find(parent[i]);
			parent[i]=p;
			return p;
		}
		
		
		public void merge(int leftRoot, int rightRoot){
			if (rank[leftRoot]<rank[rightRoot]){
				parent[leftRoot]=rightRoot;
				rank[rightRoot]+=rank[leftRoot];
			} else{
				parent[rightRoot]=leftRoot;
				rank[leftRoot]+=rank[rightRoot];
			}
		}
	}
	
	
	
	public int  kruskal(Edge[] edges, int n ){
		
		DisjoinSet disjoinSet=new DisjoinSet(n);
	
		Arrays.sort(edges, (a, b)->{
			return a.weight-b.weight;
		});
		
		int weight=0;
		int processedEdges=0;
		for (Edge edge: edges){
			int set1=disjoinSet.find(edge.vertex1);
			int set2=disjoinSet.find(edge.vertext2);
			if (set1==set2){
				//we can not do this, otherwise, we have a loop
				continue;
			}
			
			
			processedEdges++;
			disjoinSet.merge(set1, set2);
			weight+=edge.weight;
			
			if (processedEdges==n){
				break;
			}
		}
		
		return weight;
	}
	
	
	public static class Vertex {
		int vertex;
		int edgeWeight=Integer.MAX_VALUE;
		int from;
		public Vertex(int vertex, int edgeWeight, int from) {
			super();
			this.vertex = vertex;
			this.edgeWeight = edgeWeight;
			this.from = from;
		}
		
		
		
	}
	public int  prim(Edge[] edges, int n ){
		
		//need a matrix;
		int[][] matrix=new int[n][n];
		for (Edge edge:edges){
			matrix[edge.vertex1][edge.vertext2]=edge.weight;
			matrix[edge.vertext2][edge.vertex1]=edge.weight;
		}
		
		BitSet inTree=new BitSet(n);
		
		PriorityQueue<Vertex> heap=new PriorityQueue<>((a, b)-> {
			return a.edgeWeight==b.edgeWeight?0:(a.edgeWeight<b.edgeWeight?-1:1); 
		});
		//the purpose of the map is that we could findUsingArray the key in heap so that we can decrease the key.
		Map<Integer, Vertex> vertices=new HashMap<>();
		
		//initlization
		int weight=0;
		inTree.set(0);
		for (int i=1; i<n; i++){
			if (matrix[0][i]!=0){
				Vertex v=new Vertex(i, matrix[0][i], 0);
				vertices.put(i, v);
				heap.offer(v);
			}
		}
		
		int vertextProcessed=1;
		while (vertextProcessed<n){
			Vertex next=heap.poll();
			weight+=next.edgeWeight;
			inTree.set(next.vertex);
			vertextProcessed++;
			
			
			//add new edge: we should use decrease key.
			for (int i=0; i<n; i++){
				if (matrix[next.vertex][i]!=0 && !inTree.get(i)){
					int newWeight=matrix[next.vertex][i];
					if (!vertices.containsKey(i)){
						Vertex v=new Vertex(i, newWeight, next.vertex);
						vertices.put(i, v);
						heap.offer(v);
					} else if (vertices.get(i).edgeWeight>newWeight){
						//best if we could use decrease operation using fibonacci heap
						Vertex old=vertices.get(i);
						heap.remove(old);
						Vertex v=new Vertex(i, newWeight, next.vertex);
						heap.offer(v);
						vertices.put(i, v);
					}
				}
			}
			
		}
		
		return weight;
		
		
	}
	
	@Test
	public void testKruskal(){
		Edge[] edges=new Edge[11];
		edges[0]=new Edge(0, 1, 7);
		edges[1]=new Edge(1, 2, 8);
		edges[2]=new Edge(0, 3, 5);
		edges[3]=new Edge(2, 4, 5);
		edges[4]=new Edge(1, 4, 7);
		edges[5]=new Edge(1, 3, 9);
		edges[6]=new Edge(3, 4, 15);
		edges[7]=new Edge(3, 5, 6);
		edges[8]=new Edge(5, 4, 8);
		edges[9]=new Edge(4, 6, 9);
		edges[10]=new Edge(5, 6, 11);
		
		int expected=5+7+7+5+9+6;
		int result=kruskal(edges, 7);
		assertThat(result, equalTo(expected));
	}
	
	@Test
	public void testPrim(){
		Edge[] edges=new Edge[11];
		edges[0]=new Edge(0, 1, 7);
		edges[1]=new Edge(1, 2, 8);
		edges[2]=new Edge(0, 3, 5);
		edges[3]=new Edge(2, 4, 5);
		edges[4]=new Edge(1, 4, 7);
		edges[5]=new Edge(1, 3, 9);
		edges[6]=new Edge(3, 4, 15);
		edges[7]=new Edge(3, 5, 6);
		edges[8]=new Edge(5, 4, 8);
		edges[9]=new Edge(4, 6, 9);
		edges[10]=new Edge(5, 6, 11);
		
		int expected=5+7+7+5+9+6;
		int result=prim(edges, 7);
		assertThat(result, equalTo(expected));
	}

}
