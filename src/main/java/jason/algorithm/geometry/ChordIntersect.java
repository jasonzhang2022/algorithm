package jason.algorithm.geometry;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

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
	
	//dot product A->B . A->C
	public static int dot(int[] A, int[] B, int[] C){
		return (B[0]-A[0])*(C[0]-A[0])+(B[1]-A[1])*(C[1]-A[1]);
	}
	
	
	
	
	public static int numberOfIntersect(int[][] coord, int x0, int y0, int radius){
		
		int[] origin=new int[]{x0, y0};
		int[] x1=new int[]{x0+radius, y0};
		
		Comparator<int[]> coordinateComparator=(left, right)->{
			
			if (left[0]==right[0] && left[1]==right[1]){
				return 0;
			}
			int leftCross=CrossProductProperty.cross(origin, x1, left);
			int rightCross=CrossProductProperty.cross(origin, x1, right);
			
			int leftDot=DotProductProperty.dot(origin, x1, left);
			int rightDot=DotProductProperty.dot(origin, x1, right);
			
			//both is above x1
			if (leftCross>0 && rightCross>0){
				return -Integer.compare(leftDot, rightDot);
				
			} else if (leftCross<0 & rightCross<0){
				//both are under x1
				return Integer.compare(leftDot, rightDot);
			}  else if (leftCross>0 && rightCross<0){
				return -1;
			} else if (leftCross<0 && rightCross>0){
				return 1;
			} else if (leftCross==0 && rightCross==0) {
				//parallel
				return -Integer.compare(left[0], right[0]);
			}  else if (leftCross==0){
				if (left[0]>x0){
					return -1;
				} else  {
					return rightCross>0?1:-1;
				}
			} else {
				if (right[0]>x0){
					return 1;
				} else{
					return leftCross>0?-1:1;
				}
			}
		};
		
		//adjust coordination so the small coordination comes first and acts as start point.
		for (int[] twoPoints: coord){
			int[] start=new int[]{twoPoints[0], twoPoints[1]};
			int[] end=new int[]{twoPoints[2], twoPoints[3]};
			if (coordinateComparator.compare(start, end)>0){
				twoPoints[0]=end[0];
				twoPoints[1]=end[1];
				twoPoints[2]=start[0];
				twoPoints[3]=start[1];
			}
		}
		
		Map<int[], int[]> pointsMap=new IdentityHashMap<>();
		List<int[]> starts=new ArrayList<>(coord.length);
		for (int[] twoPoints: coord){
			int[] start=new int[]{twoPoints[0], twoPoints[1]};
			int[] end=new int[]{twoPoints[2], twoPoints[3]};
			pointsMap.put(start, end);
			starts.add(start);
			
		}
		Collections.sort(starts, coordinateComparator);
		int count=0;
		for (int i=0; i<starts.size(); i++){
			int[] end=pointsMap.get(starts.get(i));
			for (int j=i+1; j<starts.size(); j++){
				int[] jend=pointsMap.get(starts.get(j));
				if (coordinateComparator.compare(starts.get(j), end)<=0 && coordinateComparator.compare(jend, end)>=0){
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
		int[][] coord={
				{0, 2, 0, -2},
				};
		int expected=0;
		int count=numberOfIntersect(coord, 0, 0, 2);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm1TwoLineCross() {
		int[][] coord={
				{0, 2, 0, -2},
				{-2,0, 2,0 }
				};
		int expected=1;
		int count=numberOfIntersect(coord, 0, 0, 2);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm1TwoLineNotCross() {
		int[][] coord={
				{0, 2, -2,0},
				{0, -2, 2, 0}
				};
		int expected=0;
		int count=numberOfIntersect(coord, 0, 0, 2);
		assertEquals(expected, count);
	}
	@Test
	public void algorithm1TwoLineContactByPoint() {
		int[][] coord={
				{0, 2, -2,0},
				{-2,0,0,-2}
				};
		int expected=1;
		int count=numberOfIntersect(coord, 0, 0, 2);
		assertEquals(expected, count);
	}
	
	@Test
	public void algorithm1FourLineMutualCross() {
		int[][] coord={
				{0, 2, 0, -2},
				{1,1,-1,-1},
				{1, -1, -1, 1 },
				{-2,0, 2,0 },

				{1,1, 1, -1}
				};
		int expected=8;
		int count=numberOfIntersect(coord, 0, 0, 2);
		assertEquals(expected, count);
	}
}
