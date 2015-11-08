package jason.algorithm.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MergeSort1 extends TestSetup {

	
	public static int[] sort(int[] input){
		
		int[] output=new int[input.length];
		
		for (int segLen=1; segLen<input.length; segLen*=2){
			for (int i=0; i<input.length; i+=segLen*2){
				merge(input, output, i, i+segLen, segLen);
			}
			int[] temp=output;
			output=input;
			input=temp;
			
		}
		return input;
		
		
		
	}
	
	public static void merge(int[] input, int[] output, int start1, int start2, int segLen){
		
		int i1=start1;
		int i2=start2;
		int o=start1;
		
		while (i1<input.length && i2<input.length && i1<start1+segLen && i2<start2+segLen){
			if (input[i1]<input[i2]){
				output[o++]=input[i1++];
			} else{
				output[o++]=input[i2++];
			}
		}
		
		while (i1<input.length &&i1<start1+segLen){
			output[o++]=input[i1++];
		}
		while (i2<input.length && i2<start2+segLen){
			output[o++]=input[i2++];
		}
	}
	
	

	@Test
	public void testMergeSort1() {
		long start=System.currentTimeMillis();
		int[] output=MergeSort1.sort(input);
		long end=System.currentTimeMillis();
		System.out.println("\nMerge Sort time :"+(end-start));
		for (int i=0; i<inputLen; i++) {
			assertThat(output[i], equalTo(i));
		}
		
	}
	
}
