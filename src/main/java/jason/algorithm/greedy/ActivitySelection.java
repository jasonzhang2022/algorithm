package jason.algorithm.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * basic activity schedule: http://www.geeksforgeeks.org/greedy-algorithms-set-1-activity-selection-problem/
 * You are given n activities with their start and finish times. Select the maximum number of activities 
 * that can be performed by a single person, assuming that a person can only work on a single activity at a time.
 * 
 *
  The following algorithm solves the basic cases.
  Weighted job schedule needs DP.
 */
public class ActivitySelection {
	
	public static class Activity {
		int start;
		int end;
		int weight;
		public Activity(int start, int end, int weight) {
			super();
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		public Activity(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + end;
			result = prime * result + start;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return false;
		}
		@Override
		public String toString() {
			return "Activity [start=" + start + ", end=" + end + "]";
		}
		
		
	}
	
	public ArrayList<Activity> numberOfActivitiesFirstFinish(int[] start, int[] end){
		
		PriorityQueue<Activity> queue=new PriorityQueue<>((a,b)->a.end-b.end);
		for (int i=0; i<start.length; i++){
			queue.add(new Activity(start[i], end[i]));
		}
		ArrayList<Activity> results=new ArrayList<>(start.length);
		results.add(queue.poll());
		while (!queue.isEmpty()){
			Activity last=results.get(results.size()-1);
			
			Activity next=queue.poll();
			while (next.start<last.end && !queue.isEmpty()){
				next=queue.poll();
			}
			if (next.start>=last.end){
				results.add(next);
			}
		}
		
		return results;
	}
	
	
	public ArrayList<Activity> numberOfActivitiesLastStart(int[] start, int[] end){
		
		PriorityQueue<Activity> queue=new PriorityQueue<>((a,b)->b.start-a.start);
		for (int i=0; i<start.length; i++){
			queue.add(new Activity(start[i], end[i]));
		}
		ArrayList<Activity> results=new ArrayList<>(start.length);
		results.add(queue.poll());
		while (!queue.isEmpty()){
			Activity last=results.get(results.size()-1);
			
			Activity next=queue.poll();
			while (next.end>last.start && !queue.isEmpty()){
				next=queue.poll();
			}
			if (next.end<=last.start){
				results.add(next);
			}
		}
		
		Collections.reverse(results);
		return results;
	}
	
	@Test
	public void testFirstFinish(){
		int[] start={1, 3, 0, 5, 8, 5};
		int[] end={2, 4, 6, 7, 9, 9};
		
		List<Activity> expected=new ArrayList<>();
		int[] indices={0, 1, 3, 4};
		Arrays.stream(indices).forEach(i->expected.add(new Activity(start[i], end[i])));
		List<Activity> results=numberOfActivitiesFirstFinish(start, end);
		
		assertThat(results, hasSize(expected.size()));
		for (int i=0; i<expected.size(); i++){
			assertThat(expected.get(i), equalTo(results.get(i)));
		}
	}
	@Test
	public void testLastStart(){
		int[] start={1, 3, 0, 5, 8, 5};
		int[] end={2, 4, 6, 7, 9, 9};
		
		List<Activity> expected=new ArrayList<>();
		int[] indices={0, 1, 3, 4};
		Arrays.stream(indices).forEach(i->expected.add(new Activity(start[i], end[i])));
		List<Activity> results=numberOfActivitiesLastStart(start, end);
		
		
		assertThat(results, hasSize(expected.size()));
		for (int i=0; i<expected.size(); i++){
			assertThat(expected.get(i), equalTo(results.get(i)));
		}
	}
	
}
