package jason.datastructure.graph.flow;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class FordFulkerson {
	

	List<String> pathes;
	int[][] residual=null;
	int n;
	int[][] graph;
	public int maximumFlow(int[][] graph, int s, int f){
		this.graph=graph;
		n=graph[0].length;
		//step 1: create residual matrix
		residual=new int[n][n];
		
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				residual[i][j]=graph[i][j];
			}
		}
		
		
		int flow=0;
		int tempflow=0;
		int[] parent=null;
		pathes=new ArrayList<>(20);
		do {
			parent=new int[n];
			Arrays.fill(parent, -1);
			tempflow=findPath(residual, parent, s, f);
			flow+=tempflow;
			if (tempflow!=0){
				String path=extractPath(parent, s, f, tempflow);
				pathes.add(path);
			}
		} while (tempflow!=0);
		
		
		
		return flow;
	}
	
	
	
	public List<String> findCut(int s, int f){
		int[] sourceset=new int[n];
		//set[i]==0: sink set
		//set[i]==1 : source set
		
		int[] color= new int[n];
		Arrays.fill(color, 0); //white;
		
		Queue<Integer> discovered=new LinkedList<>();
		color[s]=1; //discovered
		discovered.offer(s);
		
		List<String> ret=new ArrayList<>();

		while (!discovered.isEmpty()){
			int u=discovered.poll();
			sourceset[u]=1; //source set
			
			for (int v=0;v<n; v++){
				if (residual[u][v]!=0 && color[v]==0){
					color[v]=1;
					discovered.add(v);
				}
			}
			color[u]=2; //full processed
		}
		
		
		int[] sinkset=new int[n];
		color= new int[n];
		Arrays.fill(color, 0); //white;
		discovered=new LinkedList<>();
		color[f]=1; //discovered
		discovered.offer(f);
		while (!discovered.isEmpty()){
			int u=discovered.poll();
			sinkset[u]=1; //sink set
			
			for (int v=0;v<n; v++){
				if (residual[u][v]!=0 && color[v]==0){
					if (sourceset[v]==1){
						//this vertex is in source set and is reachable by sink set.
						//this is one cut
						if (graph[v][u]>0){
							ret.add(v+"->"+u);
						} else {
							ret.add(u+"->"+v);
						}
					} else{
						color[v]=1;
						discovered.add(v);
					}
					
				}
			}
			color[u]=2; //full processed
		}
		
		
		
		System.out.print("source set ");
		for (int i=0; i<n; i++){
			if (sourceset[i]==1){
				System.out.print(i+" ");
			}
		}
		System.out.print("\n");
		
		System.out.print("sink set ");
		for (int i=0; i<n; i++){
			if (sinkset[i]==1){
				System.out.print(i+" ");
			}
		}
		System.out.print("\n");
		return ret;
		
		
	} 

	public String extractPath( int[] parent, int s, int f, int flow){
		System.out.printf("path flow %d, ", flow);
		
		Deque<Integer> dek=new LinkedList<>();
		int end=f;
		int start=-1;
		dek.offerFirst(end);
		do {
			start=parent[end];
			dek.offerFirst(start);
			end=start;
		}while (start!=s);
		
		String path=dek.stream().map(v->v.toString()).collect(Collectors.joining("->"));
		System.out.print(path);
		System.out.println("");
		return path;
		
	}
	
	
	public int findPath(int[][] residual, int[] parent, int s, int f){
		int[] color= new int[n];
		Arrays.fill(color, 0); //white;
		
		Queue<Integer> unprocessed=new LinkedList<>();
		color[s]=1; //discovered
		unprocessed.offer(s);
		
		outer:
		while (!unprocessed.isEmpty()){
			int u=unprocessed.poll();
			for (int v=0;v<n; v++){
				//do we have a edge;
				if (color[v]==0 && residual[u][v]>0){
					color[v]=1;
					unprocessed.offer(v);
					parent[v]=u;
					if (v==f){
						//we find path to f.
						
						break outer;
					}
				}
			}
			color[u]=2; //full processed
		}
		
		
		if (parent[f]==-1){
			return 0;
		}
		//find the minimal flow of the path
		int minFlow=Integer.MAX_VALUE;
		int end=f;
		int start=-1;
		do {
			start=parent[end];
			minFlow=Math.min(minFlow, residual[start][end]);
			end=start;
		}while (start!=s);
		
		
		//adjust residual network 
		end = f;
		start = -1;
		do {
			start = parent[end];	
			//we minus start to end
			//we add end to start at the same time
			residual[start][end]-=minFlow;
			residual[end][start]+=minFlow;
			end=start;
		} while (start != s);
		
		
		return minFlow;
		
		
		
	}
	
	//Introduction og algorithm
	//page 726
	@Test
	public void test(){
		int[][] weight={
				{ 0, 16, 13, 0, 0, 0},
				{ 0, 0, 0, 12,0, 0},
				{ 0, 14, 0, 0, 14, 0},
				{ 0, 0, 9, 0, 0, 20},
				{ 0, 0,0, 7, 0, 4},
				{ 0, 0, 0, 0, 0, 0},
		};
		
		
		int flow= maximumFlow(weight, 0, 5);
		
		assertEquals(flow, 23);

		
		List<String> cuts=findCut(0, 5);
		assertThat(cuts, hasSize(4));
		assertThat(cuts, containsInAnyOrder("4->5", "1->3", "3->2", "4->3"));
	
		assertThat(pathes, hasSize(3));
		assertThat(pathes, containsInAnyOrder("0->1->3->5", "0->2->4->5", "0->2->4->3->5") );
		
	}
	
	
	
	
}
