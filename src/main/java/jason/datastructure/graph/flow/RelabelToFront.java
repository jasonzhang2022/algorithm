package jason.datastructure.graph.flow;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.junit.Test;

public class RelabelToFront {

	public static class ListEntry {
		int v;
		ListEntry next;
		ListEntry prev;

		public ListEntry add(int v) {
			ListEntry entry = new ListEntry();
			entry.v = v;
			this.next = entry;
			entry.prev = this;
			return entry;
		}

		public ListEntry remove() {
			if (this.prev != null) {
				this.prev.next = this.next;
			}
			if (this.next != null) {
				this.next.prev = this.prev;
			}

			this.prev = null;
			this.next = null;
			return this;
		}

		public ListEntry beforeHead(ListEntry head) {
			head.prev = this;
			this.next = head;

			this.prev = null;
			return this;
		}

	}

	int n;
	int[] h;
	int[][] residual;
	LinkedList<Integer>[] neighbors;
	int[][] graph;
	int[] e; // excess
	Iterator<Integer>[] next;

	public int maximumFlow(int[][] graph, int s, int t) {
		this.graph = graph;
		n = graph[0].length;

		h = new int[n];
		e = new int[n];

		// initialize residual graph
		residual = new int[n][n];
		for (int u = 0; u < n; u++) {
			for (int v = 0; v < n; v++) {
				residual[u][v] = graph[u][v];
			}
		}

		// initialize neighbors and next
		neighbors = new LinkedList[n];
		for (int i = 0; i < n; i++) {
			neighbors[i] = new LinkedList<>();
		}

		for (int u = 0; u < n; u++) {
			for (int v = 0; v < n; v++) {
				if (graph[u][v] > 0) {
					neighbors[u].add(v);
					neighbors[v].add(u);
				}
			}
		}
		next = new Iterator[n];
		for (int i = 0; i < n; i++) {
			next[i] = neighbors[i].iterator();
		}
		ListEntry head = null;
		ListEntry current = null;
		// initialize list:
		for (int i = 0; i < n; i++) {
			if (i != s && i != t) {
				if (head == null) {
					head = new ListEntry();
					head.v = i;

					current = head;
				} else {
					current = current.add(i);
				}
			}
		}
		initialize_preflow(s, t);

		current = head;
		while (current != null) {
			int u = current.v;

			int oldheight = h[u];
			discharge(u);
			if (oldheight != h[u] && current != head) {
				current.remove();
				head = current.beforeHead(head);
				current = head;
			}
			current = current.next;
		}

		return e[t];
	}

	public void initialize_preflow(int s, int t) {
		Arrays.fill(e, 0);
		Arrays.fill(h, 0);
		h[s] = n;

		// saturating push from s;
		for (int v = 0; v < n; v++) {
			if (graph[s][v] > 0) {
				// remove the edge;
				residual[s][v] = 0;
				residual[v][s] = graph[s][v];

				e[v] = graph[s][v];
				e[s] -= graph[s][v];
			}
		}

	}

	public void discharge(int u) {
		while (e[u] > 0) {

			Iterator<Integer> upointer = next[u];
			if (upointer.hasNext()) {
				int v = upointer.next();
				if (h[u] == h[v] + 1 && residual[u][v] > 0) {
					push(u, v);
				}
			} else {
				// reach the end.
				relabel(u);
				upointer = neighbors[u].iterator();
				next[u] = upointer;
			}
		}
	}

	public int push(int u, int v) {
		int flow = Math.min(e[u], residual[u][v]);
		residual[u][v] = residual[u][v] - flow;
		residual[v][u] += flow;

		e[v] += flow;
		e[u] -= flow;
		return flow;
	}

	public int relabel(int u) {
		int newlabel = Integer.MAX_VALUE;
		for (int v = 0; v < n; v++) {
			if (residual[u][v] > 0) {
				newlabel = Math.min(newlabel, h[v]);
			}
		}
		h[u] = newlabel + 1;
		return newlabel + 1;
	}

