package jason.algorithm.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

//http://www.programcreek.com/2014/02/leetcode-combination-sum-java/
public class CombinationSumRecursive {

	

	//use math calculation.
	public static void find(int[] input, int target){
		Arrays.sort(input);
		findSub(input, target, 0, new Stack<Integer>());
	}
	
	protected static void findSub(int[] input, int target, int offset, Stack<Integer> stack){
		//System.out.printf("%d, %d, %s\n", target, offset>=input.length?0:input[offset], Arrays.deepToString(stack.toArray()));
		if (offset>=input.length){
			return;
		}
		
		int count=0;
		while (target>=count*input[offset]){
			int diff=target-count*input[offset];
			if (count!=0){
				stack.push(input[offset]);
			}
			if (diff==0){
				System.out.println(Arrays.deepToString(stack.toArray()));
			} else{
				findSub(input, diff, offset+1, stack);
			}
			
			count++;
		}
		for (int i=1; i<count; i++){
			stack.pop();
		}
		
	
		
	}
	
	
	
	@Test 
	public void test(){
		find(new int[]{2,3,6,7}, 7);
	}
	
	public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
	    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	 
	    if(candidates == null || candidates.length == 0) return result;
	 
	    ArrayList<Integer> current = new ArrayList<Integer>();
	    Arrays.sort(candidates);
	 
	    combinationSum(candidates, target, 0, current, result);
	 
	    return result;
	}
	 
	public void combinationSum(int[] candidates, int target, int j, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> result){
	   if(target == 0){
	       ArrayList<Integer> temp = new ArrayList<Integer>(curr);
	       result.add(temp);
	       return;
	   }
	 
	   for(int i=j; i<candidates.length; i++){
	       if(target < candidates[i]) 
	            return;
	 
	       curr.add(candidates[i]);
	       combinationSum(candidates, target - candidates[i], i, curr, result);
	       curr.remove(curr.size()-1); 
	   }
	}
	
	@Test 
	public void test1(){
		 ArrayList<ArrayList<Integer>>  results=combinationSum(new int[]{2,3,6,7}, 7);
		 for (ArrayList<Integer> result: results){
			 System.out.println(Arrays.deepToString(result.toArray()));
		 }
	}
	
	
}
