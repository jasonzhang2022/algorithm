package jason.algorithm.geometry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Decide whether two segments intersect or not.
 * @author jason
 *
 */
public class Intersect {
	
	public boolean hasIntersect(Point[] points){
		//0->1, turn to 2
		double d1=points[1].minus(points[0]).cross(points[2].minus(points[1]));
		//0->1, turn to 3
		double d2=points[1].minus(points[0]).cross(points[3].minus(points[1]));
		
		//2->3, turn to 0
		double d3=points[3].minus(points[2]).cross(points[0].minus(points[3]));
		
		//2->3, turn to 1
		double d4=points[3].minus(points[2]).cross(points[1].minus(points[3]));
		
		
		if (  ((d1<0 && d2>0) || (d1>0 && d2<0)) 
				&& ((d3<0 && d4>0) || (d3>0 && d4<0)) ) {
			return true;
		}
		
		if (d1==0 && onSegment(points[0], points[1], points[2])){
			return true;
		} else if (d2==0 && onSegment(points[0], points[1], points[3])){
			return true;
		} else if (d3==0 && onSegment(points[2], points[3], points[0])){
			return true;
		} else if (d4==0 && onSegment(points[2], points[3], points[1])){
			return true;
		}
		return false;
	}
	
	/*
	 * Whether P is on the segment from left to right.
	 * Precondition: p is colinear with left->right
	 */
	public boolean onSegment(Point left, Point right, Point p){
		if (p.x>=Math.min(left.x, right.x) && p.x<=Math.max(left.x, right.x)
				&& p.y>=Math.min(left.y, right.y) && p.y<=Math.max(left.y, right.y)){
			return true;
		}
		return false;
	}
	
	@Test
	public void test(){
		assertFalse(hasIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(0,1), new Point(1,1)}));
		assertTrue(hasIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(0,1), new Point(2,1)}));
		assertTrue(hasIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(0,1), new Point(3,1)}));
		assertFalse(hasIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(0,1), new Point(2,3)}));
	}
	
}
