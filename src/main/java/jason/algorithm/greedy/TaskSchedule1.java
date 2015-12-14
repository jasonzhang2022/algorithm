package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.greedy.TaskSchedule.Task;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

/**
 * Schedule from deadline to beginning
 * @author jason
 *
 */
public class TaskSchedule1 {
	Task[] results=null;
	
	/*
	 * In this implementation,
	 * task is leave to an element at index of its deadline
	 * If one element has a big deadline, we have a sparse array.
	 * 
	 * Moreover the runtime is a function of deadline
	 */
	public int schedule(Task[] tasks) {
		//sort by penalty, descending order
		Arrays.sort(tasks, (a, b)->{
			return b.penalty-a.penalty;
		});
		
		//need to find out the buggest deadline;
		int maxdeadline=Arrays.stream(tasks).mapToInt(t->t.deadline).max().getAsInt();
		
		Task[] results1=new Task[maxdeadline];
		
		
		LinkedList<Task> penalizedTask=new LinkedList<>();
		
		for (Task t: tasks){
			//we need to try maximal effort to schedule t. 
			//another element a is pushed after deadline before we scheduling t,
			//This is ok since t as a bigger penalty than a.
			//If there is no element is pushed out, there is no any side effect
			boolean scheduled=false;
			for (int d=t.deadline; d>0; d--){
				if (results1[d-1]==null){
					results1[d-1]=t;
					scheduled=true;
					break;
				}
			}
			if (!scheduled){
				penalizedTask.add(t);
			}
		}
		
		results=new Task[tasks.length];
		int index=0;
		for (Task t: results1){
			if (t!=null){
				results[index++]=t;
			}

		}
		
		int penalty=0;
		for (Task t:penalizedTask){
			results[index++]=t;
			penalty+=t.penalty;
		}
		return penalty;
		
	}
	
	public int scheduleImproved(Task[] tasks) {
		//sort by penalty, descending order
		Arrays.sort(tasks, (a, b)->{
			return b.penalty-a.penalty;
		});
		
		
		
		Task[] results1=new Task[tasks.length];
		
		
		LinkedList<Task> penalizedTask=new LinkedList<>();
		
		for (Task t: tasks){
			/**
			 * There are two cases:
			 * case 1: suppose the d.deadline<=tasks.length;
			 * In this case, the logic is the same as sparse array one (using deadline as array index)
			 * 
			 * Case 2: the deadline >tasks.length;
			 * In this case, the element just move itself to a free spot well before deadline.
			 * There must exist such a spot since we are trying to place maximal n elements in array with length n.
			 * 
			 */
			boolean scheduled=false;
			int maxIndex=Math.min(tasks.length, t.deadline)-1;
			for (int d=maxIndex; d>=0; d--){
				if (results1[d]==null){
					results1[d]=t;
					scheduled=true;
					break;
				}
			}
			if (!scheduled){
				penalizedTask.add(t);
			}
		}
		
		results=new Task[tasks.length];
		int index=0;
		for (Task t: results1){
			if (t!=null){
				results[index++]=t;
			}

		}
		
		int penalty=0;
		for (Task t:penalizedTask){
			results[index++]=t;
			penalty+=t.penalty;
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
