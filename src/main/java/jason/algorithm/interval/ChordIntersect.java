package jason.algorithm.interval;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

/*
 * http://ripcrixalis.blog.com/2011/02/08/clrs-14-1-dynamic-order-statistics/
 * 14.1-8: ⋆
Consider n chords on a circle, each defined by its endpoints.
 Describe an O(n lg n)-time algorithm for determining the number of pairs of 
 chords that intersect inside the circle. (For example, if the n chords are all 
 diameters that meet at the center, then the correct answer is C(n 2).) 
 Assume that no two chords share an endpoint.
 
 
 The answer using order statistics:
 First we assign the n*2 endpoints orders by picking an arbitrary 
 empty point on the circle and setting it to zero. Then we go over 
 the circle in clockwise order labeling each chord’s endpoints as Si,
  Ei where the start endpoints has lower value. If we can count the number 
  of start endpoints lay between Si and Ei, we get the number chords that chord i intersects.
We use OS-tree to count them. Every time we meet a start endpoint Si as 
we go over the circle clockwise we insert it into the tree (O(lg n)).
 Every time we meet an end endpoint Ei we delete its corresponding start 
 endpoint Si from the tree as well as count the # of nodes which have key 
 value larger than Si’s (That’s the number of start endpoints lay between
  Si and Ei), which is k – rank(Si), k is the number of nodes currently 
  in the tree. This step takes time O(lg n). Finally, we delete all start endpoints
   from the tree and we add all the numbers up, we get the total number of intersects.
    Since each endpoint takes time O(lg n) to insert or delete from the tree, 
    O(lg n) to call RANK, the total running time of this step is O(n lg n).
    
    
    Clever way to use order statistics for counting item from i to end. 
    We count from i to end instead of i to n since we are dynamically adding and removing the tree.
    
 */
/*
 * So what is difficulty?
 * It is difficult because we need a way to order item. 
 * It seems that there is no order for line in 2D plane. But we can designate a zero point arbitrarily.
 */
public class ChordIntersect {

	Map<double[], double[]> pointToLines = new IdentityHashMap<>();
	Map<double[], double[][]> lineToPoints = new IdentityHashMap<>();
	public  ChordIntersect(){

	}
	/*
	 *
	 * assume the center is (0, 0). If not, we can do pre-process
	 *
	 *
	 */
	public double calculateCosine(double[] point, double radius) {
		double refx = radius;
		double refy = 0;

		double x = point[0];
		double y = point[1];

		// radius^2* cosine; we use dot product property.
		double cosine = x/radius;

		if (y < 0) {
			// cosine vale is symmetric, we want it linear.
			cosine = -cosine - 2;
		}
		//we want ascending sorting.
		return -cosine;
	}




	private ArrayList<double[]> process(double[][] coord, double radius) {
		for (double[] line : coord) {
			double[] point1 = new double[]{line[0], line[1]};
			double[] point2 = new double[]{line[2], line[3]};
			pointToLines.put(point1, line);
			pointToLines.put(point2, line);
			lineToPoints.put(line, new double[][]{point1, point2});
		}

		ArrayList<double[]> points = new ArrayList<>(pointToLines.keySet());
		Collections.sort(points, Comparator.comparingDouble(point -> calculateCosine(point, radius)));
		return points;
	}

	private double[] getTheOtherPoint(double[] point) {
		double[] line = pointToLines.get(point);
		double[][] twopoints = lineToPoints.get(line);
		double[] endpoint = twopoints[0] == point ? twopoints[1] : twopoints[0];

		return endpoint;

	}

	private boolean samePoint(double[] point1, double[] point2) {
		return point1[0]==point2[0] && point1[1]==point2[1];
	}



	public int numberOfIntersect(double[][] coord, double radius) {

		int count = 0;

		ArrayList<double[]> points = process(coord, radius);
		Map<double[], Integer> pointToIndex = new IdentityHashMap<>();
		for (int i = 0; i < points.size(); i++) {
			pointToIndex.put(points.get(i), i);
		}

		TreeSet<Integer> unclosedStarts = new TreeSet<>();
		for (int i = 0; i < points.size(); i++) {
			double[] point = points.get(i);

			double[] theOtherPoint = getTheOtherPoint(point);
			int otherIndex = pointToIndex.get(theOtherPoint);
			if (otherIndex <i) {

				int j=i-1;
				while (j>0 && samePoint(point, points.get(j))){
					j--;
				}


				// this is end of interval.
				//we need to findUsingArray all starts which is greater that current start.
				// there is no start greater that i since we have not process them yet.
				count += unclosedStarts.subSet(otherIndex, false, j, true).size();
				unclosedStarts.remove(otherIndex);
			} else{
				// this is the start of internal;
				if (unclosedStarts.isEmpty()) {
					unclosedStarts.add(i);
				} else {
					int lastIndex = unclosedStarts.last();
					double[] lastStart = points.get(lastIndex);
					if (!samePoint(lastStart, point)) {
						// same start only added once
						unclosedStarts.add(i);
					}
				}

			}
		}
		assert unclosedStarts.isEmpty() : "all chord should be closed.";

		return count;
	}

	public static class TestCase {
		@Test
		public void algorithm1SingleLine() {
			double[][] coord = {
					{0, 2, 0, -2},
			};
			int expected = 0;
			int count = new ChordIntersect().numberOfIntersect(coord, 2);
			assertEquals(expected, count);
		}

		@Test
		public void algorithm1TwoLineCross() {
			double[][] coord = {
					{0, 2, 0, -2},
					{-2, 0, 2, 0}
			};
			int expected = 1;
			int count = new ChordIntersect().numberOfIntersect(coord, 2);
			assertEquals(expected, count);
		}

		@Test
		public void algorithm1TwoLineNotCross() {
			double[][] coord = {
					{0, 2, -2, 0},
					{0, -2, 2, 0}
			};
			int expected = 0;
			int count = new ChordIntersect().numberOfIntersect(coord, 2);
			assertEquals(expected, count);
		}

		@Test
		public void algorithm1TwoLineContactByPoint() {
			double[][] coord = {
					{0, 2, -2, 0},
					{-2, 0, 0, -2}
			};
			int expected = 0;
			int count = new ChordIntersect().numberOfIntersect(coord, 2);
			assertEquals(expected, count);
		}

		@Test
		public void algorithm1FourLineMutualCross() {
			double[][] coord = {
					{0, 2, 0, -2},
					{1, 1, -1, -1},
					{1, -1, -1, 1},
					{-2, 0, 2, 0},

					{1, 1, 1, -1}
			};
			int expected = 4 * 3 / 2 + 1;
			int count = new ChordIntersect().numberOfIntersect(coord, 2);
			assertEquals(expected, count);
		}
	}

}
