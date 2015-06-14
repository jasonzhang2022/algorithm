package jason.datastructure.segmenttree;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.function.IntBinaryOperator;

import org.junit.Test;

public class CompactSegmentTree {
	
	
	
	
	@FunctionalInterface
	static interface IntMapper {
		public int map(int input);
	}
	
	
	
	IntMapper indexMapper;
	IntMapper levelMapper;
	int[] result;
	
	public  int[] build (int[] input, IntBinaryOperator op){
	
		/*
		 how long is the result array
		 The result array 0....x, x+1, x+2, ..+x+n
			internal nodes=n-1, 
			total length =n-1+n=2n-1
		 
		 Suppose the tree height is h (first level is zero)
		 
		 then 2^(h-1)<n<=2^h.
		 
		 If n=2^h, this is a full binary tree, each branch has half the node.
		 The leave node from left to right is from input[0] to input[n-1];
		 
		 If n<2^h, some node is at h-1 level, 
		 
		 Suppose n<2^(h-1)/2+2^(h)/2,  (Can not fill the half left tree fully), the right tree at level h-1 must be fully filled.
		 	so right tree has r=2^(h-2) leaf nodes, left has n-r nodes;
		 suppose n>2^(h-1)/2+2^(h)/2, (left is fully filled. right tree nodes at both h and h-1 level)
		 	left tree has node: l=2^h/2=2^(h-1).
		 	
		 	right tree at level h-1 must be fully filled for this happen.
		 		suppose right tree at level h-1 has x node, x<=2^(h-1)/2
		 		for each node disappear at h-1 level, two nodes can be added at h level
		 		2^(h-1)/2=2^(h-2). y=(2^(h-2)-x)*2, the last node may not be fully filled. 
		 		So h level could have y or y-1;
		 		y+x=n-l;
		 		x=n-1-y;
		 	Once we know x, we can move those nodes starts from results[n];
		*/
		int n=input.length;
		int[] result=new int[n*2-1];
		
		
		//step 1: calculate height.
		int h=0;
		for (int i=0; i<32; i++){
			int nodes=1<<i;
			if (nodes>=n){
				h=i;
				break;
			}
		}
		int finalHeight=h;
		if (1<<h==n){
			System.arraycopy(input, 0, result, n-1, n);
			indexMapper=(x)->n-1+x;
			levelMapper=(i)->finalHeight;
		} else{
			/*
			 Suppose n<2^(h-1)/2+2^(h)/2,  (Can not fill the half left tree fully), the right tree at level h-1 must be fully filled.
			 	so right tree has r=2^(h-2) leaf nodes, left has n-r nodes;
			 	
			 	2^(h-1)/2+2^(h)/2=2(h-2)+2^(h-1)
			 	*/
			int temp=(1<<(h-2))+(1<<(h-1));
			if (n<=temp){
				int rightNodes=1<<(h-2);
				int leftNodes=n-rightNodes;
				
				int rightIndex=n-rightNodes;
				System.arraycopy(input, rightIndex, result, n-1, rightNodes);
				System.arraycopy(input, 0, result, n-1+rightNodes, leftNodes);
				indexMapper=(i)->{
					if (i>=rightIndex){
						return n-1-rightIndex+i;
					} else{
						return n-1+rightNodes+i;
					}
				};
				levelMapper=(i)->{
					if (i>=rightIndex){
						return finalHeight-1;
					} else{
						return finalHeight;
					}	
				};
			} else{
				
				/*
				 * suppose n>2^(h-1)/2+2^(h)/2, (left is fully filled. right tree nodes at both h and h-1 level)
		 	left tree has node: l=2^h/2=2^(h-1).
		 	
		 	right tree at level h-1 must be fully filled for this happen.
		 		suppose right tree at level h-1 has x node, x<=2^(h-1)/2
		 		for each node disappear at h-1 level, two nodes can be added at h level
		 		2^(h-1)/2=2^(h-2). y=(2^(h-2)-x)*2, the last node may not be fully filled. 
		 		So h level could have y or y-1;
		 		y+x=n-l;
		 		x=n-1-y;
		 	Once we know x, we can move those nodes starts from results[n];
				 */
				int l=1<<(h-1);
				int r=n-l;
				
				int halfNodesAtH_1=1<<(h-2);
				//(halfNodesAtLevelHMinusOne -x)*2+x=r 
				int x=halfNodesAtH_1*2-r;
				int nodesAtH_1=x;
				int nodesAtH=n-nodesAtH_1;
				
				int rightIndex=n-nodesAtH_1;
				
				
				System.arraycopy(input, rightIndex, result, n-1, nodesAtH_1);
				System.arraycopy(input, 0, result, n-1+nodesAtH_1, nodesAtH);
				
				
				indexMapper=(i)->{
					if (i>=rightIndex){
						return n-1-rightIndex+i;
					} else{
						return n-1+nodesAtH_1+i;
					}
				};
				levelMapper=(i)->{
					if (i>=rightIndex){
						return finalHeight-1;
					} else{
						return finalHeight;
					}	
				};
			}
		}
		
		
		fillTree(result, 0, op);
		this.result=result;
		return result;
		
		
	}
	
