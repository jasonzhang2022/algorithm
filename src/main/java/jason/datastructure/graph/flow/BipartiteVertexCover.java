package jason.datastructure.graph.flow;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

//https://lucatrevisan.wordpress.com/2011/02/23/cs261-lecture14-algorithms-in-bipartite-graphs/
public class BipartiteVertexCover {
	
	int lnum;
	int rnum;
	public String cover(int[][] matches, int[] pair){
		lnum=matches.length;
		rnum=matches[0].length;
		
		int[] pairR=new int[rnum];
		for (int u=0; u<lnum; u++){
			if (pair[u]!=-1){
				pairR[pair[u]]=u;
			}
		}
		
		int[] L1=new int[lnum];
		int[] R1=new int[rnum];
		//collect free vertex
		for (int i=0; i<lnum; i++){
			if (pair[i]==-1){
				L1[i]=1;
				spanL(L1, R1, pair, pairR, i, matches);
			}
		}
		
		
		List<String> covers=new LinkedList<>();
		//L2
		for (int i=0; i<lnum; i++){
			if (L1[i]==0){
				covers.add("L"+i);
			}
		}
		
		//R1
		for (int i=0; i<rnum; i++){
			if(R1[i]==1){
				covers.add("R"+i);
			}
		}
		
		
		
		return covers.stream().collect(Collectors.joining(","));
		
		
		
	}
	
	
	public void spanL(int[] L1, int[] R1, int[] pairL, int[] pairR, int l, int[][] matches){
		for (int r=0; r<rnum; r++){
			if (matches[l][r]==1 && R1[r]==0){
				R1[r]=1;
				spanR(L1, R1, pairL, pairR, r, matches);
			}
		}
		
	}
	
	public void spanR(int[] L1, int[] R1, int[] pairL, int[] pairR, int r, int[][] matches){
		int l=pairR[r];
		if (L1[l]==1){
			return;
		}
		L1[l]=1;
		spanL(L1, R1, pairL, pairR, l, matches);
	}
	
	//Only R1 
	public void test(){
		int[][] edges={
				{1,1,0},
				{0,1,0},
				{1,0,0}
		};
		BipartiteMatchHopcroftKarp matcher=new BipartiteMatchHopcroftKarp();
		int[] pair=matcher.match(edges);
		
		String cover=cover(edges, pair);
		assertThat(cover, equalTo("R0,R1"));
	}
	//L2 and R1
	@Test
	public void test1(){
		int[][] edges={
				{1,1,0, 0},
				{0,1,0,0},
				{1,0,0,0}, 
				{0,0,1,1}
		};
		BipartiteMatchHopcroftKarp matcher=new BipartiteMatchHopcroftKarp();
		int[] pair=matcher.match(edges);
		
		String cover=cover(edges, pair);
		assertThat(cover, equalTo("L3,R0,R1"));
	}
	
	//L2 and R1, has cross edge
		@Test
		public void test2(){
			int[][] edges={
					{1,1,0, 0},
					{0,1,0,0},
					{1,0,0,0}, 
					{0,1,1,1}
			};
			BipartiteMatchHopcroftKarp matcher=new BipartiteMatchHopcroftKarp();
			int[] pair=matcher.match(edges);
			
			String cover=cover(edges, pair);
			assertThat(cover, equalTo("L3,R0,R1"));
		}
	
}
