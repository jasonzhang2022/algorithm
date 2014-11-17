package jason.datastructure.segmenttree;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.Shuffler;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

public class SumSegmentTreeTest {

	
	
	public int scanSum(int[] input, int start, int end) {
		int sum=0;
		for (int i=start; i<=end; i++) {
			sum+=input[i];
		}
		return sum;
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
		
		SegmentTree segmentTree=new SegmentTree(input, new SumProcessor());
		
		for (int i=0; i<20; i++) {
			int start=random.nextInt(100);
			int end=random.nextInt(100);
			if (start>end) {
				int temp=start;
				start=end;
				end=temp;
			}
			
			int sum=scanSum(input, start, end);
			int stsum=segmentTree.queryRange(start, end);
			System.out.println("sum :("+start+","+end+")="+sum);
			assertThat(sum, equalTo(stsum));
		}
		
		
	}

}
