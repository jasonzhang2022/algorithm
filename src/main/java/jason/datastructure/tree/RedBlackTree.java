package jason.datastructure.tree;

public class RedBlackTree<T> {
	
	
	public static class Node<T>{
		
		//toggle between black and red
		public boolean black=false;
		public Node<T> leftChild;
		public Node<T> rightChild;
		public Node<T> parent;
		
		
		
		public String key;
		public T value;
		public Node(){
			super();
		}
		public Node(String key, T value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		
		
	}
	
	
	
	public Node<T> root;
	
	
	public Node<T> findGrandParent(Node<T> n){
		
		if (n.parent!=null && n.parent.parent!=null){
			return n.parent.parent;
		}
		return null;
		
	}
	
	public Node<T> findUncle(Node<T> n){
		Node<T> g=findGrandParent(n);
		if (g==null){
			return null;
		}
		if (g.leftChild==n.parent){
			return g.rightChild;
		} 
		return g.leftChild;
	}
	
	
	
	
	public Node<T> _initialAdd(String key, T value){
		
		if (root==null){
			root=new Node<T>(key, value);
			return root;
		}
		
		Node<T> currentNode=root;
		
		//loop is break when node is added. 
		while (true){
			int cmp=currentNode.key.compareTo(key);
			if (cmp==0){
				//TODO an update . handle this at late time.
			} else if (cmp<0){
				//should be add to left
				if (currentNode.leftChild==null){
					currentNode.leftChild=new Node<T>(key, value);
					currentNode.leftChild.parent=currentNode;
					return currentNode.leftChild;
				}
				//travese to left tree.
				currentNode=currentNode.leftChild;
			} else {
				if (currentNode.rightChild==null){
					currentNode.rightChild=new Node<T>(key, value);
					currentNode.rightChild.parent=currentNode;
					return currentNode.rightChild;
				}
				currentNode=currentNode.rightChild;
			}
		}	
	}
	
	
	public void insert(String key, T value){
		Node<T> newNode=_initialAdd(key, value);
		if (root==newNode){
			root.black=true;
			return;
		}
		Node<T> p=newNode.parent;
		if (p.black){
			return;
		}
		
	}

}
