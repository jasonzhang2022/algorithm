package jason.datastructure.graph.flow;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import org.junit.Test;
//http://www.geeksforgeeks.org/hopcroft-karp-algorithm-for-maximum-matching-set-1-introduction/
//http://www.geeksforgeeks.org/hopcroft-karp-algorithm-for-maximum-matching-set-2-implementation/
public class BipartiteMatchHopcroftKarp {

	final int NIL=0;
	final int INF=Integer.MAX_VALUE;
	int[] pairL;
	int[] pairR;
	int[] dist;
	int[][] matches;
	int lnum;
	int rnum;
	public int[] match(int[][] matches){
		this.matches=matches;
		lnum=matches[0].length;
		rnum=matches.length;
		pairL=new int[lnum+1];
		pairR=new int[rnum+1];
		Arrays.fill(pairL, NIL);
		Arrays.fill(pairR, NIL);
		
		dist=new int[lnum+1];
	
		
		while (BFS()){
			for (int u=1; u<=lnum; u++){
				if (pairL[u]==NIL){
					DFS(u);
				}
			}
		}
		
		int[] ret=new int[lnum];
		for (int i=0; i<lnum; i++){
			ret[i]=pairL[i+1]-1;
		}
		return ret;
	}
	
	public boolean BFS(){
		
		Queue<Integer> queue=new LinkedList<>();
		dist[NIL]=INF;
		for (int i=0; i<lnum; i++){
			int u=i+1;
			if (pairL[u]==NIL){
				dist[u]=0; //free
				queue.offer(u);
			} else {
				dist[u]=INF; //matched
			}
		}
		
		while (!queue.isEmpty()){
			int u=queue.poll();
			if (dist[u]<dist[NIL]){
				
				/*
				 * dist[NIL] becomes less than INF if it is touched by free vertex
				 * 
				 * if dist[u] go beyond the shortest augment path, skip it.
				 */
				
				int i=u-1;
				for (int j=0; j<rnum; j++){
					int v=j+1;
					// vertex matching v=x is INF
					// x is 0. The V is not matched before  and is free. This is the end condition 
					// x is not free, and not touched by BFS.
					if (matches[i][j]==1 && dist[pairR[v]]==INF){
						dist[pairR[v]]=dist[u]+1;
						queue.offer(pairR[v]);
					}
				}
			}
		}
		
		//reached by some free v.
		return dist[NIL]!=INF;
		
		
	}
	
	public boolean DFS(int u){
		
		//U=0 first matches the free vertex at right.
		if (u==0){
			return true;
		}
		int i=u-1;
		
		for (int j=0; j<rnum; j++){
			int v=j+1;
			if (matches[i][j]==1 && dist[pairR[v]]==dist[u]+1){
				if (DFS(pairR[v])){
					
					pairR[v]=u;
					pairL[u]=v;
					return true;
				}
			}
		}
		 dist[u] = INF;
	     return false;
	}
	
	//http://www.geeksforgeeks.org/maximum-bipartite-matching/
	@Test
	public void test3(){
		int[][] matches={
				{0,1,1,0,0,0},
				{0,0,0,0,0,0},
				{1,0,0,1,0,0},
				{0,0,1,0,0,0},
				{0,0,0,1,0,0},
				{0,0,0,0,0,1}
		};

		int[] result=match(matches);
		System.out.println(Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(",")));
		int[] expected={1, -1, 0, 2, 3, 5};
		assertTrue(Arrays.equals(result, expected));
	}

}
