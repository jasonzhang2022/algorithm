package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.greedy.TaskSchedule.Task;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Schedule from deadline to beginning
 * @author jason
 *
 */
public class TaskSchedulePQ {
	Task[] results=null;


	
	public int scheduleImproved(Task[] tasks) {
		Arrays.sort(tasks, (t1, t2)->{
			int n1 = Integer.compare(t1.deadline, t2.deadline);
			if (n1!=0){
				return n1;
			}
			return Integer.compare(t2.penalty, t1.penalty);
		});

		int lastDealine = tasks[tasks.length-1].deadline;
		LinkedList<Task> ts= Arrays.stream(tasks).collect(Collectors.toCollection(LinkedList::new));
		PriorityQueue<Task> picked = new PriorityQueue<>((t1, t2)->Integer.compare(t1.penalty, t2.penalty));



		int penalty = 0;

		for (int i=1; i<=lastDealine && !ts.isEmpty(); i++){

			// pick a task for current slot.
			picked.offer(ts.poll());

			while (!ts.isEmpty() && ts.peek().deadline<=i){
				Task top = ts.poll();
				if (top.penalty >picked.peek().penalty) {
					penalty += picked.poll().penalty;
					picked.offer(top);
				} else {
					penalty += top.penalty;
				}
			}
		}

		results = new Task[picked.size()];
		int i=0;
		while(!picked.isEmpty()){
			results[i++]=picked.poll();
		}
		return penalty;
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
		int calcultedPenalty = scheduleImproved(tasks);
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
		int calcultedPenalty = scheduleImproved(tasks);
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
	public void testSimple2() {

		int[] dealines = {2,2};
		int[] penalties = {2,3};
		Task[] tasks = new Task[dealines.length];
		for (int i = 0; i < dealines.length; i++) {
			tasks[i] = new Task(dealines[i], penalties[i]);
		}

		int expectedPenalty = 0;
		int calcultedPenalty = scheduleImproved(tasks);
		assertThat(expectedPenalty, equalTo(calcultedPenalty));
		
		StringBuilder sb=new StringBuilder();
		for (Task t: results){
			sb.append(String.format("(%d,%d),", t.deadline, t.penalty));
		}
		String expected="(2,2),(2,3),";
		String result=sb.toString();
		assertThat(expected, equalTo(result));
	}

}
