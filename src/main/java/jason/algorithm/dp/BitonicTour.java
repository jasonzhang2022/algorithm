package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Stack;

import org.junit.Test;

/*
 * Explanation: http://www.math.utep.edu/Faculty/pmdelgado2/courses/adv_algorithms/homework-08_anser.pdf
 * example input: bitonictour.html in the same directory (source https://github.com/sgtFloyd/rit_coursework/blob/master/20102%204003.515.01%20-%20Analysis%20Of%20Algorithm/04%20Bitonic%20Travelling%20Salesman/Problem%20Description.html)
 * 
 */
public class BitonicTour {

	public static class Vertex {
		public int x;
		public int y;

		public Vertex(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public Double distance(Vertex to) {
			return Math.sqrt((x - to.x) * (x - to.x) + (y - to.y) * (y - to.y));
		}
	}

	Vertex[] vs = null;
	double[][] miniCost;
	int[][] track;

	public double bitonicTour(int[][] vertices) {

		vs = new Vertex[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			vs[i] = new Vertex(vertices[i][0], vertices[i][1]);
		}
		int n = vs.length;
		Arrays.sort(vs, (a, b) -> a.x - b.x);

		miniCost = new double[n][n];
		track = new int[n][n];

		double prev = 0;
		// from 0 to i
		for (int i = 1; i < n; i++) {
			Arrays.fill(miniCost[i], -1);
			prev += vs[i - 1].distance(vs[i]);
			miniCost[0][i] = prev;
			track[0][i] = i - 1;
		}

		double temp = bitonicTourSub(n - 2, n - 1);
		miniCost[n - 2][n - 1] = temp;

		/*
		 * n-1 has to connect to n-2 at one side, otherwise n-2 can't connect to
		 * any node at right side since there is no node bigger than n-2 other
		 * than n-1
		 */

		miniCost[n - 1][n - 1] = temp + vs[n - 2].distance(vs[n - 1]);
		return miniCost[n - 1][n - 1];

	}

	/*
	 * Walk a route from start to end. All points before end including end are
	 * touched.
	 */
	public double bitonicTourSub(int start, int end) {
		// if from is zero, the function will be returned here.
		if (miniCost[start][end] >= 0) {
			return miniCost[start][end];
		}

		if (end - start == 1) {

			/*
			 * According to bitonic tour, We will walk from from rightmost
			 * point(end) to left most point(zero). Which point will we walk
			 * first to from end point? pointConnecteToEnd is this point. Which
			 * is any point from zero to start-1;
			 */
			double tempMiniCost = Double.MAX_VALUE;
			for (int pointConnectedToend = 0; pointConnectedToend < start; pointConnectedToend++) {
				double newCost = bitonicTourSub(pointConnectedToend, start)
						+ vs[pointConnectedToend].distance(vs[end]);
				if (newCost < tempMiniCost) {
					tempMiniCost = newCost;
					track[start][end] = pointConnectedToend;
				}
			}
			miniCost[start][end] = tempMiniCost;
		} else {
			/*
			 * Suppose we have a solution from end to zero. end-1 should be the
			 * first end is connected to on the left. if end-1 is connected to
			 * any other point on the right instead of end, it is a violation of
			 * bitonic rule.
			 */
			miniCost[start][end] = bitonicTourSub(start, end - 1)
					+ vs[end - 1].distance(vs[end]);
			track[start][end] = end - 1;
		}
		return miniCost[start][end];
	}

	@Test
	public void test() {
		// v0: (2,2) v1: (0,1) v2: (4,1) v3: (3,0) v4: (1,3)
		int[][] vertices = { { 2, 2 }, { 0, 1 }, { 4, 1 }, { 3, 0 }, { 1, 3 } };

		String cost = String.format("%1$.2f", bitonicTour(vertices));
		assertEquals("10.46", cost);

		// output bitonic tour
		outputPath();

	}

	public void outputPath() {
		// output sorted vertices
		for (int i = 0; i < vs.length; i++) {
			Vertex v = vs[i];
			System.out.printf("%d(%d, %d) ", i, v.x, v.y);
		}
		System.out.println("");
		// output cost table
		System.out.println("-------------cost---------");
		for (double[] rowCost : miniCost) {
			Arrays.stream(rowCost)
					.forEach(d -> System.out.printf("%1$.2f ", d));
			System.out.println("");
		}
		
		System.out.println("-------------track---------");
		for(int[] t:track){
			System.out.println(Arrays.toString(t));
		}
		System.out.println("-------------route---------");
		int[] leftTrack = new int[vs.length];
		int start = vs.length - 2;
		int end = vs.length - 1;
		while (start != 0) {
			if (end - start == 1) {
				// what point is the left point for end
				leftTrack[end] = track[start][end];
				int temp = track[start][end];
				end = start;
				start = temp;
			} else {
				leftTrack[end] = end - 1;
				end = end - 1;
			}
		}
		while (end>0){
			leftTrack[end]=end-1;
			end--;
		}

		Stack<Integer> s = new Stack<>();
		s.push(vs.length - 1);
		start = vs.length - 1;
		while (start != 0) {
			s.push(leftTrack[start]);
			start = leftTrack[start];
		}

		while (!s.isEmpty()) {
			int index = s.pop();
			Vertex v = vs[index];
			System.out.printf("%d(%d, %d)->", index, v.x, v.y);
		}
		System.out.println("");

		s.push(vs.length - 1);
		s.push(vs.length - 2);
		start = vs.length - 2;
		while (start != 0) {
			s.push(leftTrack[start]);
			start = leftTrack[start];
		}
		while (!s.isEmpty()) {
			int index = s.pop();
			Vertex v = vs[index];
			System.out.printf("%d(%d, %d)->", index, v.x, v.y);
		}
		System.out.println("");
	}

	@Test
	public void test2() {
		// v0: (2,2) v1: (0,1) v2: (4,1) v3: (3,0) v4: (1,3)
		int[][] vertices = { { 0, 0 }, { 1, 6 }, { 2, 3 }, { 5, 2 }, { 7, 1 },
				{ 8, 4 }, { 6, 5 } };

		String cost = String.format("%1$.2f", bitonicTour(vertices));

		assertEquals("25.58", cost);

		// output bitonic tour
		// output bitonic tour
		outputPath();

	}

}
