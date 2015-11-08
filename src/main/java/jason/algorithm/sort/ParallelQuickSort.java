package jason.algorithm.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import org.junit.Test;

public class ParallelQuickSort extends TestSetup {
	public static int[] sort(int[] input) {
		ForkJoinPool mainPool = new ForkJoinPool();
		mainPool.invoke(new QuickSortAction(input, 0, input.length-1));
		System.out.println("\n\tthread account: "+mainPool.getActiveThreadCount());
		System.out.println("\tparallelism: "+mainPool.getParallelism());
		System.out.println("\tsteal count: "+mainPool.getStealCount());
		mainPool.shutdown();
		return input;
	}
	
	
	public static  void swap(int[] input, int i, int j) {
		int temp=input[i];
		input[i]=input[j];
		input[j]=temp;
	}
	
	
	
	
	
	public static class QuickSortAction extends RecursiveAction {
		
		
		int[] input;
		int startIndex;
		int endIndex;
		
		public QuickSortAction(int[] input, int startIndex, int endIndex) {
			super();
			this.input = input;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		protected void compute() {
			if (startIndex>=endIndex) {
				return;
			}
			//System.out.printf("run start:%d, end:%d\n", startIndex, endIndex);
			
			if (endIndex-startIndex<=20) {
				//for short sequenece using insert sort.
				InsertionSort.sort(input, startIndex, endIndex);
				return;
			}
//			
			
			int pivotal=input[startIndex];
			
			//move pivotal to end to avoid inteference.
			swap(input, startIndex, endIndex);
			
			//if the values is <=pivotal leave it to  left, otherwise leave it to right
			//endIndex is the index where large value should be placed too.
			//startIndex the next value to be checked.
			
			int begin=startIndex;
			int end=endIndex-1;
			
			while (begin!=end) {
				if (input[begin]>pivotal) {
					swap(input, begin, end);
					end--;
				} else {
					begin++;
				}
			}
			
			//move pivotal back to its position.
			int pivotalPosition;
			if (input[begin]>pivotal) {
				swap(input, begin, endIndex);
				pivotalPosition=begin;
			} else {
				swap(input, begin+1, endIndex);
				pivotalPosition=begin+1;
			}
			
			
			QuickSortAction task1=new QuickSortAction(input, startIndex, pivotalPosition-1);
			QuickSortAction task2=new QuickSortAction(input, pivotalPosition+1, endIndex);
			invokeAll(task1, task2);
			/*
			 * This does not work. If it works, the improvement will be tremendous
			 * It could be that a fork does not flush memory. We need to flush all the swap operation we perform above.
			task1.fork();
			task2.fork();
			*/
			
		}
	}
	
	

	@Test
	public void testParallelQuickSort() {
		long start=System.currentTimeMillis();
		int[] output=ParallelQuickSort.sort(input);
		long end=System.currentTimeMillis();
		System.out.println("\nParallel Quick Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}
	
}
