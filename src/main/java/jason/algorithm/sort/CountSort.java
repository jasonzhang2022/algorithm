package jason.algorithm.sort;

import static org.junit.Assert.assertTrue;
import jason.algorithm.Swaper;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;


/**
 * Assumption: range is small. 
 * @author jason
 *
 */
public class CountSort extends TestSetup{

	
	
	public static int[] stable(int[] input, int min, int max){
		int[] counts=new int[max-min+1];
		Arrays.fill(counts, 0);
		//step 1 counts.
		for(int value: input){
			counts[value-min]=counts[value-min]+1;
		}
		
		//step2 calculate the starting index for each value
		int nextStartingIndex=0;
		int prevCount=0;
		for (int i=0; i<max-min+1; i++){
			prevCount=counts[i];
			counts[i]=nextStartingIndex;
			nextStartingIndex=nextStartingIndex+prevCount;
		}
		
		
		int[] results=new int[input.length];
		for (int i=0; i<input.length; i++){
			int value=input[i];
			int index=counts[value-min];
			results[index]=value;
			counts[value-min]=counts[value-min]+1; //increase index by one, since we add the value
		}
		return results;
	}
	
	public static int[] notStableInPlace(int[] input, int min, int max){
		int[] counts=new int[max-min+1];
		Arrays.fill(counts, 0);
		//step 1 counts.
		for(int value: input){
			counts[value-min]=counts[value-min]+1;
		}
		
		//step2 calculate the starting index for each value
		int nextStartingIndex=0;
		int prevCount=0;
		for (int i=0; i<max-min+1; i++){
			prevCount=counts[i];
			counts[i]=nextStartingIndex;
			nextStartingIndex=nextStartingIndex+prevCount;
		}
		
		int[] endIndices=Arrays.copyOf(counts, counts.length);
		
		//--------------the difference is here.
		int i=0;
		while (i<input.length){
			int value=input[i];
			int startIndex=counts[value-min];
			int endIndex=endIndices[value-min];
			if (i>=startIndex && i<=endIndex){
				i++; //value is in correct position
				continue;
			}
			//we put value in correct position, 
			//we swap value at endIndex to current position.
			//This is not stable since value at endIndex may not be the first value of its kind.
			//once we pass i, we never touch it again,
			//So endIndex >i;
			Swaper.swap(input, i, endIndex);
			endIndices[value-min]=endIndices[value-min]+1;
			
			
		}
		return input;
	}
	
	
	
	@Test
	public void test() {
		
		Random rand=new Random();
		inputLen=200;
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			//value >=50, <150
			input[i]=rand.nextInt(100)+50;
		}
		
		int[] results=stable(input, 50, 149);
		for(int i=1; i<results.length; i++){
			assertTrue(results[i]>=results[i-1]);
		}
		
		results=notStableInPlace(input, 50, 149);
		for(int i=1; i<results.length; i++){
			assertTrue(results[i]>=results[i-1]);
		}
	
		
	}
	
	

	@Test
	public void testBig() {
		
		Random rand=new Random();
		inputLen=1000;
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			//value >=50, <150
			input[i]=rand.nextInt(100)+50;
		}
		
		int[] results=stable(input, 50, 149);
		for(int i=1; i<results.length; i++){
			assertTrue(results[i]>=results[i-1]);
		}
		
		results=notStableInPlace(input, 50, 149);
		for(int i=1; i<results.length; i++){
			assertTrue(results[i]>=results[i-1]);
		}
	
		
	}
	
}
