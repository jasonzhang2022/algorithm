package jason.algorithm.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class MergeSort extends TestSetup{
	
	public static int[] sort(int[] input) {
		
		int[] output=new int[input.length];
		
		//from round 0...n, segment length is 2^n.
		int round=(int) Math.ceil( (Math.log(input.length)/Math.log(2)));
		
		for (int i=0; i<round; i++) {
			
			int segmentLen=1<<i;
			int twoSegmentLen=2*segmentLen;
			int count=0;
			while (count*twoSegmentLen<input.length) {
				int startIndex=count*twoSegmentLen;
				int endIndex=(count+1)*twoSegmentLen-1;
				int middle=count*twoSegmentLen+segmentLen-1;
				mergeSegment(input, startIndex, middle, endIndex, output);
				count++;
			}
			int[] temp=input;
			input=output;
			output=temp;
		}
		return input;
	}
	
	
	/*
	 * Merge sort input: from start to middle and from middle+1 to end to output
	 * 
	 */
	public static void mergeSegment(int[] input, int start, int middle, int end, int[] output) {
		if (end>=input.length) {
			end=input.length-1;
		}
		if (middle>=input.length) {
			middle=input.length-1;
		}
		if (end-start<=20) {
			InsertionSort.sort(input, start, end);
			for (int i=start; i<=end && i<input.length; i++) {
				output[i]=input[i];
			}
			return;
		}
		
		int first=start;
		int second=middle+1;
		
		int outputIndex=start;
		
		
		
		while (first<=middle && second<=end) {
			if (input[first]<=input[second]) {
				output[outputIndex++]=input[first++];
			} else {
				output[outputIndex++]=input[second++];
			}
		}
		while(first<=middle) {
			output[outputIndex++]=input[first++];
		}
		while (second<=end) {
			output[outputIndex++]=input[second++];
		}
		
	}
	

	@Test
	public void testMergeSort() {
		long start=System.currentTimeMillis();
		int[] output=MergeSort.sort(input);
		long end=System.currentTimeMillis();
		System.out.println("\nMerge Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}
	
}
