package jason;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import jason.datastructure.graph.ArticulationPoint;
import jason.datastructure.graph.Biconnected;
import jason.datastructure.graph.Bridge;
import jason.datastructure.graph.EulerTourDirected;
import jason.datastructure.graph.EulerTourUndirected;
import jason.datastructure.graph.HamiltonianCycle;

public class GraphConnectivityTest {
	//http://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
    @Test
    public void testArticulation()
    {
        // Create ArticulationPoints given in above diagrams
        System.out.println("Articulation points in first ArticulationPoint ");
        ArticulationPoint g1 = new ArticulationPoint(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        boolean[] ap=g1.AP();
        IntStream.Builder builder=IntStream.builder();
        for (int i=0; i<ap.length; i++){
        	if (ap[i]){
        		builder.add(i);
        	}
        }
        String result=builder.build().mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
        assertThat(result, equalTo("0,3"));

        System.out.println("Articulation points in Second ArticulationPoint");
        ArticulationPoint g2 = new ArticulationPoint(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        ap=g2.AP();
        builder=IntStream.builder();
        for (int i=0; i<ap.length; i++){
        	if (ap[i]){
        		builder.add(i);
        	}
        }
        result=builder.build().mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
        assertThat(result, equalTo("1,2"));
 
        System.out.println("Articulation points in Third ArticulationPoint ");
        ArticulationPoint g3 = new ArticulationPoint(7);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        g3.AP();
        ap=g3.AP();
        builder=IntStream.builder();
        for (int i=0; i<ap.length; i++){
        	if (ap[i]){
        		builder.add(i);
        	}
        }
        result=builder.build().mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
        assertThat(result, equalTo("1"));
 

    }
    
    
  //http://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
    @Test
    public void testBridge()
    {
        // Create ArticulationPoints given in above diagrams
        Bridge g1 = new Bridge(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        String result=g1.findBridge().stream().collect(Collectors.joining(";"));
        assertThat(result, equalTo("3-4;0-3"));

        Bridge g2 = new Bridge(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        result=g2.findBridge().stream().collect(Collectors.joining(";"));
        assertThat(result, equalTo("2-3;1-2;0-1"));
 
        Bridge g3 = new Bridge(7);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        result=g3.findBridge().stream().collect(Collectors.joining(";"));
        assertThat(result, equalTo("1-6"));

 

    }
    
  
    //0-1
    @Test
    public void testBiconnectedSimplest()
    {
        // Create ArticulationPoints given in above diagrams
        Biconnected g1 = new Biconnected(2);
        g1.addEdge(0, 1);
        String result=g1.findBcc().stream()
        		.map(list->list.stream().collect(Collectors.joining(",")))
        		.collect(Collectors.joining("\n"));
        
       assertThat(result, equalTo("0-1"));
    }
    //0-1-2
    @Test
    public void testBiconnectedLine()
    {
        // Create ArticulationPoints given in above diagrams
        Biconnected g1 = new Biconnected(3);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        
        String result=g1.findBcc().stream()
        		.map(list->list.stream().collect(Collectors.joining(",")))
        		.collect(Collectors.joining("\n"));
        
       assertThat(result, equalTo("1-2\n0-1"));
    }
    
  //0-1-2-0
    @Test
    public void testBiconnectedSimpleCycle()
    {
        // Create ArticulationPoints given in above diagrams
        Biconnected g1 = new Biconnected(3);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2,0);
        String result=g1.findBcc().stream()
        		.map(list->list.stream().collect(Collectors.joining(",")))
        		.collect(Collectors.joining("\n"));
        
       assertThat(result, equalTo("0-2,1-2,0-1"));
    }
    
  //0-1-2-0, 0-3-4-0
    @Test
    public void testBiconnectedTwoSImpleCycle()
    {
        // Create ArticulationPoints given in above diagrams
        Biconnected g1 = new Biconnected(5);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2,0);
        
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        g1.addEdge(4,0);
        
        
        String result=g1.findBcc().stream()
        		.map(list->list.stream().collect(Collectors.joining(",")))
        		.collect(Collectors.joining("\n"));
        
       assertThat(result, equalTo("0-2,1-2,0-1\n0-4,3-4,0-3"));
    }
    
  //0-1-2-0, 0-3-4-0 0-5
    @Test
    public void testBiconnectedTwoSImpleCycleOneBridge()
    {
        // Create ArticulationPoints given in above diagrams
        Biconnected g1 = new Biconnected(6);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2,0);
        
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        g1.addEdge(4,0);
        
        g1.addEdge(0, 5);
        
        
        String result=g1.findBcc().stream()
        		.map(list->list.stream().collect(Collectors.joining(",")))
        		.collect(Collectors.joining("\n"));
        
       assertThat(result, equalTo("0-2,1-2,0-1\n0-4,3-4,0-3\n0-5"));
    }
    
    
  //http://www.geeksforgeeks.org/biconnected-components/
    @Test
    public void testBiconnected()
    {
        // Create ArticulationPoints given in above diagrams
        Biconnected g1 = new Biconnected(12);
        g1.addEdge(0, 1);
        g1.addEdge(0, 6);
        g1.addEdge(1, 5);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(2, 4);
        g1.addEdge(3, 4);
        g1.addEdge(5, 6);
        g1.addEdge(5, 7);
        g1.addEdge(5, 8);
        g1.addEdge(7, 8);
        g1.addEdge(8, 9);
        g1.addEdge(10, 11);
        String result=g1.findBcc().stream()
        		.map(list->list.stream().collect(Collectors.joining(",")))
        		.collect(Collectors.joining("\n"));
        
        System.out.println(result);
        
        String expected="8-9\n5-8,7-8,5-7\n2-4,3-4,1-3,2-3,1-2\n0-6,5-6,1-5,0-1\n10-11";
        assertThat(result, equalTo(expected));


    }
    
    @Test
    public void testEulerTourSquare()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourDirected g1 = new EulerTourDirected(4);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,3);
        g1.addEdgeDirect(3,0);
        
