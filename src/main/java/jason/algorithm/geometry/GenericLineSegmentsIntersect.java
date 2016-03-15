package jason.algorithm.geometry;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
//http://www.geeksforgeeks.org/given-a-set-of-line-segments-find-if-any-two-segments-intersect/
public class GenericLineSegmentsIntersect {
	Intersect intersecter=new Intersect();

	public int intersects(int[][] lines) {
		int count=0;

		ArrayList<int[]> points = new ArrayList<>(lines.length * 2);
		int index=0;
		for (int[] line : lines) {
			int[] startpoint=new int[4];
			int[] endpoint=new int[4];
			startpoint[0]=line[0];
			startpoint[1]=0;
			startpoint[2]=line[1];
			startpoint[3]=index;
			
			endpoint[0]=line[2];
			endpoint[1]=1;
			endpoint[2]=line[3];
			endpoint[3]=index;
			
			points.add(startpoint);
			points.add(endpoint);
			index++;
		}
		
		Collections.sort(points, (left, right)->{
			if (left[0]!=right[0]){
				return Integer.compare(left[0], right[0]);
			}
			if (left[1]!=right[1]){
				return Integer.compare(left[1], right[1]);
			}
			return Integer.compare(left[2], right[2]);
		});
		
		TreeSet<Integer> activeLines=new TreeSet<>((i, j)->{
			return Integer.compare(lines[i][0], lines[j][0]);
		});
		
		Set<String> foundIntersetcs=new HashSet<>();
		for (int[] point: points){
			if (point[1]==0){
				//start point
				activeLines.add(point[3]);
				
				Integer above=activeLines.higher(point[3]);
				Integer below=activeLines.lower(point[3]);
				if (above!=null && hasIntersect(lines[above], lines[point[3]])){
					String key=String.format("%d_%d", Math.min(above, point[3]),  Math.max(above, point[3]));
					if (!foundIntersetcs.contains(key)){
						count++;
						foundIntersetcs.add(key);
					}
					
				}
				if (below!=null && hasIntersect(lines[below], lines[point[3]])){
					String key=String.format("%d_%d", Math.min(below, point[3]),  Math.max(below, point[3]));
					if (!foundIntersetcs.contains(key)){
						count++;
						foundIntersetcs.add(key);
					}
				}
				
				
				
				
			} else{
				//end point
				Integer above=activeLines.higher(point[3]);
				Integer below=activeLines.lower(point[3]);
				if (above!=null && below!=null){
					String key=String.format("%d_%d", Math.min(below, below),  Math.max(above, below));
					if (!foundIntersetcs.contains(key)){
						count++;
						foundIntersetcs.add(key);
					}
				}
				activeLines.remove(point[3]);
			}
		}
		return count;
	}

	
	public boolean hasIntersect(int[] line1, int[] line2){
		Point[] points=new Point[4];
		points[0]=new Point(line1[0], line1[1]);
		points[1]=new Point(line1[2], line1[3]);
		
		points[2]=new Point(line2[0], line2[1]);
		points[3]=new Point(line2[2], line2[3]);
		return intersecter.hasIntersect(points);
	}
	

	public static class TestCase {
		
		
		@Test
		public void testIntersectAtEndPoints(){
			GenericLineSegmentsIntersect v=new GenericLineSegmentsIntersect();
			int[][] lines={
					{1,5,5,1},
					{2,2,3,1},
					{4,1,5,2}
			};
			int expected=1;
			int result=v.intersects(lines);
			assertEquals(expected, result);
		}
		/*
		 * Test case failed.
		 * Why? The algorithm  requires that the active segments are ordered by their relative order
		 * with respect the sweep line. But the sweep line is changing dynamically. This means the order 
		 * in the tree is changing dynamically. 
		 * 
		 *  Tree set can't handle that case.
		 * 
		 * 
		 * 
		 */
		@Test
		public void testIntersectAtEndPoints2(){
			GenericLineSegmentsIntersect v=new GenericLineSegmentsIntersect();
			int[][] lines={
					{1,5,5,1},
					{1,4,5,0},
					{2,2,6,10}
			};
			int expected=2;
			int result=v.intersects(lines);
			assertEquals(expected, result);
		}
	}
}
