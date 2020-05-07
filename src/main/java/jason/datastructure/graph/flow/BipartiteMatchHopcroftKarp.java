package jason.datastructure.graph.flow;

import static jason.datastructure.graph.ConnectivityGraph.NIL;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import org.junit.Test;
//http://www.geeksforgeeks.org/hopcroft-karp-algorithm-for-maximum-matching-set-1-introduction/
//http://www.geeksforgeeks.org/hopcroft-karp-algorithm-for-maximum-matching-set-2-implementation/

/**
 * How is this different from maixmum flow.
 * 1. A BFS is used to search augmenting path. Much more efficient than try and backtrack DFS.
 *   The BFS finds multiple possible path in one go.
 * 2. keep level which is used for DFS.
 */
public class BipartiteMatchHopcroftKarp {

	int[] pairL;
	int[] pairR;
	int[] level;
	int[][] matches;
	int lnum;
	int rnum;
	public int[] match(int[][] matches){
		this.matches=matches;
		lnum=matches.length;
		rnum=matches[0].length;
		pairL=new int[lnum];
		pairR=new int[rnum];
		Arrays.fill(pairL, -1);
		Arrays.fill(pairR, -1);
		
		level =new int[lnum];
		while (BFS()) {
			for (int u =0; u<lnum; u++){
				if (level[u]==0){
					DFS(u);
				}
			}

		}
		return pairL;
	}

	/*
	 *level[] is BFS level
	 */
	public boolean BFS(){

		Deque<Integer> freeNodes = new LinkedList<>();
		level = new int[lnum];
		Arrays.fill(level, -1);
		// itinialized queues
		for (int i=0; i<lnum; i++){
			if (pairL[i]==-1){ // not paired
				freeNodes.offer(i);
				level[i]=0;
			}
		}

		boolean found = false;
		while (!freeNodes.isEmpty()){
			int u = freeNodes.poll();

			for (int v=0; v<rnum; v++){
				if (matches[u][v]==0){
					//no edge.
					continue;
				}

				//thoretically v can be augmented by multiple u.
				// This algorithm always picks up the node with low index(small u).
				// not matched by any node. Found one path.
				if (pairR[v]==-1){
					found = true;
					continue;
				}


				//if not
				int oldu = pairR[v];
				if (level[oldu]==-1) {
					//an potential alternating path from u->v->oldu.
					level[oldu] = level[u]+1;
					freeNodes.offer(oldu);
				}
			}
		}
		return found;
	}

	// level serves as visited array.
	public boolean DFS(int u){

		int ulevel = level[u];
		level[u] = -1; // u can't be re checked anymore, also serves as a mark the node is visited.
		for (int v=0; v<rnum; v++){
			if (matches[u][v]==0){
				//no edge.
				continue;
			}
			//free node.
			if (pairR[v]==-1){
				pairR[v]=u;
				pairL[u]=v;
				return true;
			}

			int oldu = pairR[v];
			if (level[oldu]==-1){
				continue;
			}
			if (level[oldu] == ulevel+1) {
				if (DFS(oldu)){
					pairR[v]=u;
					pairL[u]=v;
					return true;
				}
			}
		}
		return false;
	}

	public static class TestCase {

		//http://www.geeksforgeeks.org/maximum-bipartite-matching/
		@Test
		public void test3() {
			int[][] matches = {
					{0, 1, 1, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{1, 0, 0, 1, 0, 0},
					{0, 0, 1, 0, 0, 0},
					{0, 0, 0, 1, 0, 0},
					{0, 0, 0, 0, 0, 1}
			};

			int[] result = new BipartiteMatchHopcroftKarp().match(matches);
			System.out.println(Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(",")));
			int[] expected = {1, -1, 0, 2, 3, 5};
			assertTrue(Arrays.equals(result, expected));
		}
	}

}
