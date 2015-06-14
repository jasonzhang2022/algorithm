package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.IdentityHashMap;

import org.junit.Test;

/*
 * 1. The path starts and ends in leaf node. Suppose the path ends with an internal node X, a->X, the 
 * path can be extended further by walking from X to one of its leaf node. 
 * 
 * 
 *  So the problem becomes find the longest path between all pair of leaf nodes.
 *  
 * 2. Suppose the LCA(least common ancestor) of the longest path is A, and two leaf nodes are a, b. The path
 * is a->A->b. 
 *  A->a and A->b are two longest paths in each of A's subtrees.  Suppose c is leaf node and in the same subtree as a, 
 *  path sum of c->A is bigger than a->A, c->A->B is bigger than a->A->b.
 * 
 * To calculate the largest path sum through internal node A, we need to find out the longest path sum at each side of the tree.
 * 
 * 
 */
public class MaximumPathSum {

	public static class BinaryTreeNode {
		BinaryTreeNode left;
		BinaryTreeNode right;
		
		int val;
		
		public boolean isLef(){
			return left==null && right==null;
		}
		
		
		
		
	}
	
	public static class NodeTracker{
		int maxLefSum; // maximum path from this node to any leaf node in left
		int maxRightSum; //maximum path from this node to any right node in right;
		
		int maxPathSum; //maximum sum
		
	}
	
	
	BinaryTreeNode root;
	
	
	
	public int find(){
		NodeTracker tracker=find(root, null);
		return tracker.maxPathSum;
	}
	
	
	//IdentityHashMap<BinaryTreeNode, NodeTracker> tracker=new IdentityHashMap<BinaryTreeNode, NodeTracker>();
	public NodeTracker find(BinaryTreeNode node, BinaryTreeNode parent){
		
		if (node.isLef()){
			NodeTracker nodetracker=new NodeTracker();
			
			nodetracker.maxLefSum=node.val;
			nodetracker.maxRightSum=node.val;
			nodetracker.maxPathSum=node.val;
			//tracker.put(node, nodetracker);
			
			return nodetracker;
		}
		
		NodeTracker nodetracker=new NodeTracker();
		
		if (node.left==null){
			NodeTracker rightTracker=find(node.right, node);
			nodetracker.maxLefSum=0;
			nodetracker.maxRightSum=Math.max(node.val+rightTracker.maxLefSum, node.val+rightTracker.maxRightSum);
			if (nodetracker.maxRightSum>=rightTracker.maxPathSum){
				nodetracker.maxPathSum=nodetracker.maxRightSum;
			} else{
				nodetracker.maxPathSum=rightTracker.maxPathSum;
			}
		}  else if (node.right==null){

			NodeTracker leftTracker=find(node.left, node);
			nodetracker.maxLefSum=Math.max(node.val+leftTracker.maxLefSum, node.val+leftTracker.maxRightSum);
			nodetracker.maxRightSum=0;
			//new maximum ptah rooted at this node bigger than the one at subtree.
			if (nodetracker.maxLefSum>=leftTracker.maxPathSum){
				nodetracker.maxPathSum=nodetracker.maxLefSum;
			} else{
				nodetracker.maxPathSum=leftTracker.maxPathSum;
			}
		} else{
			
			NodeTracker rightTracker=find(node.right, node);
			NodeTracker leftTracker=find(node.left, node);

			nodetracker.maxRightSum=Math.max(node.val+rightTracker.maxLefSum, node.val+rightTracker.maxRightSum);
			nodetracker.maxLefSum=	Math.max(node.val+leftTracker.maxLefSum, node.val+leftTracker.maxRightSum);
			nodetracker.maxPathSum=Math.max(Math.max(leftTracker.maxPathSum, rightTracker.maxPathSum), nodetracker.maxLefSum+nodetracker.maxRightSum-node.val);

		}
		//tracker.put(node, nodetracker);
		return nodetracker;
		
		
	}
	
	
	@Test
	public void testMaxSumPathInSubtree(){
		
		root=new BinaryTreeNode();
		root.val=1;
		
		BinaryTreeNode node11=new BinaryTreeNode();
		node11.val=2;
		
		BinaryTreeNode node12=new BinaryTreeNode();
		node12.val=5;
		
		root.left=node11;
		root.right=node12;
		
		BinaryTreeNode node21=new BinaryTreeNode();
		BinaryTreeNode node22=new BinaryTreeNode();
		BinaryTreeNode node23=new BinaryTreeNode();
		node21.val=3;
		node22.val=4;
		node23.val=1;
		
		node11.left=node21;
		node11.right=node22;
		node12.right=node23;
		
		
		BinaryTreeNode node31=new BinaryTreeNode();
		BinaryTreeNode node32=new BinaryTreeNode();
		node31.val=1000;
		node32.val=2000;
		node23.left=node31;
		node23.right=node32;
		
		
		
		/*
		    root(1)
		    --------------------------
		    | 		                   |
		  node11(2)	                  node12(5)
  --------------------       -------------------
  |                   |                        |
  node21(3)    node22(4)	                 node23(1)
  									--------------------------
  									|                       |
  									node31(1000)            node32(2000)
		 */
		
		int maximumSum=find();
		assertEquals(3001, maximumSum);
		
	}
	
	
	@Test
	public void testMaxSumPathExtension(){
		
		root=new BinaryTreeNode();
		root.val=1;
		
		BinaryTreeNode node11=new BinaryTreeNode();
		node11.val=2;
		
		BinaryTreeNode node12=new BinaryTreeNode();
		node12.val=5;
		
		root.left=node11;
		root.right=node12;
		
		BinaryTreeNode node21=new BinaryTreeNode();
		BinaryTreeNode node22=new BinaryTreeNode();
		BinaryTreeNode node23=new BinaryTreeNode();
		node21.val=500;
		node22.val=4;
		node23.val=1;
		
		node11.left=node21;
		node11.right=node22;
		node12.right=node23;
		
		
		BinaryTreeNode node31=new BinaryTreeNode();
		BinaryTreeNode node32=new BinaryTreeNode();
		node31.val=1000;
		node32.val=500;
		node23.left=node31;
		node23.right=node32;
		
		
		
		/*
		    root(1)
		    --------------------------
		    | 		                   |
		  node11(2)	                  node12(5)
  --------------------       -------------------
  |                   |                        |
  node21(500)    node22(4)	                 node23(1)
  									--------------------------
  									|                       |
  									node31(1000)            node32(500)
		 */
		
		int maximumSum=find();
		assertEquals(1509, maximumSum);
		
	}
	
	
	@Test
	public void testMaxSumPathSingleBranch(){
		
		root=new BinaryTreeNode();
		root.val=500;
		
		BinaryTreeNode node11=new BinaryTreeNode();
		node11.val=2;
		root.left=node11;
				
				
		BinaryTreeNode node21=new BinaryTreeNode();
		BinaryTreeNode node22=new BinaryTreeNode();
		node21.val=500;
		node22.val=400;

		node11.left=node21;
		node11.right=node22;
		
		/*
		    root(500)
		    --------------------------
		    | 		                  
		  node11(2)	               
  --------------------       
  |                   |                        
  node21(500)    node22(400)	                
  									
		 */
		
		int maximumSum=find();
		assertEquals(1002, maximumSum);
		
	}
	
	
	
