package jason.algorithm.sort;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import jason.algorithm.Shuffler;

import org.junit.Before;
import org.junit.Test;

public class SortTest {

	int inputLen=1000000;
	int[] input;
	@Before
	public void setup() {
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
	}
	
	
	@Test
	public void testBinarySearch() {
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		int[] needles= {0, 20, 100, 200, 855,  1000, 101, 102, 103, inputLen-1};
		for (int needle: needles) {
			int j=InsertionSort.binarySearch(input, 0, inputLen-1, needle);
			assertThat(needle, equalTo(input[j-1]));
		}
	}
	@Test
	public void testQuickSort() {
		long start=System.currentTimeMillis();
		int[] output=QuickSort.sort(input);
		long end=System.currentTimeMillis();
		System.out.println("\nQuick Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
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
	
	@Test
	public void testParallelMergeSort() {
		long start=System.currentTimeMillis();
		int[] output=ParallelMergeSort.sort(input);
		long end=System.currentTimeMillis();
		System.out.println("\nParallel Merge Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}
	
	@Test
	public void testTimSort() {
		Integer[] inputsIntegers=new Integer[inputLen];
		for (int i=0; i<inputLen; i++) {
			inputsIntegers[i]=i;
		}
		Shuffler.shuffle(inputsIntegers);
		long start=System.currentTimeMillis();
		TimSort.sort(inputsIntegers, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		long end=System.currentTimeMillis();
		System.out.println("\nTim Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(inputsIntegers[i], equalTo(i));
		}
		
	}
	
	@Test
	public void testInsertionSort() {
		inputLen=100;
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
		int[] output=InsertionSort.sort(input);
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}

}
