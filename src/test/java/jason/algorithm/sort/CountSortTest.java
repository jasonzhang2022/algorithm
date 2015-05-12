package jason.algorithm.sort;

import static org.junit.Assert.*;
import jason.algorithm.sort.CountSort;

import java.util.Arrays;

import org.junit.Test;

public class CountSortTest {

	@Test
	public void test() {
		int[] inputs= {92, 32, 44, 55,77,66, 33, 22};
		int[] results= {22,32,33,44,55,66,77,92};
		assertTrue(Arrays.equals(CountSort.countSort(inputs, 20, 95), results));
	}
	
	@Test
	public void testCountSort1() {
		int[] inputs= {92, 32, 44, 55,77,66, 33, 22};
		int[] results= {22,32,33,44,55,66,77,92};
		assertTrue(Arrays.equals(CountSort.sort(inputs), results));
	}

}
