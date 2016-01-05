package jason.datastructure.graph.flow;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class BipartiteMatch {
	
	
	
	public int[] match(int[][] matches){
		int tnum=matches[0].length;
		int snum=matches.length;
		
		int[] sMatched=new int[snum];
		int[] tMatched=new int[tnum];
		
		Arrays.fill(sMatched, -1);
		Arrays.fill(tMatched, -1);
		int matchedSink=0;
		for (int u=0; u<snum; u++){
			int v=matchOne(u, matches, snum, tnum, sMatched, tMatched, new BitSet(tnum));
			if (v!=-1){
				matchedSink++;
			}
			if (matchedSink==tnum){
				break;
			}
		}
		
		
		return sMatched;
	}
	
	/**
	 * Find out whether we can increase the match by finding a match for U.
	 * 
	 * @param u a source
	 * @param matches matches matrix
	 * @param snum the number of source elements
	 * @param tnum the number of sink elements
	 * @param sMatched sMatched[i] is the matched sink element for an source element i;
	 * @param tMatched tMatched[i] is the source element which matches the sink element i.
	 * @param tTouched whether we should excluded tTouched[i] from consideration. It should be excluded if 1) this sink i is already 
	 * 	considered in the recursive path, or 2) there is no potential flow increase by matching this sink to an alternative source. 
	 * @return matched sink element or -1.
	 */
	public int matchOne(int u, int[][] matches, int snum, int tnum, int[] sMatched, int[] tMatched, BitSet tTouched) {
		
		for (int v=0; v<tnum; v++){
			if (matches[u][v]==1 && tMatched[v]==-1 && !tTouched.get(v)){
				sMatched[u]=v;
				tMatched[v]=u;
				return v;
			}
		}
		
		//all vertices matched. Can we improve the flow
		for (int v=0; v<tnum; v++){
			if (matches[u][v]==1  && !tTouched.get(v)){
				tTouched.set(v);
				
				int oldSource=tMatched[v];
				//disassociate old match
				tMatched[v]=-1;
				sMatched[oldSource]=-1;
				
				int newMatchForOldSource=matchOne(oldSource, matches, snum, tnum, sMatched, tMatched, tTouched);
				
				if (newMatchForOldSource==-1){
					//restore old match
					tMatched[v]=oldSource;
					sMatched[oldSource]=v;
				} else {
					//found matched.
					tMatched[v]=u;
					sMatched[u]=v;
					return v;
				}
			}
		}
		return -1;
	}
	
	//http://www.geeksforgeeks.org/maximum-bipartite-matching/
	@Test
	public void test3(){
		int[][] matches={
				{0,1,1,0,0,0},
				{0,0,0,0,0,0},
				{1,0,0,1,0,0},
				{0,0,1,0,0,0},
				{0,0,0,1,0,0},
				{0,0,0,0,0,1}
		};
		
	  int[] result=match(matches);
	  System.out.println(Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(",")));
	  int[] expected={1, -1, 0, 2, 3, 5};
	  assertTrue(Arrays.equals(result, expected));
	}
	
}
