package jason.algorithm.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jason.algorithm.Swaper;

public class RadixSort_MSD_partition extends TestSetup {

	
	public static int[] sort(int[] input){
		partition(input, 0, input.length-1, 30);
		return input;
	}
	
	
	/*
	 * Partition the input from [i, j] inclusively into two section.
	 * The first section: bit at digit is 0.
	 * The second section: bit at digit is 1. 
	 */
	public static void partition(int[] input, int i, int j, int digit){
		if (i>=j){
			return;
		}
		if (digit<0){
			return;
		}
		int start=i;
		int end=j;
		while (start!=end){
			if (isBitSet(input[start], digit)){
				Swaper.swap(input, start, end);
				end--; //input[end] has bit  at digit set to 1.
			} else {
				start++;
			}
		}
		
		if (isBitSet(input[start], digit)){
			partition(input, i, start-1, digit-1);
			partition(input, start, j, digit-1);
		} else {
			partition(input, i, start, digit-1);
			partition(input, start+1, j, digit-1);
		}
	}
	
	public static boolean isBitSet(int i, int digit){
		int x=1<<digit;
		return (i&x)==0?false:true;
	}
	

	
	@Test
	public void testMSD() {
		long start=System.currentTimeMillis();
		int[] output=RadixSort_MSD_partition.sort(input);
		long end=System.currentTimeMillis();
		System.out.println("\nMSD Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
	}
}
