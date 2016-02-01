package jason.algorithm.geometry;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Decide circle by three points
 * 
 * 
 * @author jason
 *
 */
public class CircleByThreePoints {

	public static class Circle {
		Point c;
		double radius;
		
	}
	
	
	public Circle computeCircle(Point p1, Point p2, Point p3){
		
		//linear
		if (p2.minus(p1).cross(p3.minus(p2))==0){
			return null;
		}
		
		Point middle1=new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
		Point middle2=new Point((p2.x+p3.x)/2, (p2.y+p3.y)/2);
		
		//Suppose the line 1 can be expressed Ax+By=C
		//we try to calculate A, B, C
		double A=p2.y-p1.y;
		double B=p1.x-p2.x;
		
		//line perpendicular to this line will have the format: -BX+Ay=D.
		//middle 1 pass this line
		double D=-B*middle1.x+A*middle1.y;
		//suppose the second line can be expressed Ex+Fy=H
		double E=p3.y-p2.y;
		double F=p2.x-p3.x;
		
		//line perpendicular to this line will have the format: -Fx+EY=J
		//middle 2 pass this line.
		double J=-F*middle2.x+E*middle2.y;
		
		
		//We try to calculate the intersection point of two lines
		//-Bx+AY=D and -Fx+Ey=J
	
		//special case A==0;
		double cy=0;
		double cx=0;
		boolean set=false;
		if (A==0){
			//B can not be zero. If B is zero. the first line is a constant point.
			cx=D/-B;
			cy=(J+F*cx)/E;
			set=true;
		} else if (E==0){
			cx=J/-F;
			cy=(D+B*cx)/A;
			set=true;
		}  
		
		if (B==0){
			cy=D/A;
			cx=(E*cy-J)/F;
			set=true;
		}else if (F==0){
			cy=J/E;
			cx=(A*cy-D)/B;
			set=true;
		} 
		
		if (!set){
			cx=(D*E-J*A)/(A*F-E*B);
			cy=(D+B*cx)/A;
		}
		
		
		Circle circle=new Circle();
		circle.c=new Point(cx, cy);
		circle.radius=circle.c.distance(p1);
		return circle;
	}
	
	@Test
	public void test(){
		Point p1=new Point(2,0);
		Point p2=new Point(0,2);
		Point p3=new Point(-2,0);
		
		Circle circle=computeCircle(p1, p2, p3);
		assertThat(circle.c, equalTo(new Point(0, 0)) );
		assertEquals(new Double(circle.radius).intValue(), 2);
	}
	
	@Test
	public void test2(){
		double n=Math.sqrt(2);
		Point p1=new Point(-n,n);
		Point p2=new Point(n,n);
		Point p3=new Point(n,-n);
		
		Circle circle=computeCircle(p1, p2, p3);
		assertThat(circle.c, equalTo(new Point(0, 0)) );
		assertEquals(new Double(circle.radius).intValue(), 2);
	}
}
