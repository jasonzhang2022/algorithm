package jason.datastructure.graph.flow;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//Introduction to algorithm 26.1
public class EscapeProblem {

	 
	
	public boolean escape(int[][] grid){
		
		int markedVertex=0;
		int n=grid.length;
		for (int r=0; r<n; r++){
			for (int c=0; c<n; c++){
				if (grid[r][c]==1){
					markedVertex++;
				}
			}
		}
		int[][] capacity=transform(grid);
		RelabelToFront label=new RelabelToFront();
		int V=n*n*2+2;
		int s=V-2;
		int t=V-1;
		int flow=label.maximumFlow(capacity, s, t);
		return flow==markedVertex;
	}
	
	
	public int[][] transform(int[][] grid){
		int n=grid.length;
		
		//Each vertex in grid is split as 2: in Vertex and out Vertex
		//and we add two vertex s and t.
		int V=n*n*2+2;
		
		int[][] capacity=new int[V][V];
		
		/*
		 * For each vertex (x, y) in grid, we give it an index=x*n+y.
		 * We split this vertex in two vertice: 2*index(in vertex) and 2*index+1(out index)
		 */
		for (int row=0; row<n; row++){
			for (int col=0; col<n; col++){
				int index=row*n+col;
				int inVertex=index*2;
				int outVertex=inVertex+1;
				capacity[inVertex][outVertex]=1;
				
				if (row>0){
					//we can go up;
					int upInVertex=((row-1)*n+col)*2;
					capacity[outVertex][upInVertex]=1;
				}
				if (col>0){
					//we can go left
					int leftInVertex=(row*n+col-1)*2;
					capacity[outVertex][leftInVertex]=1;
				}
				if (col<n-1){
					//we can walk to right
					int rightInVertex=(row*n+col+1)*2;
					capacity[outVertex][rightInVertex]=1;
				}
				if (row<n-1){
					//we can walk down
					int downInVertex=((row+1)*n+col)*2;
					capacity[outVertex][downInVertex]=1;
				}
			}
		}
		
		//s index= V-2;
		//t index=V-1;
		int s=V-2;
		int t=V-1;
		for (int row=0; row<n; row++){
			for (int col=0; col<n; col++){
				if (grid[row][col]==1){
					//an mark vertex
					int inVertex=(row*n+col)*2;
					capacity[s][inVertex]=1;
				}
			}
		}
		
		//boundary row
		for (int col=0; col<n; col++){
			//first row;
			int outVertex=col*2+1;
			capacity[outVertex][t]=1;
			
			//last row
			outVertex=((n-1)*n+col)*2+1;
			capacity[outVertex][t]=1;
		}
		
		//boundary col
		for (int row=0; row<n; row++){
			//first col
			int outVertex=(row*n+0)*2+1;
			capacity[outVertex][t]=1;
			
			//last col
			outVertex=(row*n+n-1)*2+1;
			capacity[outVertex][t]=1;
		}
		return capacity;
	}
	
	
	//page 761
	@Test
	public void testPositive(){
		int[][] grid={
				{0,0,0,0,0,0},
				{0,1,0,1,0,1},
				{1,1,0,1,0,1},
				{0,1,0,1,0,1},
				{0,0,0,0,0,0},
				{0,0,0,0,0,0}
		};
		assertTrue(escape(grid));
	}
	@Test
	public void testNegative(){
		int[][] grid={
				{0,0,0,0,0,0},
				{0,1,0,1,0,1},
				{1,1,0,1,1,1},
				{0,1,0,1,0,1},
				{0,0,0,0,0,0},
				{0,0,0,0,0,0}
		};
		assertFalse(escape(grid));
	}
	
}
