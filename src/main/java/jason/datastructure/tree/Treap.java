package jason.datastructure.tree;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import javax.swing.tree.VariableHeightLayoutCache;

import org.junit.experimental.theories.Theories;


/**
 * Implement order statistics tree as a practice.
 * Using Max-heap.
 * @author jason.zhang
 *
 */
public class Treap<T> {

	public static class Node<T> extends BinaryNode<T>{
		int priority;
	}

	Node<T> root;
	

	public T get(String key) {
		if (root==null){
			return null;
		}
		Node<T> current = (Node<T>) BinaryNode._searchForPositionToAdd(root, key);
		if (current.key.equals(key)){
			return current.value;
		}
		return null;
		
	}
	
	Random random=new Random(new Date().getTime());
	public void put(String key, T value) {
		Node<T> node=createNewNode(key, value);
		Node<T> parent=(Node<T>) BinaryNode._searchForPositionToAdd(root, key);
		if (parent==null){
			root=node;
			return;
		}
		if (parent.key.equals(key)){
			parent.value=value;
			return;
		}
		if (key.compareTo(parent.key)<0){
			parent.leftChild=node;
			node.parent=parent;
		}	else {
			parent.rightChild=node;
			node.parent=parent;
		}
	
		_adjustPriority(node);
		
	}
	
	
	private Node<T> createNewNode(String key, T value) {
		Node<T> newNode=new Node<T>();
		newNode.key=key;
		newNode.value=value;
		newNode.priority=random.nextInt();
		return newNode;
	}
	

	
	
	/**
	 * 
	 * @param parent
	 * @param key
	 * @return the latest parent under which node is added, or null if node is not added.
	 */
	private void _adjustPriority(Node<T> node) {

		//we use max heap
		while (node.parent!=null && node.priority>((Node<T>)node.parent).priority){
			//Node should be parent;
			if (node==node.parent.leftChild){
				node.parent.rotateRight();
			} else {
				node.parent.rotateLeft();
			}
		}
		if (node.parent==null){
			root=node;
		}
	}
	
	
	public void remove(String key) {
		if (root==null){
			return;
		}
		Node<T> targetNode=(Node<T>) BinaryNode._searchForPositionToAdd(root, key);
		if (!targetNode.key.equals(key)){
			return;
		}
		if (targetNode.leftChild!=null && targetNode.rightChild!=null){
			Node<T> predecessor=(Node<T>) BinaryNode._searchPredecessor(targetNode);
			
			targetNode.key=predecessor.key;
			targetNode.value=predecessor.value;
			
			predecessor.key=key;
			targetNode=predecessor;
		}
		
		//switch node with child
		if (targetNode.leftChild!=null){
			targetNode.key=targetNode.leftChild.key;
			targetNode.value=targetNode.leftChild.value;
			
			targetNode.leftChild.parent=null;
			targetNode.leftChild=null;
		} else if (targetNode.rightChild!=null){
			
			targetNode.key=targetNode.rightChild.key;
			targetNode.value=targetNode.rightChild.value;
			
			targetNode.rightChild.parent=null;
			targetNode.rightChild=null;
		} else {
			//has no children
			if (targetNode==root){
				root=null;
				return;
			} 
			if (targetNode.parent.leftChild==targetNode){
				targetNode.parent.leftChild=null;
				targetNode.parent=null;
			} else {
				targetNode.parent.rightChild=null;
				targetNode.parent=null;
			}
		}
		return;
		
	}


	public void walk(Consumer<BinaryNode<T>> collector) {
		if (root==null){
			return;
		}
		root.walk(collector);
		
	}
	
	
	
	
}