        String result=g1.tour();
        String expected="0->1->2->3->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testEulerTourSquareUndirected()
    {
        // Create ArticulationPoints given in above diagrams
    	EulerTourUndirected g1 = new EulerTourUndirected(4);
        g1.addEdge(0,1);
        g1.addEdge(1,2);
        g1.addEdge(2,3);
        g1.addEdge(3,0);
        
        String result=g1.tour();
        String expected="0->1->2->3->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testEulerTwoTriangles()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourDirected g1 = new EulerTourDirected(5);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,0);
        g1.addEdgeDirect(1,3);
        g1.addEdgeDirect(3,4);
        g1.addEdgeDirect(4,1);
        
        String result=g1.tour();
        String expected="0->1->3->4->1->2->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    @Test
    public void testEulerTwoTrianglesUndirected()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourUndirected g1 = new EulerTourUndirected(5);
        g1.addEdge(0,1);
        g1.addEdge(1,2);
        g1.addEdge(2,0);
        g1.addEdge(1,3);
        g1.addEdge(3,4);
        g1.addEdge(4,1);
        
        String result=g1.tour();
        String expected="0->1->3->4->1->2->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testEulerThreeCircles()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourDirected g1 = new EulerTourDirected(7);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,0);
        
        
        
        g1.addEdgeDirect(4,5);
        g1.addEdgeDirect(5,6);
        g1.addEdgeDirect(6,4);
        
        
        g1.addEdgeDirect(1,3);
        g1.addEdgeDirect(3,4);
        g1.addEdgeDirect(4,1);
        String result=g1.tour();
        String expected="0->1->3->4->5->6->4->1->2->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    @Test
    public void testEulerThreeCirclesUndirected()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourUndirected g1 = new EulerTourUndirected(7);
        g1.addEdge(0,1);
        g1.addEdge(1,2);
        g1.addEdge(2,0);
        
        
        
        g1.addEdge(4,5);
        g1.addEdge(5,6);
        g1.addEdge(6,4);
        
        
        g1.addEdge(1,3);
        g1.addEdge(3,4);
        g1.addEdge(4,1);
        String result=g1.tour();
        String expected="0->1->3->4->5->6->4->1->2->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    @Test
    public void testEulerPathThreeCircles()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourDirected g1 = new EulerTourDirected(7);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
       // g1.addEdgeDirect(2,0);
        
        
        
        g1.addEdgeDirect(4,5);
        g1.addEdgeDirect(5,6);
        g1.addEdgeDirect(6,4);
        
        
        g1.addEdgeDirect(1,3);
        g1.addEdgeDirect(3,4);
        g1.addEdgeDirect(4,1);
        String result=g1.tour();
        String expected="0->1->3->4->5->6->4->1->2";
        assertTrue(g1.isEulerianPath());
        assertFalse(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testEulerThreeCirclesShareOnePoint()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourDirected g1 = new EulerTourDirected(7);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
       g1.addEdgeDirect(2,0);
        
        
        
        g1.addEdgeDirect(1,5);
        g1.addEdgeDirect(5,6);
        g1.addEdgeDirect(6,1);
        
        
        g1.addEdgeDirect(1,3);
        g1.addEdgeDirect(3,4);
        g1.addEdgeDirect(4,1);
        String result=g1.tour();
        String expected="0->1->3->4->1->5->6->1->2->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testEulerThreeCirclesShareOnePointUndirected()
    {
        // Create ArticulationPoints given in above diagrams
    	EulerTourUndirected g1 = new EulerTourUndirected(7);
        g1.addEdge(0,1);
        g1.addEdge(1,2);
       g1.addEdge(2,0);
        
        
        
        g1.addEdge(1,5);
        g1.addEdge(5,6);
        g1.addEdge(6,1);
        
        
        g1.addEdge(1,3);
        g1.addEdge(3,4);
        g1.addEdge(4,1);
        String result=g1.tour();
        String expected="0->1->3->4->1->5->6->1->2->0";
        assertTrue(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    @Test
    public void testEulerPath()
    {
        // Create ArticulationPoints given in above diagrams
        EulerTourDirected g1 = new EulerTourDirected(4);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,3);
        g1.addEdgeDirect(3,0);
        

        String result=g1.tour();
        String expected="0->1->2->3->1";
        assertTrue(g1.isEulerianPath());
        assertFalse(g1.isEulerianCycle());
        assertThat(result, equalTo(expected));
    }
    
    
    @Test
    public void testHamiltonianCycle()
    {
        // Create ArticulationPoints given in above diagrams
    	HamiltonianCycle g1 = new HamiltonianCycle(4);
    	g1.setDirected(true);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,3);
        g1.addEdgeDirect(3,0);
        

        boolean hasCycle=g1.findCycle();
        int[] path=g1.getPath();
        assertTrue(hasCycle);
        String expected="0->1->2->3";
        String result=Arrays.stream(path).mapToObj(String::valueOf).collect(Collectors.joining("->"));
        assertThat(result, equalTo(expected));
    }
    
    
    @Test
    public void testHamiltonianCycleOne()
    {
        // Create ArticulationPoints given in above diagrams
    	HamiltonianCycle g1 = new HamiltonianCycle(5);
    	g1.setDirected(true);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,3);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,4);
        g1.addEdgeDirect(4,1);
        g1.addEdgeDirect(4,3);
        g1.addEdgeDirect(3,0);
       
        

        boolean hasCycle=g1.findCycle();
        int[] path=g1.getPath();
        assertTrue(hasCycle);
        String expected="0->1->2->4->3";
        String result=Arrays.stream(path).mapToObj(String::valueOf).collect(Collectors.joining("->"));
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testHamiltonianCycleOneUndirected()
    {
        // Create ArticulationPoints given in above diagrams
    	HamiltonianCycle g1 = new HamiltonianCycle(5);
    	g1.setDirected(false);
        g1.addEdge(0,1);
        g1.addEdge(1,3);
        g1.addEdge(1,2);
        g1.addEdge(2,4);
        g1.addEdge(4,1);
        g1.addEdge(4,3);
        g1.addEdge(3,0);
       
        

        boolean hasCycle=g1.findCycle();
        int[] path=g1.getPath();
        assertTrue(hasCycle);
        String expected="0->1->2->4->3";
        String result=Arrays.stream(path).mapToObj(String::valueOf).collect(Collectors.joining("->"));
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testHamiltonianCycleNegative()
    {
        // Create ArticulationPoints given in above diagrams
    	HamiltonianCycle g1 = new HamiltonianCycle(4);
    	g1.setDirected(true);
        g1.addEdgeDirect(0,1);
        g1.addEdgeDirect(1,2);
        g1.addEdgeDirect(2,3);
        g1.addEdgeDirect(3,1);
        

        boolean hasCycle=g1.findCycle();
        int[] path=g1.getPath();
        assertFalse(hasCycle);
    }
}
