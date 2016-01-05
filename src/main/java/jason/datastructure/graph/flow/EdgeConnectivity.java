package jason.datastructure.graph.flow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//Introduction to algorithm: 26.2-11
//http://www.eecs.berkeley.edu/~luca/cs261/lecture13.pdf
/**
 * 
 * @author jason
 * http://www.eecs.berkeley.edu/~luca/cs261/lecture13.pdf
 * 
 * The algorithm above picks an arbitrary vertex as source vertex, s. 
 * 
 * It then run |V|-1 time minimum cut, one for each vertices in V except s.
 * 
 * We have |V|-1 number of flows for |V-1| time minimum cut, one for each minimum cut. 
 * 
 * The minimum flow among the |V|-1 flows is the k in  k-edge connected.
 *   
 * But is the K from source s is good for other sources?
 * 
 * Here is a proof.
 * 
 * 1) Suppose v1 is picked as source, we get a value K.
 *  (we 
 * 2) Suppose  v2 is picked as source, we get a value K-1. 
 * 3) Suppose that v2 has only K-1 distinct paths with v3.
 * 
 * First,  we have at least K distinct path between v1 and v2 inducted from 1. 
 * At least one of the path doesn't contain v3. Otherwise all these k path are valid
 * distinct path from v2 to v3. This contradicts point 3.  We name this path P1.
 * 
 * Similarly we have at least k distinct path between v1 and v3, and one of the path doesn't 
 * contain v1.  We name this path P2. 
 * 
 * Right now path P1->P2 is a valid distinct path from v2 to v3. We have one more valid path. 
 * This contradict the second point.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class EdgeConnectivity {
	
	
	
	@Test
	public void test(){
		int[][] weight={
				{0,1,1,0,0},
				{1,0,0,1,0},
				{1,0,0,1,1},
				{0,1,1,0,0},
				{0,0,1,0,0}
				
		};
		
		int kConnectivity=Integer.MAX_VALUE;
		for (int i=1; i<5; i++){
			FordFulkerson ford=new FordFulkerson();
			int newConnectivity=ford.maximumFlow(weight, 0, i);
			System.out.printf("Connecttivity: %d->%d: %d\n", 0, i, newConnectivity);
			kConnectivity=Math.min(newConnectivity, kConnectivity);
		}
	
		
		assertEquals(kConnectivity, 1);

	}
	
	//http://www.eecs.berkeley.edu/~luca/cs261/lecture13.pdf
	@Test
	public void test3(){
		int[][] weight={
				{0,1,1,1,0,0,0,0},
				{1,0,0,0,1,1,0,0},
				{1,0,0,0,1,0,1,0},
				{1,0,0,0,0,1,1,0},
				{0,1,1,0,0,0,0,1},
				{0,1,0,1,0,0,0,1},
				{0,0,1,1,0,0,0,1},
				{0,0,0,0,1,1,1,0},
				
		};
		
		int kConnectivity=Integer.MAX_VALUE;
		for (int i=1; i<7; i++){
			FordFulkerson ford=new FordFulkerson();
			int newConnectivity=ford.maximumFlow(weight, 0, i);
			System.out.printf("Connecttivity: %d->%d: %d\n", 0, i, newConnectivity);
			kConnectivity=Math.min(newConnectivity, kConnectivity);
		}
	
		
		assertEquals(kConnectivity, 3);

	}
}
