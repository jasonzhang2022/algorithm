package jason.algorithm.combinatoric;

import static org.junit.Assert.assertEquals;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.junit.Test;

public class Permutation {
	
	
	/*
	 * create a sub array
	 */
	public static class SubArray {
		int[] array;
		int offset;

		public SubArray(int[] array, int offset) {
			super();
			this.array = array;
			this.offset = offset;
		}
		public int get(int i){
			return array[i+offset];
		}
		
		public void swap(int i, int j){
			int tmp=array[i+offset];
			array[i+offset]=array[j+offset];
			array[j+offset]=tmp;
		}
		
		public int getLength(){
			return array.length-offset;
		}
		public int getOffset() {
			return offset;
		}
	}
	
	/**
	 * 
	 * @param ints input elements
	 * @param consumer how to handle output
	 * @param finalLen input could be length of n, but we only need partial permutation, permute k element of n.
	 */
	public static void permute(int[] ints, Consumer<int[]> consumer, int finalLen){
		SubArray subArray=new SubArray(ints, 0);
		permute(subArray, consumer, finalLen);
	}
	
	
	
	/*
	 * Reduce the problem to 
	 * what is 1-permutation for K. 
	 */
	private static void endPermute(SubArray input, Consumer<int[]> consumer, int finalLen){
		Set<Integer> beFirst=new HashSet<Integer>();
		
		//for element at N-K, give each of them once chance at the end.
		for (int i=0; i<input.getLength(); i++){
			if (beFirst.contains(input.get(i))){
				continue;
			}
			beFirst.add(input.get(i));
			input.swap(i,  0);
			consumer.accept(input.array);
			input.swap(0,  i);
		}
	}
	
	
	private static void permute(SubArray input, Consumer<int[]> consumer, int finalLen){
		
		
		
		if (input.getOffset()+1==finalLen){
			endPermute(input, consumer, finalLen);
			return;
		}
		
		Set<Integer> beFirst=new HashSet<Integer>();
		for (int i=0; i<input.getLength(); i++){
			/*
			 * We are going to produce all permutation which starts with 
			 * input[i];
			 */
			if (beFirst.contains(input.get(i))){
				//this element has be first before.
				continue;
			}
			beFirst.add(input.get(i));
			
			//all permutation starts with input[i];
			input.swap(i,  0);
			
			SubArray child=new SubArray(input.array, input.offset+1 );
			
			permute(child, consumer, finalLen);
			
			input.swap(0,  i);
		}
		
		
	}
	
	
	//---------------------------------test case
	public static class Count {
		int count;
	}

	public static class TestCase {

		/**
		 * All elements in input are unique
		 */
		@Test
		public void TestPermutationN() {
			String input = "ABCD";
			int expectedPermutation = 4 * 3 * 2 * 1;
			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int index : a) {
					System.out.print(input.charAt(index));
				}
				System.out.print("\n");
				c.count++;
			};
			Permutation.permute(new int[]{0, 1, 2, 3}, consumer, 4);

			assertEquals(c.count, expectedPermutation);
		}

		/**
		 * Some element are duplicated.
		 * Here ABCDBC is a multiset. B  and C appear twice each time.
		 */
		@Test
		public void TestPermutationNDuplicate() {
			//this is a multiset
			String input = "ABCDBC";
			int expectedPermutation = 6 * 5 * 4 * 3 * 2 * 1 / 2 / 2;
			Count c = new Count();

			Set<String> sets = new HashSet<>();
			Consumer<int[]> consumer = a -> {
				StringBuilder sb = new StringBuilder();
				for (int index : a) {
					System.out.print(input.charAt(index));
					sb.append(input.charAt(index));

				}
				sets.add(sb.toString());
				System.out.print("\n");
				c.count++;
			};
			int[] ints = new int[]{0, 1, 2, 3, 1, 2};
			Arrays.sort(ints);
			Permutation.permute(ints, consumer, 6);
			//Permutation.permuteAvoidDuplicate(new int[]{0, 1,2,3, 1,2}, consumer);

			assertEquals(c.count, expectedPermutation);
			assertEquals(sets.size(), c.count);
		}

		@org.junit.Test
		public void TestPermutationNDuplicate2() {
			//this is a multiset
			String input = "ABAB";
			int expectedPermutation = 6;
			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int index : a) {
					System.out.print(input.charAt(index));
				}
				System.out.print("\n");
				c.count++;
			};
			int[] ints = new int[]{0, 0, 1, 1};
			Arrays.sort(ints);
			Permutation.permute(ints, consumer, 4);
			//Permutation.permuteAvoidDuplicate(new int[]{0, 1,2,3, 1,2}, consumer);

			assertEquals(c.count, expectedPermutation);
		}

		@Test
		public void TestPermutationNK() {
			String input = "ABCDEFGH";
			int k = 3;

			int expectedPermutation = 8 * 7 * 6;

			Count c = new Count();

			Consumer<int[]> consumer = a -> {
				for (int i = 0; i < k; i++) {
					System.out.print(input.charAt(a[i]));
				}
				System.out.print("\n");
				c.count++;
			};
			;
			Permutation.permute(IntStream.range(0, 8).toArray(), consumer, k);
			//Permutation.permuteAvoidDuplicate(new int[]{0, 1,2,3, 1,2}, consumer);

			assertEquals(c.count, expectedPermutation);


		}
	}

}
