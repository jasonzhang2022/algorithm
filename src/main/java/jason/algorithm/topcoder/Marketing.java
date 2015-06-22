package jason.algorithm.topcoder;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

//http://community.topcoder.com/stat?c=problem_statement&pm=1524&rd=4472
/*
 * First, when there us nxn mutual relationship, we form a grid. we could use DFS or BFS to solve the problem.
 * 
 * Then think: 
 * 1)how is the edge defined. This decides how we can traverse from one vertex to another vertex.
 * 	Is this a direct graph or undirected graph? This grapp is undirected. So for each edge specification
 * we specifies two edges.
 * 
 * on the other hand, course schedule(see below) is directed graph
 * 2)how to mark node during traverse. In this particular example, we use 3 values for mark.
 * a): not visited. This case should exist for all graph traverse.
 * b) 1: in group one.
 * c) 2) in another group
 * 
 * 
 * 
 * 
 * Compare this with the course schedule example
 * http://www.programcreek.com/2014/06/leetcode-course-schedule-ii-java/
 * 
 */
public class Marketing {
	
	public static int find(String[] input){
		
		int[][] compete=new int[input.length][input.length];
		
		for (int i=0; i<input.length; i++){
			String s=input[i];
			if (s.length()==0){
				continue;
			}
			String[] competedWith=s.split(" ");
			for (int j=0; j<competedWith.length; j++){
				int c=Integer.parseInt(competedWith[j]);
				compete[i][c]=1;
				compete[c][i]=1;
			}
		}
		
		int cluster=0;
		
		/*
		 * grp[i]==0: not tocuhed.
		 * grp[i]==1: in the same grp.
		 * grp[i]==2 in the opposite grp
		 */
		int[] grp=new int[input.length];
		
		for (int i=0; i<input.length; i++){
			if (grp[i]==0){
				boolean ret=walkDFS(compete, i, grp);
				if (!ret){
					return -1;
				}
				cluster++;
			}
		}
	
			return 1<<cluster;
		
	}
	
	
	public static boolean walkDFS(int[][] compete, int from, int[] grp){
		
		Stack<Integer> stack=new Stack<>();
		stack.push(from);
		grp[from]=1;
		while (!stack.isEmpty()){
			
			int current=stack.pop();
			for (int i=0; i<compete.length; i++){
				if(compete[current][i]==1){
					//all i should go to opposite group.
					if (grp[i]==0){
						//not assigned yet
						if (grp[current]==1){
							grp[i]=2;
						} else{
							grp[i]=1;
						}
						stack.push(i);
					} else if (grp[i]==grp[current]){
						//it is assigned to the same group as current by other compete.
						return false;
						
					} else{
						//it has the correct group as is assigned by other compete
					}
				}
			}
		}
		return true;
	}
	
	

	
	
	@Test
	public void test(){
		assertEquals(2, find(new String[]{"1 4","2","3","0",""}));
		
		assertEquals(-1, find(new String[]{"1","2","0"}));
		
		assertEquals(2, find(new String[]{"1","2","3","0","0 5","1"}));
		
		assertEquals(1073741824, find(new String[]{"","","","","","","","","","",
				 "","","","","","","","","","",
				 "","","","","","","","","",""}));
		
		assertEquals(-1, find(new String[]{"1","2","3","0","5","6","4"}));
	}
}
