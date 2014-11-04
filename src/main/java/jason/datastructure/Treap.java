package jason.datastructure;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Treap {

	public static class Node {
		Node leftNode;
		Node rightNode;
		Node parent;
		Integer key;
		int priority;
	}

	Node root;

	public boolean contains(Integer key) {

		Node current = root;
		while (current != null) {
			int c = current.key.compareTo(key);
			if (c == 0) {
				return true;
			} else if (c < 0) {
				current = current.rightNode;
			} else {
				current = current.leftNode;
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
		root=node;
		
	}
	
	
	private Node createNewNode(Integer key) {
		Node newNode=new Node();
		newNode.key=key;
		newNode.priority=random.nextInt();
		return newNode;
	}
	private Node addNode(Node parent, Integer key) {
		if (parent==null) {
			return createNewNode(key);
		}
		
		int c=key.compareTo(parent.key);
		if (c==0) {
			return parent; //already exists, do nothing.
		}
		if (c<0) {
			Node childNode=addNode(parent.leftNode, key);
			parent.leftNode=childNode;
			childNode.parent=parent;
			
			if (childNode.priority>parent.priority) {
				//rotate right;
				parent.parent=childNode;
				parent.leftNode=childNode.rightNode;
				childNode.rightNode=parent;
				if (parent.leftNode!=null) {
					parent.leftNode.parent=parent;
				}
				return childNode; //new parent node;
			} else {
				return parent;
			}
		} else {
			Node childNode=addNode(parent.rightNode, key);
			parent.rightNode=childNode;
			childNode.parent=parent;
			
			if (childNode.priority<parent.priority) {
				//rotate left
				parent.parent=childNode;
				parent.rightNode=childNode.leftNode;
				childNode.leftNode=parent;
				if (parent.rightNode!=null) {
					parent.rightNode.parent=parent;
				}
				return childNode;
			} else {
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
	
	
	private void rotateDown(Node parent, Node child, boolean rightRotation) {
		if (child.priority<parent.priority) {
			return; //no rotation is needed.
		}
		
		Node grandParent=parent.parent;
		
		parent.parent=child;
		if (rightRotation) {
			parent.leftNode=child.rightNode;
			child.rightNode=parent;	
			if (parent.leftNode!=null) {
				parent.leftNode.parent=parent;
			}
		} else {
			parent.rightNode=child.leftNode;
			child.leftNode=parent;
			if (parent.rightNode!=null) {
				parent.rightNode.parent=parent;
			}
		}
		if (grandParent!=null) {
			child.parent=grandParent;
			int c1=parent.key.compareTo(grandParent.key);
			if (c1<0) {
				//parent is at left side of grand parent.
				grandParent.leftNode=child;
				
			} else {
				//parent is at the right side of grand parent.
				grandParent.rightNode=child;
			}
		}
		
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
		
		//when code comes here. parent is a leave node.
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
	}
}
