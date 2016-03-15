package jason.algorithm.geometry;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Test;

public class VerticalHorizontalLineSegmentIntersection {
	
	
	public static class Line {
		int[] firstPoint;
		int[] secondPoint;
		
		public Line(int[] firstPoint, int[] secondPoint) {
			super();
			this.firstPoint = firstPoint;
			this.secondPoint = secondPoint;
		}

		public boolean isVertical(){
			return firstPoint[0]==secondPoint[0];
		}
		
		
	}
	
	public int intersect(int[][] segments){
		
		
		int count=0;
		Map<int[], Line> track=new IdentityHashMap<>();
		List<int[]> points=new ArrayList<>(segments.length*2);
		for (int i=0; i<segments.length; i++){
			int[] firstpoint=new int[]{segments[i][0], segments[i][1]};
			int[] secondpoint=new int[]{segments[i][2], segments[i][3]};
			
			Line line=new Line(firstpoint, secondpoint);
			if (line.isVertical()){
				track.put(firstpoint, line);
				points.add(firstpoint);
			} else{
				track.put(firstpoint, line);
				track.put(secondpoint, line);
				points.add(firstpoint);
				points.add(secondpoint);
			}
		}
		Collections.sort(points, (l, r)->Integer.compare(l[0], r[0]));
		
		
		//assume that no two active line at the same height
		Comparator<Line> c=(l, r)->Integer.compare(l.firstPoint[1], r.firstPoint[1]);
		//c=c.thenComparing((l, r)->Integer.compare(l.firstPoint[0], r.firstPoint[0]));
		//c=c.thenComparing((l, r)->Integer.compare(l.secondPoint[0], r.secondPoint[0]));
		
		
		TreeSet<Line> activeHorizontalLines=new TreeSet<>(c);
		
		for (int i=0; i<points.size();){
			
			ArrayList<Line> endpoints=new ArrayList<>();
			ArrayList<Line> startpoints=new ArrayList<>();
			ArrayList<Line> lines=new ArrayList<>();
			
			int x=points.get(i)[0];
			while (i<points.size() && points.get(i)[0]==x){
				Line line=track.get(points.get(i));
				if (line.isVertical()){
					lines.add(line);
				} else if (line.firstPoint==points.get(i)){
					startpoints.add(line);
				} else{
					endpoints.add(line);
				}
				i++;
			}
			
			for (Line line: startpoints){
				activeHorizontalLines.add(line);
			}
			
			
			for (Line line: lines){
				Line lowline=new Line(line.firstPoint, line.firstPoint);
				Line highline=new Line(line.secondPoint, line.secondPoint);
				count+=activeHorizontalLines.subSet(lowline, true, highline, true).size();
			}
			
			for (Line line: endpoints){
				activeHorizontalLines.remove(line);
			}
			
			
			
		}
		
		
		return count;
		
		
	}
	
	public static class TestCase {
		
		
		@Test
		public void testIntersectAtEndPoints(){
			VerticalHorizontalLineSegmentIntersection v=new VerticalHorizontalLineSegmentIntersection();
			int[][] lines={
					{0,1,2,1},
					{2,0,2,3},
					{2,2, 3,2}
			};
			int expected=2;
			int result=v.intersect(lines);
			assertEquals(expected, result);
		}
		
		@Test
		public void test(){
			VerticalHorizontalLineSegmentIntersection v=new VerticalHorizontalLineSegmentIntersection();
			int[][] lines={
					{0,1,2,1},
					{2,0,2,2}
			};
			int expected=1;
			int result=v.intersect(lines);
			assertEquals(expected, result);
		}
		
		@Test
		public void test2(){
			VerticalHorizontalLineSegmentIntersection v=new VerticalHorizontalLineSegmentIntersection();
			int[][] lines={
					{0,1,2,1},
					{2,0,2,2},
					{3,1, 4,1}
			};
			int expected=1;
			int result=v.intersect(lines);
			assertEquals(expected, result);
		}
	}
}