	//solution from here: http://www.programcreek.com/2013/02/leetcode-binary-tree-maximum-path-sum-java/
	public int maxPathSum(BinaryTreeNode root) {
		int max[] = new int[1]; 
		max[0] = Integer.MIN_VALUE;
		calculateSum(root, max);
		return max[0];
	}
	 
	public int calculateSum(BinaryTreeNode root, int[] max) {
		if (root == null)
			return 0;
	 
		int left = calculateSum(root.left, max);
		int right = calculateSum(root.right, max);
	 
		int current = Math.max(root.val, Math.max(root.val + left, root.val + right));
	 
		max[0] = Math.max(max[0], Math.max(current, left + root.val + right));
	 
		return current;
	}
	
	@Test
	public void testMaxSumPathInSubtreeWebSitesolution(){
		
		root=new BinaryTreeNode();
		root.val=1;
		
		BinaryTreeNode node11=new BinaryTreeNode();
		node11.val=2;
		
		BinaryTreeNode node12=new BinaryTreeNode();
		node12.val=5;
		
		root.left=node11;
		root.right=node12;
		
		BinaryTreeNode node21=new BinaryTreeNode();
		BinaryTreeNode node22=new BinaryTreeNode();
		BinaryTreeNode node23=new BinaryTreeNode();
		node21.val=3;
		node22.val=4;
		node23.val=1;
		
		node11.left=node21;
		node11.right=node22;
		node12.right=node23;
		
		
		BinaryTreeNode node31=new BinaryTreeNode();
		BinaryTreeNode node32=new BinaryTreeNode();
		node31.val=1000;
		node32.val=2000;
		node23.left=node31;
		node23.right=node32;
		
		
		
		/*
		    root(1)
		    --------------------------
		    | 		                   |
		  node11(2)	                  node12(5)
  --------------------       -------------------
  |                   |                        |
  node21(3)    node22(4)	                 node23(1)
  									--------------------------
  									|                       |
  									node31(1000)            node32(2000)
		 */
		
		int maximumSum=maxPathSum(root);
		assertEquals(3001, maximumSum);
		
	}
	
}
