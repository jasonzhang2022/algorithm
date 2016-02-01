package jason.algorithm.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Test;

/**
 * CSLR page 1020: 33.1-4
 * @author jason
 *
 *Show how to determine in O(n2 lg n) time whether any three points in a set of n points are collinear.
 *
 *The following implementation is nLogN
 */
public class DecideColinear {
	
	Random rand;
	public Point decideColienar(Point[] points){
		rand=new Random();
		Comparator<Point> comparator=(a, b)->{
			if (a.y<0){
				a=new Point(-a.x, -a.y);
			}
			if (b.y<0){
				b=new Point(-b.x, -b.y);
			}
			//cross product.
			double cross=a.cross(b);
			if (cross<0){
				//a above b, that is a is larger than b
				return 1;
			} else if (cross>0){
				return -1;
			}
			return 0;
		};
		
		//nlogN
		Arrays.sort(points, comparator);
		//n
		for (int i=0; i<points.length-3; i++){
			if (comparator.compare(points[i], points[i+1])==0 
					&& comparator.compare(points[i+1], points[i+2])==0
					)
				return points[i];
		}
		return null;
	}
	
	
	
	
	
	public void swap(Point[] points, int i, int j){
		Point temp=points[i];
		points[i]=points[j];
		points[j]=temp;
	}
	
	
	@Test
	public void test(){
		
		//(1,1) (2,2), (-1, -1)
		Point[] points={
				new Point(1,1), 
				   new  Point(3,0),
				    new Point(2,2),
				    new Point(-2,2),
				    new Point(-1,-2),
				    new Point(1,-2),
				    new Point(-1,-1)
		};
		
		
		Point result=decideColienar(points);
		assertNotNull(result);
		assertThat(result,  equalTo(new Point(1,1)) );

		Point[] points1={
				new Point(1,1), 
				   new  Point(3,0),
				    new Point(2,2),
				    new Point(-2,2),
				    new Point(-1,-2),
				    new Point(1,-2),
		};
		result=decideColienar(points1);
		assertNull(result);
	}
	
}
