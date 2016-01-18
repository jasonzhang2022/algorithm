package jason.algorithm.recursion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

//http://www.programcreek.com/2013/01/leetcode-convert-sorted-array-to-binary-search-tree-java/
public class ConvertSortedArrayIntoBalancedBST {

	public TreeNode<Integer> buildBST(int[] input){
		return buildBST(input, 0, input.length-1);
	}
	
	
	public TreeNode<Integer> buildBST(int[] input, int from, int to){
		if (from==to){
			return new TreeNode<Integer>(input[from]);
		}
		
		int root=(from+to)/2;
		TreeNode<Integer> rootNode=new TreeNode<>(input[root]);
		if (from<root){
			rootNode.left=buildBST(input,from, root-1);
		} 
		if (to>root){
			rootNode.right=buildBST(input, root+1, to);
		}
		return rootNode;
		
	}
	@Test
	public void test(){
		int[] input={1,2,3,4,5,6,7};
		
		TreeNode<Integer> root=buildBST(input);
		assertThat(root.toString(), equalTo("4(2(1,3),6(5,7))"));
	}
	@Test
	public void test1(){
		int[] input={1};
		
		TreeNode<Integer> root=buildBST(input);
		assertThat(root.toString(), equalTo("1"));
	}
	@Test
	public void test2(){
		int[] input={1,2};
		
		TreeNode<Integer> root=buildBST(input);
		assertThat(root.toString(), equalTo("1(,2)"));
	}
	@Test
	public void test3(){
		int[] input={1,2,3};
		
		TreeNode<Integer> root=buildBST(input);
		assertThat(root.toString(), equalTo("2(1,3)"));
	}
	
	@Test
	public void test4(){
		int[] input={1,2,3,4};
		
		TreeNode<Integer> root=buildBST(input);
		assertThat(root.toString(), equalTo("2(1,3(,4))"));
	}
	
	
	//use the same approach as List
	public TreeNode<Integer> buildBST1(int[] input){
		int[] index={0};
		return buildBST1(input, 0, input.length-1, index);
	}
	
	
	public TreeNode<Integer> buildBST1(int[] input, int from, int to, int[] index){
		if (from==to){
			return new TreeNode<Integer>(input[index[0]++]);
		}
		
		int root=(from+to)/2;
		TreeNode<Integer> rootNode=new TreeNode<>(0);
		if (from<root){
			rootNode.left=buildBST1(input,from, root-1, index);
		} 
		rootNode.val=input[index[0]++];
		if (to>root){
			rootNode.right=buildBST1(input, root+1, to, index);
		}
		return rootNode;
		
	}
	
	@Test
	public void testA(){
		int[] input={1,2,3,4,5,6,7};
		
		TreeNode<Integer> root=buildBST1(input);
		assertThat(root.toString(), equalTo("4(2(1,3),6(5,7))"));
	}
	@Test
	public void testA1(){
		int[] input={1};
		
		TreeNode<Integer> root=buildBST1(input);
		assertThat(root.toString(), equalTo("1"));
	}
	@Test
	public void testA2(){
		int[] input={1,2};
		
		TreeNode<Integer> root=buildBST1(input);
		assertThat(root.toString(), equalTo("1(,2)"));
	}
	@Test
	public void testA3(){
		int[] input={1,2,3};
		
		TreeNode<Integer> root=buildBST1(input);
		assertThat(root.toString(), equalTo("2(1,3)"));
	}
	
	@Test
	public void testA4(){
		int[] input={1,2,3,4};
		
		TreeNode<Integer> root=buildBST1(input);
		assertThat(root.toString(), equalTo("2(1,3(,4))"));
	}
}
