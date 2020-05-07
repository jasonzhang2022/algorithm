package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

//http://www.programcreek.com/2013/01/construct-binary-tree-from-inorder-and-postorder-traversal/
public class COnstructBT {
	
	public static int[] construct(int[] inorder, int[] postorder){
		
		
		int height=height(inorder.length);
		int[] result=new int[ (1<<(height+1))-1];
		
		//int[] result=new int[inorder.length];
		
		fillTree(result, 0, inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
		
		return result;
	}
	
	
	protected static void fillTree(int[] result, int index, 
			int[] inorder, int inoffset, int inend, 
			int[] postorder,int postoffset, int postend){
		
		result[index]=postorder[postend];
		
		//end case.
		if (inoffset==inend){
			result[index]=inorder[inoffset];
			return;
		}
		
		for (int i=inoffset; i<=inend; i++){
			//findUsingArray root in inorder
			if (inorder[i]==postorder[postend]){
				
				int leftLen=i-inoffset;
				int rightLen=inend-i;
				if (leftLen>0){
					fillTree(result, index*2+1, inorder, inoffset, i-1, postorder, postoffset, postoffset+leftLen-1);
				}
				if (rightLen>0){
					fillTree(result, index*2+2, inorder, i+1, inend, postorder, postoffset+leftLen, postend-1);
				}
				break;
			}
		}
		
		
	}
	

	/*
	 * Assume that all leave nodes at the same level.
	 */
	public static int height(int n){
		int h=0;
		while (1<<h < n){
			h++;
		}
		return h;
	}
	
	@Test
	public void test(){
		int[] inorder={4,2,5, 1, 6,7,3,8};
		int[] postorder={4,5,2,6,7,8,3,1};
		
		int result[]=construct(inorder, postorder);
		System.out.println(Arrays.toString(result));
		
		assertTrue(Arrays.equals(result, new int[]{1, 2, 3, 4, 5, 7, 8, 0, 0, 0, 0, 6, 0, 0, 0}));
		
	}
	
}
