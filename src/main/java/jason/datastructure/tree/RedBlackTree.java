package jason.datastructure.tree;

import java.util.ArrayList;
import java.util.function.Consumer;

public class RedBlackTree<T> {
	
	
	public static class Node<T> extends BinaryNode<T>{
		
		//toggle between black and red. red by default.
		public boolean black=false;
		
		public void setRed(){
			black=false;
		}
		public void setBlack(){
			black=true;
		}
		public boolean isRed(){
			return !black;
		}
		public boolean isBlack(){
			return black;
		}
		public Node(String key, T value) {
			super(key, value);
		}
		
		
	}
	public Node<T> root=null;
	
	
	public Node<T> _searchForPositionToAdd(Node<T> node, String key){
		if (node==null){
			return null;
		}
		int cmp=key.compareTo(node.key);
		if (cmp<0){
			//we should go left.
			if (node.leftChild!=null){
				//has left
				return _searchForPositionToAdd((Node<T>)node.leftChild, key);
			} else {
				return node;
			}
		} else if (cmp>0){
			if (node.rightChild!=null){
				return _searchForPositionToAdd((Node<T>)node.rightChild, key);
			} else {
				return node;
			}
		} else {
			return node;
		}
	}
	
	
	public Node<T> _initialAdd(String key, T value, Node<T> node){
		Node<T> newNode=new Node<T>(key, value);
		newNode.parent=node;
		if (node!=null){
			int cmp=key.compareTo(node.key);
			if (cmp==0){
				node.value=value;
				return node;
			}
			if (cmp<0){
				newNode.parent=node;
				node.leftChild=newNode;
			} else {
				newNode.parent=node;
				node.rightChild=newNode;
			}
		}
		return newNode;
	}
	public void _rebalanceAfterAdd(Node<T> N){
		if (N.parent==null){
			N.setBlack();
			root=N;
			return ;
		}
		
		if (((Node<T>)N.parent).isBlack()){
			return; //done.
		}
		
		//parent is red right now. 
		//G is not null, P is red which can't be root.
		Node<T> G=(Node<T>)N.getGrandParent(); 
		Node<T> U=(Node<T>)N.getUncle(); //could be null
		Node<T> P=(Node<T>)N.parent;
		
		if (U!=null && U.isRed()){
			P.setBlack();
			U.setBlack();
			G.setRed();
			_rebalanceAfterAdd(G);
			return;
		}
		
		
		if (P == G.leftChild && N == P.rightChild) {
			P.rotateLeft();
			N = P;
		} else if (P == G.rightChild && N == P.leftChild) {
			P.rotateRight();
			N = P;
		}
		
		G=(Node<T>)N.getGrandParent(); 
		U=(Node<T>)N.getUncle(); //could be null
		P=(Node<T>)N.parent;
		/**
		 * N is on the same side with P.
		 */
		if (N==P.leftChild){
			G.rotateRight();
		} else {
			G.rotateLeft();
		}
		P.setBlack();
		G.setRed();	
		if (P.parent==null){
			root=P;
		}
		
	}
	
	
	public void put(String key, T value){
		Node<T> parentNode=_searchForPositionToAdd(root, key);
		Node<T> newNode=_initialAdd(key, value, parentNode);
		_rebalanceAfterAdd(newNode);
	}
	
	
	public void delete(String key){
		Node<T> N=_searchForPositionToAdd(root, key);
		if (N==null){
			return;
		}
		//not found.
		if (!N.key.equals(key)){
			return ;
		}
		
		//internal node. switch it to predecessor
		if (N.leftChild!=null && N.rightChild!=null){
			Node<T> predecessor=_searchPredecessor(N);
		
			N.key=predecessor.key;
			N.value=predecessor.value;

			//does not matter, we are going to delete it.
			predecessor.key=key;
			predecessor.value=null;
			
			N=predecessor;
		}
		
		//-------------N is leaf node with maximal one leaf.
		if (N.isRed()){
			//it can not has children.
			if (N.parent.leftChild==N){
				N.parent.leftChild=null;
			} else {
				N.parent.rightChild=null;
			}
			N.parent=null;
			return;
		}
		
		
		
		//-----------------N is black right now.
		//N can have red child
		if (N.leftChild!=null){
			//replace left child with key
			N.key=N.leftChild.key;
			N.value=N.leftChild.value;
			
			N.leftChild.parent=null;
			N.leftChild=null;
			return ;
		} else if (N.rightChild!=null){
			N.key=N.rightChild.key;
			N.value=N.rightChild.value;
			
			N.rightChild.parent=null;
			N.rightChild=null;
			return;
		}
		
		//----------------N has not child.
		_addOneBlackToSubtree(N);
		//remove N
		if (N.parent!=null){
			if (N.parent.leftChild==N){
				N.parent.leftChild=null;
			} else {
				N.parent.rightChild=null;
			}
			N.parent=null;
		} else {
			//we are removing the tree rroot.
			root=null;
		}
		
	}
	
