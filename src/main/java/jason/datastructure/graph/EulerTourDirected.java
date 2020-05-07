package jason.datastructure.graph;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

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

		int[] ins = new int[V];
		int[]  outs = new int[V];
		for (int u=0; u<V; u++){
			for (int v: adj[u]){
				outs[u]++;
				ins[v]++;
			}
		}

		for (int i=0; i<V; i++){
			if (ins[i]!=outs[i]){
				return false;
			}
		}
		return true;

	}
	
	public boolean isEulerianPath(){

		int[] ins = new int[V];
		int[]  outs = new int[V];
		for (int u=0; u<V; u++){
			for (int v: adj[u]){
				outs[u]++;
				ins[v]++;
			}
		}

		Set<Integer> diffs = new HashSet<>();
		for (int i=0; i<V; i++){
			int diff = ins[i]-outs[i];
			if (diff==0){
				continue;
			}
			if (Math.abs(diff)!=1){
				return false;
			}
			diffs.add(diff);
		}

		if (diffs.size()!=2){
			return false;
		}

		if (diffs.stream().mapToInt(Integer::intValue).sum()!=0){
			return false;
		}
		return true;

	}


	private void tourUtil(int u, Set<String> visitedEdges,  Stack<Integer> path) {

		for (int v: adj[u]){
			String key = String.format("%d_%d", u, v);
			if (visitedEdges.contains(key)){
				continue;
			}

			visitedEdges.add(key);
			tourUtil(v, visitedEdges, path);
		}

		path.push(u);

	}
	/**
	 * We need to mark a edge visit or not. we don't need to mark a
	 * @return
	 */
	public String tour(){

		Set<String> visistedEdge = new HashSet<>();
		Stack<Integer> path = new Stack<>();

		tourUtil(0, visistedEdge, path );

		List<Integer> reversed = new ArrayList<>(path.size());
		while (!path.isEmpty()){
			reversed.add(path.pop());
		}
		return reversed.stream().map(i->i.toString()).collect(Collectors.joining("->"));

	}


	public static class TestCase {
		@Test
		public void testEulerCycle(){
			EulerTourDirected graph = new EulerTourDirected(5);

			graph.addEdgeDirect(0, 3);
			graph.addEdgeDirect(3,4);
			graph.addEdgeDirect(4, 0);
			graph.addEdgeDirect(0, 2);
			graph.addEdgeDirect(2, 1);
			graph.addEdgeDirect(1, 0);

			assertTrue(graph.isEulerianCycle());

		}

		@Test
		public void testEulerPath(){
			EulerTourDirected graph = new EulerTourDirected(5);

			graph.addEdgeDirect(0, 3);
			graph.addEdgeDirect(3,4);
			graph.addEdgeDirect(4, 0);
			graph.addEdgeDirect(0, 2);
			graph.addEdgeDirect(2, 1);
			//graph.addEdgeDirect(1, 0);

			assertFalse(graph.isEulerianCycle());
			assertTrue(graph.isEulerianPath());

		}

		@Test
		public void testEulerTour(){
			EulerTourDirected graph = new EulerTourDirected(5);

			graph.addEdgeDirect(0, 3);
			graph.addEdgeDirect(3,4);
			graph.addEdgeDirect(4, 0);
			graph.addEdgeDirect(0, 2);
			graph.addEdgeDirect(2, 1);
			graph.addEdgeDirect(1, 0);

			String cycle = graph.tour();
			assertThat(cycle, equalTo("0->3->4->0->2->1->0"));
		}
	}
}
