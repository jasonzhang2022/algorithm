package jason.algorithm.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.junit.Test;

public class CombinationSum {

	
	public static void combineRepeatable(int[] input, int target){
		combineRepeatableSub(input, target, 0, new Stack<Integer>());
	}
	
	private static void combineRepeatableSub(int[] input, int target, int offset, Stack<Integer> stack){
		if (target==0){
			System.out.println(Arrays.deepToString(stack.toArray()));
			return;
		}
		
		
		//start a tree rooted at start, a
		//each tree will have sub nodes from start to length;
		for (int start=offset; start<input.length; start++){
			if (start>target){
				//start is illegal choice for root
				continue;
			}
			
			//start is a good choice
			stack.push(input[start]);
			combineRepeatableSub(input, target-input[start], start, stack);
			stack.pop();
			
		}
		
	}
	
	@Test 
	public void testRepeatble(){
		combineRepeatable(new int[]{2,3,6,7}, 7);
	}
	
	public static void combineOnce(int[] input, int target){
		combineOnceSub(input, target, 0, new Stack<Integer>());
	}
	
	private static void combineOnceSub(int[] input, int target, int offset, Stack<Integer> stack){
		if (target==0){
			System.out.println(Arrays.deepToString(stack.toArray()));
			return;
		}
		
		
		//start a tree rooted at start, a
		//each tree will have sub nodes from start+1 to length; start can only be used once
		for (int start=offset; start<input.length; start++){
			if (start>target){
				//start is illegal choice for root
				continue;
			}
			
			//start is a good choice
			stack.push(input[start]);
			combineOnceSub(input, target-input[start], start+1, stack);
			stack.pop();
			
		}
		
	}
	
	@Test 
	public void testOnce(){
		combineOnce(new int[]{2,3, 4, 5, 7}, 12);
	}
}