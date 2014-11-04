package jason.datastructure;

import java.util.Date;
import java.util.List;
import java.util.Random;

//http://igoro.com/archive/skip-lists-are-fascinating/
public class SkipList {

	public static final int MAX_HEIGHT=33;
	public static class Node {
		Integer value;
		
		//nexts[0] will hold while list
		Node[] nexts;
		
		public Node(int nextsHeight, Integer v) {
			nexts=new Node[nextsHeight];
			value=v;
		}
	}
	
	
	Node head=new  Node(33, null);
	Node tail=new Node(33, null);
	Random random=new Random(new Date().getTime());
	
	
	public void add(Integer v) {
		int level=random.nextInt(33);
		
		Node newNode=new Node(level+1, v);
		
		Node current=head;
		for (int i=MAX_HEIGHT-1; i>=0; i--) {
			for (;current.nexts[i]!=null; current=current.nexts[i]) {
				//next is too bigger, we should not go next,
				//but go to next level
				if (current.nexts[i].value.compareTo(v)>0) {
					break;
				}
				
			}
			if (i<=level) {
				newNode.nexts[i]=current.nexts[i];
				current.nexts[i]=newNode;
			}
		}
	}
	
	public boolean remove(Integer v) {
		Node current=head;
		boolean found=false;
		for (int i=MAX_HEIGHT-1; i>=0; i--) {
			for (;current.nexts[i]!=null; current=current.nexts[i]) {
				if (v.compareTo(current.nexts[i].value)==0) {
					current.nexts[i]=current.nexts[i].nexts[i]; //break the link
					found=true;
					break;
				}
				
				//next is too bigger, we should not go next,
				//but go to next level
				if (current.nexts[i].value.compareTo(v)>0) {
					break;
				}
			}
		}
		return found;
	}
	
	public boolean contains(Integer v) {
		Node current=head;
		for (int i=MAX_HEIGHT-1; i>=0; i--) {
			for (;current.nexts[i]!=null; current=current.nexts[i]) {
				if (v.compareTo(current.nexts[i].value)==0) {
					current.nexts[i]=current.nexts[i].nexts[i]; //break the link
					return true;
				}
				
				//next is too bigger, we should not go next,
				//but go to next level
				if (current.nexts[i].value.compareTo(v)>0) {
					break;
				}
			}
		}
		return false;
	}
	
	public void collect(List<Integer> results) {
		
		Node current=head.nexts[0];
		while (current!=null) {
			results.add(current.value);
			current=current.nexts[0];
		}
	}
	
}
