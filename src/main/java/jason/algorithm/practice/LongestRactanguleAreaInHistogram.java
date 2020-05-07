package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

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
	
	
	//practice what is in solution above by myself
	public static int largestRectangleArea1(int[] height) {
		if (height==null || height.length==0){
			return 0;
		}
		
		/*
		 * stack contains the index of the height.
		 * for height[stack[i]]=j, it contains the right edge of a continuous area with height j ends at i.
		 * This continuous area starts from stack[i-1];
		 */
		Stack<Integer> stack=new Stack<Integer>();
		int max = 0;
		for (int i=0; i<height.length; i++) {

			while (!stack.isEmpty() && height[stack.peek()]>height[i]){
				int j = stack.pop();
				int jstart = stack.isEmpty()?0: stack.peek()+1;
				max = Math.max(max, (i-jstart)*height[j]);
			}

			stack.push(i);
		}

		while (!stack.isEmpty()) {
			// from i to end, no height less than h
			int i = stack.pop();
			int h = height[i];

			int istart = stack.isEmpty()?0: stack.peek()+1;

			max = Math.max(max, (height.length -istart)*h);
		}
		

		return max;
	}

	
	@Test
	public void testFind(){
		
		int[] input={1,2,3,4,5,6};
		//bar 4 with 3 four area
		assertEquals(12, find(input));
		
		int[] input1={2,3,2,4};
		//2*4=8
		assertEquals(8, find(input1));
		
		int[] input2={1,3,2,4};
		//2*3=6
		assertEquals(6, find(input2));
		
		int[] input3={2,3,4, 1,1,4,1};
		//1*7=7
		assertEquals(7, find(input3));
		
		int[] input4={2,2,1,1,1};
		//1*7=7
		assertEquals(5, find(input4));
		
		//2*4=8
		assertEquals(8, find(new int[]{1,1,3,3,2,2}));
	}
	@Test
	public void testWebsite(){
		
		int[] input={1,2,3,4,5,6};
		//bar 4 with 3 four area
		assertEquals(12, largestRectangleArea(input));
		
		int[] input1={2,3,2,4};
		//2*4=8
		assertEquals(8, largestRectangleArea(input1));
		
		int[] input2={1,3,2,4};
		//2*3=6
		assertEquals(6, largestRectangleArea(input2));
		
		int[] input3={2,3,4, 1,1,4,1};
		//1*7=7
		assertEquals(7, largestRectangleArea(input3));
		int[] input4={2,2,1,1,1};
		//1*7=7
		assertEquals(5, largestRectangleArea(input4));
		
		//2*4=8
		assertEquals(8, largestRectangleArea(new int[]{1,1,3,3,2,2}));
	}

	@Test
	public void tempTest(){
		int[] input1={2,3,2,4};
		//2*4=8
		assertEquals(8, largestRectangleArea1(input1));
	}

	@Test
	public void testSelfCopy(){
		
		int[] input={1,2,3,4,5,6};
		//bar 4 with 3 four area
		assertEquals(12, largestRectangleArea1(input));
		
		int[] input1={2,3,2,4};
		//2*4=8
		assertEquals(8, largestRectangleArea1(input1));
		
		int[] input2={1,3,2,4};
		//2*3=6
		assertEquals(6, largestRectangleArea1(input2));
		
		int[] input3={2,3,4, 1,1,4,1};
		//1*7=7
		assertEquals(7, largestRectangleArea1(input3));
		int[] input4={2,2,1,1,1};
		//1*7=7
		assertEquals(5, largestRectangleArea1(input4));
		
		//2*4=8
		assertEquals(8, largestRectangleArea1(new int[]{1,1,3,3,2,2}));
	}
	
	
}
