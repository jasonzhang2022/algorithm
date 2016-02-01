package jason.algorithm.geometry;


import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
/**
 * CLRS page 1020 33.1-3
 * @author jason
 *The polar angle of a point p1 with respect to an origin point p0 is the angle of the vector p1 - p0
 * in the usual polar coordinate system. For example, the polar angle of (3, 5) with respect to (2, 4)
 *  is the angle of the vector (1, 1), which is 45 degrees or π/4 radians. The polar angle of (3, 3) 
 *  with respect to (2, 4) is the angle of the vector (1, -1), which is 315 degrees or 7π/4 radians. 
 *  Write pseudocode to sort a sequence [p1, p2, ..., pn] of n points according to their polar angles
 *   with respect to a given origin point p0. Your procedure should take O(n lg n) time and use
 *    cross products to compare angles.
 */
public class PolarAngle {

	/*
	 * Sort by cross product. 
	 *Integer math is more accurate than float math.
	 */
	public List<Point> sort(List<Point> points, Point origin){
		points.sort((a, b)->{
			if (a.y-origin.y>0 && b.y-origin.y<0){
				return -1;
			}
			if (a.y-origin.y<0 && b.y-origin.y>0){
				return 1;
			}
			//cross product.
			double cross=a.minus(origin).cross(b.minus(origin));
			if (cross<0){
				//a above b, that is a is larger than b
				return 1;
			} else if (cross>0){
				return -1;
			}
			return 0;
			
		});
		return points;
	}
	
	
	public List<Point> sortByRadian(List<Point> points, Point origin){
		
		points.sort((a, b)->{
			double aradian=Math.atan2(a.y-origin.y, a.x-origin.x);
			double bradian=Math.atan2(b.y-origin.y, b.x-origin.x);
			
			if (aradian<0){
				aradian+=Math.PI*2;
			}
			if (bradian<0){
				bradian+=Math.PI*2;
			}
			
			return Double.compare(aradian, bradian);
			
		});
		return points;
		
	}
	
	
	
	@Test
	public void test(){
		Point[] points={
				new Point(1,1),
				   new  Point(3,0),
				    new Point(2,2),
				    new Point(-2,2),
				    new Point(-1,-2),
				    new Point(1,-2)
		};
		
		Point[] expected={
				 new  Point(3,0),
				new Point(1,1),
				    new Point(2,2),
				    new Point(-2,2),
				    new Point(-1,-2),
				    new Point(1,-2)
		};
		
		
		List<Point> resultsCross=sort(Arrays.asList(points), new Point(0, 0));
		List<Point> resultsRadian=sortByRadian(Arrays.asList(points), new Point(0,0));
		assertThat(resultsCross, contains(expected) );
		assertThat(resultsRadian, contains(expected) );
		
	}
}
