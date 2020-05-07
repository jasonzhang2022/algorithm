package jason.datastructure.disjointset;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.junit.Test;

/*
 * Introudction to algorithm: Question 21-1 page 582
 * http://llhuii.is-programmer.com/posts/31126.html
 * off-line minimum problem
 */
public class DisjoinSetMinimumOffline {


	// use priority queue
	public static int[] extract1(String[] input, int n){

		PriorityQueue<Integer> pq = new PriorityQueue<>();
		List<Integer> result = new LinkedList<>();

		for (String str: input){
			if (str.equals("E")){
				if (!pq.isEmpty()){
					result.add(pq.poll());
				}
			} else {
				pq.offer(Integer.valueOf(str));
			}
		}
		return result.stream().mapToInt(i->i.intValue()).toArray();
	}


	//use disjoint set.
	public static int[] extract(String[] input, int n){
		int[] numToExtractNode = new int[n+1];
		int numberOfNode = input.length - n;
		int[] set = new int[numberOfNode];
		int[] result = new int[numberOfNode];

		//zero means no result.
		Arrays.fill(result, 0);

		int nodeIndex = 0;
		set[nodeIndex] = nodeIndex;
		for (String str: input){
			if (str.equals("E")){
				nodeIndex++;
				// node points to itself.
				if (nodeIndex <numberOfNode) {
					set[nodeIndex] = nodeIndex;
				}
				continue;
			}
			int num = Integer.valueOf(str);
			numToExtractNode[num]=nodeIndex;
		}

		for (int i=1; i<=n; i++){
			nodeIndex = findAndMerge(numToExtractNode[i], set);
			if (nodeIndex == -1){
				continue;
			}
			if (result[nodeIndex]==0){
				result[nodeIndex] = i;

				if (nodeIndex <numberOfNode-1) {
					set[nodeIndex] = nodeIndex+1;
				}
			}
		}

		return result;


	}

	private static int findAndMerge(int node, int[] set){
		if (node==set.length) {
			return -1;
		}
		int parent = set[node];
		if (parent==node){
			return node;
		} else {
			parent = findAndMerge(parent, set);
			set[node] = parent;
			return parent;
		}
	}


	public static class TestCase {
		@Test
		public void testStartWithE(){
			String[] input={"E", "1", "2"};
			int[] expected={};
			String expectedStr=
					Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			int[] result=extract(input, 2);
			String resultStr=Arrays.stream(result).filter(i->i!=0).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			assertThat(resultStr, equalTo(expectedStr));
		}

		@Test
		public void testEndE(){
			String[] input={"1", "2", "E"};
			int[] expected={1};
			String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			int[] result=extract(input, 2);
			String resultStr=Arrays.stream(result).filter(i->i!=0).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			assertThat(resultStr, equalTo(expectedStr));
		}

		@Test
		public void testSomeEmptyExtractionInMiddle(){
			String[] input={"1", "E", "E", "2"};
			int[] expected={1};
			String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			int[] result=extract(input, 2);
			String resultStr=Arrays.stream(result).filter(i->i!=0).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			assertThat(resultStr, equalTo(expectedStr));
		}

		@Test
		public void testBasic(){
			String[] input={"4", "8", "E", "3", "E", "9", "2", "6", "E", "E", "E", "1", "7", "E", "5"};
			int[] expected={4, 3,2,6,8,1};
			String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			int[] result=extract(input, 9);
			String resultStr=Arrays.stream(result).filter(i->i!=0).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
			assertThat(resultStr, equalTo(expectedStr));
		}
	}

}
