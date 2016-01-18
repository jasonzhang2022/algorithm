package jason.algorithm.recursion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.junit.Test;

//http://www.programcreek.com/2013/01/leetcode-convert-sorted-list-to-binary-search-tree-java/
public class ConvertSortedLinkedListIntoBalancedBST {
	
	public TreeNode<Integer> buildBST(List<Integer> input){
		int size=input.size();
		ListIterator<Integer> iterator=input.listIterator();
		return buildBSTSub(iterator, 0, size-1);
	}
	
	public TreeNode<Integer> buildBSTSub(ListIterator<Integer> iterator, int from, int to){
		if (from==to){
			return new TreeNode<Integer>(iterator.next());
		}
		
		int middle=(from+to)/2;
		TreeNode<Integer> root=new TreeNode<>(0);
		if (from<middle){
			root.left=buildBSTSub(iterator, from, middle-1);
		}
		root.val=iterator.next();
		if (to>middle){
			root.right=buildBSTSub(iterator, middle+1, to);
		}
		return root;
	}

	@Test
	public void test(){
		int[] input={1,2,3,4,5,6,7};
		List<Integer> list=Arrays.stream(input).boxed().collect(Collectors.toList());
		TreeNode<Integer> root=buildBST(list);
		assertThat(root.toString(), equalTo("4(2(1,3),6(5,7))"));
	}
	@Test
	public void test1(){
		int[] input={1};
		
		List<Integer> list=Arrays.stream(input).boxed().collect(Collectors.toList());
		TreeNode<Integer> root=buildBST(list);
		assertThat(root.toString(), equalTo("1"));
	}
	@Test
	public void test2(){
		int[] input={1,2};
		
		List<Integer> list=Arrays.stream(input).boxed().collect(Collectors.toList());
		TreeNode<Integer> root=buildBST(list);
		assertThat(root.toString(), equalTo("1(,2)"));
	}
	@Test
	public void test3(){
		int[] input={1,2,3};
		
		List<Integer> list=Arrays.stream(input).boxed().collect(Collectors.toList());
		TreeNode<Integer> root=buildBST(list);
		assertThat(root.toString(), equalTo("2(1,3)"));
	}
	
	@Test
	public void test4(){
		int[] input={1,2,3,4};
		
		List<Integer> list=Arrays.stream(input).boxed().collect(Collectors.toList());
		TreeNode<Integer> root=buildBST(list);
		assertThat(root.toString(), equalTo("2(1,3(,4))"));
	}
}
