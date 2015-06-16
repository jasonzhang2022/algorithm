package jason.algorithm.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


/**
 * How many shape of the android screen lock maze has. 
 * It is a 3x3 matrix.
 * Basically we are asking how many subgraph.
 * 
 * We loop all the subgraph, each time we add an edge
 * @author jason
 *
 */
public class HowManySubgraph {
	
	public static class Pair{
		int x;
		int y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	
	public static class Edge{
		Pair from;
		Pair to;
	}
	
	
	public static class Shape {
		
	
		/*
		 * Each shape has a number of edges.  All of these edge uniquely defines 
		 * a graph.
		 * Each edge has an index.
		 * We could use BitSet edges=new BitSet(); to record which edge in the graph.
		 * In this particular question, we have total 20 edge. We need 20 bits to represent it
		 * so we use an integer.
		 */
		int edges=0;
		public boolean hasEdge(int edgeIndex){
			return (edges & (1<<edgeIndex) )==0?false:true;
		}
		
		public void addEdge(int edgeIndex){
			edges =edges|(1<<edgeIndex);
		}
		
		
		/*
		 * use a bit map to record what vertex is in the map.
		 * we keep this vertex so we can quickly find out if one edge can be 
		 * connected to the subgraph or not. 
		 */
		short vertices=0;
		
		/*
		 * Is a vertex in the shape
		 */
		public boolean hasVertex(int row, int col){
			int index=row*3+col;
			short bit=(short) (1<<index);
			return (bit & vertices)==0?false:true;
		}
		
		//add a vertex to the shape
		public void setVertex(int row, int col){
			int index=row*3+col;
			short bit=(short) (1<<index);
			vertices=(short) (vertices|bit);
		}
		
		public Shape cloneAndAdd(Edge edge, int edgeIndex){
			if (hasEdge(edgeIndex)){
				//already has the edge
				return null;
			}
			
			//is the new edge connected with any point in the shape
			int set=0;
			if (hasVertex(edge.from.x, edge.from.y)){
				set++;
			}
			if (hasVertex(edge.to.x, edge.to.y)){
				set++;
			}
			
			//but 1) the new edge is not connected to any
			//existing edge  2) has edge.
			if ( edges!=0 && set==0){
				return null;
			}
			
			Shape s=new Shape();
			s.vertices=vertices;
			s.setVertex(edge.from.x, edge.from.y);
			s.setVertex(edge.to.x, edge.to.y);
			s.edges=edges;
			s.addEdge(edgeIndex);
			
			return s;
		}
		
		
		public int hashCode(){
			return edges;
		}
		
		public boolean equals(Object obj){
			if (obj==null || !(obj instanceof Shape)){
				return false;
			}
			Shape s=(Shape)obj;
			return s.edges==edges;
			
		}
		
	}

	int[][] walk={
			{0, 1}, //right node.
			{1, 0}, //down node
			{-1, -1}, //left diagonal
			{1,1} //right diagonal
			};
	
	public ArrayList<Edge> collectEdges(){
		
		ArrayList<Edge> edges=new ArrayList<Edge>(30);
		
		for (int x=0; x<3; x++){
			for (int y=0; y<3; y++){
				
				for (int[] step: walk){
					int newX=x+step[0];
					int newY=y+step[1];
					if (newX>=0 && newX<3 &&  newY>=0 && newY<3){
						Edge edge=new Edge();
						edge.from=new Pair(x, y);
						edge.to=new Pair(newX, newY);
						edges.add(edge);
					}
				}
			}
		}
		return edges;
	}
	
	ArrayList<Edge> edges=collectEdges();
	
	public int combination(){
		
	
		System.out.printf("found %d edges\n", edges.size());
		
		int totalnum=0;
		Shape emptyShape=new Shape();
		
		Set<Shape> preSets=new HashSet<>();
		preSets.add(emptyShape);
		
		//subgraph with j number of edges.
		for (int numEdge=1; numEdge<=edges.size(); numEdge++){
			
			Set<Shape> currentSets=new HashSet<Shape>();
			
			//for each combination in previous set with edge (j-1), add one edge
			for (Shape s: preSets){
				//add a new edge to current s.
				for (int i=0; i<edges.size(); i++){
					Shape news=s.cloneAndAdd(edges.get(i), i);
					if (news!=null){
						currentSets.add(news);
					}
				}
			}
			
			System.out.printf("number of combination ofr subgraph with edge %d: %d\n", numEdge, currentSets.size());
			totalnum+=currentSets.size();
			preSets=currentSets;
		}
		return totalnum;
	}
	
	@Test
	public void test(){
		HowManySubgraph many=new HowManySubgraph();
		int total=many.combination();
		System.out.printf("has total %d subgraph\n", total);
	}
	

}
