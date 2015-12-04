package jason.datastructure.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class CourseSchedule2 {
	//DFS loop detection.
	public boolean canFinish(int numCourses, int[][] prerequisites){
		
		AjacentGraph graph=new AjacentGraph();
		for (int i=0; i<numCourses; i++){
			graph.addVertex(new Integer(i).toString());
		}
		graph.initGraph();
		for (int i=0; i<prerequisites.length; i++){
			int from=prerequisites[i][1];
			int to=prerequisites[i][0];
			
			graph.addEdge(new Integer(from).toString(), new Integer(to).toString(), 1);
		}
		
		
		int[] colors=new int[numCourses];
		
		/*
		 * 0:white
		 * 1: grey
		 * 2: black;
		 */
		Arrays.fill(colors, 0);
		
		for (int i=0; i<numCourses; i++){
			if (colors[i]==0){
				if (!DFS(i, graph, colors)){
					return false;
				}
			}
		}
		return true;
		
	}
	
	public boolean DFS(int vertex, AjacentGraph graph, int[] colors){
		
		colors[vertex]=1; //grey;
		
		
		for (Edge edge: graph.getEdges(vertex)){
			int to=edge.to;
			if (colors[to]==1){
				//to is parent, this is a loop
				return false;
			} 
			if (colors[to]==0){
				if (!DFS(to, graph, colors)){
					return false;
				}
			} else {
				//black, skip
			}
		}
		
		colors[vertex]=2; //black;
		return true;
		
	}

	@Test 
	public void testAdjacentList(){
		int[][] prerequisites={
				{0, 1},
				{1,2}, 
				{ 2, 0}
				
		};
		
		//0, 1,2,3
		assertFalse(canFinish(4, prerequisites));
		
		//0, 1,2,3,4
		int[][] prerequisites1={
				{0, 1},
				{1,2}, 
				{2,3 }
				
		};
		assertTrue(canFinish(5, prerequisites1));
	}
}
