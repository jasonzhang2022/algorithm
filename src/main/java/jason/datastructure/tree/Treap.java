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

	public static class Node {
		Node leftNode;
		Node rightNode;
		Node parent;
		Integer key;
		int priority;
		int orderStatistics=1;
	}

	Node root;
	

	public boolean contains(Integer key) {

		Node current = root;
		while (current != null) {
			int c = key.compareTo(current.key);
			if (c == 0) {
				return true;
			} else if (c < 0) {
				current = current.leftNode;
			} else {
				current = current.rightNode;
			}

		}
		return false;
	}

	public void collect(List<Integer> results) {
		collectNode(root, results);
	}
	
	private void collectNode(Node root, List<Integer> results) {
		if (root==null) {
			return;
		}
		collectNode(root.leftNode, results);
		results.add(root.key);
		collectNode(root.rightNode, results);
	}
	
	Random random=new Random(new Date().getTime());
	public void add(Integer key) {
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
	
	private Node createNewNode(Integer key) {
		Node newNode=new Node();
		newNode.key=key;
		newNode.priority=random.nextInt();
		return newNode;
	}
	
	private void updateStatistics(Node node) {
		int statistics=1; //self
		if (node.leftNode!=null) {
			statistics+=node.leftNode.orderStatistics;
		}
		if (node.rightNode!=null) {
			statistics+=node.rightNode.orderStatistics;
		}
		node.orderStatistics=statistics;
		
	}
	
	
	private void leftRotate(Node parent, Node childNode) {
		Node grandParent=parent.parent;
		
		parent.parent=childNode;
		parent.rightNode=childNode.leftNode;
		
		childNode.leftNode=parent;
		if (parent.rightNode!=null) {
			parent.rightNode.parent=parent;
		}
		
		childNode.parent=grandParent;
		if (grandParent!=null) {
			
			int c1=parent.key.compareTo(grandParent.key);
			if (c1<0) {
				//parent is at left side of grand parent.
				grandParent.leftNode=childNode;
				
			} else {
				//parent is at the right side of grand parent.
				grandParent.rightNode=childNode;
			}
		}
		updateStatistics(parent);
		updateStatistics(childNode);
	}
	
	private void rightRotate(Node parent, Node childNode) {
		
		Node grandParent=parent.parent;
		//rotate right;
		parent.parent=childNode;
		parent.leftNode=childNode.rightNode;
		childNode.rightNode=parent;
		if (parent.leftNode!=null) {
			parent.leftNode.parent=parent;
		}
		childNode.parent=grandParent;
		if (grandParent!=null) {
			
			int c1=parent.key.compareTo(grandParent.key);
			if (c1<0) {
				//parent is at left side of grand parent.
				grandParent.leftNode=childNode;
				
			} else {
				//parent is at the right side of grand parent.
				grandParent.rightNode=childNode;
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
	private Node addNode(Node parent, Integer key) {
		if (parent==null) {
			return createNewNode(key);
		}
		
		int c=key.compareTo(parent.key);
		if (c==0) {
			return null; //already exists, do nothing.
		}
		if (c<0) {
			Node childNode=addNode(parent.leftNode, key);
			if (childNode==null) {
				return null; //nothing is added
			}
			parent.leftNode=childNode;
			childNode.parent=parent;
			
			if (childNode.priority>parent.priority) {
				rightRotate(parent, childNode);
				return childNode; //new parent node;
			} else {
				parent.orderStatistics=parent.orderStatistics+1;
				return parent;
			}
		} else {
			Node childNode=addNode(parent.rightNode, key);
			
			if (childNode==null) {
				return null; //nothing is added
			}
			
			parent.rightNode=childNode;
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
	
	
	public void remove(Integer key) {
		Node current = root;
		while (current != null) {
			int c = current.key.compareTo(key);
			if (c == 0) {
				break;
			} else if (c < 0) {
				current = current.rightNode;
			} else {
				current = current.leftNode;
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
		if (parent.leftNode!=null && parent.rightNode!=null) {
			if (parent.leftNode.priority>parent.rightNode.priority) {
				rotateDown(parent, parent.leftNode, true);
				
			} else {
				rotateDown(parent, parent.rightNode, false);
			}
			return;
		}
		
		if (parent.leftNode!=null) {
			rotateDown(parent, parent.leftNode, true);
			return;
		}
		
		if (parent.rightNode!=null) {
			rotateDown(parent, parent.rightNode, false);
			return;
		}
		
		
	}
	
	
	private void removeNode(Node node) {
		node.priority=Integer.MIN_VALUE;
		if (node.leftNode!=null && node.rightNode!=null) {
			if (node.leftNode.priority>node.rightNode.priority) {
				rotateDown(node, node.leftNode, true);
				
			} else {
				rotateDown(node, node.rightNode, false);
			}
		}  else if (node.leftNode!=null) {
			rotateDown(node, node.leftNode, true);
		} else if (node.rightNode!=null) {
			rotateDown(node, node.rightNode, false);
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
				node.parent.leftNode=null;
				node.parent=null;
			} else {
				node.parent.rightNode=null;
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
	public Integer select(int index) {
		if (root==null) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (root.orderStatistics<=index) {
			throw new ArrayIndexOutOfBoundsException();
		}
		Node current=root;
		while (current!=null) {
			int leftNodes=0;
			if (current.leftNode!=null) {
				leftNodes=current.leftNode.orderStatistics;
			}
			if (index==leftNodes) {
				return current.key;
			}
			if (index<leftNodes) {
				current=current.leftNode;
				continue;
			}
			index=index-leftNodes-1;
			current=current.rightNode;
		}
		
		throw new RuntimeException("Not found");
	}
	
	//return the index the key in the list.
	public int rank(Integer key) {
	
		int startIndex=0;
		Node current=root;
		
		while (current!=null) {
			int c=key.compareTo(current.key);
			int leftNodes=0;
			if (current.leftNode!=null) {
				leftNodes=current.leftNode.orderStatistics;
			}
			if (c==0) {	
				return startIndex+leftNodes;
			} else if (c<0) {
				current=current.leftNode;
			} else {
				startIndex=startIndex+leftNodes+1;
				current=current.rightNode;
			}
		}
		throw new RuntimeException("Not found");
		
	}
}
