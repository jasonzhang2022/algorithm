package jason;

import static org.junit.Assert.*;
import jason.datastructure.RadixSort;

import java.util.Arrays;

import javax.print.DocFlavor.INPUT_STREAM;

import org.junit.Test;

public class RadixSortTest {

	@Test
	public void testLCD() {
		int[] input= {1, 2, 21, 44, 35, 10000, 100, 257 };
		int[] results= {1, 2, 21, 35, 44, 100, 257, 10000};
		assertTrue(Arrays.equals(results, RadixSort.LCD(input)));
		
		int[] input1= {}; 
		int[] results1= {};
		assertTrue(Arrays.equals(results1, RadixSort.LCD(input1)));
		
		
		int[] input2= {1};
		int[] results2= {1};
		assertTrue(Arrays.equals(results2, RadixSort.LCD(input2)));
		
		
	}

}
