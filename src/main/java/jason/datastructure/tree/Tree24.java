package jason.datastructure.tree;

import java.util.Arrays;
import java.util.function.Consumer;

import jason.algorithm.Swaper;

public class Tree24<T> {
	
	
	
	public static <F> void swap(F[] input, int j, int i){
		F temp=input[j];
		input[j]=input[i];
		input[i]=temp;
	}
	
	public static class KeyValue<T> {
		String key; 
		T value;
		public KeyValue(String key, T value) {
			super();
			this.key = key;
			this.value = value;
		}
		
	}
	
	public static class Node<T>{
		public KeyValue<T>[] keys=new KeyValue[3];
		public Node<T>[] children=new Node[4];
		public Node<T> parent;
		
		public boolean isLeaf(){
			if (children[0]==null){
				return true;
			}
			return false;
		}
		
		public KeyValue findKey(String key){
			for (KeyValue kv: keys){
				if (kv==null){
					break;
				}
				if (kv.key.equals(key)){
					return kv;
				}
			}
			return null;
		}
		
		public int backInsert(String key, T value){
			if (keys[2]!=null){
				return -1;
			}
			keys[2]=new KeyValue(key, value);
			int currentIdx=2;
			while (currentIdx>0){
				if(keys[currentIdx-1]==null){
					swap(keys, currentIdx, currentIdx-1);
					currentIdx--;
				} else if (keys[currentIdx].key.compareTo(keys[currentIdx-1].key)<0){
					swap(keys, currentIdx, currentIdx-1);
					currentIdx--;
				} else{
					break;
				}
			}
			return currentIdx;
		}
	}
	
	
	Node<T> root=new Node<T>();
	
	public void insert(String key, T value){
		insert(root, key, value);
	}
	
	KeyValue<T>[] scratch=new KeyValue[4];
	Node<T>[] scratchNodes=new Node[5];
	
	/**
	 * Precondition: targetNode already has 3 keys, we need to split
	 * @param targetNode the node to be splitted.
	 * @param kv the new KeyValye added to cause the node to split 
	 * @param newChild newChild to add the children list
	 */
	public void split(Node<T> targetNode, KeyValue<T> kv, Node<T> newChild){
		
		
		Node<T> oldParent=targetNode.parent;
		//find out where the new key should be inserted.
		for (int i=0; i<3; i++){
			scratch[i]=targetNode.keys[i];
		}
		scratch[3]=kv;
		
		for (int i=0; i<4; i++){
			scratchNodes[i]=targetNode.children[i];
		}
		scratchNodes[4]=newChild;
		
		
		for (int j=3; j>0; j--){
			if (scratch[j].key.compareTo(scratch[j-1].key)<0){
				swap(scratch, j, j-1);
				swap(scratchNodes, j+1, j);
			} else {
				break;
			}
		}
		
		Node<T> leftNode=targetNode;
		Arrays.fill(targetNode.keys, null);
		Arrays.fill(targetNode.children, null);
		
		leftNode.keys[0]=scratch[0];
		//leftNode.keys[1]=scratch[1]; //this is the key to be promoted
		leftNode.children[0]=scratchNodes[0];
		leftNode.children[1]=scratchNodes[1];
		leftNode.parent=oldParent;
		
		Node<T> rightNode=new Node<T>();
		rightNode.keys[0]=scratch[2];
		rightNode.keys[1]=scratch[3];
		rightNode.children[0]=scratchNodes[2];
		rightNode.children[1]=scratchNodes[3];
		rightNode.children[2]=scratchNodes[4];
		rightNode.parent=oldParent;
		
		
		
		if (oldParent==null) {
			//we need a new root.
			Node<T> newRoot=new Node<T>();
			newRoot.keys[0]=scratch[1];
			newRoot.children[0]=leftNode;
			newRoot.children[1]=rightNode;
			leftNode.parent=newRoot;
			rightNode.parent=newRoot;
			root=newRoot;
			return;
		}
		
		if (oldParent.keys[2]==null){
			//we have space to insert the new key;
			oldParent.keys[2]=scratch[1];
			oldParent.children[3]=rightNode;
			for (int j=2; j>0; j--){
				if (oldParent.keys[j-1]==null){
					swap(oldParent.keys, j, j-1);
					swap(oldParent.children, j+1, j);
				} else {
					if (oldParent.keys[j-1].key.compareTo(oldParent.keys[j].key)>0){
						
						//swap
						swap(oldParent.keys, j, j-1);
						swap(oldParent.children, j+1, j);
					} else {
						//we are in correct position
					}
				}
			}
			return;
			
		}
		if (oldParent.keys[2]!=null){
			split(oldParent, scratch[1], rightNode);
			return;
		}
	}
	

	
	public void insert(Node<T> targetNode, String key, T value){
		if (targetNode.isLeaf()){
			KeyValue existingKeyValue=targetNode.findKey(key);
			if (existingKeyValue!=null){
				//replace value
				existingKeyValue.value=value;
				return;
			}
			int insertedIdx=targetNode.backInsert(key, value);
			if (insertedIdx>=0){
				//inserted;
				return;
			}
			
			//we need to split the node.
			split(targetNode, new KeyValue(key, value), null);
			return;
		}
		
		//never insert at internal node.
		KeyValue existingKeyValue=targetNode.findKey(key);
		if (existingKeyValue!=null){
			existingKeyValue.value=value;
			return;
		}
		
		for (int i=0; i<3; i++){
			if (targetNode.keys[i]==null){
				insert(targetNode.children[i+1], key, value);
				return;
			} else {
				if (targetNode.keys[i].key.compareTo(key)>0){
					insert(targetNode.children[i], key, value);
					return;
				} 
			}
		}
		insert(targetNode.children[3], key, value);
		return;
	}
	
	
	
	
	public void delete(String key){
		
	}
	
	
	public void delete(Node<T> node, String key){
		if (node==null){
			//not found.
			return;
		}
		
		int lastIdx=0;
		for (int i=0; i<2; i++){
			if (node.keys[i]==null){
				break;
			}
			if (node.keys[i].key.compareTo(key)<0){
				//go to left node.
				delete(node.children[i], key);
				return;
			} else if (node.keys[i].key.compareTo(key)==0){
				if (!node.isLeaf()){
					swapAndDelete(node, i);
				} else {
					deleteLeafKey(node, i);
				}
				return;
			} else {
				lastIdx=i;
			}
		}
		delete(node.children[lastIdx+1], key);
		
	}
	public void swapAndDelete(Node<T> node, int index){
		
		//first swap with predecessor
		Node<T> leftChild=node.children[index];
		Node<T> currentNode=leftChild;
		int keyIndex=0;
		outer:
		while (true ){
			if (currentNode.isLeaf()){
				for (int j=2; j>=0; j--){
					if (currentNode.keys[j]!=null){
						keyIndex=j;
						break outer;
					}
				}
			} 

			for (int j=4; j>=0; j--){
				if (currentNode.children[j]!=null){
					currentNode=currentNode.children[j];
					continue;
				}
			}
		}
		
		node.keys[index]=currentNode.keys[keyIndex];
		currentNode.keys[keyIndex]=null;
		
		
		deleteLeafKey(currentNode, keyIndex);
	}
	
