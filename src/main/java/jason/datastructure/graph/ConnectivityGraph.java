package jason.datastructure.graph;

import java.util.LinkedList;

public class ConnectivityGraph {
	public int V;   // No. of vertices
	 
    // Array  of lists for Adjacency List Representation
    public LinkedList<Integer> adj[];
    public int time = 0;
    public static final int NIL = -1;
 
    // Constructor
    public ConnectivityGraph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }
 
    //Function to add an edge into the ArticulationPoint
    public void addEdge(int v, int w)
    {
        adj[v].add(w);  // Add w to v's list.
        adj[w].add(v);  //Add v to w's list
    }
}
