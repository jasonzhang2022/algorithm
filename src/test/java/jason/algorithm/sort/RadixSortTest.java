package jason.algorithm.sort;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import jason.algorithm.sort.RadixSort_LCD_bucket;
import jason.algorithm.util.Shuffler;

import java.util.Arrays;

import javax.print.DocFlavor.INPUT_STREAM;

import org.junit.Before;
import org.junit.Test;

public class RadixSortTest {

	@Test
	public void testBucket() {
		int[] input= {1, 2, 21, 44, 35, 10000, 100, 257 };
		int[] results= {1, 2, 21, 35, 44, 100, 257, 10000};
		assertTrue(Arrays.equals(results, RadixSort_LCD_bucket.LCD(input)));
		
		int[] input1= {}; 
		int[] results1= {};
		assertTrue(Arrays.equals(results1, RadixSort_LCD_bucket.LCD(input1)));
		
		
		int[] input2= {1};
		int[] results2= {1};
		assertTrue(Arrays.equals(results2, RadixSort_LCD_bucket.LCD(input2)));
		
		
	}
	
	@Test
	public void testCount() {
		int[] input= {1, 2, 21, 44, 35, 10000, 100, 257 };
		int[] results= {1, 2, 21, 35, 44, 100, 257, 10000};
		assertTrue(Arrays.equals(results, RadixSort_LCD_Count.sort(input)));
		
		int[] input1= {}; 
		int[] results1= {};
		assertTrue(Arrays.equals(results1, RadixSort_LCD_Count.sort(input1)));
		
		
		int[] input2= {1};
		int[] results2= {1};
		assertTrue(Arrays.equals(results2, RadixSort_LCD_Count.sort(input2)));
	}
	
	@Test
	public void testMSD() {
		int[] input= {1, 2, 21, 44, 35, 10000, 100, 257 };
		int[] results= {1, 2, 21, 35, 44, 100, 257, 10000};
		assertTrue(Arrays.equals(results, RadixSort_MSD_partition.sort(input)));
		
		int[] input1= {}; 
		int[] results1= {};
		assertTrue(Arrays.equals(results1, RadixSort_MSD_partition.sort(input1)));
		
		
		int[] input2= {1};
		int[] results2= {1};
		assertTrue(Arrays.equals(results2, RadixSort_MSD_partition.sort(input2)));
		
		int[] input3= {2, 1};
		int[] results3= {1, 2};
		assertTrue(Arrays.equals(results3, RadixSort_MSD_partition.sort(input3)));
	}
	
	@Test
	public void testMSD2(){
		int[] input=setup(1000);
		int[] results=RadixSort_MSD_partition.sort(input);
		for (int i=0; i<results.length; i++){
			assertThat(results[i], equalTo(i));
		}
		
	}


	public int[] setup(int inputLen) {
		int[] input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
		return input;
	}
}
