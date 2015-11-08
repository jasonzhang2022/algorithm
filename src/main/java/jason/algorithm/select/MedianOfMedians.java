package jason.algorithm.select;

import static jason.algorithm.Swaper.swap;
import static org.junit.Assert.assertTrue;
import jason.algorithm.Shuffler;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
public class MedianOfMedians {

	public static int median(int[] input) {
		Random random=new Random(new Date().getTime());
		return median(input, 0, input.length-1, random);
	}
	
	/**
	 * @param input
	 * @param start
	 * @param end inclusive
	 * @return
	 */
	public static int median(int[] input, int start, int end, Random random) {
		//inclusive
		int endIndex=(end-start)/5;
		if (endIndex==0) {
			//has less than five elements
			Arrays.sort(input, start, end+1);
			return input[ (start+end+1)/2];
		}
		for (int i=0; i<=endIndex; i++) {
			int segmentStart=i*5+start;
			int  segmentend=(i+1)*5+start-1;
			int kth=3;
			if (segmentend>end) {
				segmentend=end;
				kth=(segmentend-segmentStart)/2+1;
				if (kth+segmentend>segmentend) {
					kth--;
				}
			}
			
			QuickSelect.select(input, segmentStart, segmentend, kth, random);
			swap(input, i, segmentStart+kth-1);
		}
		
		//when codes come here, all medians are in the region of start ->
		return median(input, start, endIndex, random);
	}
	
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

}
