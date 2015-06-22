package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HOuseRobberCircle {
	
	public static int maxValue(int[] input){
		
		if (input.length==0){
			return 0;
		}
		if (input.length==1){
			return input[0];
		}
		if (input.length==2){
			return Math.max(input[0], input[1]);
		}
		
		
		int p2WithZero=input[0];
		int p2WithoutZero=0;
		
		int p1WithZero=input[0];
		int p1WithoutZero=input[1];
		
		
		for (int i=2; i<input.length; i++){
			
			//case 1: include self
			int case1WithoutZero=p2WithoutZero+input[i];
			int case1WithZero=p2WithZero+input[i];
			
			
			//case 2 not include self.
			int case2WithoutZero=p1WithoutZero;
			int case2WithZero=p1WithZero;
			
			p2WithoutZero=p1WithoutZero;
			p2WithZero=p1WithZero;
			
			p1WithoutZero=Math.max(case1WithoutZero, case2WithoutZero);
			p1WithZero=Math.max(case1WithZero, case2WithZero);
		}
		
		return Math.max(Math.max(p2WithZero, p2WithoutZero), p1WithoutZero);
		
		
	}
	
	@Test
	public void test() {
/*
		assertEquals(3, maxValue(new int[] { 3 }));
		assertEquals(4, maxValue(new int[] { 3, 4 }));
		assertEquals(5, maxValue(new int[] { 3, 4, 5 }));
		assertEquals(10, maxValue(new int[] { 3, 4, 5, 6 }));

		assertEquals(43, maxValue(new int[] { 20, 5, 23, 9 }));
	*/	
		assertEquals(29, maxValue(new int[] { 5, 20, 23, 9 }));
	}

}
