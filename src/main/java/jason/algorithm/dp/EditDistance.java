package jason.algorithm.dp;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Test;

//http://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/
public class EditDistance {
	
	int[][] cost;
	char[] from;
	char[] to;
	public  int edit(char[] from, char[] to){
		this.from = from;
		this.to = to;
		cost = new int[from.length+1][to.length+1];

		// suppose the from has 0 charatcer.
		for (int i=0; i<=to.length; i++){
			// insert the last character from end.
			cost[0][i]=i;
		}

		for (int i=1; i<=from.length; i++){
			Arrays.fill(cost[i], -1);
			cost[i][0]=i; //delete this characters.
		}
		return editSub(from.length, to.length);
	}
	
	/*
	 * Index starting from 1
	 */
	public int editSub(int fromLength,  int toLength){

		if (fromLength==0 || toLength==0){
			return cost[fromLength][toLength];
		}
		if (cost[fromLength][toLength]!=-1){
			return cost[fromLength][toLength];
		}

		// how can be the last charatcer at fromEnd can be moved to toEnd.
		char f = from[fromLength-1];
		char t = to[toLength-1];

		int value;
		if (f==t){
			value = editSub(fromLength-1, toLength -1);
		} else {
			//delete f.
			int deleteF = editSub(fromLength-1, toLength) +1;
			int replace = editSub(fromLength-1, toLength-1)+1;
			int insert = editSub(fromLength, toLength -1) +1;
			value = IntStream.of(deleteF, replace, insert).min().getAsInt();

		}
		cost[fromLength][toLength]= value;

		return value;
	}

	public static class TestCase {
		@Test
		public void test1() {
			String from = "geek";
			String to = "gesek";
			int expected = 1;
			assertEquals(expected, new EditDistance().edit(from.toCharArray(), to.toCharArray()));
		}

		@Test
		public void test2() {
			String from = "cat";
			String to = "cut";
			int expected = 1;
			assertEquals(expected, new EditDistance().edit(from.toCharArray(), to.toCharArray()));
		}

		@Test
		public void test() {
			String from = "sunday";
			String to = "saturday";
			int expected = 3;
			assertEquals(expected, new EditDistance().edit(from.toCharArray(), to.toCharArray()));
		}

	}
}
