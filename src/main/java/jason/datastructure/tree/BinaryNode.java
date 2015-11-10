package jason.datastructure.tree;

import java.util.function.Consumer;

public class BinaryNode<T> {

	public BinaryNode<T> left;
	public BinaryNode<T> right;
	public BinaryNode<T> parent;

	public String key;
	public T value;

	public BinaryNode() {
		super();
	}

	public BinaryNode(String key, T value) {
		super();
		this.key = key;
		this.value = value;
	}

	public BinaryNode<T> getGrandParent() {
		if (parent != null) {
			return parent.parent;
		}
		return null;
	}

	public BinaryNode<T> getUncle() {
		BinaryNode<T> g = getGrandParent();
		if (g == null) {
			return null;
		}
		if (g.left == parent) {
			return g.right;
		} else {
			return g.left;
		}
	}

	public BinaryNode<T> getSibling() {
		if (parent == null) {
			return null;
		}
		if (parent.left == this) {
			return parent.right;
		} else {
			return parent.left;
		}
	}

	public void rotateRight() {
		// hold reference to all moved nodes.
		BinaryNode<T> p = this.parent;
		BinaryNode<T> leftChild = this.left;
		BinaryNode<T> leftRight = leftChild.right;

		if (p != null) {
			if (p.left == this) {
				p.left = leftChild;
			} else {
				p.right = leftChild;
			}
		}
		leftChild.parent = p;

		leftChild.right = this;
		this.parent = leftChild;

		if (leftRight!=null){
			leftRight.parent = this;
		}
		this.left = leftRight;
	}

	public void rotateLeft() {
		// hold reference to all moved nodes.
		BinaryNode<T> p = this.parent;
		BinaryNode<T> rightChild = this.right;
		BinaryNode<T> rightLeft = rightChild.left;

		if (p != null) {
			if (p.left == this) {
				p.left = rightChild;
			} else {
				p.right = rightChild;
			}
		}
		rightChild.parent = p;

		rightChild.left = this;
		this.parent = rightChild;
		if (rightLeft!=null){
			rightLeft.parent = this;
		}
	
		this.right = rightLeft;
	}

	public void walk(Consumer<BinaryNode<T>> consumer) {
		if (this.left != null) {
			left.walk(consumer);
		}
		consumer.accept(this);
		if (right != null) {
			right.walk(consumer);
		}
	}
	
	public static <T> BinaryNode<T> _searchForPositionToAdd(BinaryNode<T> node, String key){
		if (node==null){
			return null;
		}
		int cmp=key.compareTo(node.key);
		if (cmp<0){
			//we should go left.
			if (node.left!=null){
				//has left
				return _searchForPositionToAdd(node.left, key);
			} else {
				return node;
			}
		} else if (cmp>0){
			if (node.right!=null){
				return _searchForPositionToAdd(node.right, key);
			} else {
				return node;
			}
		} else {
			return node;
		}
	}
	
	/* Could be null */
	public static <T> BinaryNode<T>  _searchPredecessor(BinaryNode<T>node){
		//node is the leftmost itself
		if (node.left==null){
			//we need to go to parent
			BinaryNode<T> parent=node.parent;
			BinaryNode<T> currentNode=node;
			while (parent!=null && parent.right!=currentNode){
				currentNode=parent;
				parent=currentNode.parent;
			}
			return parent;
		}
		BinaryNode<T> currentNode=node.left;
		while (currentNode.right!=null){
			currentNode= currentNode.right;
		}
		return currentNode;
	}
	
	
	public static <T> void inorderIterative(BinaryNode<T> root, Consumer<BinaryNode<T>> consumer){
		
		if (root==null){
			return;
		}
		
		BinaryNode<T> prev=null;
		BinaryNode<T> next=root;
		while (next!=null){
			BinaryNode<T> current=next;
			
			if (prev==null || prev==current.parent){
				//go down
				if (current.left!=null){
					next=current.left;
					prev=current;
				} else if (current.right!=null){
					consumer.accept(current);
					next=current.right;
					prev=current;
				} else {
					consumer.accept(current);
					prev=current;
				}
			} else if (current.left==prev){
				//from left child
				consumer.accept(current);
				prev=current;
				if (current.right!=null){
					next=current.right;
				} else {
					next=current.parent;
				}
				
			} else{
				//current.right==prev
				//from right child
				prev=current;
				next=current.parent;
			}
		}
	}
	
}
