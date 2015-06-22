package jason.algorithm.topcoder;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Stack;

import org.junit.Test;

public class Circuits {

	
	public static int longest(String[] connectsStr, String[] costsStr){
		int N=connectsStr.length;
		int[][] costs=new int[N][N];
		for (int r=0; r<N; r++){
			if (connectsStr[r].length()==0){
				continue;
			}
			String[] edges=connectsStr[r].split(" ");
			String[] weights=costsStr[r].split(" ");
			for (int j=0; j<edges.length; j++){
				int toVertex=Integer.parseInt(edges[j]);
				int cost=Integer.parseInt(weights[j]);
				costs[r][toVertex]=cost;
			}
		}
		
		//maxResult[i] is the max result from i to all possible nodes.
		int[] maxResult=new int[N];
		Arrays.fill(maxResult, -1);
		
		//no cyclic so we don't need to mark wether a node is visited or not.
		int max=0;
		for (int i=0; i<N; i++){
			max=Math.max(max, findLongestFromI(i, costs, maxResult));
		}
		
		return max;
		
		
	}
	
	
	public static int findLongestFromI(int from, int[][] costs, int[] maxResult){
		if (maxResult[from]!=-1){
			return maxResult[from];
		}
		
		int max=0;
		for (int col=0; col<costs.length; col++){
			//we have costs.
			if (costs[from][col]!=0){
				max=Math.max(max, findLongestFromI(col, costs, maxResult)+costs[from][col]);
			}
		}
		maxResult[from]=max;
		return max;
	}
	
	
	
	@Test
	public void test(){
		assertEquals(12, longest(new String[]{"1 2",
				 "2",
				 ""}, new String[]{"5 3",
				 "7",
				 ""}));
		
		
		
		assertEquals(10, longest(new String[]{"1 2 3 4 5","2 3 4 5","3 4 5","4 5","5",""}, new String[]{"2 2 2 2 2","2 2 2 2","2 2 2","2 2","2",""}));
		
		assertEquals(9, longest(new String[]{"1","2","3","","5","6","7",""}, new String[]{"2","2","2","","3","3","3",""}));
		
		
		assertEquals(22, longest(new String[]{"","2 3 5","4 5","5 6","7","7 8","8 9","10",
				 "10 11 12","11","12","12",""}, new String[]{"","3 2 9","2 4","6 9","3","1 2","1 2","5",
				 "5 6 9","2","5","3",""}));
		
		assertEquals(105, longest(new String[]{"","2 3","3 4 5","4 6","5 6","7","5 7",""}, new String[]{"","30 50","19 6 40","12 10","35 23","8","11 20",""}));
		
	}
	
	public static int longestStack(String[] connectsStr, String[] costsStr){
		int N=connectsStr.length;
		int[][] costs=new int[N][N];
		for (int r=0; r<N; r++){
			if (connectsStr[r].length()==0){
				continue;
			}
			String[] edges=connectsStr[r].split(" ");
			String[] weights=costsStr[r].split(" ");
			for (int j=0; j<edges.length; j++){
				int toVertex=Integer.parseInt(edges[j]);
				int cost=Integer.parseInt(weights[j]);
				costs[r][toVertex]=cost;
			}
		}
		
		//maxResult[i] is the max result from i to all possible nodes.
		int[] maxResult=new int[N];
		Arrays.fill(maxResult, -1);
		
		//no cyclic so we don't need to mark wether a node is visited or not.
		int max=0;
		for (int i=0; i<N; i++){
			max=Math.max(max, findLongestFromIStack(i, costs, maxResult));
		}
		
		return max;
		
		
	}
	
	public static int findLongestFromIStack(int from, int[][] costs, int[] maxResult){
		if (maxResult[from]!=-1){
			return maxResult[from];
		}
		
		Stack<Integer> childNodes=new Stack<>();
	
		childNodes.push(from);
		while (!childNodes.isEmpty()){
			int current=childNodes.peek();
			
			boolean allHasValue=true;
			int max=0;
			for (int col=0; col<costs.length; col++){
				if (costs[current][col]!=0 ){
					if (maxResult[col]==-1){
						childNodes.push(col);
						allHasValue=false;
					} else{
						max=Math.max(maxResult[col]+costs[current][col], max);
					}
				}
			}
			
			if (allHasValue){
				maxResult[current]=max;
				childNodes.pop();
			}
		}
		return maxResult[from];
	}
	
	
	
	@Test
	public void testStack(){
		assertEquals(12, longestStack(new String[]{"1 2",
				 "2",
				 ""}, new String[]{"5 3",
				 "7",
				 ""}));
		
		
		
		assertEquals(10, longestStack(new String[]{"1 2 3 4 5","2 3 4 5","3 4 5","4 5","5",""}, new String[]{"2 2 2 2 2","2 2 2 2","2 2 2","2 2","2",""}));
		
		assertEquals(9, longestStack(new String[]{"1","2","3","","5","6","7",""}, new String[]{"2","2","2","","3","3","3",""}));
		
		
		assertEquals(22, longestStack(new String[]{"","2 3 5","4 5","5 6","7","7 8","8 9","10",
				 "10 11 12","11","12","12",""}, new String[]{"","3 2 9","2 4","6 9","3","1 2","1 2","5",
				 "5 6 9","2","5","3",""}));
		
		assertEquals(105, longestStack(new String[]{"","2 3","3 4 5","4 6","5 6","7","5 7",""}, new String[]{"","30 50","19 6 40","12 10","35 23","8","11 20",""}));
		
	}
}
