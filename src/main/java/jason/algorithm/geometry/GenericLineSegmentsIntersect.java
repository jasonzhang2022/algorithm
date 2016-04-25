package jason.algorithm.geometry;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import org.junit.Test;
//http://www.geeksforgeeks.org/given-a-set-of-line-segments-find-if-any-two-segments-intersect/
/*
 * Key points: relative order at a sweep line can only change
 * 1) a point is added or deleted
 * or 
 * 2) line is crossed.
 */
//http://jeffe.cs.illinois.edu/teaching/373/notes/x06-sweepline.pdf
public class GenericLineSegmentsIntersect {
	Intersect intersecter=new Intersect();
	
	public static class EventPoint {
		int x;
		int y;
		EventPoint otherEnd;
		int[] line;
		public EventPoint(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public boolean isStart(){
			return x<otherEnd.x;
		}
		
	}

	public boolean intersects(int[][] lines) {
		ArrayList<EventPoint> points = new ArrayList<>(lines.length * 2);
		for (int[] line : lines) {
			EventPoint start=new EventPoint(line[0], line[1]);
			EventPoint end=new EventPoint(line[2], line[3]);
			start.line=line;
			end.line=line;
			start.otherEnd=end;
			end.otherEnd=start;
			points.add(start);
			points.add(end);
		}
		
		Collections.sort(points, (left, right)->{
			if (left.x!=right.x){
				return Integer.compare(left.x, right.x);
			}
			//end comes first
			if (left.isStart() && !right.isStart()) {
				return 1;
			}
			if (!left.isStart() && right.isStart()){
				return -1;
			}
			
			return Integer.compare(left.y, right.y);
		});
		
		TreeSet<int[]> activeLines=new TreeSet<>((i, j)->{
			return Integer.compare(i[1], j[1]);
		});
		
		
		for (EventPoint point: points){
			if (point.isStart()){
				activeLines.add(point.line);
				
				
				int[] above=activeLines.higher(point.line);
				if (above!=null && hasIntersect(above, point.line)){
					return true;
				}
				
				int[] below=activeLines.lower(point.line);
				if (below!=null && hasIntersect(below, point.line)){
					return true;
				}
				
			} else{
				activeLines.remove(point.line);
				int[] above=activeLines.higher(point.line);
				int[] below=activeLines.lower(point.line);
				if (above!=null && below!=null && hasIntersect(below, above)){
					return true;
				}
			}
		}
		return false;
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
		
			assertTrue(v.intersects(lines));
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

			assertTrue(v.intersects(lines));
		}
	}
}
