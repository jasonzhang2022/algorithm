package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;
/**
 * The idea is this
 * At each task slot (time point),
 * 
 * We pick out the one with biggest penalty from the set of eligible tasks to schedule to minimize penalty
 * 
 * If we have more than one to schedule after the first one is scheduled (remaining set)  
 * We will swap the task Y in remaining set with the task T where T is already scheduled and has a small penalty than Y.
 * 
 * We maintain a set of overflow tasks which is scheduled after deadline and already penalized. We will not penalize these tasks second time.
 * 
 * 
 * @author jason
 *
 */
public class TaskSchedule {

	public static class Task {
		int deadline;
		int penalty;
		
		int remaingPenalty;
		int schedulePosition;

		public Task(int deadline, int penalty) {
			super();
			this.deadline = deadline;
			this.penalty = penalty;
			this.remaingPenalty=this.penalty;
		}

	}

	Task[] results=null;
	public int schedule(Task[] tasks) {

		// sort task by deadline.
		Arrays.sort(tasks, (a, b) -> a.deadline - b.deadline);

		/**
		 * Scheduled tasks: task in a time slot.
		 * We use minHeadp so that we can always findUsingArray the one with minimal penalty.
		 */
		PriorityQueue<Task> scheduledTasks = new PriorityQueue<>(
				(a, b) -> a.penalty - b.penalty);

		//tasks is already penalized, but not scheduled yet (there is no time slot so it is not scheduled).
		Queue<Task> overflowedTasks = new LinkedList<>();
		
		Stack<Integer> freeSlots=new Stack<Integer>();

		int penalty = 0;
		int index = 0;
		for (int time = 1; time <= tasks[tasks.length - 1].deadline; time++) {

			// collect all elements that should be scheduled in this round. Descending by
			// penalty
			PriorityQueue<Task> tasksThisRound = new PriorityQueue<>(
					(a, b) -> b.penalty - a.penalty);
			while (index<tasks.length && tasks[index].deadline <= time) {
				tasksThisRound.add(tasks[index++]);
			}
			if (tasksThisRound.isEmpty()) {
				if (!overflowedTasks.isEmpty()) {
					//schedule a penalized, but in queue task.
					Task task = overflowedTasks.poll();
					task.schedulePosition = time;
					scheduledTasks.offer(task);
				} else {
					//we have a slot there is no task to schedule.
					freeSlots.push(time);
				}
				continue;
			}

			//schedule the first one, there is no penalty.
			Task t = tasksThisRound.poll();
			t.schedulePosition = time;
			scheduledTasks.offer(t);
			

			// if we have more task remaining to be scheduled. All of them
			// should be moved to next.
			// but we could replace some of the task already scheduled so far
			// with this one.
			while (!tasksThisRound.isEmpty()) {
				t = tasksThisRound.poll();
				
				if (!freeSlots.isEmpty()){
					int freeSlot=freeSlots.pop();
					t.schedulePosition = freeSlot;
					scheduledTasks.offer(t);
					continue;
				}
				
				
				if (!scheduledTasks.isEmpty()
						&& t.penalty > scheduledTasks.peek().penalty) {
					//findUsingArray one with small penalty that t
					
					// swap
					Task small = scheduledTasks.poll();
					scheduledTasks.offer(t);
					t.schedulePosition = small.schedulePosition;

					// small will be scheduled next round and punished.
					penalty += small.remaingPenalty;
					small.remaingPenalty=0;
					overflowedTasks.offer(small);
				} else {
					
					//penalize it
					penalty += t.remaingPenalty;
					t.remaingPenalty=0;
					
					//queue up for slot available later. 
					overflowedTasks.offer(t);
					
				}
			}
		}

		Task[] scheduled = new Task[tasks[tasks.length - 1].deadline];
		for (Task t : scheduledTasks) {
			scheduled[t.schedulePosition-1] = t;
		}
		results = new Task[tasks.length];
		index = 0;
		for (Task t : scheduled) {
			if (t != null) {
				results[index++] = t;
			}
		}
		for (Task t : overflowedTasks) {
			results[index++] = t;
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
}
