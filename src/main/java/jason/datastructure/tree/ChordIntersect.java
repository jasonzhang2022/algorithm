package jason.datastructure.tree;

import static org.junit.Assert.assertEquals;
import jason.algorithm.interval.VoiceMixer.Volume;
import jason.algorithm.interval.VoiceMixer1.Speaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
	
	/**
	 * 
	 * @author jason
	 * represent a chord by radian degree.
	 * Given a circle,
	 * Traditionally the positive horizontal line has degree 0. negative horizontal line has degree Math.PI.
	 * Line above x has positive radian. 
	 * Line below x has negative radian
	 * We draw two lines. 
	 * One is from origin(center) to first point.
	 * The other is from origin (center) to second point.
	 *
	 * So the chord can be represented as an interval
	 * [the radian for start line, the radian for end line]
	 * 
	 * To make this easy we will have a Radian from 0 to 2 Math.PI instead of O ->Math.PI->-Matth.PI->0
	 * 
	 * Any two lines intersect if the radian interval overlap.
	 */
	public static class ChordRadian{
		double start;
		double end;
		
		/**
		 * 
		 * @param x1 x for one point
		 * @param y1 y for one point
		 * @param x2 x for another point
		 * @param y2 y for another point
		 * @param x0 origin of center
		 * @param y0 origin of center
		 */
		public ChordRadian(double x1, double y1, double x2, double y2, int x0, int y0){

				double radian1=Math.atan2(y1-y0, x1-x0);
				double radian2=Math.atan2(y2-y0, x2-x0);
				if (radian1<0){
					//turn it from Math.PI to Math.
					radian1=Math.PI*2+radian1;
				}
				if (radian2<0){
					radian2=Math.PI*2+radian2;
				}
				start=Math.min(radian1, radian2);
				end=Math.max(radian1, radian2);
		}
	}
	
	
	/**
	 * 
	 * @param coord a matrix to represent lines. Each row is a line. 
	 * @param x0 origin 
	 * @param y0 origin
	 * @return
	 */
	public static int numberOfIntersect(double[][] coord, int x0, int y0){
		int row=coord.length;
		
		ChordRadian[] intervals=new ChordRadian[row];
		for (int i=0; i<row; i++){
			intervals[i]=new ChordRadian(coord[i][0],coord[i][1], coord[i][2],coord[i][3], x0, y0);
		}
		
		//O(NLogN)
		Arrays.sort(intervals, (a, b)->{
			if (a.start-b.start>0){
				return 1;
			} else if (a.start==b.start){
				return 0;
			} else {
				return -1;
			}
		});
		
		
		int count=0;
		for (int i=0; i<row-1; i++){
			ChordRadian currentChord=intervals[i];
			for (int j=i+1; j<row; j++){
				if (intervals[j].start==currentChord.start){
					continue;
				}
				if (intervals[j].start<currentChord.end && intervals[j].end>currentChord.end){
					count++;
				} else {
					break;
				}
			}
		}
		return count;
	}
	
	@Test
	public void algorithm1SingleLine() {
		double[][] coord={
				{0, 2, 0, -2},
				};
		int expected=0;
		int count=numberOfIntersect(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm1TwoLineCross() {
		double[][] coord={
				{0, 2, 0, -2},
				{-2,0, 2,0 }
				};
		int expected=1;
		int count=numberOfIntersect(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm1TwoLineNotCross() {
		double[][] coord={
				{0, 2, -2,0},
				{0, -2, 2, 0}
				};
		int expected=0;
		int count=numberOfIntersect(coord, 0, 0);
		assertEquals(expected, count);
	}
	@Test
	public void algorithm1TwoLineContactByPoint() {
		double[][] coord={
				{0, 2, -2,0},
				{-2,0,0,-2}
				};
		int expected=0;
		int count=numberOfIntersect(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm1FourLineMutualCross() {
		double[][] coord={
				{0, 2, 0, -2},
				{1,1,-1,-1},
				{1, -1, -1, 1 },
				{-2,0, 2,0 },

				{1,1, 1, -1}
				};
		int expected=4*3/2+1;
		int count=numberOfIntersect(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	//----------------------------solution 2.
	/*
	 * Implement using order statistics as illustrated in class comment.
	 * 
	 * Since we use TreeSet, it can't handle equal key easily
	 * 
	 * we need a system to order point in the circle. we order the circle like this
	 * 1. clock  wise.
	 * 2. (0, Radius) is the first point
	 * 
	 */
	
	public static class Point implements Comparable<Point>{
		double [][] coord;
		int index;
		boolean start;
		
		int x0; //origin's x
		int y0; //oriign's y
		
		
		
		public Point(double[][] coord, int index, boolean start, int x0, int y0) {
			super();
			this.coord = coord;
			this.index = index;
			this.start = start;
			this.x0 = x0;
			this.y0 = y0;
		}
		
		public boolean smallStart(){
			if (start){
				Point end=new Point(coord, index, false, x0, y0);
				return this.compareTo(end)<0?true:false;
			} else {
				Point start=new Point(coord, index, true, x0, y0);
				return start.compareTo(this)<0?true:false;
			}
		}
		public double getX(){
			return start?coord[index][0]: coord[index][2];
		}
		public double getY(){
			return start?coord[index][1]:coord[index][3];
		}
		
		public int getArea(){
			if (getX()>=x0 && getY()>=y0){
				return 1;
			} 
			if (getX()>=x0 && getY()<=y0){
				return 2;
			}
			if (getX()<x0 && getY()<y0){
				return 3;
			}
			return 4;
		}
		@Override
		public int compareTo(Point o) {
			if (getArea()!=o.getArea()){
				return getArea()-o.getArea();
			}
			if (getX()==o.getX() && getY()==o.getY()){
				return 0;
			}
			//assume there is no equal point
			if (getArea()==1){
				return getX()-o.getX()<0?-1:1;
			}
			if (getArea()==2){
				return getX()-o.getX()<0?1:-1;
			}
			if (getArea()==3){
				return getX()-o.getX()<0?1:-1;
			}
			return getX()-o.getX()<0?-1:1;
		}
		
		
		
	}
	public static int numberOfIntersect2(double[][] coord, int x0, int y0){
		
		TreeSet<Point> treeSet=new TreeSet<Point>(Point::compareTo);
		int row=coord.length;
		Point[] points=new Point[row*2];
		for (int i=0; i<row; i++){
			points[i*2]=new Point(coord, i, true, x0, y0);;
			points[i*2+1]=new Point(coord, i, false, x0, y0);
		}
		//sort by points.
		Arrays.sort(points);
		
		int count=0;
		for (Point p: points){
			if ( (p.start && p.smallStart())||(!p.start && !p.smallStart())){
				treeSet.add(p);
				continue;
			}
			
			Point small=new Point(p.coord, p.index, !p.start, x0, y0);
			Point large=p;
			
		
			NavigableSet<Point> subset=treeSet.subSet(small, true, large, true);
			count+=subset.size()-1;
			Point first=subset.first();
			Point last=subset.last();
			
			treeSet.remove(first);
		}
		return count;
	}
	
	
	@Test
	public void algorithm2SingleLine() {
		double[][] coord={
				{0, 2, 0, -2},
				};
		int expected=0;
		int count=numberOfIntersect2(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm2TwoLineCross() {
		double[][] coord={
				{0, 2, 0, -2},
				{-2,0, 2,0 }
				};
		int expected=1;
		int count=numberOfIntersect2(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm2TwoLineNotCross() {
		double[][] coord={
				{0, 2, -2,0},
				{0, -2, 2, 0}
				};
		int expected=0;
		int count=numberOfIntersect2(coord, 0, 0);
		assertEquals(expected, count);
	}
	/*
	 * Failed. The algorithm can't handle contact at the same circle.
	 */
	@Test
	public void algorithm2TwoLineContactByPoint() {
		double[][] coord={
				{0, 2, -2,0},
				{-2,0,0,-2}
				};
		int expected=0;
		int count=numberOfIntersect2(coord, 0, 0);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm2FourLineMutualCross() {
		double[][] coord={
				{0, 2, 0, -2},
				{1,1,-1,-1},
				{1, -1, -1, 1 },
				{-2,0, 2,0 },
				};
		int expected=4*3/2;
		int count=numberOfIntersect2(coord, 0, 0);
		assertEquals(expected, count);
	}
	@Test
	public void algorithm2FourLineMutualCross1() {
		double[][] coord={
				{0, 2, 0, -2},
				{1,1,-1,-1},
				{1, -1, -1, 1 },
				{-2,0, 2,0 },

				{1,1, 1, -1}
				};
		int expected=4*3/2+1;
		int count=numberOfIntersect2(coord, 0, 0);
		assertEquals(expected, count);
	}
	

}
