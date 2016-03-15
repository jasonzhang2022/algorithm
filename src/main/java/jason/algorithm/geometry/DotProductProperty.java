package jason.algorithm.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DotProductProperty {
	
	//(0,0)->B dot B->C
	public static int dot(int[]A, int[]B, int[] C){
		return (B[0]-A[0])*(C[0]-B[0])+(B[1]-A[1])*(C[1]-B[1]);
	}
	
	public static double distance(int[] A, int[] B){
		return Math.sqrt((B[1]-A[1])*(B[1]-A[1]) + (B[0]-A[0])*(B[0]-A[0]) );
	}
	
	public static class TestCase {
		/*
		 * Dot product can't test angle direction, but angle magnitude
		 * 
		 * If the product is positive, the angle is less than PI/2
		 */
		@Test
		public void testDotPositive(){
			int[] A={0, 0};
			int[] B={0, 2};
			int[] C1={1, 3};
			
			
			int result1=dot(A, B, C1);
			double angle1=Math.acos(dot(A, B, C1)/distance(A, B)/distance(B, C1))/Math.PI;
			
			C1[0]=-1;
			int result2=dot(A, B, C1);
			double angle2=Math.acos(dot(A, B, C1)/distance(A, B)/distance(B, C1))/Math.PI;
			
			assertEquals(result1, result2);
			assertEquals(String.format("%.3f",angle1), String.format("%.3f",angle2));
		}
		/*
		 * If the value is zero, the angle is PI/2, two lines are perpendicular to each other
		 */
		@Test
		public void testDotZero(){
			int[] A={0, 0};
			int[] B={0, 2};
			int[] C1={1, 2};
			
			
			int result1=dot(A, B, C1);
			assertEquals(result1, 0);
			double angle1=Math.acos(dot(A, B, C1)/distance(A, B)/distance(B, C1))/Math.PI;
			
			C1[0]=-1;
			int result2=dot(A, B, C1);
			double angle2=Math.acos(dot(A, B, C1)/distance(A, B)/distance(B, C1))/Math.PI;
			
			assertEquals(result1, result2);
			assertEquals(String.format("%.3f",angle1), String.format("%.3f",angle2));
		}
		
		/*
		 * If the angle is greater than PI/2, the dot product is negative
		 */
		@Test
		public void testDotNegative(){
			int[] A={0, 0};
			int[] B={0, 2};
			int[] C1={1, 1};
			
			
			int result1=dot(A, B, C1);
			assertTrue(result1<0);
			double angle1=Math.acos(dot(A, B, C1)/distance(A, B)/distance(B, C1))/Math.PI;
			
			C1[0]=-1;
			int result2=dot(A, B, C1);
			double angle2=Math.acos(dot(A, B, C1)/distance(A, B)/distance(B, C1))/Math.PI;
			
			assertEquals(result1, result2);
			assertEquals(String.format("%.3f",angle1), String.format("%.3f",angle2));
		}
	}
	
}
