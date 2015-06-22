package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class JumpGameMinimum {
	public static int miniJump(int[] input){
		int[] mins=new int[input.length];
		Arrays.fill(mins, -1);
		mins[0]=0;
		
		
		for (int i=0; i<input.length; i++){
			if (mins[i]==-1){
				continue;
			}
			
			for (int j=1; j<=input[i]; j++){
				//is this logic correct?
				/*
				if (j+i==input.length){
					return mins[i]+1;
				}*/
				
				if (j+i<input.length){
					if (mins[j]==-1){
						mins[j]=mins[i]+1;
					} else{
						mins[j]=Math.min(mins[j], mins[i]+1);
					}
				}
				
				
			}
			
		}
		
		return mins[input.length-1];
		
		
		
		
	}

	@Test
	public void test(){
		assertEquals(-1, miniJump(new int[]{0, 3}));
	
		assertEquals(1, miniJump(new int[]{3, 1,1,2}));
	}
	
	public static int miniJump1(int[] input){
		
		int preMaxPosition=0;
		int maxPosition=0;
		
		int numberOfJump=0;
		for (int i=0; i<input.length && i<=maxPosition; i++){
			
			if (i>preMaxPosition){
				
			}
			
			maxPosition=Math.max(maxPosition, i+input[i]);
		}
		
		if (maxPosition<input.length-1){
			//can not reach
			return -1;
		}
		return numberOfJump;
		
	}
}
