package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import jason.algorithm.greedy.ActivitySelection.Activity;

/*
 *  Weighted job schedule: http://www.geeksforgeeks.org/weighted-job-scheduling/
 * Given N jobs where every job is represented by following three elements of it.
1) Start Time
2) Finish Time.
3) Profit or Value Associated.
Find the maximum profit subset of jobs such that no two jobs in the subset overlap.

 */
public class ActivitySelectionWeightedDAG {
	
	public static class Activity {
		int start;
		int end;
		int weight;
		int index;
		LinkedList<Activity>  adjacent=new LinkedList<>();
		public Activity(int start, int end, int weight) {
			super();
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		
	}

	public List<Activity>  maximizeWeight(int[][] data){
		int n=data.length;
		Activity[] vertices=new Activity[data.length];
		for (int i=0; i<n; i++){
			vertices[i]=new Activity(data[i][0], data[i][1], data[i][2]);
		}
		//sort by start time.
		Arrays.sort(vertices, (u, v)->Integer.compare(u.start, v.start));
		for (int i=0; i<n; i++){
			vertices[i].index=i;
		}
		
		//construct ajacent list
		/*
		 * Connect vertex u to other vertex
		 * 
		 */
		for (int u=0; u<n; u++){
			Activity from=vertices[u];
			Activity firstTo=null;
			//find the first vertex which is compatible.
			for (int v=u+1; v<n; v++){
				if (vertices[v].start>=from.end){
					firstTo=vertices[v];
					from.adjacent.add(firstTo);
					break;
				}
			}
			
			if (firstTo==null){
				break;
			}
			
			//stop at the vertex which is compatible with firstTo.
			//Since this vertex will be connected to firstTo
			for (int v=firstTo.index+1; v<n; v++){
				Activity to=vertices[v];
				if (to.start<firstTo.end){
					from.adjacent.add(to);	
				} else{
					break;
				}
			}
		}
		
		//topologically sort all vertices.
		LinkedList<Integer> sorted=new LinkedList<>();
		BitSet touched=new BitSet(n);
		for (int u=0; u<n; u++){
			if (!touched.get(u)){
				topoSortDFS(sorted, u, touched, vertices);
			}
		}
		
		
		int[] parent=new int[n];
		int[] weights=new int[n];
		Arrays.fill(parent, -1);
		Arrays.fill(weights, -1);
		for(int u=0; u<n; u++){
			weights[u]=vertices[u].weight;
		}
		
		//go over all edges.
		int maxIndex=-1;
		int maxWeight=0;
		for (int u: sorted){
			for (Activity vv: vertices[u].adjacent){
				int v=vv.index;
				if (weights[v]<weights[u]+vertices[v].weight){
					weights[v]=weights[u]+vertices[v].weight;
					parent[v]=u;
				}
			}
		}
		
		for (int u=n-1; u>=0; u--){
			if (weights[u]>maxWeight){
				maxWeight=weights[u];
				maxIndex=u;
			}
		}
		
		LinkedList<Activity> results=new LinkedList<>();
		int current=maxIndex;
		while (current!=-1){
			results.addFirst(vertices[current]);
			current=parent[current];
		}
		
		return results;
		
		
	}
	
	public void topoSortDFS(LinkedList<Integer> sorted, int u, BitSet touched, Activity[] vertices){
		touched.set(u);
		
		for (Activity v: vertices[u].adjacent){
			if (!touched.get(v.index)){
				topoSortDFS(sorted, v.index, touched, vertices);
			}
		}
		sorted.addFirst(u);
	}
	
	@Test
	public void testMaximize(){
		int[][] data = {{3, 10, 20}, {1, 2, 50}, {6, 19, 100}, {2, 100, 200}};
		
		List<Activity> results=maximizeWeight(data);
		String result=results.stream().map(a->a.start+"->"+a.end).collect(Collectors.joining(","));
		int weight=results.stream().mapToInt(a->a.weight).sum();
		
		assertEquals(weight, 250);
		
		assertThat(result, equalTo("1->2,2->100"));
	}
	@Test
	public void testMaximize1(){
		int[][] data = {{0,2, 10}, {1, 3, 5}, {2, 4, 2}, {3, 5, 8}, {5, 7, 4}, {6,8,3}};
		
		List<Activity> results=maximizeWeight(data);
		String result=results.stream().map(a->a.start+"->"+a.end).collect(Collectors.joining(","));
		int weight=results.stream().mapToInt(a->a.weight).sum();
		
		assertEquals(weight, 22);
		
		assertThat(result, equalTo("0->2,3->5,5->7"));
	}
}
