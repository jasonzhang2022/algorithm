package jason.algorithm.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Give Two lines. Decide the intersection point
 * @author jason
 * 
 * suppose the first line is y=ax+b,
 * the second line is y=cx+d
 */
public class IntersectPoint {
	public Point computeIntersect(Point[] points){
		Intersect intersect=new Intersect();
		if (!intersect.hasIntersect(points)){
			return null;
		}
		
		
		
		/* suppose the first line is y=ax+b,
		 * the second line is y=cx+d
		 */
		//a slope of first line
		double a=(points[1].y-points[0].y)/(points[1].x - points[0].x);
		double b=points[0].y-a*points[0].x;
		
		//c is slope of second line
		double c=(points[3].y-points[2].y)/(points[3].x - points[2].x);
		double d=points[2].y-c*points[2].x;
	
		//both are vertical line. and has intersection.
		if ( (points[1].x - points[0].x)==0 && (points[3].x - points[2].x)==0 ) {
			//There could be multiple point.
			double x=points[1].x;
			double y=points[3].y;
			if (intersect.onSegment(points[0], points[1], points[2]) ){
				y=points[2].y;
			}
			return new Point(x, y);
		}
		
		
		if ((points[1].x - points[0].x)==0){
			//the first line is vertical line. The line is basic x=constant.
			//intersection point x has to be the x for first line.
			double x=points[1].x;
			double y=c*x+d;
			return new Point(x, y);
		}
		if((points[3].x - points[2].x)==0){
			//the second line is vertical line, the line is basic x=constant;
			double x=points[2].x;
			double y=a*x+b;
			return new Point(x, y);
		}
		
		if (a==c){
			//parallel and colinear
			if (intersect.onSegment(points[0], points[1], points[2])){
				return points[2];
			} else{
				return points[3];
			}
		}
		
		//right now we know a, b, c, d.
		//we calculate an x, y which satisfiy both formula
		//x=(d-b)/(a-c)
		double x=(d-b)/(a-c);
		double y=a*x+b;
		return new Point(x, y);
	}
	
	@Test
	public void test(){
		
		//both are vertical line
		assertThat(computeIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(2,3), new Point(2,1)}), equalTo(new Point(2,1)));
		
		//one is vertical line
		assertThat(computeIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(0,1), new Point(2,1)}), equalTo(new Point(2,1)));
		assertThat(computeIntersect(new Point[]{new Point(2,0), new Point(2,2), new Point(0,1), new Point(3,1)}), equalTo(new Point(2,1)));
		
		//both are not vertical lines, has intersect
		assertThat(computeIntersect(new Point[]{new Point(-1,0), new Point(1,2), new Point(1,0), new Point(-1,2)}), equalTo(new Point(0,1)));
		
		//colinear
		assertThat(computeIntersect(new Point[]{new Point(-1,0), new Point(1,2), new Point(0,1), new Point(2,3)}), equalTo(new Point(0,1)));
	}
}