	/**
	 * 
	 * TODO: check rotation concept
	 * and adjust root
	 * Subtree rooted at root missing one black node.
	 *  We need to add one black node to maintain redblack property.
	 *  
	 *  The root is black as is defined by redblack tree.
	 * @param root
	 */
	protected void _addOneBlackToSubtree(Node<T> root){
		if (root.parent==null){
			/*
			 * This is tree root. It has no sibling tree. We do not need to adjust black path
			 */
			root.setBlack(); //set it black to avoid violation: root is black.
			return;
		}
		
		Node<T> sibling=(Node<T>) root.getSibling();
		
		//----------------------convert red sibling into black sibling.
		if (isRed(sibling)){
			sibling.setBlack();
			((Node<T>)root.parent).setRed();
			//the parent has to be black. its children have to be black
			if (sibling==root.parent.rightChild){
				root.parent.rotateRight();
			} else {
				root.parent.rotateLeft();
			}
		}
		
		//---------------Black sibling.
		//right now the N has black sibling. 
		// The above code turns red sibling into black sibling. 
		//N still miss one black node in  path.
		sibling=(Node<T>) root.getSibling(); //sibling is changed.
		if (isBlack((Node<T>) sibling.leftChild) && isBlack((Node<T>) sibling.rightChild)){
			if (isBlack((Node<T>) root.parent)){	
				sibling.setRed();
				//both branches under root.parent missing a black node since we convert one
				// black not in sibling to red.
				_addOneBlackToSubtree((Node<T>) root.parent);
			} else {
				sibling.setRed();
				((Node<T>)root.parent).setBlack();
			}
			return;
		}
		
		//-----------s is black, and has red children
		if (root==root.parent.leftChild){
			if (isRed(sibling.leftChild) && isBlack(sibling.rightChild)){
				sibling.setRed();
				((Node<T>)sibling.leftChild).setBlack();
				sibling.rotateLeft();
			}
		} else {
			if (isRed(sibling.rightChild) && isBlack(sibling.leftChild)){
				sibling.setRed();
				((Node<T>)sibling.rightChild).setBlack();
				sibling.rotateLeft();
			}
		}
		
		//sibling right child is red.
		sibling=(Node<T>) root.getSibling();
		boolean isParentRed=isRed(root.parent);
		((Node<T>)root.parent).setBlack();
		if (isParentRed){
			sibling.setRed();
		} else {
			sibling.setBlack();
		}
		if (root==root.parent.leftChild){
			((Node<T>)sibling.rightChild).setBlack();
			root.parent.rotateRight();
			
		} else {
			((Node<T>)sibling.leftChild).setBlack();
			root.parent.rotateLeft();
		}
		
	}
	

	
	
	public Node<T>  _searchPredecessor(Node<T>node){
			
		Node<T> currentNode=(Node<T>) node.leftChild;
		while (currentNode.rightChild!=null){
			currentNode=(Node<T>) currentNode.rightChild;
		}
		return currentNode;
	}
	
	
	public void walk(Consumer<BinaryNode<T>> consumer){
		if (root!=null){
			root.walk(consumer);
		}
	}
	public T get(String key){
		Node<T> t=_searchForPositionToAdd(root, key);
		if (t!=null && t.key.equals(key)){
			return t.value;
		}
		return null;
	}
	
	
	
	//----------------utility
	protected boolean isBlack(BinaryNode<T> node){
		if (node==null || ((Node<T>)node).isBlack()){
			return true;
		}
		return false;
	}
	protected boolean isRed(BinaryNode<T> node){
		if (node!=null && ((Node<T>)node).isRed()){
			return true;
		}
		return false;
	}

}
