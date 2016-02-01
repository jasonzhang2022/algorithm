package jason.algorithm.geometry;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;



import org.junit.Test;

import jason.algorithm.geometry.CircleByThreePoints.Circle;

//https://community.topcoder.com/stat?c=problem_statement&pm=2260&rd=4735
public class TVTower {
	
	double miniRadius=Integer.MAX_VALUE;
	Point[] points;
	public double miniRadius(int[] xs, int[] ys){
		points=new Point[xs.length];
		for (int i=0; i<xs.length; i++){
			points[i]=new Point(xs[i], ys[i]);
		}
		
		for (int i=0; i<xs.length-1; i++){
			Point p1=points[i];
			for(int j=i+1; j<xs.length; j++){
				Point p2=points[j];
				Point middle=p1.middle(p2);
				//if we use middle as orgin, what is the miniRadius
				computeMiniRadiusFromOneCenter(middle);
				
				for (int k=j+1; k<xs.length; k++){
					Point p3=points[k];
					Circle circle=new CircleByThreePoints().computeCircle(p1, p2, p3);
					computeMiniRadiusFromOneCenter(circle.c);
				}
			}
			
			
		}
		return miniRadius;
	}
	
	public void computeMiniRadiusFromOneCenter(Point center){
		double max=0;
		for (int i=0; i<points.length; i++){
			max=Math.max(max, center.distance(points[i]));
		}
		miniRadius=Math.min(max, miniRadius);
	}

	@Test
	public void test(){
		double miniRadius=miniRadius(new int[]{1, 0, -1, 0}, new int[]{0, 1, 0, -1});
		assertTrue(1.0d==miniRadius );
	}
	
	@Test
	public void test1(){
		double miniRadius=miniRadius(new int[]{5, 3, -4, 2}, new int[]{0, 4, 3, 2});
		String r=String.format("%.3f", miniRadius);
		assertThat(r, equalTo("4.743"));
	}
}
