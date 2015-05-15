package jason.datastructure.tree;

import java.util.function.Consumer;

public class BinaryNode<T> {

	public BinaryNode<T> leftChild;
	public BinaryNode<T> rightChild;
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
		if (g.leftChild == parent) {
			return g.rightChild;
		} else {
			return g.leftChild;
		}
	}

	public BinaryNode<T> getSibling() {
		if (parent == null) {
			return null;
		}
		if (parent.leftChild == this) {
			return parent.rightChild;
		} else {
			return parent.leftChild;
		}
	}

	public void rotateRight() {
		// hold reference to all moved nodes.
		BinaryNode<T> p = this.parent;
		BinaryNode<T> leftChild = this.leftChild;
		BinaryNode<T> leftRight = leftChild.rightChild;

		if (p != null) {
			if (p.leftChild == this) {
				p.leftChild = leftChild;
			} else {
				p.rightChild = leftChild;
			}
		}
		leftChild.parent = p;

		leftChild.rightChild = this;
		this.parent = leftChild;

		if (leftRight!=null){
			leftRight.parent = this;
		}
		this.leftChild = leftRight;
	}

	public void rotateLeft() {
		// hold reference to all moved nodes.
		BinaryNode<T> p = this.parent;
		BinaryNode<T> rightChild = this.rightChild;
		BinaryNode<T> rightLeft = rightChild.leftChild;

		if (p != null) {
			if (p.leftChild == this) {
				p.leftChild = rightChild;
			} else {
				p.rightChild = rightChild;
			}
		}
		rightChild.parent = p;

		rightChild.leftChild = this;
		this.parent = rightChild;
		if (rightLeft!=null){
			rightLeft.parent = this;
		}
	
		this.rightChild = rightLeft;
	}

	public void walk(Consumer<BinaryNode<T>> consumer) {
		if (this.leftChild != null) {
			leftChild.walk(consumer);
		}
		consumer.accept(this);
		if (rightChild != null) {
			rightChild.walk(consumer);
		}
	}
}
