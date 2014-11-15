package jason.datastructure.rmq;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.datastructure.Shuffler;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

public class RMQTest {
	
	int inputLen=1000;
	@Test
	public void test() {
		int[] input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		
		Shuffler.shuffle(input);
		Random random=new Random(new Date().getTime());
	
		RMQ scanrmq=new ScanRMQ();
		scanrmq.init(input);
		
		RMQ dpRmq=new DpRMQ();
		dpRmq.init(input);
		
		RMQ sqrtRmq=new SqrtPartitionRMQ();
		sqrtRmq.init(input);
		
		RMQ sparseTableRMQ=new SparseTableRMQ();
		sparseTableRMQ.init(input);
		
		RMQ segmentTreeRmq=new SegmentTreeRMQ();
		segmentTreeRmq.init(input);
		for (int i=0; i<50; i++) {
			int start=random.nextInt(inputLen);
			int end=random.nextInt(inputLen);
			if (start>end) {
				int temp=start;
				start=end;
				end=temp;
			}
			int min=scanrmq.minimum(start, end);
			
			assertThat(dpRmq.minimum(start, end), equalTo(min));
			assertThat(sqrtRmq.minimum(start, end), equalTo(min));
			assertThat(sparseTableRMQ.minimum(start, end), equalTo(min));
			assertThat(segmentTreeRmq.minimum(start, end), equalTo(min));

		}
		
		
	}
	
	
	@Test
	public void TestSparseTable() {
		int[] input= {20, 30, 15, 17, 3,4,2,1};
		
		RMQ scanrmq=new ScanRMQ();
		scanrmq.init(input);
	
		RMQ sparseTableRMQ=new SparseTableRMQ();
		sparseTableRMQ.init(input);
		
		int start=0;
		int end=6;
		assertThat(scanrmq.minimum(start, end), equalTo(2));
		assertThat(sparseTableRMQ.minimum(start, end), equalTo(2));
		
		
	}

}
