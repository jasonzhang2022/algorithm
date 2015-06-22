package jason.algorithm.topcoder;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

//http://community.topcoder.com/stat?c=problem_statement&pm=1170&rd=4371
public class Escape {
	
	int N=501;
	
	int[][] costs;
	int[][] walks=new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
	
	public int lowest(String[] harmful, String[] deadly){
		costs=new int[N][N];
		
		
		//1 means harmful
		for (String h: harmful){
			Integer[]  coords=Arrays.stream(h.split(" ")).map((s)->Integer.parseInt(s)).toArray(Integer[]::new);
			
			int x1=Math.min(coords[0], coords[2]);
			int x2=Math.max(coords[0], coords[2]);
			
			int y1=Math.min(coords[1], coords[3]);
			int y2=Math.max(coords[1], coords[3]);
			
			for (int x=x1; x<=x2; x++){
				for (int y=y1; y<=y2; y++){
					costs[x][y]=1;
				}
			}
		}
		
		//-1 deadly
		for (String h: deadly){
			Integer[]  coords=Arrays.stream(h.split(" ")).map((s)->Integer.parseInt(s)).toArray(Integer[]::new);
			
			int x1=Math.min(coords[0], coords[2]);
			int x2=Math.max(coords[0], coords[2]);
			
			int y1=Math.min(coords[1], coords[3]);
			int y2=Math.max(coords[1], coords[3]);
			
			for (int x=x1; x<=x2; x++){
				for (int y=y1; y<=y2; y++){
					costs[x][y]=-1;
				}
			}
		}
		
		
		//0 means normal.
		Queue<Pair> nextBreadth=new LinkedList<Pair>();
		nextBreadth.offer(new Pair(0,0));
		costs[0][0]=2; 
		//make the minimal length to be 2 so that cost[x][y]==1 is from harmful, not from some path
		//see comment below
		
		while (!nextBreadth.isEmpty()){
			Pair xy=nextBreadth.poll();
			int cost=costs[xy.x][xy.y];
			if (xy.x==500 && xy.y==500){
				return cost-2;
			}
			for (int[] delta: walks){
				int newX=xy.x+delta[0];
				int newY=xy.y+delta[1];
				nextLevelInBFS(newX, newY, nextBreadth, cost);
			}
			
		}
		
		if (costs[500][500]>1){
			return costs[500][500]-2;
		}
		
		return -1;
	}
	
	
	
	
	public void nextLevelInBFS(int x, int y, Queue<Pair> nextBreadth, int prevCost){
		
		//out of boundary
		if (x<0 || x>=N || y<0 || y>=N){
			return;
		}
		
		if (costs[x][y]==-1){
			//deadly, unreachable.
			return ;
		}
		//first time to touch harmful cell.
		//will be next breadth
		//this is why we have costs[0][0]==2, if we set costs[0][0]==0,
		//we don't know costs[x][y]==1 mean it is 1 from harmful cell or it is from some walking path.
		if (costs[x][y]==1){
			costs[x][y]=prevCost+1;
			nextBreadth.offer(new Pair(x, y));
			return;
		}
		if (costs[x][y]>1){
			//have costs from some path, already in queue or be processed.
			return;
		}
		
		//cost is zero right now
		
		Queue<Pair> sameBreadth=new LinkedList<>();
		sameBreadth.offer(new Pair(x, y));
		costs[x][y]=prevCost; //processed.
		
		while (!sameBreadth.isEmpty()){
			Pair p=sameBreadth.poll();
			for (int[] delta: walks){
				int newX=p.x+delta[0];
				int newY=p.y+delta[1];
				if (newX<0 || newX>=N || newY<0 || newY>=N){
					continue;
				}
				
				if (costs[newX][newY]==-1){
					//deadly cell,
					continue;
				}
				
				if (costs[newX][newY]==0){
					sameBreadth.offer(new Pair(newX, newY));
					costs[newX][newY]=prevCost;
				}
				
				
				if (costs[newX][newY]==1){
					nextBreadth.offer(new Pair(newX, newY));
					costs[newX][newY]=prevCost+1;
				}
				//have a cost larger than 1,  from some path or already processed
				
			}
		}
	}
	
	
	@Test
	public void test(){
		
		assertEquals(0, lowest(new String[]{}, new String[]{}));
		
		
		assertEquals(1000, lowest(new String[]{"500 0 0 500"}, new String[]{"0 0 0 0"}));
		
		
		
		assertEquals(1000, lowest(new String[]{"0 0 250 250","250 250 500 500"}, new String[]{"0 251 249 500","251 0 500 249"}));
		
		
		assertEquals(-1, lowest(new String[]{"0 0 250 250","250 250 500 500"}, new String[]{"0 250 250 500","250 0 500 250"}));
		
		
		assertEquals(254, lowest(new String[]{"468 209 456 32",
				 "71 260 306 427",
				 "420 90 424 492",
				 "374 253 54 253",
				 "319 334 152 431",
				 "38 93 204 84",
				 "246 0 434 263",
				 "12 18 118 461",
				 "215 462 44 317",
				 "447 214 28 475",
				 "3 89 38 125",
				 "157 108 138 264",
				 "363 17 333 387",
				 "457 362 396 324",
				 "95 27 374 175",
				 "381 196 265 302",
				 "105 255 253 134",
				 "0 308 453 55",
				 "169 28 313 498",
				 "103 247 165 376",
				 "264 287 363 407",
				 "185 255 110 415",
				 "475 126 293 112",
				 "285 200 66 484",
				 "60 178 461 301",
				 "347 352 470 479",
				 "433 130 383 370",
				 "405 378 117 377",
				 "403 324 369 133",
				 "12 63 174 309",
				 "181 0 356 56",
				 "473 380 315 378"}, new String[]{"250 384 355 234",
				 "28 155 470 4",
				 "333 405 12 456",
				 "329 221 239 215",
				 "334 20 429 338",
				 "85 42 188 388",
				 "219 187 12 111",
				 "467 453 358 133",
				 "472 172 257 288",
				 "412 246 431 86",
				 "335 22 448 47",
				 "150 14 149 11",
				 "224 136 466 328",
				 "369 209 184 262",
				 "274 488 425 195",
				 "55 82 279 253",
				 "153 201 65 228",
				 "208 230 132 223",
				 "369 305 397 267",
				 "200 145 98 198",
				 "422 67 252 479",
				 "231 252 401 190",
				 "312 20 0 350",
				 "406 72 207 294",
				 "488 329 338 326",
				 "117 264 497 447",
				 "491 341 139 438",
				 "40 413 329 290",
				 "148 245 53 386",
				 "147 70 186 131",
				 "300 407 71 183",
				 "300 186 251 198",
				 "178 67 487 77",
				 "98 158 55 433",
				 "167 231 253 90",
				 "268 406 81 271",
				 "312 161 387 153",
				 "33 442 25 412",
				 "56 69 177 428",
				 "5 92 61 247"}));
		
	}

}
