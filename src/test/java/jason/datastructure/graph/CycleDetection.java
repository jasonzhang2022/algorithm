package jason.datastructure.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import jason.datastructure.graph.shortest.FindAllCycle;

public class CycleDetection {
	  @Test
	    public void test()
	    {
	        // Create ArticulationPoints given in above diagrams
		  FindAllCycle g1 = new FindAllCycle(5);
	        g1.addEdgeDirect(0,1);
	        g1.addEdgeDirect(1,2);
	        g1.addEdgeDirect(2,0);
	        
	        g1.addEdgeDirect(1,3);
	        g1.addEdgeDirect(3,4);
	        g1.addEdgeDirect(4,0);
	        
	        List<String> cycles=g1.find();
	        String result=cycles.stream().collect(Collectors.joining(";"));
	        
	        
	        assertThat(result,  equalTo("0->1->2->0;0->1->3->4->0"));
	    }
	    
}
