package jason.datastructure.tree;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Implement 2-4 tree follow wikipedia
 * @author jason.zhang
 *
 */
public class Tree24Wiki<T> {

	
	public static<T> void swap(T[] array, int i, int j){
		T temp=array[i];
		array[i]=array[j];
		array[j]=temp;
	}
	public static class KeyValue<T> {
		public String key;
		public T value;
		public KeyValue() {
			super();
		}
		public KeyValue(String key, T value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
	
	
	
	
	public static class Node<T> {
		public KeyValue<T>[] values=new KeyValue[3];
		public Node<T>[] children=new Node[4];
		public Node<T> parent;
		
		public boolean isLeaf(){
			return children[0]==null;
		}
		public boolean hasSpace(){
			return values[2]==null;
		}
		
		public int keyIdx(String key){
			for (int i=0; i<3; i++){
				if (values[i]!=null && values[i].key.equals(key)){
					return i;
				}
			}
			return -1;
		}
		
		public Node<T> searchChildNode(String key){
			int lastIndex=0;
			for (int i=0; i<3; i++){
				if (values[i]!=null && key.compareTo(values[i].key)<0){
					//got down, return 
					return children[i];
				}
				lastIndex=i;
			}
			return children[lastIndex+1];
			
		}
		
		public int insertValue(KeyValue<T> kv){
			if (!hasSpace()){
				return -1;
			}
			int insertedIdx=2;
			values[2]=kv;
			
			while (insertedIdx>0){
				if (values[insertedIdx-1]==null){
					swap(values, insertedIdx, insertedIdx-1);
					insertedIdx--;
					continue;
				} else if (values[insertedIdx-1].key.compareTo(kv.key)>0){
					swap(values, insertedIdx, insertedIdx-1);
					insertedIdx--;
					continue;
				} else {
					break;
				}
			}
			return insertedIdx;
		}
	}
	
	public Node<T> root=null;
	
	public void put(String key, T value){
		if (root==null){
			root=new Node<T>();
			root.values[0]=new KeyValue(key, value);
			return;
		}
		traceAndPut(root, new KeyValue(key, value));
		
	}
	
	protected void moveKeyUp(Node<T> parent, KeyValue<T> kv, Node<T> left, Node<T> right, Node<T> oldNode){
		int oldIdx=0;
		for (;oldIdx<3; oldIdx++){
			if (parent.children[oldIdx]==oldNode){
				break;
			}
		}
		//oldIdx will be 0, 1, 2
		int currentIdx=2;
		parent.values[2]=kv;
		while (currentIdx>oldIdx){
			swap(parent.values, currentIdx, currentIdx-1);
			swap(parent.children, currentIdx+1, currentIdx);
			currentIdx--;
		}
		parent.children[oldIdx]=left;
		parent.children[oldIdx+1]=right;
		
		left.parent=parent;
		right.parent=parent;
		
		//clean up
		oldNode.parent=null;
		
		
		
	}
	
	public void traceAndPut(Node<T> currentNode, KeyValue<T> kv){
		
		//replace
		int keyIdx=currentNode.keyIdx(kv.key);
		if (keyIdx>=0){
			//replace
			currentNode.values[keyIdx].value=kv.value;
			return;
		}
		
		if (!currentNode.hasSpace()){
			
			Node<T> left=new Node<T>();
			left.values[0]=currentNode.values[0];
			left.children[0]=currentNode.children[0];
			left.children[1]=currentNode.children[1];
			if (!currentNode.isLeaf()){
				left.children[0].parent=left;
				left.children[1].parent=left;
			}
			
			Node<T> right=new Node<T>();
			right.values[0]=currentNode.values[2];
			right.children[0]=currentNode.children[2];
			right.children[1]=currentNode.children[3];
			if (!currentNode.isLeaf()){
				right.children[0].parent=right;
				right.children[1].parent=right;
			}
			
			
		
			Node<T> oldParent=currentNode.parent;
			KeyValue<T> promotedKey=currentNode.values[1];
			//destroy currentNode.
			//Arrays.fill(currentNode.values, null);
			//Arrays.fill(currentNode.children, null);
			//move new node to parent.
			//the parent node definitely has less than space, if it does not, 
			//we should already do the same operation before.
			if (oldParent==null){
				//currentNode is root.
				root=new Node<T>();
				root.values[0]= promotedKey;
				root.children[0]=left;
				left.parent=root;
				root.children[1]=right;
				right.parent=root;
				traceAndPut(root, kv);
			} else {
				moveKeyUp(oldParent,  promotedKey, left, right, currentNode);
				traceAndPut(oldParent, kv);
			}
			
			return;
		}
		
		//--------we have space.
		//not found, trace down.
		if (currentNode.isLeaf()){
			//we definitely can insert
			currentNode.insertValue(kv);
			return;
		}
		
		//walk down.
		Node<T> childNode=currentNode.searchChildNode(kv.key);
		traceAndPut(childNode, kv);
	}
	
	
	
	//---------walk tree
	
	public void walk(Consumer<KeyValue<T>> consumer){
		walk(root, consumer);
	}

	private void walk(Node<T> node, Consumer<KeyValue<T>> consumer) {
		if (node==null){
			return;
		}
		walk(node.children[0], consumer);
		for (int i=0; i<3; i++){
			
			if (node.values[i]!=null){
				consumer.accept(node.values[i]);
			}
			walk(node.children[i+1], consumer);	
		}
	}
	
	public T get(String key){
		return get(root, key);
	}
	
	protected T get(Node<T> node, String key){
		if (node==null){
			return null;
		}
		int keyIdx=node.keyIdx(key);
		if (keyIdx>=0){
			return node.values[keyIdx].value;
		}
		return get(node.searchChildNode(key), key);
	}
}
