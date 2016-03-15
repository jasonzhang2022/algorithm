package jason.algorithm.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CrossProductProperty {
	//A->B X A->C
	public static int cross(int[] A, int[] B, int[] C) {
		return (B[0]-A[0])*(C[1]-A[1])-(B[1]-A[1])*(C[0]-A[0]);
	}

	public static double distance(int[] A, int[] B) {
		return Math.sqrt((B[1] - A[1]) * (B[1] - A[1]) + (B[0] - A[0]) * (B[0] - A[0]));
	}

	public static class TestCase {
		/*
		 *if A->B, A->C is clockwise rotation, the value is negative
		 *It is positive otherwise
		 *
		 *Ths positive and negative value follow the regular coordination system convention.
		 */
		@Test
		public void testCrossPositiveNegative() {
			int[] A = { 0, 0 };
			int[] B = { 0, 2 };
			int[] C1 = { 1, 3 };

			int result1 = cross(A, B, C1);
			double angle1 = Math.asin(cross(A, B, C1) / distance(A, B) / distance(B, C1)) / Math.PI;

			C1[0] = -1;
			int result2 = cross(A, B, C1);
			double angle2 = -Math.asin(cross(A, B, C1) / distance(A, B) / distance(B, C1)) / Math.PI;

			assertEquals(result1, -result2);
			assertEquals(String.format("%.3f", angle1), String.format("%.3f", angle2));
		}

		/*
		 * Two angle x, and PI-X gives the same product.
		 * Cross product can only express direction, but not magnitude
		 */
		@Test
		public void testCrossMagnitude() {
			int[] A = { 0, 0 };
			int[] B = { 0, 2 };
			int[] C1 = { 1, 3 };

			int result1 = cross(A, B, C1);
			double angle1 = Math.asin(cross(A, B, C1) / distance(A, B) / distance(B, C1)) / Math.PI;

			C1[1] = 1 ;
			int result2 = cross(A, B, C1);
			double angle2 = Math.asin(cross(A, B, C1) / distance(A, B) / distance(B, C1)) / Math.PI;

			assertEquals(result1, result2);
			assertEquals(String.format("%.3f", angle1), String.format("%.3f", angle2));
		}
	}
}