	public int fillTree(int[] result, int nodeIndex, IntBinaryOperator op){
		if (nodeIndex*2+1>=result.length){
			return result[nodeIndex];
		}
		int left=fillTree(result,  nodeIndex*2+1, op);
		int right=fillTree(result, nodeIndex*2+2, op);
		result[nodeIndex]=op.applyAsInt(left, right);
		return result[nodeIndex];
	}
	

	public int getSum(int from, int to){
		
		
		int fromIndex=indexMapper.map(from);
		int toIndex=indexMapper.map(to);
		
		int fromLevel=height(fromIndex);
		int toLevel=height(toIndex);
		
		System.out.printf("from  %d= %d , to  %d=:%d, level %d, %d\n", fromIndex, height(fromIndex), toIndex, height(toIndex), fromLevel, toLevel);
		
		int leftDelta=0;
		int rightDelta=0;
		
		
		
		int fromParent=fromIndex;
		int toParent=toIndex;
		
		//move from one level up
		if (fromLevel>toLevel){
			fromParent=(fromIndex-1)/2;
			if (fromParent*2+1!=fromIndex){
				//we move up from right node.
				leftDelta=result[fromParent*2+1];
			}
		}
		
		while (fromParent!=toParent){
			int newFromParent=(fromParent-1)/2;
			if (newFromParent*2+1!=fromParent){
				leftDelta+=result[newFromParent*2+1];
			}
			fromParent=newFromParent;
			
			int newToParent=(toParent-1)/2;
			if (newToParent*2+2!=toParent && newToParent*2+2<result.length){
				rightDelta+=result[newToParent*2+2];
			}
			toParent=newToParent;
			System.out.printf("from level: %d= %d , to level%d=:%d\n", fromParent, height(fromParent), toParent, height(toParent));
		}
		
		return result[fromParent]-leftDelta-rightDelta;
	}
	
	
	/*
	 * Test total sum is correct
	 */
	@Test
	public void test(){
		
		int[] input=null;
		
		int[] testLen={1,2,3,4,5,6,7,8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
		
		for (int i=0; i<testLen.length; i++){
			input=new int[testLen[i]];
			int sum=0;
			for (int j=0; j<testLen[i]; j++){
				input[j]=j;
				sum+=j;
			}
			CompactSegmentTree compactSegmentTree=new CompactSegmentTree();
			int[] result=compactSegmentTree.build(input, (x, y)->x+y);
			assertEquals(sum, result[0]);
		}
	}
	
	public int height (int index){
		int h=0;
		while (index>=((1<<h)-1)){
			h++;
		}
		return h-1;
		
		
	}
	
	/*
	 * Test range minimal query
	 */
	@Test
	public void testRMQ100(){
		
		int[] input=new int[100];
		for (int i=1; i<=100; i++){
			input[i-1]=i;
		}
		CompactSegmentTree compactSegmentTree=new CompactSegmentTree();
		compactSegmentTree.build(input, (x, y)->x+y);
		
		Random rand=new Random();
		for (int i=0; i<30; i++){
			int left=rand.nextInt(50);
			int right=rand.nextInt(50)+50;
			
			int sum=0;
			for (int j=left; j<=right; j++){
				sum+=input[j];
			}
			int sum1=compactSegmentTree.getSum(left, right);
			if (sum!=sum1){
				System.out.printf("%d, %d\n", left, right);
			}
			assertEquals(sum, sum1);
			
		}
	
		
	}
	/*
	 * Test range minimal query
	 */
	@Test
	public void testRMQ64(){
		
		int[] input=new int[64];
		for (int i=1; i<=64; i++){
			input[i-1]=i;
		}
		CompactSegmentTree compactSegmentTree=new CompactSegmentTree();
		compactSegmentTree.build(input, (x, y)->x+y);
		
		Random rand=new Random();
		for (int i=0; i<30; i++){
			int left=rand.nextInt(32);
			int right=rand.nextInt(32)+32;
			
			int sum=0;
			for (int j=left; j<=right; j++){
				sum+=input[j];
			}
			int sum1=compactSegmentTree.getSum(left, right);
			if (sum!=sum1){
				System.out.printf("%d, %d\n", left, right);
			}
			assertEquals(sum, sum1);
			
		}
	
		
	}

	/*
	 * Test range minimal query
	 */
	@Test
	public void testRMQ60(){
		
		int[] input=new int[60];
		for (int i=1; i<=60; i++){
			input[i-1]=i;
		}
		CompactSegmentTree compactSegmentTree=new CompactSegmentTree();
		compactSegmentTree.build(input, (x, y)->x+y);
		
		Random rand=new Random();
		for (int i=0; i<30; i++){
			int left=rand.nextInt(32);
			int right=rand.nextInt(32)+28;
			
			int sum=0;
			for (int j=left; j<=right; j++){
				sum+=input[j];
			}
			int sum1=compactSegmentTree.getSum(left, right);
			if (sum!=sum1){
				System.out.printf("%d, %d\n", left, right);
			}
			assertEquals(sum, sum1);
			
		}
	
		
	}

}
