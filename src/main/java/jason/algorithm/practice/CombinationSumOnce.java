package jason.algorithm.practice;

import java.util.Arrays;
import java.util.Stack;

import org.junit.Test;

public class CombinationSumOnce {

	public static void find(int[] input, int target){
		find(input, 0, target, new Stack<Integer>());
	}
	
	public static void find(int[] input, int offset, int target, Stack<Integer> result){
		if (target==0){
			System.out.println(Arrays.deepToString(result.toArray()));
			return;
		}
		if(offset>=input.length){
			return;
		}
		
		
		if (input[offset]<=target){
			//include self
			result.push(input[offset]);
			find(input, offset+1, target-input[offset], result);
			result.pop();
		}
		
		//do not push self
		
		find(input, offset+1, target, result);
		
	}
	
	@Test 
	public void test(){
		find(new int[]{2,3, 4, 5, 7}, 12);
	}
	
}
