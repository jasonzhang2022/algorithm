package jason.algorithm.practice;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

//http://www.programcreek.com/2014/05/leetcode-largest-rectangle-in-histogram-java/
public class LongestRactanguleAreaInHistogram {
	
	
	public static int find(int[] input){
		
		int maxArea=0;

		//we use this to track all those bar value which area has not been finalized yet.
		Map<Integer, Integer> barValueStartIndex=new HashMap<Integer, Integer>();
		
		for (int i=0; i<input.length; i++){
			
			int currentBarValue=input[i];
			int currentBarStartIndex=i;
			
			Set<Integer> bars=barValueStartIndex.keySet();
			for (Iterator<Integer> iter=bars.iterator(); iter.hasNext();){
			
				Integer barInLeft=iter.next();
				if (barInLeft>=currentBarValue){
					int barIndex=barValueStartIndex.get(barInLeft);
					//we finalize the area that is taller than currentBarValue;
					if (barInLeft>currentBarValue){
						//we end the area with this bar value.
						int area=(i-barIndex)*barInLeft;
						if (area>maxArea){
							maxArea=area;
						}
						iter.remove();
						//barValueStartIndex.remove(barInLeft);
					}
					
					//Suppose we have a bar value is 4, 
					//The area with a height of 4 starts from preceeding bar with height >=barValue;
					if (barIndex<currentBarStartIndex){
						currentBarStartIndex=barIndex;
					}
				}
			}
			barValueStartIndex.put(currentBarValue, currentBarStartIndex);
		}
		
		
		//finalize all those reaming bar.
		for (Integer barValue: barValueStartIndex.keySet()){
			int area=(input.length-barValueStartIndex.get(barValue))*barValue;
			if (area>maxArea){
				maxArea=area;
			}
		}
		
		
		return maxArea;
	}
	
	//solution here:http://www.programcreek.com/2014/05/leetcode-largest-rectangle-in-histogram-java/
	public static int largestRectangleArea(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
	 
		Stack<Integer> stack = new Stack<Integer>();
	 
		int max = 0;
		int i = 0;
	 
		while (i < height.length) {
			//push index to stack when the current height is larger than the previous one
			if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
				stack.push(i);
				i++;
			} else {
			//calculate max value when the current height is less than the previous one
				int p = stack.pop();
				int h = height[p];
				int w = stack.isEmpty() ? i : i - stack.peek() - 1;
				max = Math.max(h * w, max);
			}
	 
		}
	 
		while (!stack.isEmpty()) {
			int p = stack.pop();
			int h = height[p];
			int w = stack.isEmpty() ? i : i - stack.peek() - 1;
			max = Math.max(h * w, max);
		}
	 
		return max;
	}

}
