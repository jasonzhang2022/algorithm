package jason.datastructure.graph.shortest;

import java.util.LinkedList;
import java.util.stream.Collectors;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConstructShortestSubgraph {
	
	public int[][] construct(int[][] shortestWeight, int[][] weight){
		int n=weight[0].length;
		
		int[][] parent=new int[n][n];
		
		//run time is O(n^3)
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				//calculate the parent of path(i->j)
				for (int k=0; k<n; k++){
					if (k==j ||shortestWeight[i][j]==Integer.MAX_VALUE){
						continue;
					}
					if (weight[k][j]!=Integer.MAX_VALUE && shortestWeight[i][k]!=Integer.MAX_VALUE){
						//we have an edge
						if (shortestWeight[i][k]+weight[k][j]==shortestWeight[i][j]){
							parent[i][j]=k;
							break;
						}
					}
				}
			}
		}
		return parent;
		
	}
	
	@Test
	public void test(){
		int[][] weight={
				{ 0, 3, 8, Integer.MAX_VALUE, -4},
				{ Integer.MAX_VALUE, 0,Integer.MAX_VALUE,1,7},
				{Integer.MAX_VALUE,4,0,Integer.MAX_VALUE,Integer.MAX_VALUE},
				{2,Integer.MAX_VALUE,-5,0, Integer.MAX_VALUE},
				{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,6,0}
		};
		int[][] shortestWeight={
				{0,1,-3,2,4},
				{3,0, -4,1,-1},
				{7,4,0,5,3},
				{2,-1,-5, 0, -2},
				{8,5,1,6,0}
		};
		
		int[][] parent=construct(shortestWeight, weight);
		
		assertThat(retrievePath(1,4, parent), equalTo("1->3->0->4"));
		
		
	}
	
	public String retrievePath(int i, int j, int[][] parent){
		LinkedList<Integer> path=new LinkedList<Integer>();
		
	
		int k=j;
		while (k!=i){
			path.offerFirst(k);
			k=parent[i][k];
		}
		path.offerFirst(i);
		
		return path.stream().map(v->v.toString()).collect(Collectors.joining("->"));
	}

}
