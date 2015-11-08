package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class BInarySearchFirstIndex {
	
	
	public int BST(int[] num, int x){
		return BST(num, 0, num.length-1, x);
	}
	
	protected int BST(int[] num, int offset, int end, int x){
		if (end==offset){
			if (num[offset]==x){
				return offset;
			} else{
				return -1;
			}
		}
		
		int middle=offset+(end-offset)/2;
		if (num[middle]>=x){
			return BST(num, offset, middle, x);
		} else{
			return BST(num, middle+1, end, x);
		}
	}
	
	
	@Test
	public void test(){
		assertEquals(0, BST(new int[]{5,5,5,5,5}, 5));
	}
	@Test
	public void test1(){
		assertEquals(1, BST(new int[]{0,5,5,5,5}, 5));
	}
	@Test
	public void test2(){
		assertEquals(2, BST(new int[]{0,1,5,5,5}, 5));
	}
	@Test
	public void test3(){
		assertEquals(3, BST(new int[]{0,1,2,5,5}, 5));
	}
	@Test
	public void test4(){
		assertEquals(4, BST(new int[]{0,1,2,3,5}, 5));
	}

	@Test
	public void test5(){
		assertEquals(4, BST(new int[]{0,1,2,3,5, 6}, 5));
	}
}
