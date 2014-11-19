package jason.algorithm.select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import jason.algorithm.Shuffler;

import org.junit.Test;

public class TestSelect {

	@Test
	public void testMedianOfMedian() {
		int[] input=new int[100];
		for (int i=0; i<input.length; i++) {
			input[i]=i;
		}
		
		for (int i=0; i<100; i++) {
			Shuffler.shuffle(input);
			int median=MedianOfMedians.median(input);
			//System.out.println(median);
			assertTrue(median>=30 && median<=70);
		}
		
	}
	
	@Test
	public void testSelect() {
		int[] input=new int[100];
		for (int i=0; i<input.length; i++) {
			input[i]=i+1;
		}
		
		//check 100 times.
		for (int i=0; i<100; i++) {
			Shuffler.shuffle(input);
			int index=QuickSelect.select(input, 50);
			assertEquals(input[index], 50);
			assertEquals(index, 49);
			
			
			index=QuickSelect.select(input, 30);
			assertEquals(index, 29);
			assertEquals(input[index], 30);
			
		}
	}
	

}
