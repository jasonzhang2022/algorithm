package jason.datastructure.heap;

import java.util.ArrayList;


/**
 * Implements a heap using an array
 * For each node at index i, the left child node is at 2i+1, the right child node is at 2i+2
 * The parent node is at floor( (i-1)/2);
 * @author jason.zhang
 *
 * @param <T>
 */
public class BinaryHeap<T> implements Heap<T>{

	Node<T>[] nodes;
	int size;
	public BinaryHeap(int capacity){
		nodes=new Node[capacity];
	}
	
	
	//TODO
	//can be done in O(n) time.
	public void buildHeap(ArrayList<Node<T>> values) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void insert(T value, double priority) {
		if (size==nodes.length) {
			throw new RuntimeException("Over capacity");
		}
		Node<T> newNode=new Node<>(value,priority);
		nodes[size]=newNode;
		
		shiftUp(newNode, size);
		++size;
		
		return;
	}
	
	public void shiftUp(Node<T> node, int index) {
		if (index<=0) {
			return;
		}
		int parentIndex=(index-1)/2;
		Node<T> parentNode=nodes[parentIndex];
		if (parentNode.getmPriority()<=node.getmPriority()) {
			return;
		}
		nodes[parentIndex]=node;
		nodes[index]=parentNode;
		shiftUp(node, parentIndex);
	}

	/**
	 * remove root and change last one with root.
	 */
	@Override
	public T delete() {
		if (size==0) {
			return null;
		}
		
		Node<T> root=nodes[0];
		Node<T> lastNode=nodes[size-1];
		nodes[0]=lastNode;
		nodes[size-1]=null;
		
		shiftDown(lastNode, 0);
		--size;
		
		return root.getValue();
	}

	protected void shiftDown(Node<T> node, int index) {
		int leftIndex=2*index+1;
		int rightIndex=2*index+2;
		if (leftIndex>=size) {
			//no child node
			return;
		}
		Node<T> leftNode=nodes[leftIndex];
		Node<T> rightNode=null;
		if (rightIndex<size) {
			rightNode=nodes[rightIndex];
		}
		if (rightNode==null && leftNode==null) {
			return; //no child.
		}
		if (rightNode==null ) {
			//only check with leftNode
			if (leftNode.getmPriority()<node.mPriority) {
				nodes[index]=leftNode;
				nodes[leftIndex]=node;
				shiftDown(node, leftIndex);
			}
			return;
		} 
		
		if (node.getmPriority()<=leftNode.getmPriority() && node.getmPriority()<=rightNode.getmPriority()) {
			return;
		}
		
		if (leftNode.getmPriority()<rightNode.getmPriority()) {
			nodes[index]=leftNode;
			nodes[leftIndex]=node;
			shiftDown(node, leftIndex);
		} else {
			nodes[index]=rightNode;
			nodes[rightIndex]=node;
			shiftDown(node, rightIndex);
		}
	}
	
	
	@Override
	public T get() {
		if (size==0) {
			return null;
		}
		return nodes[0].getValue();
	}


	@Override
	public int size() {
		return size;
	}

	
	
}
