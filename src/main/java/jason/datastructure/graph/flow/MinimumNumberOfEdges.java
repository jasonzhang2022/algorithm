package jason.datastructure.graph.flow;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

//Introduction to algorithm: 26.2-13
/*
 * Suppose you wish to findUsingArray among all minimum cuts in a flow network G, one that contains the smallest number of edges.
 * Solution
 * Suppose that in original flow network, the cost of all possible cuts are in non-descending order 
 *  C0, C0, ..., C0+i, ..., C0+i+1
 *  
 *  First we have one and more cuts which are minimum, the costs are C0, 
 *  After that we have a set of cuts which are bigger than mimimum, the costs are C0+I , I>=1;
 *  
 *  Let scale each edge capacity to C*|E|
 *  
 *  The new cost for all cuts becomes.
 *  C0*|E| ..., C0*|E|+I*|E|. The order does not change. Minimum  cuts are still be minimum
 *  
 *  Let us use a new capacbity function: C(e)=C*|E|+1;
 *  
 *  The new cost for all cuts becomes.
 *  
 *  CO*|E|+x, C0*|E|+y, ... C0*|E|+I*|E|+z; //x, y, z are number of edges in the cuts
 *  
 *  First, among all minimum cuts using original capacity function, the cuts with small number of edges has a small cost
 *   using the new ca[acity function
 *  
 *  Second C0*|E|+y <C0*|E|+|E|<=C0*|E|+I*|E|<C0*|E|+I*|E|+z, so any non-minimum cuts using original capacity function will 
 *  be non-minimum cuts using the new capacity function. 
 *  
 *   C0*|E|+y <C0*|E|+|E| since y<|E|, all cuts have less than |E| number of edges.
 *  C0*|E|+|E|<=C0*|E|+I*|E|  since I >=1
 *  C0*|E|+I*|E|<C0*|E|+I*|E|+z since z>0;
 *  
 */
public class MinimumNumberOfEdges {
	
	@Test
	public void testFordFulkerson(){
		int[][] capacity={
				{ 0, 1, 1, 0, 0},
				{ 0, 0, 0, 1, 0},
				{ 0, 0, 0, 1, 0},
				{ 0, 0, 0, 0, 2},
				{ 0, 0, 0, 0, 0},
				
		};
		
		int n=capacity[0].length;
		for (int u=0; u<n; u++){
			for (int v=0; v<n; v++){
				if (capacity[u][v]!=0){
					capacity[u][v]=capacity[u][v]*n+1;
				}
			}
		}
		
		FordFulkerson fordFulkerson=new FordFulkerson();
		int flow=fordFulkerson.maximumFlow(capacity, 0, 4);
		List<String> cuts= fordFulkerson.findCut(0, 4);
		
		
		
		assertThat(cuts, hasSize(1));
		assertThat(cuts, containsInAnyOrder("3->4") );
		
		
	}
	
	@Test
	public void testLabel(){
		int[][] capacity={
				{ 0, 1, 1, 0, 0},
				{ 0, 0, 0, 1, 0},
				{ 0, 0, 0, 1, 0},
				{ 0, 0, 0, 0, 2},
				{ 0, 0, 0, 0, 0},
				
		};
		
		int n=capacity[0].length;
		for (int u=0; u<n; u++){
			for (int v=0; v<n; v++){
				if (capacity[u][v]!=0){
					capacity[u][v]=capacity[u][v]*n+1;
				}
			}
		}
		
		RelabelToFront relabel=new RelabelToFront();
		int flow=relabel.maximumFlow(capacity, 0, 4);
		List<String> cuts= relabel.findCuts(0, 4);
		
		
		
		assertThat(cuts, hasSize(1));
		assertThat(cuts, containsInAnyOrder("3->4") );
		
		
	}
	
		
	

}