	public void deleteLeafKey(Node<T> node, int index){
		node.keys[index]=null;
		if (index!=0){
			return;
		}
		if (index==0){
			//can ww borrow from sibling
			if (tryBorrow(node)){
				return;
			}
			
			
		}
	}
	
	
	
	
	//rotate node from current position in children to 
	public void counterClockRotate(Node<T> node){
		Node<T> parent=node.parent;
		int nodeIndex=-1;
		for (int i=0; i<parent.children.length; i++){
			if (parent.children[i]==node){
				nodeIndex=i;
				break;
			}
		}
		
		
		
	}
	
	
	//node keys is empty, 
	public boolean tryBorrow(Node<T> node, Node<T> newChild){
		Node<T> parent=node.parent;
		int nodeIndex=-1;
		for (int i=0; i<parent.children.length; i++){
			if (parent.children[i]==node){
				nodeIndex=i;
				break;
			}
		}
		
		
		//we have left sibling, and left sibling has more than one keys
		if (nodeIndex!=0 && parent.children[nodeIndex-1].keys[1]!=null){
			
			Node<T> leftSibiling=parent.children[nodeIndex-1];
			
			
			int borrowIndex=parent.children[nodeIndex-1].keys[2]!=null?2:1;
			
			//step 1: arrange key
			//borrow
			node.keys[0]=parent.keys[nodeIndex-1];
			parent.keys[nodeIndex-1]=leftSibiling.keys[borrowIndex];
			leftSibiling.keys[borrowIndex]=null;
			
			//migrate the sibling right node.
			Node<T> rightNode=leftSibiling.children[borrowIndex+1];
			node.children[0]=rightNode;
			leftSibiling.children[borrowIndex+1]=null;
			if (rightNode!=null){
				rightNode.parent=node;
			}
			node.children[1]=newChild;
			newChild.parent=node;
							
			return true;
		}
		
		//try right sibling
		if (nodeIndex<3 && parent.children[nodeIndex + 1] != null
				&& parent.children[nodeIndex +1].keys[1] != null) {
			
			Node<T> rightSibNode=parent.children[nodeIndex + 1];
			
			//arrange key;
			
			// borrow
			node.keys[0] = parent.keys[nodeIndex ];
			parent.keys[nodeIndex] = rightSibNode.keys[0];
			//shit key
			rightSibNode.keys[0]=rightSibNode.keys[1];
			rightSibNode.keys[1]=rightSibNode.keys[2];
			rightSibNode.keys[2]=null;
			
			//migrate node.
			Node<T> leftNode=rightSibNode.children[0];
			rightSibNode.children[0]=rightSibNode.children[1];
			rightSibNode.children[1]=rightSibNode.children[2];
			rightSibNode.children[2]=rightSibNode.children[3];
			rightSibNode.children[3]=null;
			
			node.children[0]=newChild;
			newChild.parent=node;
			
			node.children[1]=leftNode;
			leftNode.parent=node;
			
			
		}
		
		return false;
	}
	
	
	public boolean moveParentDown(Node<T> node){
		
		Node<T> parent=node.parent;
		int nodeIndex=-1;
		for (int i=0; i<parent.children.length; i++){
			if (parent.children[i]==node){
				nodeIndex=i;
				break;
			}
		}
		
		if (nodeIndex!=0){
			//move Parent down to left sibling.
			Node<T> leftSib=parent.children[nodeIndex-1];
			
			
			
		} else {
			//move parent down to right sibling
		}
		
		
		
		return false;
	}
	
	
	
	//--------------------------------trasverse
	public void processTree(Consumer<KeyValue<T>> consumer){
		processNode(root, consumer);
	}
	
	public void processNode(Node<T> node, Consumer<KeyValue<T>> consumer){
		if (node==null){
			return;
		}
		int lastIndex=0;
		for (int i=0; i<3; i++){
			if (node.keys[i]==null){
				break;
			}
			
			if (node.children[i]!=null){
				processNode(node.children[i], consumer);
			}
			consumer.accept(node.keys[i]);
			lastIndex=i;
		}
		processNode(node.children[lastIndex+1], consumer);
		
		
	}
}
