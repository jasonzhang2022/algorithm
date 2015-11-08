package jason.datastructure.tree;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 一个BST, 给你一个区间, 返回结点值全部在区间内的子树的结点个数.
比如:
2
1 3
区间 [1,2], 只有子树1, 
返回结点个数1 .
 */
public class BSTSUbTreeNode {
	
	public static class BinaryNode{
		BinaryNode leftChild;
		BinaryNode rightChild;
		int val;
		public BinaryNode(int val) {
			super();
			this.val = val;
		}
		
	}
	
	public int find(BinaryNode node, int from, int to){
		int[] inRangeSubTree=new int[1];
		find(node, from, to, inRangeSubTree);
		return inRangeSubTree[0];
	}
	
	public boolean find(BinaryNode node, int from, int to, int[] inRangeSubTree){
		if (node==null){
			return true;
		}
		
		boolean allInRangeLeft=false;
		if (node.val>=from){
			allInRangeLeft=find(node.leftChild, from, to,inRangeSubTree);
		}
		
		boolean allInRangeRight=false;
		if (node.val<=to){
			allInRangeRight=find(node.rightChild, from, to, inRangeSubTree);
		}
		
		if (allInRangeLeft && allInRangeRight && (node.val>=from&& node.val<=to)){
			inRangeSubTree[0]++;
			return true;
		}
		return false;
	}
	
	
	
	@Test
	public void test(){
		
		BinaryNode root=new BinaryNode( 2);
		root.leftChild=new BinaryNode( 1);
		root.rightChild=new BinaryNode( 3);
		assertEquals(1, find(root, 1,2));
		
	
	}

	@Test
	public void test1(){
		
		BinaryNode root=new BinaryNode( 7);
		root.leftChild=new BinaryNode( 5);
		root.rightChild=new BinaryNode(12);
		
		root.leftChild.leftChild=new BinaryNode(3);
		root.leftChild.rightChild=new BinaryNode(6);
		
		root.rightChild.leftChild=new BinaryNode(8);
		root.rightChild.rightChild=new BinaryNode(13);
		
		
		assertEquals(2, find(root, 6,9));
		
		assertEquals(3, find(root, 2,7));
		
	
	}

}
