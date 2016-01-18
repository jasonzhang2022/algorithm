package jason.algorithm.recursion;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

import org.junit.Test;





public class BinaryTreePreorder {
	
	public <T> void recursive(TreeNode<T> root, Consumer<T> consumer){
		if (root!=null){
			consumer.accept(root.val);
		}
		if (root.left!=null){
			recursive(root.left, consumer);
		}
		if (root.right!=null){
			recursive(root.right, consumer);
		}
	}
	
	public  TreeNode<Integer> setupTree(){
		TreeNode<Integer> root=new TreeNode<>(10);
		root.left=new TreeNode<>(5);
		root.right=new TreeNode<>(3);
		
		root.left.left=new TreeNode<>(2);
		root.left.right=new TreeNode<>(1);
		
		root.right.left=new TreeNode<>(6);
		root.right.right=new TreeNode<>(4);
		return root;
	}
	
	@Test
	public void testPreorderTraversal(){
		TreeNode<Integer> root=setupTree();
		int[] preorders={10, 5, 2, 1, 3, 6, 4};
		
		List<Integer> results=new ArrayList<>(preorders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
	
		recursive(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
		results.clear();
		recursive(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
	
	}
	
	
	public <T> void genericIterative(TreeNode<T> root, Consumer<T> consumer){
		
		if (root==null){
			return;
		}
	
		Stack<TreeNode<T>> stack=new Stack<>();
		
		TreeNode<T> current=null;
		TreeNode<T> prev=null;
		
		stack.push(root);
		while (!stack.isEmpty()){
			current=stack.peek();
			
			//case 1: moved here from parent
			if (prev==null || prev.left==current || prev.right==current){
				consumer.accept(current.val);
				if (current.left!=null){
					stack.push(current.left);
				} else if (current.right!=null){
					stack.push(current.right);
				} else {
					stack.pop();
				}
				prev=current;
				continue;
			}
			
			//move to sibling
			if (prev==current.left){
				if (current.right!=null){
					stack.push(current.right);
				} else {
					stack.pop();
				}
				prev=current;
				continue;
			}
			
			//move to parent
			if (prev==current.right){
				stack.pop();
				prev=current;
				continue;
			}
		}
	}

	@Test
	public void testGenericIterativePreorderTraversal(){
		TreeNode<Integer> root=setupTree();
		int[] preorders={10, 5, 2, 1, 3, 6, 4};
		
		List<Integer> results=new ArrayList<>(preorders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
	
		genericIterative(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
		results.clear();
		genericIterative(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
	}
	
	/**
	 * Recursive traversal is easy. The iterative approach is creative.
	 * 
	 * We get one element from stack, then we expand the stack by processing the element.
	 * We can pop it since we don't need it anymore.
	 * If we can't pop it (we need it later), we need to push further element and process the element at a later time
	 * 
	 * Here there is an important concept. We retrieve one node from stack, process it and push more nodes. That
	 * is here why I name the internal variable nodeToExpandStack
	 * 
	 * Please also see readme.md for analysis
	 * 
	 * @param root
	 * @return
	 */
	public <T> void enhancedIterative(TreeNode<T> root, Consumer<T> consumer){
		
		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		stack.push(root);

		while (!stack.empty()) {
			TreeNode<T> nodeToExpandStack = stack.pop();
			consumer.accept(nodeToExpandStack.val);

			if (nodeToExpandStack.right != null) {
				stack.push(nodeToExpandStack.right);
			}
			if (nodeToExpandStack.left != null) {
				stack.push(nodeToExpandStack.left);
			}

		}
	}
	
	@Test
	public void testEnhancedIterativePreorderTraversal(){
		TreeNode<Integer> root=setupTree();
		int[] preorders={10, 5, 2, 1, 3, 6, 4};
		
		List<Integer> results=new ArrayList<>(preorders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
	
		enhancedIterative(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
		results.clear();
		enhancedIterative(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
	}
	
}
