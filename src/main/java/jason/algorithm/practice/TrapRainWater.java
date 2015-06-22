package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

//http://www.programcreek.com/2014/06/leetcode-trapping-rain-water-java/
/*
 * Idea:
 *  If there is descending sequence, push it to stack. 
 *  If we see a ascending sequence, we can trap some water. 
 *  
 *  We need to pop to a position which is larger than current bar level.
 *  At the same time we track how much area is occupied by the bar itself.
 *  
 */
public class TrapRainWater {

	
	public static int trapWater(int[] input){
		if (input.length<=2){
			return 0;
		}
		int start=0;
		int end=input.length-1;
		while (input[start]<input[start+1] && start<input.length-2){
			start++;
		}
		while (input[end-1]>input[end] && end-1>=0){
			end--;
		}
		if (start==end){
			return 0;
		}
		
		if (input[start]<input[end]){
			return walkRight(input, new int[]{start, end}, 0);
		} else{
			return walkLeft(input, new int[]{start, end}, 0);
		}
	}
	
	public static int walkLeft(int[] input, int[] leftRight, int total){
		int left=leftRight[0];
		int right=leftRight[1];
		
		if (left==right){
			return total;
		}
		
		for (int j=right-1; j>left; j--){
			if (input[j]<=input[right]){
				total+=input[right]-input[j];
			} else{
				if (input[j]<=input[left]){
					right=j;
					continue;
				} else{
					leftRight[1]=j;
					return walkRight(input, leftRight, total);
				}
			}
		}
		return total;
		
		
	}
	
	public static int walkRight(int[] input, int[] leftRight, int total){
		int left=leftRight[0];
		int right=leftRight[1];
		if (left==right){
			return total;
		}
		for (int j=left+1; j<right; j++){
			if (input[j]<=input[left]){
				total+=input[left]-input[j];
			} else{
				if (input[j]<=input[right]){
					left=j;
					continue;
				} else{
					leftRight[0]=j;
					return walkLeft(input, leftRight, total);
				}
			}
		}
		return total;
	}
	
	@Test
	public void test(){
		assertEquals(0, trapWater(new int[]{}));
		assertEquals(0, trapWater(new int[]{1}));
		assertEquals(0, trapWater(new int[]{1,2,3}));
		assertEquals(0, trapWater(new int[]{1,2,3,4,4,3,2,1}));
		assertEquals(1, trapWater(new int[]{3,1, 2}));
		assertEquals(1, trapWater(new int[]{3,3,1,2}));
		assertEquals(6, trapWater(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
		
	}
	
	
	
	
	
	
	
}
