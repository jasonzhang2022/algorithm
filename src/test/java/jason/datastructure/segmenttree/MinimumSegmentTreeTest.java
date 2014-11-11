package jason.datastructure.segmenttree;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.datastructure.Shuffler;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

public class MinimumSegmentTreeTest {

	
	
	public int scanMinimum(int[] input, int start, int end) {
		int min=Integer.MAX_VALUE;
		for (int i=start; i<=end; i++) {
			if (input[i]<min) {
				min=input[i];
			}
		}
		return min;
	}
	
	@Test
	public void test() {
		int[] input=new int[100];
		for (int i=0; i<100; i++) {
			input[i]=i;
		}
		
		Shuffler.shuffle(input);
		for (int i=0; i<10; i++) {
			System.out.print(i*10+": ");
			for (int j=0; j<10; j++) {
				System.out.print(input[i*10+j]+" ");
			}
			System.out.println("");
		}
		
		Random random=new Random(new Date().getTime());
		
		SegmentTree segmentTree=new SegmentTree(input, new MinimumProcessor());
		
		for (int i=0; i<20; i++) {
			int start=random.nextInt(100);
			int end=random.nextInt(100);
			if (start>end) {
				int temp=start;
				start=end;
				end=temp;
			}
			
			int min=scanMinimum(input, start, end);
			int stmin=segmentTree.queryRange(start, end);
			System.out.println("min :("+start+","+end+")="+min);
			assertThat(min, equalTo(stmin));
		}
		
		
	}

}
