package jason.datastructure.graph.flow;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class BipartiteMatch {

	public int[] match(int[][] matches){
		int srcNum = matches.length;
		int destNum = matches[0].length;

		int[] srcMatched = new int[srcNum];
		int[] destMatched = new int[destNum];
		Arrays.fill(srcMatched, -1);
		Arrays.fill(destMatched, -1);

		for (int s=0; s<srcNum; s++){
			if (srcMatched[s]==-1){
				//not matched with anymore.
				matchOne(s, matches, destNum, srcMatched, destMatched, new int[destNum]);
			}
		}

		return srcMatched;
	}
	
	/**
	 * Find out whether we can increase the match by finding a match for U.
	 * 
	 * @param u a source
	 * @param matches matches matrix
	 * @param tnum the number of sink elements
	 * @param sMatched sMatched[i] is the matched sink element for an source element i;
	 * @param tMatched tMatched[i] is the source element which matches the sink element i.
	 * @param visited whether we should excluded tTouched[i] from consideration. It should be excluded if 1) this sink i is already
	 * 	considered in the recursive path, or 2) there is no potential flow increase by matching this sink to an alternative source. 
	 * @return matched sink element or -1.
	 */
	public int matchOne(int u, int[][] matches, int tnum, int[] sMatched, int[] tMatched, int[] visited) {

		// This is one DFS search from src to dest
		for (int dest=0; dest<tnum; dest++){

			// already in current DFS path
			if (visited[dest]==1){
				continue;
			}

			// no possible edge;
			if (matches[u][dest]==0){
				continue;
			}

			// dest is not used/matched by any source
			if (tMatched[dest] ==-1){// there is one edge
				sMatched[u] = dest;
				tMatched[dest] = u;
				// found one path;
				return 1;
			}

			// there is a reverse edge from dest to some source
			//tMatched[dest]!=-1;
			int oldSource = tMatched[dest];
			// Match old source with other dest to see whether we can have an extra matching or not.
			visited[dest]=1;
			if (matchOne(oldSource, matches, tnum, sMatched, tMatched, visited)==1){
				// extra path.

				tMatched[dest] = u;
				sMatched[u] =dest;
				return 1;
			}
		}

		// not found.
		return 0;
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

			int[] result = new BipartiteMatch().match(matches);
			System.out.println(Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(",")));
			int[] expected = {1, -1, 0, 2, 3, 5};
			assertTrue(Arrays.equals(result, expected));
		}
	}
	
}
