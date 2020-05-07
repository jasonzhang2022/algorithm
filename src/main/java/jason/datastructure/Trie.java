package jason.datastructure;

import java.util.ArrayList;


/*
 * https://www.topcoder.com/community/data-science/data-science-tutorials/using-tries/
 * Trie performs better when number of entries is big.
 * Trie can perform prefix lookup. Hastable cann't.
 * 
 * The tries can insert and findUsingArray strings in O(L) time (where L represent the length of a single word). This is much faster than set ,
 * but is it a bit faster than a hash table.
The set <string> and the hash tables can only findUsingArray in a dictionary words that match exactly
with the single word that we are finding; the trie allow us to findUsingArray
words that have a single character different, a prefix in common, a character missing, etc.
 */

public class Trie<T>{

	public static final int SymbolLength=256;
	
	
	public static class Entry<E>{
		public String key;
		public E value;
		public Entry(String k, E v){
			key=k;
			value=v;
		}
	}
	
	
	//a tree node in the Trie structure
	public static class Node<E> {
		/*
		 * For an internal node, the value can be null. If the value is null for internal node, 
		 * it means the key for this internal node does not exists.
		 * Value should exist for all external nodes.
		 */
		E value;  
		Node<E>[] children=new Node[SymbolLength];
		
	}
	
	
	Node<T> root;
	int size=0;
	
	public Trie(){
		root=new Node<T>();
	}
	
	public int size() {
		return size;
	}
	public void put(String key, T value) {
		if (key==null) {
			return;
		}
		if (value==null) {
			delete(key);
		}
		
		if (key.length()==0) {
			if (root.value==null) {
				size++;
			}
			root.value=value;
			return;
		}
		put(root, key, value, 0);
	}
	
	
	public T get(String key) {
		if (key.equals("")) {
			return root.value;
		}
		Node<T> valueNode=findPrefixNode(root, key, 0);
		if (valueNode==null) {
			return null;
		}
		return valueNode.value;
	}
	
	public boolean contains(String key) {
		return get(key)!=null;
	}
	
	private T get(Node<T> parent, String key, int childKeyPosition) {
		char ch=key.charAt(childKeyPosition);
		Node<T> current=parent.children[ch];
		if (current==null) {
			return null;
		}
		
		if (childKeyPosition==key.length()-1) {
			return current.value;
		} else {
			return get(current, key, childKeyPosition+1);
		}
		
	}
	
	public void delete(String key) {
		if (key.equals("")) {
			if (root.value!=null) {
				size--;
			}
			root.value=null;
			return;
		}
		delete(root, key, 0);
	}
	
	private void delete(Node<T> parent, String key, int childKeyPosition) {
		char ch=key.charAt(childKeyPosition);
		Node<T> current=parent.children[ch];
		if (current==null) {
			return;
		}
		if (childKeyPosition==key.length()-1) {
			//reach the end 
			if (current.value!=null) {
				size--;
			}
			current.value=null;
		} else {
			delete(current, key, childKeyPosition+1);
			//shrink the tree.
			if (current.value==null && !hasChildren(current)) {
				parent.children[ch]=null;
			}
		}
	
		
		
		
	}
	
	private boolean hasChildren(Node<T> node) {
		for (Node<T> child: node.children) {
			if (child!=null) {
				return true;
			} 
		}
		return false;
	}
	
	
	private void put(Node<T> parent, String key, T value, int childKeyPosition) {	
		char ch=key.charAt(childKeyPosition);
		Node<T> current=parent.children[ch];
		if (current==null) {
			//no such children, add one.
			current=new Node<T>();
			parent.children[ch]=current;
		}
		
		
		//we reach the end of the key, 
		if (childKeyPosition==key.length()-1) {
			if (current.value==null) {
				size++;
			}
			current.value=value;
			return;
		} else {
			put(current, key, value, childKeyPosition+1);
		}
	}
	
	
	
	public ArrayList<Entry<T>> collect() {
		ArrayList<Entry<T>> entries=new ArrayList<Entry<T>>(size);
		collect(root, new StringBuilder(""), entries);
		return entries;
	}
	public ArrayList<Entry<T>> collectPrefix(String prefix) {
		ArrayList<Entry<T>> entries=new ArrayList<Entry<T>>(size);
		Node<T> prefixNode=findPrefixNode(root, prefix, 0);
		if (prefixNode==null) {
			return entries;
		}
		collect(prefixNode, new StringBuilder(prefix), entries);
		return entries;
	}
	
	protected void collect(Node<T> start,StringBuilder startkey, ArrayList<Entry<T>> entries) {
		if (start.value!=null) {
			entries.add(new Entry<T>(startkey.toString(), start.value));
		}
		int startKeyLength=startkey.length();
		for (int i=0; i<SymbolLength; i++) {
			if (start.children[i]!=null) {
				collect(start.children[i], startkey.append((char)i), entries);
				startkey.setLength(startKeyLength);
			}
		}
	}
	
	
	
	protected Node<T> findPrefixNode(Node<T> parent, String key, int childKeyPosition){
		char ch=key.charAt(childKeyPosition);
		Node<T> current=parent.children[ch];
		if (current==null) {
			return null;
		}
		
		if (childKeyPosition==key.length()-1) {
			return current;
		} else {
			return findPrefixNode(current, key, childKeyPosition+1);
		}
		
	}
}
