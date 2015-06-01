package jason.algorithm.practice;

import java.util.ArrayList;
import java.util.HashMap;

//http://www.programcreek.com/2014/05/leetcode-course-schedule-java/
/*
 * There are a total of n courses you have to take, labeled from 0 to n - 1. Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]. Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

For example, given 2 and [[1,0]], there are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.

For another example, given 2 and [[1,0],[0,1]], there are a total of 2 courses to take. To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.

 */
public class CourseScheduleGraphCycle {

	public boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null) {
			throw new IllegalArgumentException("illegal prerequisites array");
		}

		int len = prerequisites.length;

		if (numCourses == 0 || len == 0) {
			return true;
		}

		// track visited courses
		int[] visit = new int[numCourses];

		// use the map to store what courses depend on a course
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		for (int[] a : prerequisites) {
			if (map.containsKey(a[1])) {
				map.get(a[1]).add(a[0]);
			} else {
				ArrayList<Integer> l = new ArrayList<Integer>();
				l.add(a[0]);
				map.put(a[1], l);
			}
		}

		for (int i = 0; i < numCourses; i++) {
			if (!canFinishDFS(map, visit, i))
				return false;
		}

		return true;
	}

	private boolean canFinishDFS(HashMap<Integer, ArrayList<Integer>> map,
			int[] visit, int i) {
		/*
		 * if visit[i]==-1: it means 1) i is touched in current traversal OR 
		 * 2) we already decided in other traversal that i can not be finished.
		 * 
		 * if visit[i]==1: we already decided that in other traversal that i
		 * can be finished.
		 * 
		 */
		
		
		if (visit[i] == -1)
			return false;
		if (visit[i] == 1)
			return true;

		visit[i] = -1; //mark i touched in current traversal.
		if (map.containsKey(i)) {
			for (int j : map.get(i)) {
				if (!canFinishDFS(map, visit, j))
					return false;
			}
		}

		//if we never reach this step, visit[i] is left to -1 state
		//which means that i can't be traversed.
		visit[i] = 1; //i can be finished. it is marked as 1.

		return true;
	}
}
