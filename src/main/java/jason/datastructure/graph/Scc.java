package jason.datastructure.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class Scc {
	
	
	int[] colors;
	Stack<Integer> stack;
	
	Set<String> scc;
	//http://www.geeksforgeeks.org/strongly-connected-components/
	//Kosaraju’s algorithm
	public List<Set<String>> findScc(AjacentGraph graph){
		
		
		//use stack for topological sorting
		stack=new Stack<>();
		
		//first DFS
		colors=new int[graph.ajacentList.size()];
		Arrays.fill(colors, 0);
		for (int i=0; i<colors.length; i++){
			if (colors[i]==0){
				DFS(graph, i);
			}
		}
		List<Set<String>> result=new LinkedList<>();
		
		//transpose
		AjacentGraph t=graph.transpose();
		
		//second DFS
		Arrays.fill(colors, 0);
		while (!stack.isEmpty()){
			int i=stack.pop();
			if (colors[i]==0){
				if (scc!=null){
					result.add(scc);
				}
				scc=new HashSet<String>();
				DFST(t, i);
			}
		}
		result.add(scc);
		
		return result;
		
		
		
		
	}
	
	
	/*
	 * 22.5: 
	 * Give an O (V + E)-time algorithm to compute the component graph of a directed
graph G = (V, E). Make sure that there is at most one edge between two vertices
in the component graph your algorithm produces.
	 */
	
	
	
	
	public void DFS(AjacentGraph graph, int i){
		colors[i]=1; //black
		
		for (Edge edge: graph.getEdges(i)){
			int to=edge.to;
			if (colors[to]==0){
				DFS(graph, to);
			}
		}
		stack.push(i);
		
	}
	
	public void DFST(AjacentGraph graph, int i){
		colors[i]=1; //black
		
		for (Edge edge: graph.getEdges(i)){
			int to=edge.to;
			if (colors[to]==0){
				DFST(graph, to);
			}
		}
		scc.add(graph.vertices.getVertexByIndex(i).name);
		
	}
	
	
	
	
	
	@Test 
	public void testSccCompute(){
		AjacentGraph g=new AjacentGraph();
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		g.addVertex("f");
		g.addVertex("g");
		g.addVertex("h");
		
		g.initGraph();
		g.addEdge("a", "b", 1);
		g.addEdge("b", "c", 1);
		g.addEdge("b", "e", 1);
		g.addEdge("e", "a", 1);
		g.addEdge("e", "f", 1);
		g.addEdge("b", "f", 1);
		
		g.addEdge("f", "g", 1);
		g.addEdge("g", "f", 1);
		g.addEdge("c", "g", 1);
		g.addEdge("c", "d", 1);
		g.addEdge("d", "c", 1);
		g.addEdge("g", "h", 1);
		g.addEdge("d", "h", 1);
		g.addEdge("h", "h", 1);
		
		List<Set<String>> result=findScc(g);
		String resultStr=result.stream().map(s->s.stream().collect(Collectors.joining(","))).collect(Collectors.joining("|"));
		System.out.println(resultStr);
		String expected="a,b,e|c,d|f,g|h";
		assertThat(resultStr, equalTo(expected));
		
		
	}
	
	
	
	int[] low;
	int[] disc;
	Stack<Integer> oneScc;
	int time;
	List<String> result;
	
	public void findSccUseLowDiscUtil(AjacentGraph graph, int u){
		
		colors[u]=1; //grey
		low[u]=disc[u]=++time;
		stack.push(u);
		
		for (Edge e: graph.getEdges(u)){
			int v=e.to;
			if (colors[v]==0){
				//not visited before
				findSccUseLowDiscUtil(graph, v);
				low[u]=Math.min(low[v], low[u]);
			} else if (colors[v]==1){
				//v is parent of U
				low[u]=Math.min(low[u], disc[v]);
			} else {
				//forward or cross, ignore it.
			}
		}
		
		colors[u]=2;
		
		IntStream.Builder builder=IntStream.builder();
		if (low[u]==disc[u]){
			//u is the root of one scc, we collect it
			//if the low[u] is not from children, all scc children belongs to are already processed.
			while (stack.peek()!=u){
				int c=stack.pop();
				builder.add(c);
			}
			
			stack.pop();//pop u itself
			builder.add(u); 
			result.add(builder.build().sorted().mapToObj(i->graph.vertices.getVertexByIndex(i).name).collect(Collectors.joining(",")));
		}
		

	}
	
	public void findSccUseLowDisc(AjacentGraph graph){
		int V=graph.size();
		result=new ArrayList<>();
		low=new int[V];
		disc=new int[V];
		stack=new Stack<>();
		time=0;
		colors=new int[V];
		Arrays.fill(colors, 0);
		
		for (int i=0; i<V; i++){
			if (colors[i]==0){
				findSccUseLowDiscUtil(graph, i);
			}
		}
		
		
	}
	

	@Test 
	public void testSccComputeUsingLowDsic(){
		AjacentGraph g=new AjacentGraph();
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		g.addVertex("f");
		g.addVertex("g");
		g.addVertex("h");
		
		g.initGraph();
		g.addEdge("a", "b", 1);
		g.addEdge("b", "c", 1);
		g.addEdge("b", "e", 1);
		g.addEdge("e", "a", 1);
		g.addEdge("e", "f", 1);
		g.addEdge("b", "f", 1);
		
		g.addEdge("f", "g", 1);
		g.addEdge("g", "f", 1);
		g.addEdge("c", "g", 1);
		g.addEdge("c", "d", 1);
		g.addEdge("d", "c", 1);
		g.addEdge("g", "h", 1);
		g.addEdge("d", "h", 1);
		g.addEdge("h", "h", 1);
		
		findSccUseLowDisc(g);
		String resultStr=result.stream().collect(Collectors.joining("|"));
		System.out.println(resultStr);
		String expected="h|f,g|c,d|a,b,e";
		assertThat(resultStr, equalTo(expected));
		
		
	}
	
	
	
	/*
	 * 22.5: 
	 * Give an O (V + E)-time algorithm to compute the component graph of a directed
graph G = (V, E). Make sure that there is at most one edge between two vertices
in the component graph your algorithm produces.
	 */
	 public AjacentGraph componentGraph(AjacentGraph g){
		 int n=g.vertices.verticesArray.size();
		 List<Set<String>> result=findScc(g);
		 
		
		 
		 AjacentGraph ret=new AjacentGraph();
		 for (int i=0; i<result.size(); i++){
			 ret.addVertex(new Integer(i).toString());
		 }
		 ret.initGraph();
		 
		 //a map from vertex index to scc
		 int[] scc=new int[n];
		 int i=0;
		 for (Set<String> strs: result){
			 for (String name: strs){
				 scc[g.vertices.getVertexByName(name).index]=i;
			 }
			 i++;
		 }
		 
		 //extra data structure outside graph to assistant graph building
		 Set<String> sccedge=new HashSet<>();
		 g.ajacentList.forEach(edges->edges.forEach(edge->{
			 int from=edge.from;
			 int to=edge.to;
			 if (scc[from]==scc[to]){
				 return;
			 }
			 int sccfrom=scc[from];
			 int sccto=scc[to];
			 String key=String.format("%d_%d", sccfrom, sccto);
			 if (sccedge.contains(key)){
				 //already one edge like this
				 return;
			 }
			 ret.addEdge(sccfrom, sccto);
			 sccedge.add(key);
			
		 }));
		 
		 return ret;
	 }
	
		@Test 
		public void testSccCollapse(){
			AjacentGraph g=new AjacentGraph();
			g.addVertex("a");
			g.addVertex("b");
			g.addVertex("c");
			g.addVertex("d");
			g.addVertex("e");
			g.addVertex("f");
			g.addVertex("g");
			g.addVertex("h");
			
			g.initGraph();
			g.addEdge("a", "b", 1);
			g.addEdge("b", "c", 1);
			g.addEdge("b", "e", 1);
			g.addEdge("e", "a", 1);
			g.addEdge("e", "f", 1);
			g.addEdge("b", "f", 1);
			
			g.addEdge("f", "g", 1);
			g.addEdge("g", "f", 1);
			g.addEdge("c", "g", 1);
			g.addEdge("c", "d", 1);
			g.addEdge("d", "c", 1);
			g.addEdge("g", "h", 1);
			g.addEdge("d", "h", 1);
			g.addEdge("h", "h", 1);
			
			//an extra dege from first component to third
			g.addEdge("a", "f", 1);
			
			 AjacentGraph scc=componentGraph(g);
			 //a,b,e|c,d|f,g|h
			 assertThat(scc.vertices.verticesArray.size(), equalTo(4));
			 assertThat(scc.getEdges(0), hasSize(2));
			 assertThat(scc.getEdges(1), hasSize(2));
			 assertThat(scc.getEdges(2), hasSize(1));
			 assertThat(scc.getEdges(3), hasSize(0));
			 
			 
		}

}