	// Introduction og algorithm
	// page 726
	@Test
	public void test() {
		int[][] weight = { { 0, 16, 13, 0, 0, 0 }, { 0, 0, 0, 12, 0, 0 }, { 0, 14, 0, 0, 14, 0 }, { 0, 0, 9, 0, 0, 20 },
				{ 0, 0, 0, 7, 0, 4 }, { 0, 0, 0, 0, 0, 0 }, };

		int flow = maximumFlow(weight, 0, 5);

		assertEquals(flow, 23);

		List<String> cuts = findCuts(0, 5);
		assertThat(cuts, hasSize(4));
		assertThat(cuts, containsInAnyOrder("4->5", "1->3", "3->2", "4->3"));
	
		

		List<List<Integer>> pathes = findPaths(0, 5);
		List<String> pathStrs = pathes.stream()
				.map(l -> l.stream().map(i -> i.toString()).collect(Collectors.joining("->")))
				.collect(Collectors.toList());
		assertThat(pathStrs, hasSize(3));
		assertThat(pathStrs, containsInAnyOrder("0->1->3->5", "0->2->4->5", "0->2->4->3->5"));

	}

	public List<String> findCuts(int s, int t) {

		List<String> cuts = new LinkedList<>();
		int[] color = new int[n];
		Arrays.fill(color, 0); // white
		Queue<Integer> discovered = new LinkedList<>();

		BitSet sourceSet = new BitSet(n);
		sourceSet.set(s);

		color[s] = 1; // grey
		discovered.offer(s);

		while (!discovered.isEmpty()) {
			int u = discovered.poll();

			// u has not be discovered
			for (int v = 0; v < n; v++) {
				if (color[v] == 0 && residual[u][v] != 0) {
					color[v] = 1;
					discovered.offer(v);
					sourceSet.set(v);
				}
			}
		}
		System.out.print("source set ");

		for (int i = 0; i < n; i++) {
			if (sourceSet.get(i)) {
				System.out.print(i);
			}
		}
		System.out.println("");

		color = new int[n];
		Arrays.fill(color, 0); // white
		discovered = new LinkedList<>();

		BitSet sinkSet = new BitSet(n);
		sinkSet.set(t);

		color[t] = 1; // grey
		discovered.offer(t);

		while (!discovered.isEmpty()) {
			int u = discovered.poll();

			// u has not be discovered
			for (int v = 0; v < n; v++) {
				if (color[v] == 0 && residual[u][v] != 0) {
				

					if (sourceSet.get(v)) {
						if (graph[u][v] > 0) {
							cuts.add(u + "->" + v);
						} else {
							cuts.add(v + "->" + u);
						}
					} else {
						color[v] = 1;
						discovered.offer(v);
						sinkSet.set(v);
					}
				}
			}
		}
		System.out.print("sink set ");

		for (int i = 0; i < n; i++) {
			if (sinkSet.get(i)) {
				System.out.print(i);
			}
		}
		System.out.println("");
		return cuts;

	}

	public List<List<Integer>> findPaths(int s, int t) {
		List<List<Integer>> pathes = new LinkedList<>();

		while (e[t] > 0) {
			pathes.add(findPath(s, t));
		}
		return pathes;

	}

	public List<Integer> findPath(int s, int t) {
		List<Integer> path = new LinkedList<>();
		int current = t;
		int flow = e[t];
		while (current != s) {
			for (int i = 0; i < n; i++) {
				if (residual[current][i] > 0 && graph[current][i] == 0) {
					path.add(current);
					flow = Math.min(flow, residual[current][i]);
					current = i;
					break;
				}
			}
		}
		path.add(s);
		e[t] -= flow;

		Iterator<Integer> iter = path.iterator();
		int u = iter.next();

		while (iter.hasNext()) {
			int v = iter.next();
			residual[u][v] -= flow;
			u = v;
		}

		Collections.reverse(path);
		return path;
	}

}
