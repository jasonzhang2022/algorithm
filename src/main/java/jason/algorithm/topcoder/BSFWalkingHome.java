package jason.algorithm.topcoder;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

/**
 * Differemt from other BSF, some step has a value 1, some block has a value 0.
 * 
 * All nodes have the same cross is treated as the same level
 * 
 * @author jason
 *
 */
//http://community.topcoder.com/stat?c=problem_statement&pm=3444&rd=5868
public class BSFWalkingHome {
	
	public static int fewestCrossings(String... map){
		
		//x is row/string index, y is column index;
		Pair source=null;
		Pair target=null;
		int count=0;
		for (String s: map){
			if (s.indexOf('S')!=-1)
			{
				source=new Pair(count, s.indexOf('S'));
			}
			if (s.indexOf('H')!=-1){
				target=new Pair(count, s.indexOf('H'));
			}
			if (source!=null && target!=null){
				break;
			}
			count++;
		}
		
		PriorityQueue<Pair> bfs=new PriorityQueue<>((a,b)->{return a.value-b.value;});
		bfs.offer(source);
		int[][] moves={{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		
		Set<Pair> processed=new HashSet<>();
		processed.add(source);
		while (!bfs.isEmpty()){
			Pair current=bfs.poll();
			
			//System.out.printf("%d, %d=%d\n", current.x, current.y, current.value);
			/*
			if (current.equals(target)){
				return current.value;
			}
			*/
			for (int[] move: moves){
				int x=current.x+move[0];
				int y=current.y+move[1];
				
				
				//Do we cross bound?
				if (x<0 || x>=map.length || y<0 || y>=map[x].length()){
					continue;
				}
				if (map[x].charAt(y)=='F'|| map[x].charAt(y)=='*'){
					continue;
				}
				
				if (move[0]==0 && map[x].charAt(y)=='-'){
					//move horizontally
					continue;
				}
				if (move[1]==0 && map[x].charAt(y)=='|'){
					//move vertically.
					continue;
				}
				Pair newPair=new Pair(x, y);
				if (processed.contains(newPair)){
					continue;
				}
				int newValue=current.value;
				if (map[x].charAt(y)=='H'){
					return newValue;
				}
				if (map[x].charAt(y)!='.' ){
					if ((move[0]==0 && map[current.x].charAt(current.y)!='|') || (move[1]==0 && map[current.x].charAt(current.y)!='-')){
						newValue++;
					}
				}
				//do we need to check exists, decrease key?
				newPair.value=newValue;
				bfs.offer(newPair);
				processed.add(newPair);
			}
		}
		return -1;
	}

	@Test
	public void test(){
		assertEquals(0, fewestCrossings(    	
				"S.|..",
				 "..|.H",
				 "..|..",
				 "....."));
		
		assertEquals(1, fewestCrossings(    	
				"S.|..",
				 "..|.H"));
		
		
		
		
		assertEquals(1, fewestCrossings(    	
				"S.||...",
				 "..||...",
				 "..||...",
				 "..||..H"));
		
		
		assertEquals(1, fewestCrossings(    	
				"S.....",
				 "---*--",
				 "...|..",
				 "...|.H"));
		
		assertEquals(2, fewestCrossings(    	
				"S.F..",
				 "..F..",
				 "--*--",
				 "..|..",
				 "..|.H"));
		
		assertEquals(27, fewestCrossings(    	
				"H|.|.|.|.|.|.|.|.|.|.|.|.|.",
				 "F|F|F|F|F|F|F|F|F|F|F|F|F|-",
				 "S|.|.|.|.|.|.|.|.|.|.|.|.|."));
		
		assertEquals(-1, fewestCrossings( "S-H"));
	};
}
