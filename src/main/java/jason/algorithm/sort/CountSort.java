package jason.algorithm.sort;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;


/**
 * Both sorting method work. But the first methid is more versatile.
 * First method takes an range. This may lead to reduced count[] array size.
 * But second method can be modified to use the same min/max.
 * 
 * The important feature about the first method is that.
 * 1. it is stable. 
 * 2. it can accept an object as array element and a function to extract an integer key from object. 
 * This is powerful concept. In reality, we sort may different things. But all sortings could be reduce a 
 * primitive key such as integre or string. 
 * 
 * @author jason
 *
 */
public class CountSort {

	
	public static int[] countSort(int[] input, int min, int max) {
		int[] counts=new int[max-min+2];
		for (int value: input) {
			counts[value-min+1]++;
		}
		for (int i=1; i<(max-min+2);i++) {
			counts[i]=counts[i]+counts[i-1];
		}
		int[] results=new int[input.length];
		
		for (int value: input) {
			results[counts[value-min]]=value;
			counts[value-min]=counts[value-min]+1;
		}
		return results;
		
	}
	
	
	
	
	
	
	
	/**
	 * Assum the value in input is less than 100(0-99)
	 * @param input
	 * @return
	 */
	public static final int[] sort(int[] input){
		int[] count=new int[100];
		//linear
		for (int i=0; i<input.length; i++) {
			count[input[i]]=count[input[i]]+1;
		}
		int outputIndex=0;
		for (int i=0; i<100; i++){
			for (int j=0; j<count[i]; j++){
				input[outputIndex++]=i;
			}
		}
		return input;
		
	}
}
