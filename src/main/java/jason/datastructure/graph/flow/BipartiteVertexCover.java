package jason.datastructure.graph.flow;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.junit.Test;

//https://lucatrevisan.wordpress.com/2011/02/23/cs261-lecture14-algorithms-in-bipartite-graphs/
public class BipartiteVertexCover {

	public String cover(int[][] matches){
		BipartiteMatchHopcroftKarp matcher = new BipartiteMatchHopcroftKarp();
		matcher.match(matches);
		int[] zsetL= new int[matcher.lnum];
		int[] zsetR = new int[matcher.rnum];

		Queue<Integer> sources = new LinkedList<>();

		// add free vertices.
		for (int u=0; u<matcher.lnum; u++) {
			if (matcher.pairL[u]==-1){
				sources.offer(u);
			}
		}

		// which v node is tocuhed.
		while (!sources.isEmpty()){
			// each reached vertex in U is added to zset.
			int u= sources.poll();
			zsetL[u] =1;

			for (int v=0; v<matcher.rnum; v++){
				// not edge
				if (matches[u][v]==0){
					continue;
				}
				if (matcher.pairL[u]==v){
					// not unmatched edge.
					continue;
				}
				if (zsetR[v]==1){
					continue;
				}

				zsetR[v]=1;
				if (matcher.pairR[v]!=-1) {
					// There is an alternating path.
					sources.offer(matcher.pairR[v]);
				}
			}
		}

		LinkedList<String> results = new LinkedList<>();
		for (int i=0; i<zsetL.length; i++){
			if (zsetL[i]==0){
				// all vertex not in zset
				results.add("L"+i);
			}
		}
		for (int i=0; i<zsetR.length; i++){
			// all vertext in zset
			if (zsetR[i]!=0){
				results.add("R"+i);
			}
		}
		return results.stream().collect(Collectors.joining(","));
	}


	public static class TestCase {
		@Test
		public void test() {
			int[][] edges = {
					{1, 1, 0},
					{0, 1, 0},
					{1, 0, 0}
			};


			String cover = new BipartiteVertexCover().cover(edges);
			assertThat(cover, equalTo("R0,R1"));
		}

		@Test
		public void test1() {
			int[][] edges = {
					{1, 1, 0, 0},
					{0, 1, 0, 0},
					{1, 0, 0, 0},
					{0, 0, 1, 1}
			};

			String cover = new BipartiteVertexCover().cover(edges);
			assertThat(cover, equalTo("L3,R0,R1"));
		}

		@Test
		public void test2() {
			int[][] edges = {
					{1, 1, 0, 0},
					{0, 1, 0, 0},
					{1, 0, 0, 0},
					{0, 1, 1, 1}
			};

			String cover = new BipartiteVertexCover().cover(edges);
			assertThat(cover, equalTo("L3,R0,R1"));
		}
	}
	
}
