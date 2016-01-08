package jason.datastructure.graph.flow;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

//check readme.md
public class MinimumPathCoverDAG {

	
	public String findPathes(int[][] graph){
		int n=graph.length;
		BipartiteMatch matcher=new BipartiteMatch();
		int[] pair=matcher.match(transform(graph));
		
		
		String[]  pathes=new String[n];
		for (int i=0; i<graph.length; i++){
			if (pair[i]!=0){
				//find the path starting from
				int start=i;
				List<Integer> path=new LinkedList<>();
				
				int current=start;
				while (pair[current]!=-1){
					path.add(current);
					int oldcurrent=current;
					current=pair[current];
					pair[oldcurrent]=-1;
				}
				
				pathes[start]=path.stream().map(v->v.toString()).collect(Collectors.joining("->"));
				if (pathes[current]==null){
					pathes[start]=pathes[start]+"->"+current;
				} else{
					pathes[start]=pathes[start]+"->"+pathes[current];
					pathes[current]=null;
				}
			}
		}
		
		return Arrays.stream(pathes).filter(s->s!=null).collect(Collectors.joining(","));
		
		
	}
	/*
	 * Transform graph into a bipartite graph described in readme.md
	 */
	public int[][] transform(int[][] graph){
		return graph;
	}
	
	//use the DAG here: http://cg2010studio.com/2012/01/13/%E8%B7%AF%E5%BE%91%E8%A6%86%E8%93%8B-path-cover/
	@Test
	public void test3(){
		int[][] edges={
				{0,1,0,0,1,0,0,0,0},//0
				{0,0,1,0,0,1,0,0,0},//1
				{0,0,0,0,0,0,0,0,0},//2
				{0,0,0,0,0,0,1,0,0},//3
				{0,0,0,0,0,0,0,1,1},//4
				{0,0,0,0,1,0,0,0,1},//5
				{0,0,0,0,0,0,0,1,0},//6
				{0,0,0,0,0,0,0,0,0},//7
				{0,0,0,0,0,0,0,1,0},//8
				
		};
		
	 String result= findPathes(edges);
	  assertThat(result, equalTo("0->1->2,3->6->7,5->4->8"));
	}
}
