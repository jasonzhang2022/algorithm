package jason.algorithm.dp;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

/**
 * Give an O(n*n)-time algorithm to findUsingArray the longest monotonically increasing
 * subsequence of a sequence of n numbers
 *
 * The longest Increasing Subsequence (LIS) problem is to findUsingArray the length of the
 * longest subsequence of a given sequence such that all elements of the
 * subsequence are sorted in increasing order. For example, length of LIS for {
 * 10, 22, 9, 33, 21, 50, 41, 60, 80 } is 6 and LIS is {10, 22, 33, 50, 60, 80}.
 * 
 * @author jason
 *
 */
public class longestIncreasingSubsequence {

	public static int[] findUsingArray(int[] input) {

		int n = input.length;


		ArrayList<List<Integer>> liss= new ArrayList<>(n);
		List<Integer> list = new ArrayList<>();
		list.add(input[0]);
		liss.add(list);
		int[] track = new int[n];
		track[0]=input[0];
		int trackEnd = 1;


		for (int i=0; i<n; i++){
			int num = input[i];

			int insertionIndex = Arrays.binarySearch(track, 0, trackEnd, num);
			if (insertionIndex<0){
				int targetIndex = -insertionIndex-1;
				List<Integer> list1 = new ArrayList<>();
				if (targetIndex!=0){
					list1 = new ArrayList<>(liss.get(targetIndex-1));
				}
				list1.add(num);
				if (targetIndex<liss.size()) {
					liss.set(targetIndex, list1);
				} else {
					liss.add(list1);
				}
				track[targetIndex]=num;
				if (targetIndex==trackEnd){
					trackEnd++;
				}
			}
		}

		return  liss.get(liss.size()-1).stream().mapToInt(Integer::intValue).toArray();
		
	}

	
	//http://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
	/*
	 * A crucial part of this algorithm is that the active sequences (sub sequence accumulated so far) have two orders
	 * 1. length;
	 * 2. end elements.
	 * These two orders are in sync all the times.
	 * So we only need one data structure to keep them in order can search a particular entry in logN time.
	 * Since there is no duplicated length, so there are n entries at maximum.
	 * The search will take logN at maximum.
	 * Let S be a set of LIS of length i  when the k number of elements are processed.
	 * For all elements in S, we keep the one with smallest last element. If any element in S can be exteneds, this one can
	 * definitely be extended. This is the best one.
	 * 
	 * 
	 */
	
	public static int[] findUsingTree(int[] input){
		
		TreeSet<LinkedList<Integer>> lis = new TreeSet<>( (l, r)->Integer.compare(l.getLast(), r.getLast()));
		LinkedList<Integer> tempList = new LinkedList<>();
		tempList.add(input[0]);
		lis.add(tempList);

		for (int i=1; i<input.length; i++){
			int num = input[i];
			LinkedList<Integer> temp =  new LinkedList<>();
			temp.add(num);

			LinkedList<Integer> smaller=lis.floor(temp);

			//nothing smaller than num.
			if (smaller ==null){
				lis.remove(lis.first());
				lis.add(temp);
				continue;
			}
			if (smaller.getLast() == num){
				continue;
			}

			temp = new LinkedList<>(smaller);
			temp.add(num);

			LinkedList<Integer> higher = lis.higher(smaller);
			if (higher!=null){
				lis.remove(higher);
			}
			lis.add(temp);
		}

		return lis.last().stream().mapToInt(Integer::intValue).toArray();
	}


	public static class TestCase {
		@Test
		public void test() {
			int[] input = {10, 22, 9, 33, 21, 50, 41, 60, 80};
			int[] expected = {10, 22, 33, 50, 60, 80};
			int[] expected1 = {10, 22, 33, 41, 60, 80};


			assertTrue(Arrays.equals(expected, findUsingArray(input)) || Arrays.equals(expected1, findUsingArray(input)));
		}

		@Test
		public void testNlogN() {
			int[] input = {10, 22, 9, 33, 21, 50, 41, 60, 80};
			int[] expected = {10, 22, 33, 50, 60, 80};
			int[] expected1 = {10, 22, 33, 41, 60, 80};


			assertTrue(Arrays.equals(expected, findUsingTree(input)) || Arrays.equals(expected1, findUsingTree(input)));
		}
	}
	
	

}
