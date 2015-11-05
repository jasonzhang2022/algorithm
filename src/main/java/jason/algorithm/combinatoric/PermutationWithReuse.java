package jason.algorithm.combinatoric;

import static org.junit.Assert.assertEquals;
import jason.algorithm.combinatoric.Permutation.Count;
import jason.algorithm.combinatoric.Permutation.SubArray;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Test;

public class PermutationWithReuse {

	/**
	 * 
	 * @param ints input elements
	 * @param consumer how to handle output
	 * @param finalLen input could be length of n, but we only need partial permutation, permute k element of n.
	 */
	public static void permute(int[] ints, Consumer<int[]> consumer, int k){
		int[] output=new int[k];
		nextIndex(ints, consumer, k, 0, output);
		
	}
	
	private static void nextIndex(int[] ints, Consumer<int[]> consumer, int k, int index, int[] output ){
		if (index==k){
			consumer.accept(output);
			return;
		}
		Set<Integer> used=new HashSet<Integer>();
		/*
		 * Give each element a chance to be index
		 */
		for (int j=0; j<ints.length; j++){
			if (used.contains(ints[j])){
				continue;
			} 
			used.add(ints[j]);
			output[index]=ints[j];
			nextIndex(ints, consumer, k, index+1, output);
		}
	}
	

	/**
	 * All elements in input are unique
	 */
	@Test
	public void TestPermutationN(){
		String input="ABC";
		int expectedPermutation=3*3*3;
		Count c=new Count();
		
		Consumer<int[]> consumer= a->{
			for (int index: a){
				System.out.print(input.charAt(index));
			}
			System.out.print("\n");
			c.count++;
		};
		PermutationWithReuse.permute(new int[]{0, 1,2}, consumer, 3);
		
		assertEquals(c.count, expectedPermutation);
	}
	/**
	 * All elements in input are unique
	 */
	@Test
	public void TestPermutationK(){
		String input="ABC";
		int expectedPermutation=3*3;
		Count c=new Count();
		
		Consumer<int[]> consumer= a->{
			for (int index: a){
				System.out.print(input.charAt(index));
			}
			System.out.print("\n");
			c.count++;
		};
		PermutationWithReuse.permute(new int[]{0, 1,2}, consumer, 2);
		
		assertEquals(c.count, expectedPermutation);
	}
	
	/**
	 * All elements in input are unique
	 */
	@Test
	public void TestPermutationDuploication(){
		String input="ABA";
		int expectedPermutation=2*2*2;
		Count c=new Count();
		
		Consumer<int[]> consumer= a->{
			for (int index: a){
				System.out.print(input.charAt(index));
			}
			System.out.print("\n");
			c.count++;
		};
		PermutationWithReuse.permute(new int[]{0, 1, 0}, consumer, 3);
		
		assertEquals(c.count, expectedPermutation);
	}
}
