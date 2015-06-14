package jason.algorithm.topcoder;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;

import org.junit.Test;

public class BruteForceGeneralChess {

	static int[][] threanedBy={ {-2,-1},  {-2,1},  {-1,-2},  {-1,2},  {1,-2},  {1,2},  {2,-1},  {2,1} };
	public static String[] attackPositions(String[] pieces){
		
		Set<Pair> commonThreantedBy=new HashSet<Pair>();
		
		int count=0;
		for(String coord: pieces){
			
			Set<Pair> singleThreanedBy=new HashSet<Pair>();
			
			String[] xy=coord.split(",");
			int x=Integer.parseInt(xy[0]);
			int y=Integer.parseInt(xy[1]);
			for (int[] delta: threanedBy){
				int newX=x+delta[0];
				int newY=y+delta[1];
				singleThreanedBy.add(new Pair(newX, newY));
			}
			
			if (count==0){
				commonThreantedBy.addAll(singleThreanedBy);
			} else{
				commonThreantedBy.retainAll(singleThreanedBy);
			}
			if (commonThreantedBy.isEmpty()){
				break;
			}
			count++;
		}
		
		ArrayList<Pair> pairs=new ArrayList<>(commonThreantedBy);
		
		pairs.sort(Comparator.comparingInt(Pair::getX).thenComparingInt(Pair::getY));
		int[] len=new int[]{0};
		String[] ret=new String[pairs.size()];
		pairs.forEach((p)->{
			ret[len[0]++]=p.x+","+p.y;
		});
		
		return ret;
		
	}
	
	@Test
	public void test(){
		
		assertTrue(Arrays.deepEquals(new String[]{ "-2,-1",  "-2,1",  "-1,-2",  "-1,2",  "1,-2",  "1,2",  "2,-1",  "2,1"}, attackPositions(new String[]{"0,0"})));


		assertTrue(Arrays.deepEquals(new String[]{"0,0",  "1,-1"}, 
				attackPositions(new String[]{"2,1", "-1,-2"})));
		
		assertTrue(Arrays.deepEquals(new String[]{}, 
				attackPositions(new String[]{"0,0", "2,1"})));
		
		assertTrue(Arrays.deepEquals(new String[]{"-1001,998"}, 
				attackPositions(new String[]{"-1000,1000", "-999,999", "-999,997"})));
	}
}
