package jason.algorithm.recursion;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

import org.junit.Test;


public class BinaryTreeInorder {
	public <T> void recursive(TreeNode<T> root, Consumer<T> consumer){
		if (root.left!=null){
			recursive(root.left, consumer);
		}
		consumer.accept(root.val);
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
	public void testRecursive(){
		TreeNode<Integer> root=setupTree();
		int[] orders={2, 5, 1, 10, 6, 3, 4};
		
		List<Integer> results=new ArrayList<>(orders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
	
		recursive(root, consumer);
		
		for (int i=0; i<orders.length; i++){
			assertEquals(orders[i], results.get(i).intValue());
		}
		results.clear();
		recursive(root, consumer);
		
		for (int i=0; i<orders.length; i++){
			assertEquals(orders[i], results.get(i).intValue());
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
				if (current.left!=null){
					stack.push(current.left);
				} else if (current.right!=null){
					stack.push(current.right);
				} else {
					consumer.accept(current.val);
					stack.pop();
				}
				prev=current;
				continue;
			}
			
			//move to sibling
			if (prev==current.left){
				consumer.accept(current.val);
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
	public void testGenericIterative(){
		TreeNode<Integer> root=setupTree();
		int[] orders={2, 5, 1, 10, 6, 3, 4};
		
		List<Integer> results=new ArrayList<>(orders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
	
		genericIterative(root, consumer);
		
		for (int i=0; i<orders.length; i++){
			assertEquals(orders[i], results.get(i).intValue());
		}
		results.clear();
		genericIterative(root, consumer);
		
		for (int i=0; i<orders.length; i++){
			assertEquals(orders[i], results.get(i).intValue());
		}
	
	}
	
	/**
	 * Preorder gets all its information from stack. 
	 * inorder has a nodeToExpandStack besides the stack. nodeToExpandStak serves as 
	 * the same purpose as prev.
	 * @param root
	 * @param consumer
	 */
	public <T> void enhancedIterative(TreeNode<T> root, Consumer<T> consumer) {

		if (root == null)
			return;
		TreeNode<T> nodeToExpandStack = root;
		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		while (!stack.empty() || nodeToExpandStack != null) {
			
			// if it is not null, push to stack
			// and go down the tree to left
			if (nodeToExpandStack != null) {
				stack.push(nodeToExpandStack);
				nodeToExpandStack = nodeToExpandStack.left;
	
				// if no left child
				// pop stack, process the node
				// then let p point to the right
			} else {
				TreeNode<T> t = stack.pop();
				consumer.accept(t.val);
				nodeToExpandStack = t.right;
			}
		}
	}
	
	@Test
	public void testSnhancedIterative(){
		TreeNode<Integer> root=setupTree();
		int[] orders={2, 5, 1, 10, 6, 3, 4};
		
		List<Integer> results=new ArrayList<>(orders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
	
		enhancedIterative(root, consumer);
		
		for (int i=0; i<orders.length; i++){
			assertEquals(orders[i], results.get(i).intValue());
		}
		results.clear();
		enhancedIterative(root, consumer);
		
		for (int i=0; i<orders.length; i++){
			assertEquals(orders[i], results.get(i).intValue());
		}
	}
}
