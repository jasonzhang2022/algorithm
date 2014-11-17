package jason.algorithm.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ParallelMergeSort {
	public static int[] sort(int[] input) {
		ForkJoinPool mainPool = new ForkJoinPool();
		int[] output=new int[input.length];
		
		
		//from round 0...n, segment length is 2^n.
		int round=(int) Math.ceil( (Math.log(input.length)/Math.log(2)));
		
		for (int i=3; i<round; i++) {
			//minimal length=16
			int segmentLen=1<<i;
			mergeRound(segmentLen, input, output, mainPool);
			int[] temp=input;
			input=output;
			output=temp;
		}
		System.out.println("\n\tthread account: "+mainPool.getActiveThreadCount());
		System.out.println("\tparallelism: "+mainPool.getParallelism());
		System.out.println("\tsteal count: "+mainPool.getStealCount());
		mainPool.shutdown();
		return input;
	}
	
	private static void mergeRound1(int segmentLen,  int[] input, int[] output, ForkJoinPool mainPool) {
		int twoSegmentLen=2*segmentLen;
		int count=0;
		while (count*twoSegmentLen<input.length) {
			int startIndex=count*twoSegmentLen;
			int endIndex=(count+1)*twoSegmentLen-1;
			int middle=count*twoSegmentLen+segmentLen-1;
			mainPool.invoke(ForkJoinTask.adapt(new MergeTask(input, startIndex, middle, endIndex, output)));
			count++;
		}
	}
	
	private static void mergeRound(int segmentLen,  int[] input, int[] output, ForkJoinPool mainPool) {
		int twoSegmentLen=2*segmentLen;
		int count=0;
		List<Callable<Object>> tasks=new ArrayList<>(input.length/twoSegmentLen+1);
		while (count*twoSegmentLen<input.length) {
			int startIndex=count*twoSegmentLen;
			int endIndex=(count+1)*twoSegmentLen-1;
			int middle=count*twoSegmentLen+segmentLen-1;
			
			tasks.add(Executors.callable(new MergeTask(input, startIndex, middle, endIndex, output)));
			count++;
		}
		mainPool.invokeAll( tasks);
		
		
		
	}
	
	
	public static class MergeTask implements Runnable{
		int[] input;
		int startIndex;
		int endIndex;
		int middle;
		int[] output;
		
		
		public MergeTask(int[] input, int startIndex, int middle, int endIndex, int[] output) {
			super();
			this.input = input;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.middle=middle;
			this.output = output;
		}

		@Override
		public void run() {
			if (endIndex>=input.length) {
				endIndex=input.length-1;
			}
			if (endIndex-startIndex<=20) {
				InsertionSort.sort(input, startIndex, endIndex);
				for (int i=startIndex; i<=endIndex && i<input.length; i++) {
					output[i]=input[i];
				}
				return;
			}
			MergeSort.mergeSegment(input, startIndex, middle, endIndex, output);
		}
		
	}
}
