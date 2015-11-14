package jason.datastructure.tree;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

/*
 * nversion Count for an array indicates – how far (or close) the array is from being sorted. If array is already sorted then inversion count is 0. If array is sorted in reverse order that inversion count is the maximum. 
Formally speaking, two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j Example:
The sequence 2, 4, 1, 3, 5 has three inversions (2, 1), (4, 1), (4, 3).
 */
public class CountInversion {

	
	public static int count(int[] input){
		TreeSet<Integer> tree=new TreeSet<>();
		int count=0;
		for(int i: input){
			tree.add(i);
			count+=tree.tailSet(i, false).size();
		}
		return count;
	}

	@Test
	public void testCount() {
		assertEquals(count(new int[]{2,4,1,3,5}), 3);
		
	}
	
	
	
	
}
