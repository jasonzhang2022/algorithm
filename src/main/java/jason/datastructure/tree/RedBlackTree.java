package jason.datastructure.tree;

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
		}
		
		if (U==null || U.isBlack()){
			if (P==G.leftChild && N==P.rightChild){
				N.rotateLeft();
				_rebalanceAfterAdd(N);
				return;
			} else if (P==G.rightChild && N==P.leftChild){
				N.rotateRight();
				_rebalanceAfterAdd(N);
				return;
			}
		}
			
		
		/**
		 * N is on the same side with P.
		 */
		if (N==P.leftChild){
			P.rotateRight();
		} else {
			P.rotateLeft();
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

}
