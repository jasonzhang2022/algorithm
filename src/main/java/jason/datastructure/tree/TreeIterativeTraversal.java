package jason.datastructure.tree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

//http://www.programcreek.com/2012/12/leetcode-solution-for-binary-tree-preorder-traversal-in-java/
public class TreeIterativeTraversal<T> {

	public static class TreeNode<T> {
		T val;
		TreeNode<T> left;
		TreeNode<T> right;

		TreeNode(T x) {
			val = x;
		}
	}
	
	
	
	
	
	
	
	
	/*
	 * The key is keeping two context nodes:
	 * current node: the node we need to process.
	 * previous node: the node we process before.
	 * 
	 * By keep these two nodes, we know our traversal route
	 */
	public  void preorderTraversal1(TreeNode<T> node, Consumer<T> consumer){
		if (node==null){
			return;
		}
		
		TreeNode<T> prev=null; //node processed in previous cycle
		TreeNode<T> next=null; //node to be processed in next cycle
		
		Stack<TreeNode<T>> stack=new Stack<>();
		
		
		//initialization
		consumer.accept(node.val);
		stack.push(node);
		prev=node;
		
		if (node.left!=null){
			next=prev.left;
		} else {
			next=prev.right;
			//if null, we will be stopped in the while loop
		}
		
		/*
		 * In each cycle, we process current and prepare prev and current for next cycle
		 * if current is null, we have nothing to process
		 * all element in stack are already processed.
		 */
		while (next!=null){
			TreeNode<T> current=next;
			
			//prev is not null,  and current is not null, we have complete context
			//case 1: walk down to left
			//case 2: walk down to right
			if (current==prev.left || current==prev.right){
				//we come here from prev->left or prev->right;
				consumer.accept(current.val);
				
				//decide next step.
				if (current.left!=null){
					stack.push(current);
					next=current.left;
					prev=current;
				} else if (current.right!=null){
					stack.push(current);
					next=current.right;
					prev=current;
				} else {
					//we need to walk up from left/right side.
					next=prev;
					prev=current; //we don't pop up.
					//TODO check root case
				}	
			}else if (current.left==prev){
				//case 3: walk up from left
				//we need to output right side.
				if (current.right!=null){
					prev=current;
					next=current.right;
					//go to case 2:
				} else {
					//no right child, we need go up
					prev=current;
					stack.pop(); //this should be current;
					
					if (!stack.isEmpty()){
						next=stack.pop();
					} else{
						next=null;
					}
				}
			} else if (current.right==prev){
				//case 4:  walk up from right child
				prev=current;
				stack.pop();
				if (!stack.isEmpty()){
					next=stack.pop();
				} else{
					next=null;
				}
			}
		}
	}
	
	
	
	
	

	/**
	 * Recursive traversal is easy. The iterative approach is creative.
	 * 
	 * We get one element from stack, then we expand the stack by processing the element.
	 * We can pop it since we don't need it anymore.
	 * If we can't pop it (we need it later), we need to push further element and process the element at a later time
	 * 
	 * @param root
	 * @return
	 */
	public void preorderTraversal(TreeNode<T> root, Consumer<T> consumer) {

		if (root == null)
			return;

		// The stack represents to node to be processed
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

	
	public void inorderTraversal(TreeNode<T> root, Consumer<T> consumer) {

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

	public void postorderTraversal(TreeNode<T> root, Consumer<T> consumer) {

		if (root == null)
			return;

		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		stack.push(root);

		TreeNode<T> prev = null;
		while (!stack.empty()) {
			TreeNode<T> curr = stack.peek();

			// go down the tree.
			// check if current node is leaf, if so, process it and pop stack,
			// otherwise, keep going down
			if (prev == null || prev.left == curr || prev.right == curr) {
				// prev == null is the situation for the root node
				if (curr.left != null) {
					stack.push(curr.left);
				} else if (curr.right != null) {
					stack.push(curr.right);
				} else {
					stack.pop();
					consumer.accept(curr.val);
				}

				// go up the tree from left node
				// need to check if there is a right child
				// if yes, push it to stack
				// otherwise, process parent and pop stack
			} else if (curr.left == prev) {
				if (curr.right != null) {
					stack.push(curr.right);
				} else {
					stack.pop();
					consumer.accept(curr.val);
				}

				// go up the tree from right node
				// after coming back from right node, process parent node and
				// pop stack.
			} else if (curr.right == prev) {
				stack.pop();
				consumer.accept(curr.val);
			}

			prev = curr;
		}

	}
	
	public void postorderTraversal1(TreeNode<T> root, Consumer<T> consumer) {

		if (root == null)
			return;

		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		stack.push(root);

		TreeNode<T> prev = null;
		while (!stack.empty()) {
			TreeNode<T> curr = stack.peek();

			// go down the tree.
			// check if current node is leaf, if so, process it and pop stack,
			// otherwise, keep going down
			if (prev == null || prev.left == curr || prev.right == curr) {
				// prev == null is the situation for the root node
				if (curr.left != null) {
					stack.push(curr.left);
				} else if (curr.right != null) {
					stack.push(curr.right);
				} else {
					stack.pop();
					consumer.accept(curr.val);
				}

				// go up the tree from left node
				// need to check if there is a right child
				// if yes, push it to stack
				// otherwise, process parent and pop stack
			} else if (curr.left == prev) {
				if (curr.right != null) {
					stack.push(curr.right);
				} else {
					stack.pop();
					consumer.accept(curr.val);
				}

				// go up the tree from right node
				// after coming back from right node, process parent node and
				// pop stack.
			} else if (curr.right == prev) {
				stack.pop();
				consumer.accept(curr.val);
			}

			prev = curr;
		}

	}
	
	
	
	@Test
	public void testPreorderTraversal(){
		TreeNode<Integer> root=new TreeNode<>(10);
		root.left=new TreeNode<>(5);
		root.right=new TreeNode<>(3);
		
		root.left.left=new TreeNode<>(2);
		root.left.right=new TreeNode<>(1);
		
		root.right.left=new TreeNode<>(6);
		root.right.right=new TreeNode<>(4);
		
		int[] preorders={10, 5, 2, 1, 3, 6, 4};
		
		List<Integer> results=new ArrayList<>(preorders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
		TreeIterativeTraversal<Integer> traverser=new TreeIterativeTraversal<>();
		traverser.preorderTraversal(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
		results.clear();
		traverser.preorderTraversal1(root, consumer);
		
		for (int i=0; i<preorders.length; i++){
			assertEquals(preorders[i], results.get(i).intValue());
		}
	
	}
	
	@Test
	public void testinorderTraversal(){
		TreeNode<Integer> root=new TreeNode<>(10);
		root.left=new TreeNode<>(5);
		root.right=new TreeNode<>(3);
		
		root.left.left=new TreeNode<>(2);
		root.left.right=new TreeNode<>(1);
		
		root.right.left=new TreeNode<>(6);
		root.right.right=new TreeNode<>(4);
		
		int[] inorders={2, 5, 1, 10, 6, 3, 4};
		
		List<Integer> results=new ArrayList<>(inorders.length);
		Consumer<Integer> consumer=a->{
			results.add(a);
		};
		
		TreeIterativeTraversal<Integer> traverser=new TreeIterativeTraversal<>();
		traverser.inorderTraversal(root, consumer);
		
		for (int i=0; i<inorders.length; i++){
			assertEquals(inorders[i], results.get(i).intValue());
		}
		
	
	}
}
