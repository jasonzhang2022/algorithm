package jason.algorithm.dp;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

/**
 * Give an O(n*n)-time algorithm to find the longest monotomically increasing
 * subsequence of a sequence of n numbers
 *
 * The longest Increasing Subsequence (LIS) problem is to find the length of the
 * longest subsequence of a given sequence such that all elements of the
 * subsequence are sorted in increasing order. For example, length of LIS for {
 * 10, 22, 9, 33, 21, 50, 41, 60, 80 } is 6 and LIS is {10, 22, 33, 50, 60, 80}.
 * 
 * @author jason
 *
 */
public class longestIncreasingSubsequence {

	public static int[] find(int[] input) {
		/*
		 * lastnumber[i] gives the last number for LIS for input[0...i];
		 */
		int[] lastnumber = new int[input.length];

		/*
		 * length[i] gives the length of LIS for input[0...i]
		 */
		int[] length = new int[input.length];
		
		
		int[] from=new int[input.length];
		

		// base case.
		lastnumber[0] = input[0];
		length[0] = 1;
		from[0]=0;
		for (int i = 1; i < input.length; i++) {

			// base case
			length[i] = 1;
			lastnumber[i] = input[i];
			from[i]=i;
			
			for (int j = 0; j < i; j++) {
				// case we increase the LIS for input[0...j];
				if (input[i] > lastnumber[j]) {
					if (length[j] + 1 > length[i]) {
						length[i] = length[j] + 1;
						lastnumber[i] = input[i];
						from[i]=j;
					}
				} else if (length[j] > length[i]){
					length[i] = length[j];
					lastnumber[i] = lastnumber[j];
					from[i]=j;
				}
			}
		}
		int trackIndex=input.length-1;
		int[] result=new int[length[trackIndex]];
		do {
			result[length[trackIndex]-1]=lastnumber[trackIndex];
			if (length[trackIndex]==1){
				break;
			}
			trackIndex=from[trackIndex];
		} while (true);
		return result;
		
	}
	
	@Test
	public void test(){
		int[] input= { 10, 22, 9, 33, 21, 50, 41, 60, 80 };
		int[] expected={10, 22, 33, 50, 60, 80};
		int[] expected1={10, 22, 33, 41, 60, 80};
		
		
		assertTrue(Arrays.equals(expected, find(input)) ||Arrays.equals(expected1, find(input)));
	}
	
	
	//-----------------------------OnlogN approach
	public static class OneLIS {
		int index;  //for input[0...index]
		int lastNumber; //the last number for LIS
		int from; //from the index
		int length; // how long is the LIS
		public OneLIS(int index, int lastNumber, int from, int length) {
			super();
			this.index = index;
			this.lastNumber = lastNumber;
			this.from = from;
			this.length=length;
		}
		
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
	 * 
	 * 
	 */
	
	public static int[] findNlogN(int[] input){
		
		OneLIS[] track=new OneLIS[input.length];
		track[0]=new OneLIS(0, input[0], 0, 1); 
		NavigableMap<Integer, OneLIS> bst=new TreeMap<>();
		bst.put(track[0].lastNumber, track[0]);
		
		for (int i=1; i<input.length; i++){
			
			
			NavigableMap.Entry<Integer, OneLIS> lower=bst.lowerEntry(input[i]);
			NavigableMap.Entry<Integer, OneLIS> higher=bst.higherEntry(input[i]);
			if (lower==null){
				//there is no active list with end element<input[i], create a new entry
				track[i]=new OneLIS(i, input[i], i, 1);
				bst.put(input[i], track[i]);
				continue;
			}
			//lower is definitely not null. 
			track[i]=new OneLIS(i, input[i], lower.getValue().index, lower.getValue().length+1);
			bst.put(input[i], track[i]);
			if (higher!=null && higher.getValue().length==track[i].length){
				bst.remove(higher.getKey());
				//TODO do we need to remove this from track?
			}
		}
		
		OneLIS currentLIS=track[input.length-1];
		int[] result=new int[currentLIS.length];
		do {
			result[currentLIS.length-1]=currentLIS.lastNumber;
			if (currentLIS.length==1){
				break;
			}
			currentLIS=track[currentLIS.from];
		} while (true);
		return result;
		
		
	}
	@Test
	public void testNlogN(){
		int[] input= { 10, 22, 9, 33, 21, 50, 41, 60, 80 };
		int[] expected={10, 22, 33, 50, 60, 80};
		int[] expected1={10, 22, 33, 41, 60, 80};
		

		assertTrue(Arrays.equals(expected, findNlogN(input)) || Arrays.equals(expected1, findNlogN(input)));
	}
	
	

}
