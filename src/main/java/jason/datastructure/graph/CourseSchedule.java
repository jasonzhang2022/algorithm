package jason.datastructure.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

//http://www.programcreek.com/2014/05/leetcode-course-schedule-java/
/*
 * There are a total of n courses you have to take, labeled from 0 to n - 1. Some courses may
 *  have prerequisites, for example to take course 0 you have to first take course 1,
 *  which is expressed as a pair: [0,1]. Given the total number of courses and a list of
 *   prerequisite pairs, is it possible for you to finish all courses?

 For example, given 2 and [[1,0]], there are a total of 2 courses to take. To take course 1 
 you should have finished course 0. So it is possible.

 For another example, given 2 and [[1,0],[0,1]], there are a total of 2 courses to take. 
 To take course 1 you should have finished course 0, and to take course 0 you should also have
 finished course 1. So it is impossible.
 
 {@see CourseSchedule2.java}: Typical DFS to find loop
 */
public class CourseSchedule {

	// mark edge
	public boolean canFinishByEdge(int numCourses, int[][] prerequisites) {
		if (prerequisites == null) {
			throw new IllegalArgumentException("illegal prerequisites array");
		}

		int len = prerequisites.length;

		if (numCourses == 0 || len == 0) {
			return true;
		}
		int[][] matrix = new int[numCourses][numCourses];
		for (int row = 0; row < prerequisites.length; row++) {
			matrix[prerequisites[row][1]][prerequisites[row][0]] = 1;
		}

		int[] vertexVisited=new int[numCourses];
		for (int vertex = 0; vertex < numCourses; vertex++) {
			if (!dfsMarkEdge(vertex, matrix, numCourses, vertexVisited)) {
				return false;
			}
		}
		return true;

	}

	private boolean dfsMarkEdge(int vertex, int[][] matrix, int n, int[] vertexVisited) {
		
		if (vertexVisited[vertex]==1){
			return true;
		}
		for (int i = 0; i < n; i++) {
			if (matrix[vertex][i] == 2) {
				// this edge is already walked.
				return false;
			} else if (matrix[vertex][i] == 1) {
				// has edge
				matrix[vertex][i] = 2;
				if (!dfsMarkEdge(i, matrix, n, vertexVisited)) {
					return false;
				}
			} else {
				// no edge
			}

		}
		vertexVisited[vertex]=1;
		return true;
	}

	public boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null) {
			throw new IllegalArgumentException("illegal prerequisites array");
		}

		int len = prerequisites.length;

		if (numCourses == 0 || len == 0) {
			return true;
		}

		ArrayList<Integer>[] adjacentGraph = new ArrayList[numCourses];
		for (int row = 0; row < prerequisites.length; row++) {
			if (adjacentGraph[prerequisites[row][1]] == null) {
				adjacentGraph[prerequisites[row][1]] = new ArrayList<Integer>();
			}
			adjacentGraph[prerequisites[row][1]].add(prerequisites[row][0]);
		}
		int[] vertexVisited = new int[numCourses];

		for (int vertex = 0; vertex < numCourses; vertex++) {
			if (!dfs(vertex, numCourses, adjacentGraph, vertexVisited)) {
				return false;
			}
		}
		return true;

	}

	/*
	 * Depth traverse from vertex
	 */
	private boolean dfs(int vertex, int n, ArrayList<Integer>[] adjacentGraph,
			int[] vertexVisited) {
		if (vertexVisited[vertex] == -1) {
			return false;
		}
		if (vertexVisited[vertex] == 1) {
			return true;
		}

		if (adjacentGraph[vertex] == null) {
			vertexVisited[vertex] = 1; // no course depends on this course.
			return true;
		}

		// mark vertex as being visited
		vertexVisited[vertex] = -1;

		for (int adjacentVertex : adjacentGraph[vertex]) {
			boolean childReturn = dfs(adjacentVertex, n, adjacentGraph,
					vertexVisited);
			if (!childReturn) {
				return false;
			}
		}
		vertexVisited[vertex] = 2;
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
	
	@Test 
	public void testAdjacentMatrix(){
		int[][] prerequisites={
				{0, 1},
				{1,2}, 
				{ 2, 0}
				
		};
		
		//0, 1,2,3
		assertFalse(canFinishByEdge(4, prerequisites));
		
		//0, 1,2,3,4
		int[][] prerequisites1={
				{0, 1},
				{1,2}, 
				{2,3 }
				
		};
		assertTrue(canFinishByEdge(5, prerequisites1));
	}

}
