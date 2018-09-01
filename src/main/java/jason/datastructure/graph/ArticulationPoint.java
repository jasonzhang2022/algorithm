package jason.datastructure.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//http://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
public class ArticulationPoint extends ConnectivityGraph {
 
    public ArticulationPoint(int v) {
		super(v);
	}

   /*
   'time' variable  is defined in parent class. time here means depth in graph traversal.
   When the root is reached by DFS, the time is 0.
   When root's children is reached, the time for root's children is 1.
   ...
   
  
   When a node is discovered at time=t, its disc[node]=t.
   It can reach one node that has not been discovered yet. The disc[child]=t+1
   It can also reach one node that has been discovered. This child node will have a time disc[child]= x <= t since it 
   is discovered before this node
   
   The lowest time that can be reached by this node is low[node]=min(x, low[node]) where low[node]=t at the very 
   begnning.
 
   If low[node] <disc[node], some descendent nodes loop back to its parent.
   if low[node] == disc[node], none of descendent nodes loop back to its parent. It is an articulation point.
   
   We can keep track of children[node]= number of nodes that can reached by this node.
   children[node] =children[node]+children[child]+1
   
   Each time, we found a articulation point, we check against children[node] with cached maxchild(initialized to zero).
   if (children[node]>machild){
    we record node has candidate result.
   }
   
   ...
   
   * Actually, low[node]=min(low(child), low(node), not min(disc[child], low(node))
   */
	
	// A recursive function that find articulation points using DFS
    // u --> The vertex to be visited next
    // visited[] --> keeps tract of visited vertices
    // disc[] --> Stores discovery times of visited vertices. time means depth in graph traversal
    // parent[] --> Stores parent vertices in DFS tree
    // low[]--> Stores the lowest depth u can reach. Since graph can have loop, so low[] <= disc[]
    // ap[] --> Store articulation points
    void APUtil(int u, boolean visited[], int disc[],
                int low[], int parent[], boolean ap[]
	       )
    {
 
        // Count of children in DFS Tree
        int children = 0;
 
        // Mark the current node as visited
        visited[u] = true;
 
        /*
	U is discovered at depth t.
	lowest of depth that can be reached by u is t.
	*/
        disc[u] = low[u] = ++time;
 
        // Go through all vertices aadjacent to this
        Iterator<Integer> i = adj[u].iterator();
        while (i.hasNext())
        {
            int v = i.next();  // v is current adjacent of u
 
            // If v is not visited yet, then make it a child of u
            // in DFS tree and recur for it
            if (!visited[v])
            {
                children++;
                parent[v] = u;
                APUtil(v, visited, disc, low, parent, ap);
 
                // Check if the subtree rooted with v has a connection to
                // one of the ancestors of u
                low[u]  = Math.min(low[u], low[v]);
 
                // u is an articulation point in following cases
 
                // (1) u is root of DFS tree and has two or more chilren.
                if (parent[u] == NIL && children > 1)
                    ap[u] = true;
 
                // (2) If u is not root and low value of one of its child
                // is more than discovery value of u.
                if (parent[u] != NIL && low[v] >= disc[u])
                    ap[u] = true;
            }
 
            // Update low value of u for parent function calls.
            else if (v != parent[u])
                low[u]  = Math.min(low[u], disc[v]);
        }
    }
 
    int[] low;
    // The function to do DFS traversal. It uses recursive function APUtil()
    public boolean[] AP()
    {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        int disc[] = new int[V];
        low = new int[V];
        int parent[] = new int[V];
        boolean ap[] = new boolean[V]; // To store articulation points
 
        // Initialize parent and visited, and ap(articulation point)
        // arrays
        for (int i = 0; i < V; i++)
        {
            parent[i] = NIL;
            visited[i] = false;
            ap[i] = false;
        }
 
        // Call the recursive helper function to find articulation
        // points in DFS tree rooted with vertex 'i'
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                APUtil(i, visited, disc, low, parent, ap);
 
        // Now ap[] contains articulation points, print them
        return ap;
    }
 
  
    
   
    
}

