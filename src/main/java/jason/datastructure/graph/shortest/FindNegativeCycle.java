package jason.datastructure.graph.shortest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import jason.datastructure.graph.AjacentGraph;
import jason.datastructure.graph.Edge;

public class FindNegativeCycle {

	List<String> cycles;

	public List<String> find(AjacentGraph graph) {
		int V = graph.size();
		int[] color = new int[V];
		int[] parent = new int[V];
		int[] distance = new int[V];

		cycles = new LinkedList<>();
		for (int i = 0; i < V; i++) {
			if (color[i] == 0) {
				parent[i] = -1;
				distance[i] = 0;
				DFSUtil(i, color, parent, distance, graph);
			}
		}
		return cycles;
	}

	public void DFSUtil(int u, int[] color, int[] parent, int[] distance, AjacentGraph graph) {

		color[u] = 1; // grey
		for (Edge edge : graph.ajacentList.get(u)) {
			int v = edge.to;
			if (color[v] == 1) {
				// back edge, we have a cycle
				// do we have negative edge
				if (edge.distance + distance[u] - distance[v] < 0) {
					cycles.add(outputCycle(u, v, parent));
				}

			} else if (color[v] == 2) {
				// cross edge or forward edge

			} else {
				parent[v] = u;
				distance[v] = distance[u] + edge.distance;
				DFSUtil(v, color, parent, distance, graph);
			}
		}

		color[u] = 2;
	}

	public String outputCycle(int u, int v, int[] parent) {
		LinkedList<Integer> cycle = new LinkedList<Integer>();
		cycle.addFirst(v);
		int current = u;
		while (current != v) {
			cycle.addFirst(current);
			current = parent[current];
		}
		cycle.addFirst(v);
		return cycle.stream().map(x -> x.toString()).collect(Collectors.joining("->"));

	}

	@Test
	public void testAjacentListNegativeOneCycle() {
		AjacentGraph graph = new AjacentGraph();
		graph.addVertex("0");
		graph.addVertex("1");
		graph.addVertex("2");
		graph.addVertex("3");
		graph.addVertex("4");
		graph.initGraph();
		graph.addEdge("0", "1", 1);
		graph.addEdge("1", "2", -1);
		graph.addEdge("2", "0", 1);

		graph.addEdge("1", "3", 1);
		graph.addEdge("3", "4", 3);
		graph.addEdge("4", "0", -8);

		List<String> cycles = find(graph);
		String result = cycles.stream().collect(Collectors.joining(";"));

		assertThat(result, equalTo("0->1->3->4->0"));

	}
	@Test
	public void testAjacentListNegativeTwoCycle() {
		AjacentGraph graph = new AjacentGraph();
		graph.addVertex("0");
		graph.addVertex("1");
		graph.addVertex("2");
		graph.addVertex("3");
		graph.addVertex("4");
		graph.initGraph();
		graph.addEdge("0", "1", 1);
		graph.addEdge("1", "2", -1);
		graph.addEdge("2", "0", -1);

		graph.addEdge("1", "3", 1);
		graph.addEdge("3", "4", 3);
		graph.addEdge("4", "0", -8);

		List<String> cycles = find(graph);
		String result = cycles.stream().collect(Collectors.joining(";"));

		assertThat(result,  equalTo("0->1->2->0;0->1->3->4->0"));

	}

}
