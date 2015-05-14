package jason.datastructure.tree;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.tree.VariableHeightLayoutCache;

import org.junit.experimental.theories.Theories;


/**
 * Implement order statistics tree as a practice.
 * Using Max-heap.
 * @author jason.zhang
 *
 */
public class Treap {

	public static class Node<T> extends BinaryNode<T>{
		int priority;
		int orderStatistics=1;
	}

	Node root;
	

	public boolean contains(String key) {

		Node current = root;
		while (current != null) {
			int c = key.compareTo(current.key);
			if (c == 0) {
				return true;
			} else if (c < 0) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}

		}
		return false;
	}

	public void collect(List<String> results) {
		collectNode(root, results);
	}
	
	private void collectNode(Node root, List<String> results) {
		if (root==null) {
			return;
		}
		collectNode(root.leftChild, results);
		results.add(root.key);
		collectNode(root.rightChild, results);
	}
	
	Random random=new Random(new Date().getTime());
	public void add(String key) {
		Node node=addNode(root, key);
		if (node!=null) {
			root=node;
		}

		
	}
	public int size() {
		if (root==null) {
			return 0;
		}
		return root.orderStatistics;
	}
	
	private Node createNewNode(String key) {
		Node newNode=new Node();
		newNode.key=key;
		newNode.priority=random.nextInt();
		return newNode;
	}
	
	private void updateStatistics(Node node) {
		int statistics=1; //self
		if (node.leftChild!=null) {
			statistics+=node.leftChild.orderStatistics;
		}
		if (node.rightChild!=null) {
			statistics+=node.rightChild.orderStatistics;
		}
		node.orderStatistics=statistics;
		
	}
	
	
	private void leftRotate(Node parent, Node childNode) {
		Node grandParent=parent.parent;
		
		parent.parent=childNode;
		parent.rightChild=childNode.leftChild;
		
		childNode.leftChild=parent;
		if (parent.rightChild!=null) {
			parent.rightChild.parent=parent;
		}
		
		childNode.parent=grandParent;
		if (grandParent!=null) {
			
			int c1=parent.key.compareTo(grandParent.key);
			if (c1<0) {
				//parent is at left side of grand parent.
				grandParent.leftChild=childNode;
				
			} else {
				//parent is at the right side of grand parent.
				grandParent.rightChild=childNode;
			}
		}
		updateStatistics(parent);
		updateStatistics(childNode);
	}
	
	private void rightRotate(Node parent, Node childNode) {
		
		Node grandParent=parent.parent;
		//rotate right;
		parent.parent=childNode;
		parent.leftChild=childNode.rightChild;
		childNode.rightChild=parent;
		if (parent.leftChild!=null) {
			parent.leftChild.parent=parent;
		}
		childNode.parent=grandParent;
		if (grandParent!=null) {
			
			int c1=parent.key.compareTo(grandParent.key);
			if (c1<0) {
				//parent is at left side of grand parent.
				grandParent.leftChild=childNode;
				
			} else {
				//parent is at the right side of grand parent.
				grandParent.rightChild=childNode;
			}
		} 
		updateStatistics(parent);
		updateStatistics(childNode);
	}
	
	
	/**
	 * 
	 * @param parent
	 * @param key
	 * @return the latest parent under which node is added, or null if node is not added.
	 */
	private Node addNode(Node parent, String key) {
		if (parent==null) {
			return createNewNode(key);
		}
		
		int c=key.compareTo(parent.key);
		if (c==0) {
			return null; //already exists, do nothing.
		}
		if (c<0) {
			Node childNode=addNode(parent.leftChild, key);
			if (childNode==null) {
				return null; //nothing is added
			}
			parent.leftChild=childNode;
			childNode.parent=parent;
			
			if (childNode.priority>parent.priority) {
				rightRotate(parent, childNode);
				return childNode; //new parent node;
			} else {
				parent.orderStatistics=parent.orderStatistics+1;
				return parent;
			}
		} else {
			Node childNode=addNode(parent.rightChild, key);
			
			if (childNode==null) {
				return null; //nothing is added
			}
			
			parent.rightChild=childNode;
			childNode.parent=parent;
			
			if (childNode.priority<parent.priority) {
				leftRotate(parent, childNode);
				return childNode;
			} else {
				parent.orderStatistics=parent.orderStatistics+1;
				return parent;
			}
		}
	}
	
	
	public void remove(String key) {
		Node current = root;
		while (current != null) {
			int c = current.key.compareTo(key);
			if (c == 0) {
				break;
			} else if (c < 0) {
				current = current.rightChild;
			} else {
				current = current.leftChild;
			}

		}
		if (current==null) {
			return; //not found;
		}
		removeNode(current);
	}
	
	//used by remove only. the parent node will be removed finally.
	private void rotateDown(Node parent, Node child, boolean rightRotation) {
		if (child.priority<parent.priority) {
			return; //no rotation is needed.
		}
		if (rightRotation) {
			rightRotate(parent, child);
		} else {
			leftRotate(parent, child);
		}
		//the parent node will be removed.
		child.orderStatistics=child.orderStatistics-1;
		
		//parent is child right now.
		if (parent.leftChild!=null && parent.rightChild!=null) {
			if (parent.leftChild.priority>parent.rightChild.priority) {
				rotateDown(parent, parent.leftChild, true);
				
			} else {
				rotateDown(parent, parent.rightChild, false);
			}
			return;
		}
		
		if (parent.leftChild!=null) {
			rotateDown(parent, parent.leftChild, true);
			return;
		}
		
		if (parent.rightChild!=null) {
			rotateDown(parent, parent.rightChild, false);
			return;
		}
		
		
	}
	
	
	private void removeNode(Node node) {
		node.priority=String.MIN_VALUE;
		if (node.leftChild!=null && node.rightChild!=null) {
			if (node.leftChild.priority>node.rightChild.priority) {
				rotateDown(node, node.leftChild, true);
				
			} else {
				rotateDown(node, node.rightChild, false);
			}
		}  else if (node.leftChild!=null) {
			rotateDown(node, node.leftChild, true);
		} else if (node.rightChild!=null) {
			rotateDown(node, node.rightChild, false);
		}
		 /*
		  * else: the node is a leave node.
		  */
		
		Node current=node.parent;
		//when code comes here. node is a leave node.
		if (node.parent==null) {
			//it is root.
			root=null;
			
		} else {
			int c=node.key.compareTo(node.parent.key);
			if (c<0) {
				node.parent.leftChild=null;
				node.parent=null;
			} else {
				node.parent.rightChild=null;
				node.parent=null;
			}
		}
		
		//make sure parent statistics is correct
		while (current!=null) {
			updateStatistics(current);
			current=current.parent;
		}
		
	}
	
	//select the ith value.
	public String select(int index) {
		if (root==null) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (root.orderStatistics<=index) {
			throw new ArrayIndexOutOfBoundsException();
		}
		Node current=root;
		while (current!=null) {
			int leftNodes=0;
			if (current.leftChild!=null) {
				leftNodes=current.leftChild.orderStatistics;
			}
			if (index==leftNodes) {
				return current.key;
			}
			if (index<leftNodes) {
				current=current.leftChild;
				continue;
			}
			index=index-leftNodes-1;
			current=current.rightChild;
		}
		
		throw new RuntimeException("Not found");
	}
	
	//return the index the key in the list.
	public int rank(String key) {
	
		int startIndex=0;
		Node current=root;
		
		while (current!=null) {
			int c=key.compareTo(current.key);
			int leftNodes=0;
			if (current.leftChild!=null) {
				leftNodes=current.leftChild.orderStatistics;
			}
			if (c==0) {	
				return startIndex+leftNodes;
			} else if (c<0) {
				current=current.leftChild;
			} else {
				startIndex=startIndex+leftNodes+1;
				current=current.rightChild;
			}
		}
		throw new RuntimeException("Not found");
		
	}
}
