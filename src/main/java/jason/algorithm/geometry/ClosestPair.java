package jason.algorithm.geometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
//http://www.geeksforgeeks.org/closest-pair-of-points/
public class ClosestPair {

	public double closetPair(int[][] coords){
	
		
		//sorting by x coordinates
		Arrays.sort(coords, (l, r)->Integer.compare(l[0], r[0]));
		
		
		//arrange active coordinates by y then by x,
		Comparator<int[]> c=(l, r)->Integer.compare(l[1], r[1]);
		c=c.thenComparing((l, r)->Integer.compare(l[0], r[0]));
		TreeSet<int[]> activeCoords=new TreeSet<>(c);
		
		double minDistance=distance(coords[0], coords[1]);
		activeCoords.add(coords[0]);
		activeCoords.add(coords[1]);
		
		for (int i=2; i<coords.length; i++){
			int[] currentCoord=coords[i];
			
			int[] small=new int[]{ (int) Math.floor(currentCoord[0]-minDistance), (int) Math.floor(currentCoord[1]-minDistance)};
			int[] big= new int[]{ currentCoord[0], (int) Math.floor(currentCoord[1]+minDistance)};
			
			for(int[] between: activeCoords.subSet(small, big)){
				double d=distance(between, currentCoord);
				if (d<minDistance){
					minDistance=d;
				}
			}
			
			small=new int[]{ (int) Math.floor(currentCoord[0]-minDistance), currentCoord[1]};
			activeCoords.headSet(small, true).clear();
			activeCoords.add(currentCoord);
		}
		
		return minDistance;
		
		
	}
	
	
	public double distance(int[] one, int[] two){
		return Math.sqrt((two[1]-one[1])*(two[1]-one[1])+  (two[0]-one[0])*(two[0]-one[0]));
	}
	
	
	public static class TestCase {
		@Test
		public void test(){
			double result=new ClosestPair().closetPair(new int[][]{{2, 3}, {12, 30}, {40, 50}, {5, 1}, {12, 10}, {3, 4}});
			double expected=Math.sqrt(2);
			assertThat(String.format("%.4f", expected), equalTo(String.format("%.4f", result)));
		}
		
		@Test
		public void test1(){
			double result=new ClosestPair().closetPair(new int[][]{{2, 3}, {12, 30}, {40, 50}, {5, 1}, {4,1}, {12, 10}, {3, 4}});
			double expected=1.0d;
			assertThat(String.format("%.4f", expected), equalTo(String.format("%.4f", result)));
		}
	}
}
