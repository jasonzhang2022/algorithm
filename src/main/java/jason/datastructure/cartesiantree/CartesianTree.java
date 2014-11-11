package jason.datastructure.cartesiantree;

//http://wcipeg.com/wiki/Cartesian_tree
public class CartesianTree {

	public static class Node {
		public int index;
		public Node leftNode=null;
		public Node rightNode=null;
		public Node parent=null;
	}
	
	int[] input;
	protected Node root;
	protected Node lastAddedNode;
	public  CartesianTree(int[] input) {
		this.input=input;
		for (int i=0; i<input.length; i++) {
			addNode(i);
		}
	}
	
	
	//add a node.
	protected void addNode(int index) {
		Node newNode=new Node();
		newNode.index=index;
		if (lastAddedNode==null) {
			root=newNode;
			lastAddedNode=newNode;
			return;
		}
		
		if (input[newNode.index]>=input[lastAddedNode.index]) {
			newNode.parent=lastAddedNode;
			lastAddedNode.rightNode=newNode;
			lastAddedNode=newNode;
			return;
		}
		
		
		
		//newNode becomes the lastAdded one.
		//lastAddedNode=newNode;
		
		Node current=lastAddedNode;
		while (current!=null) {
			if (input[current.index]<input[newNode.index]) {
				//find one less than current one, current should be its left child
				Node temp=current.rightNode;
				
				newNode.parent=current;
				current.rightNode=newNode;
				if (temp!=null) {
					newNode.leftNode=temp;
					temp.parent=newNode;
				}
			
				lastAddedNode=newNode;
				break;
			}
			current=current.parent;
		}
		if (current==null) {
			//newNode should be new root.
			root.parent=newNode;
			newNode.leftNode=root;
			root=newNode;
			lastAddedNode=newNode;
		}
	}
	
	
	
}
