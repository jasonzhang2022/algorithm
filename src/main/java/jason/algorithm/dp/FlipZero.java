package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

//http://www.geeksforgeeks.org/find-zeroes-to-be-flipped-so-that-number-of-consecutive-1s-is-maximized/
public class FlipZero {

	public static int findCount(int[] input, int n){
		
		/*
		 * lastPositionWithNZero[i] is the index from which to i that contains n zero.
		 */
		int[] lastPositionWithNZero=new int[input.length];
		Arrays.fill(lastPositionWithNZero, -1);
		
		//initization.
		int temp=0;
		int i=0;
		for (; i<input.length; i++){
			if (input[i]==0){
				temp++;
			}
			if (temp==n){
				break;
			}
		}
		if (temp<n){
			return input.length;
		}
		int maxlen=i-0+1;
		lastPositionWithNZero[i]=0;
		for (i++; i<input.length; i++){
			if (input[i]==1){
				lastPositionWithNZero[i]=lastPositionWithNZero[i-1];
				maxlen=Math.max(maxlen, i-lastPositionWithNZero[i]+1);
			} else{
				//remove one zero from beginning
				int j=lastPositionWithNZero[i-1];
				for (; j<i; j++){
					if (input[j]==0){
						break;
					}
				}
				lastPositionWithNZero[i]=j+1;
				maxlen=Math.max(maxlen, i-lastPositionWithNZero[i]+1);
			}
		}
		return maxlen;
		
	}
	
	@Test
	public void testCount(){
		assertEquals(4, findCount(new int[]{0,0,0,1}, 4));
		assertEquals(5, findCount(new int[]{1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1}, 1));
		assertEquals(8, findCount(new int[]{1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1}, 2));

		
	}
	
}
