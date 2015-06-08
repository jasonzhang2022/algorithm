package jason.datastructure.tree;

import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Consumer;

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

	/**
	 * Recursive traversal is easy. The iterative approach is creative.
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
			TreeNode<T> n = stack.pop();
			consumer.accept(n.val);

			if (n.right != null) {
				stack.push(n.right);
			}
			if (n.left != null) {
				stack.push(n.left);
			}

		}

	}

	public void inorderTraversal(TreeNode<T> root, Consumer<T> consumer) {

		if (root == null)
			return;

		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		// define a pointer to track nodes
		TreeNode<T> p = root;

		while (!stack.empty() || p != null) {

			// if it is not null, push to stack
			// and go down the tree to left
			if (p != null) {
				stack.push(p);
				p = p.left;

				// if no left child
				// pop stack, process the node
				// then let p point to the right
			} else {
				TreeNode<T> t = stack.pop();
				consumer.accept(t.val);
				p = t.right;
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
}
