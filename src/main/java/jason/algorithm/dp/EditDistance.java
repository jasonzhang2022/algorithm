package jason.algorithm.dp;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

//http://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/
public class EditDistance {
	
	int[][] cost;
	char[] from;
	char[] to;
	public int edit(char[] from, char[] to){
		this.from=from;
		this.to=to;
		
		
		cost=new int[from.length+1][to.length+1];
		for (int[] row:cost){
			Arrays.fill(row, -1);
		}
		for(int i=0; i<=to.length; i++){
			cost[0][i]=i; //from "" to to[0...i-1], each operation is a insetion
		}
		for (int i=0; i<=from.length; i++){
			cost[i][0]=i; //i deletion
		}
		
		
		
		return editSub(from.length, to.length);
	}
	
	/*
	 * Index starting from 1
	 */
	public int editSub(int fromEnd,  int toEnd){
		if (cost[fromEnd][toEnd]!=-1){
			return cost[fromEnd][toEnd];
		}
		
		int fromc=from[fromEnd-1];
		int toc=to[toEnd-1];
		if (fromc==toc){
			cost[fromEnd][toEnd]=editSub(fromEnd-1, toEnd-1);
			return cost[fromEnd][toEnd];
		}
		
		int edit=Integer.MAX_VALUE;
		//replace
		int replaceCost=editSub(fromEnd-1, toEnd-1)+1;
		edit=Math.min(edit, replaceCost);
		int deletionjCost=editSub(fromEnd-1, toEnd)+1; //delete last character from from
		edit=Math.min(edit, deletionjCost);
		int insertionCost=editSub(fromEnd, toEnd-1)+1; //insert toc at the end of from character
		edit=Math.min(edit, insertionCost);
		
		cost[fromEnd][toEnd]=edit;
		return edit;
	}
	
	@Test
	public void test1(){
		String from="geek";
		String to="gesek";
		int expected=1;
		assertEquals(expected, edit(from.toCharArray(), to.toCharArray()));
	}
	
	@Test
	public void test2(){
		String from="cat";
		String to="cut";
		int expected=1;
		assertEquals(expected, edit(from.toCharArray(), to.toCharArray()));
	}
	
	@Test
	public void test(){
		String from="sunday";
		String to="saturday";
		int expected=3;
		assertEquals(expected, edit(from.toCharArray(), to.toCharArray()));
	}

}
