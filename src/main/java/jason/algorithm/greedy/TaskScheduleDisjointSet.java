package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.greedy.TaskSchedule.Task;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;
/*
 * http://yinyanghu.github.io/posts/2014-03-20-clrs-problem-16-4.html
 */
public class TaskScheduleDisjointSet {
	
	Task[] results;
	public int schedule(Task[] tasks) {
		//sort by penalty, descending order
		Arrays.sort(tasks, (a, b)->{
			return Integer.compare(b.penalty, a.penalty);
		});
		
		results=new Task[tasks.length];
		/*
		 * next[0]=0 is a sentinel: all tree are filled.
		 */
		int[] next=new int[tasks.length+1];
		for (int i=0; i<next.length; i++){
			next[i]=i;
		}
		int penalty=0;
		for (Task t: tasks){
			int targetIndex=Math.min(tasks.length, t.deadline);
			int freeIndex=find(next, targetIndex);
			if (freeIndex!=0){
				results[freeIndex-1]=t;
				
				next[freeIndex]=next[freeIndex-1];
			} else{
				//add this to the end.
				freeIndex=find(next, tasks.length);
				results[freeIndex-1]=t;
				next[freeIndex]=next[freeIndex-1];
				penalty+=t.penalty;
			}
		}
		return penalty;
	}
	
	
	/*
	 * Find the root element, 
	 * the root element is the element that is empty. It has the characteristic
	 * next[i]=i
	 */
	public int find(int[] next, int k){
		if (next[k]==k){
			//root.
			return k;
		}
		
		//recursive traverse the parent tree to find the root.
		//path compression
		int root=find(next, next[k]);
		next[k]=root;
		return root;
	}
	
	
	@Test
	public void test() {

		int[] dealines = { 4, 2, 4, 3, 1, 4, 6 };
		int[] penalties = { 70, 60, 50, 40, 30, 20, 10 };
		Task[] tasks = new Task[dealines.length];
		for (int i = 0; i < dealines.length; i++) {
			tasks[i] = new Task(dealines[i], penalties[i]);
		}

		int expectedPenalty = 50;
		int calcultedPenalty = schedule(tasks);
		assertThat(expectedPenalty, equalTo(calcultedPenalty));
		
		for (Task t: results){
			System.out.printf("(%d, %d),", t.deadline, t.penalty);
		}
		System.out.println("");
	}
	
	@Test
	public void testSimple() {

		int[] dealines = {1, 2,2};
		int[] penalties = { 1,2,3};
		Task[] tasks = new Task[dealines.length];
		for (int i = 0; i < dealines.length; i++) {
			tasks[i] = new Task(dealines[i], penalties[i]);
		}

		int expectedPenalty = 1;
		int calcultedPenalty = schedule(tasks);
		assertThat(expectedPenalty, equalTo(calcultedPenalty));
		
		StringBuilder sb=new StringBuilder();
		for (Task t: results){
			sb.append(String.format("(%d,%d),", t.deadline, t.penalty));
		}
		String expected="(2,2),(2,3),(1,1),";
		String result=sb.toString();
		assertThat(expected, equalTo(result));
	}
	
	/**
	 * The logic is not correct. This test can't pass.
	 * Since we don't have a logic to fill the hole left before.
	 */
	@Test
	public void testSimple2() {

		int[] dealines = {2,2};
		int[] penalties = {2,3};
		Task[] tasks = new Task[dealines.length];
		for (int i = 0; i < dealines.length; i++) {
			tasks[i] = new Task(dealines[i], penalties[i]);
		}

		int expectedPenalty = 0;
		int calcultedPenalty = schedule(tasks);
		assertThat(expectedPenalty, equalTo(calcultedPenalty));
		
		StringBuilder sb=new StringBuilder();
		for (Task t: results){
			sb.append(String.format("(%d,%d),", t.deadline, t.penalty));
		}
		String expected="(2,2),(2,3),";
		String result=sb.toString();
		assertThat(expected, equalTo(result));
	}
	
	/**
	 * The logic is not correct. This test can't pass.
	 * Since we don't have a logic to fill the hole left before.
	 */
	@Test
	public void testBig() {

		int n=1000;
		Random rand1=new Random();
		Random rand2=new Random();
		Task[] tasks = new Task[n];
		for (int i=0; i<n; i++){
			tasks[i] = new Task(rand1.nextInt(10000), rand2.nextInt(500));
		}
		
		Task[] tasksClone=Arrays.copyOf(tasks, n);

		TaskSchedule1 ts=new TaskSchedule1();
		int expectedPenalty = ts.scheduleImproved(tasks);
		int calcultedPenalty = schedule(tasksClone);
		
		assertThat(expectedPenalty, equalTo(calcultedPenalty));
		
		StringBuilder sb=new StringBuilder();
		for (Task t: ts.results){
			sb.append(String.format("(%d,%d),", t.deadline, t.penalty));
		}
		String expected=sb.toString();
		
		
		StringBuilder sb1=new StringBuilder();
		for (Task t: results){
			sb1.append(String.format("(%d,%d),", t.deadline, t.penalty));
		}
		String result=sb1.toString();
		System.out.println(result);
		assertThat(expected, equalTo(result));
	}

}
