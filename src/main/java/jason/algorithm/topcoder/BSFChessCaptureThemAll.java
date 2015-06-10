package jason.algorithm.topcoder;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;
//http://community.topcoder.com/stat?c=problem_statement&pm=2915&rd=5853
public class BSFChessCaptureThemAll {
	
	
	public static int fastKnight(String knight, String rook, String queen){
		
		int[] knightIdx=new int[2];
		int[] rookIdx=new int[2];
		int[] queenIdx=new int[2];
		
		knightIdx[0]=knight.charAt(0)-'a'+1;
		knightIdx[1]=knight.charAt(1)-'0';
		
		rookIdx[0]=rook.charAt(0)-'a'+1;
		rookIdx[1]=rook.charAt(1)-'0';
		
		queenIdx[0]=queen.charAt(0)-'a'+1;
		queenIdx[1]=queen.charAt(1)-'0';
		
		
		Queue<int[]> bsf=new LinkedList<>();
		Queue<Integer> levels=new LinkedList<Integer>();
		levels.offer(0);
		bsf.add(knightIdx);
		Set<String> jumped=new HashSet<>();
		jumped.add(knightIdx[0]+"-"+knightIdx[1]);
		
		int[][] jumpDist={{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {1, -2}, {-1, 2}, {1, 2}};
		
		
		int[] captured=null;
		int firstLevel=0;
		outer:
		while (!bsf.isEmpty()){
			int[] position=bsf.poll();
			int level=levels.poll();
			
			for (int[] dist: jumpDist){
				int x=position[0]+dist[0];
				int y=position[1]+dist[1];
				if (x<1 || x>8 || y<1|| y>8){
					continue;
				}
				if (jumped.contains(x+"-"+y)){
					continue;
				}
				if (x==rookIdx[0] && y==rookIdx[1]){
					captured=rookIdx;
					firstLevel=level+1;
					break outer;
				}
				if (x==queenIdx[0] && y==queenIdx[1]){
					captured=queenIdx;
					firstLevel=level+1;
					break outer;
				}
				if (!jumped.contains(x+"-"+y)){
					bsf.offer(new int[]{x, y});
					levels.offer(level+1);
					jumped.add(x+"-"+y);
				}
			}
		}
		
		//from start to end.
		bsf.clear();
		levels.clear();
		jumped.clear();
		int[] start=queenIdx;
		int[] end=rookIdx;
		if (captured[0]==rookIdx[0] && captured[1]==rookIdx[1]){
			start=rookIdx;
			end=queenIdx;
		}
		bsf.offer(start);
		levels.offer(0);
		jumped.add(start[0]+"-"+start[1]);
		while (!bsf.isEmpty()){
			int[] position=bsf.poll();
			int level=levels.poll();
			
			for (int[] dist: jumpDist){
				int x=position[0]+dist[0];
				int y=position[1]+dist[1];
				if (x<1 || x>8 || y<1|| y>8){
					continue;
				}
				if (jumped.contains(x+"-"+y)){
					continue;
				}
				if (x==end[0] && y==end[1]){
					return firstLevel+1+level;
				}
				if (!jumped.contains(x+"-"+y)){
					bsf.offer(new int[]{x, y});
					levels.offer(level+1);
					jumped.add(x+"-"+y);
				}
			}
		}
		return -1;
	}

	@Test
	public void test(){
		assertEquals(2, fastKnight("a1", "b3", "c5"));
		
		assertEquals(3, fastKnight("b1", "c3", "a3"));
		
		
		assertEquals(6, fastKnight("a1", "a2", "b2"));
		
		assertEquals(3, fastKnight("a5", "b7", "e4"));
		
		assertEquals(6, fastKnight("h8", "e2", "d2"));
	}
}
