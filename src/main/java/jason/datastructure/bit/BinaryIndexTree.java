package jason.datastructure.bit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
public class BinaryIndexTree {
	
	int[] tree;
	int maxIndex;
	BinaryIndexTree(int inputLen){
		tree=new int[inputLen+1];
		maxIndex=inputLen;
	}
	
	public int readSumTo(int index){
		index++;
		int sum=0;
		while (index>0){
			sum+=tree[index];
			index-=index&-index;
		}
		return sum;
	}
	
	public void update(int index, int val){
		index++;
		while (index<=maxIndex){
			tree[index]+=val;
			index+=index&-index;
		}
	}
	
	public int readValueAt(int index){
		index++;
		int sum=tree[index];
		int z=index-(index&-index);
		index--;
		
		while (index!=z){
			sum-=tree[index];
			index-=index&-index;
		}
		return sum;
	}
	
	
	public int findLastBitWith1(int i){
		int k=1;
		
		while ((i=i>>1)!=0){
			k=k<<1;
		}
		return k;
	}
	
	public int findIndexForSum(int sum){
		int bitMask=findLastBitWith1(maxIndex);
		int index=0;
		
		while (bitMask>0 && index<=maxIndex) {
			int t=index+bitMask;
			if(tree[t]<=sum){
				sum-=tree[t];
				index=t;
			}
			bitMask=bitMask>>1;
		}
		if (sum==0){
			return index-1;
		} 
		return -1;
	}
	
	public static class TestCase {
		
		
		
		@Test
		public void test(){
			BinaryIndexTree bit=new BinaryIndexTree(16);
			int[] input=new int[]{1,	0,	2,	1,	1,	3,	0,	4,	2,	5,	2,	2,	3,	1,	0,	2};
			int[] sum=new int[input.length];
			sum[0]=input[0];
			for (int i=1; i<input.length; i++) {
				sum[i]=input[i]+sum[i-1];
			}
			for (int i=0; i<input.length; i++){
				bit.update(i, input[i]);
			}
			
			for (int i=0; i<input.length; i++){
				assertEquals(input[i], bit.readValueAt(i));
			}
			
			for (int i=0; i<input.length; i++){
				assertEquals(sum[i], bit.readSumTo(i));
			}
			assertEquals(9, bit.findIndexForSum(sum[9]));
		}
	}
	
	
	

}
