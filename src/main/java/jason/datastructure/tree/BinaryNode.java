package jason.datastructure.tree;

import java.util.function.Consumer;



public class BinaryNode<T> {

	public BinaryNode<T> leftChild;
	public BinaryNode<T> rightChild;
	public BinaryNode<T> parent;
	
	
	
	public String key;
	public T value;
	public BinaryNode(){
		super();
	}
	public BinaryNode(String key, T value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public BinaryNode<T> getGrandParent(){
		if (parent!=null ){
			return parent.parent;
		}
		return null;
	}
	public BinaryNode<T> getUncle(){
		BinaryNode<T> g=getGrandParent();
		if (g==null){
			return null;
		}
		if (g.leftChild==parent){
			return g.rightChild;
		} else {
			return g.leftChild;
		}
	}
	
	public void rotateRight(){
		//hold reference to all moved nodes.
		BinaryNode<T> g=getGrandParent();
		BinaryNode<T> p=this.parent;
		BinaryNode<T> right=this.rightChild;
		
		//manage node p
		p.leftChild=right;
		if (right!=null){
			right.parent=p;
		}
		p.parent=this;
		
		
		//mange self;
		parent=g;
		if (g!=null){
			if (g.leftChild==p){
				g.leftChild=this;
			} else{
				g.rightChild=this;
			}
		}
		rightChild=p;
	}
	public void rotateLeft(){
		//hold reference to all moved nodes.
		BinaryNode<T> g=getGrandParent();
		BinaryNode<T> p=this.parent;
		BinaryNode<T> left=this.leftChild;
		
		//manage node p
		p.rightChild=left;
		if (left!=null){
			left.parent=p;
		}
		p.parent=this;
		
		
		//mange self;
		parent=g;
		if (g!=null){
			if (g.leftChild==p){
				g.leftChild=this;
			} else{
				g.rightChild=this;
			}
		}
		leftChild=p;
		
	}
	
	public void walk(Consumer<BinaryNode<T>> consumer){
		if (this.leftChild!=null){
			leftChild.walk(consumer);
		}
		consumer.accept(this);
		if (rightChild!=null){
			rightChild.walk(consumer);
		}
	}
}
