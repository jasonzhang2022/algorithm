package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.BitSet;

import org.junit.Test;

public class JumpGame {
	
	
	//worst case is O(N^2)
	public static boolean reachable(int[] input){
		
		BitSet reached=new BitSet(input.length);
		if (input[0]==0){
			return false;
		}
		reached.set(0);
		

		for (int i=0; i<input.length; i++){
			if (!reached.get(i)){
				continue;
			}
			for (int j=1; j<=input[i]; j++){
				if (j+i==input.length-1 ){
					return true;
				}
				if (j+i<input.length && input[j+i]!=0){
					reached.set(j+i);
				}
			}
		}
		return false;
	}
	
	@Test
	public void test(){
		assertFalse(reachable(new int[]{0, 3}));
		assertFalse(reachable(new int[]{1, 0, 4}));
		assertFalse(reachable(new int[]{2, 1, 0, 3}));
		
		assertTrue(reachable(new int[]{3, 1, 0, 2}));
		
		assertTrue(reachable(new int[]{3, 1, 0, 0}));
	}

	
	//better than my solution: linear time
	public static boolean canJump(int[] A) {
	    if(A.length <= 1)
	        return true;
	 
	    int max = A[0]; //max stands for the largest index that can be reached.
	 
	    for(int i=0; i<A.length; i++){
	        //if not enough to go to next
	        if(max <= i && A[i] == 0) 
	            return false;
	 
	        //update max    
	        if(i + A[i] > max){
	            max = i + A[i];
	        }
	 
	        //max is enough to reach the end
	        if(max >= A.length-1) 
	            return true;
	    }
	 
	    return false;    
	}
}
