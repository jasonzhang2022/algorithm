package jason.algorithm.topcoder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

//http://community.topcoder.com/stat?c=problem_statement&pm=2998&rd=5857
public class GrafixMask {
	
	
	public static class BitMap{
		int column;
		int row;
		BitSet bitMap;
		
		public BitMap(int row, int column) {
			super();
			this.column = column;
			this.row = row;
			bitMap=new BitSet(row*column);
		}
		
		public boolean get(int x, int y){
			return bitMap.get(x*column+y);
		}
		public void set(int x, int y){
			bitMap.set(x*column+y);
		}
		public void clear(int x, int y){
			bitMap.clear(x*column+y);
		}
	}
	
	public static int[] sortedAreas(String... rectangles){
		
		BitMap mask=new BitMap(400, 600);
		
		for (String s: rectangles){
			String[] coords=s.split(" ");
			int topx=Integer.valueOf(coords[0]);
			int topy=Integer.valueOf(coords[1]);
			
			int bottomx=Integer.valueOf(coords[2]);
			int bottomy=Integer.valueOf(coords[3]);
			
			//mask
			for (int x=topx; x<=bottomx; x++){
				for (int y=topy; y<=bottomy; y++){
					mask.set(x, y);
				}
			}
		}
		
		
		BitMap visited=new BitMap(400, 600);
		
		ArrayList<Integer> ints=new ArrayList<>(100);
		for (int x=0; x<400; x++){
			for (int y=0; y<600; y++){
				if (visited.get(x, y)){
					continue;
				}
				//mark as visited
				visited.set(x, y);
				if (!mask.get(x, y)){
					ints.add(extendUnmarked(mask, visited, x, y));
				}
			}
		}
		
		int[] ret=new int[ints.size()];
		for( int i=0; i<ints.size(); i++){
			ret[i]=ints.get(i);
		}
		
		Arrays.sort(ret);
		return ret;
		
		
	}
	
	
	public static int[][] walk={{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	public static int extendUnmarked(BitMap mask, BitMap visited, int x, int y){
		
		int count=0;
		Queue<Pair> toCheck=new LinkedList<>();
		toCheck.offer(new Pair(x, y));
		while( !toCheck.isEmpty()){
			Pair p=toCheck.poll();
			count++;
			
			for (int[] coord: walk){
				int x1=coord[0]+p.x;
				int y1=coord[1]+p.y;
				
				if (x1<0 || x1>=400 || y1<0 || y1>=600){
					continue;
				}
				
				//already visited
				if (visited.get(x1, y1)){
					continue;
				}
				visited.set(x1, y1);
				//masked
				if (!mask.get(x1, y1)){
					visited.set(x1, y1);
					toCheck.offer(new Pair(x1, y1));
				}
			}
		}
		
		//toCheck is empty
		return count;
	}
	
	
	@Test
	public void test(){
		int[] ret=sortedAreas("0 292 399 307");
		assertTrue(Arrays.equals(ret, new int[]{116800,116800}));
		
		
		
		ret=sortedAreas("48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547");
		assertTrue(Arrays.equals(ret, new int[]{22816,192608 }));
		
		
		ret=sortedAreas("0 192 399 207", "0 392 399 407", "120 0 135 599", "260 0 275 599");
		assertTrue(Arrays.equals(ret, new int[]{22080,  22816,  22816,  23040,  23040,  23808,  23808,  23808,  23808 }));
		
		
		ret=sortedAreas("50 300 199 300", "201 300 350 300", "200 50 200 299", "200 301 200 550");
		assertTrue(Arrays.equals(ret, new int[]{1,  239199 }));
		
		
		ret=sortedAreas("0 20 399 20", "0 44 399 44", "0 68 399 68", "0 92 399 92",
				 "0 116 399 116", "0 140 399 140", "0 164 399 164", "0 188 399 188",
				 "0 212 399 212", "0 236 399 236", "0 260 399 260", "0 284 399 284",
				 "0 308 399 308", "0 332 399 332", "0 356 399 356", "0 380 399 380",
				 "0 404 399 404", "0 428 399 428", "0 452 399 452", "0 476 399 476",
				 "0 500 399 500", "0 524 399 524", "0 548 399 548", "0 572 399 572",
				 "0 596 399 596", "5 0 5 599", "21 0 21 599", "37 0 37 599",
				 "53 0 53 599", "69 0 69 599", "85 0 85 599", "101 0 101 599",
				 "117 0 117 599", "133 0 133 599", "149 0 149 599", "165 0 165 599",
				 "181 0 181 599", "197 0 197 599", "213 0 213 599", "229 0 229 599",
				 "245 0 245 599", "261 0 261 599", "277 0 277 599", "293 0 293 599",
				 "309 0 309 599", "325 0 325 599", "341 0 341 599", "357 0 357 599",
				 "373 0 373 599", "389 0 389 599");
		assertTrue(Arrays.equals(ret, new int[]{15,  30,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  45,  100,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  115,  200,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  230,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  300,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345,  345 }));

	}

}
