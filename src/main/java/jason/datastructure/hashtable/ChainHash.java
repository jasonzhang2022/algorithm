package jason.datastructure.hashtable;

import java.util.ArrayList;
import java.util.ListIterator;
/**
 * Separate chaining for collision resolution
 * 	There can be multiple choice for separate chaining.
 * 	 one can use LinkedList, ordered Array List or self balanced binary tree.
 *   Self-balanced binary tree is only used if lookup time must be guaranteed
 *   But usually, the number of elements in the chian is small. There is not need for complicated data structure.
 *   
 *  The drawback for the hash table itself is that the cost of hash function. For hash table with small 
 *  number of entries, a short list scan may perform well than has table lookup. So don't overuse hashmap for small number
 *   of items.
 *  
 *  Although operations on a hash table take constant time on average, 
 *  the cost of a good hash function can be significantly higher than the inner loop of the
 *   lookup algorithm for a sequential list or search tree. 
 *   Thus hash tables are not effective when the number of entries is very small. 
 *   (However, in some cases the high cost of computing the hash 
 *   function can be mitigated by saving the hash value together with the key.)
 * 
 * For certain string processing applications, such as spell-checking, 
 * hash tables may be less efficient than tries, finite automata, or Judy arrays. 
 * Also, if each key is represented by a small enough number of bits, then, 
 * instead of a hash table, one may use the key directly as the index into an array of values. 
 * Note that there are no collisions in this case.
 * 
 * @author jason
 *
 * @param <T>
 */
public class ChainHash<T> {

	
	public static class Node<T> {
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
	
	public ArrayList<Node<T>>[] slots;
	
	public ChainHash(){
		this(12);
	}
	
	public ChainHash(int initialCapacity){
		slots=new ArrayList[initialCapacity];
	}
	
	
	protected int hash(String key){
		int hashCode=Math.abs(key.hashCode());
		
		//modulu approach
		return hashCode%slots.length;
		
		//another approach is mask approach
		//assume that the size is always of power f two.
	
	}
	
	public void resize(int newCapacity){
		ArrayList<Node<T>>[] oldSlots=slots;
		 
		slots=new ArrayList[newCapacity];
		 for (ArrayList<Node<T>> bucket: oldSlots){
			 if (bucket==null){
				 continue;
			 }
			 for (Node<T> node: bucket){
				 int index=hash(node.key);
				 ArrayList<Node<T>> newBucket=slots[index];
				 if (newBucket==null){
					 newBucket=new ArrayList<Node<T>>(3);
					 slots[index]=newBucket;
				 }
				 //no duplicated key checking
				 newBucket.add(node); 
			 }
		 }
		 
	}
	
	
	public void put(String key, T value){
		int index=hash(key);
		ArrayList<Node<T>> bucket=slots[index];
		if (bucket==null){
			bucket=new ArrayList<Node<T>>(3);
			slots[index]=bucket;
		}
		
		for (Node<T> node: bucket){
			if (node.key.equals(key)){
				node.value=value;
				return;
			}
		}
		bucket.add(new Node<T>(key, value));
	}
	
	public void remove(String key){
		int index=hash(key);
		ArrayList<Node<T>> bucket=slots[index];
		if (bucket==null){
			return;
		}
		for (ListIterator<Node<T>> li=bucket.listIterator(); li.hasNext();){
			Node<T> node=li.next();
			if (node.key.equals(key)){
				li.remove();
				return;
			}
		}
	}
	
	
	public T get(String key){
		int index=hash(key);
		ArrayList<Node<T>> bucket=slots[index];
		if (bucket==null){
			return null;
		}
		
		for (Node<T> node: bucket){
			if (node.key.equals(key)){
				return node.value;
			}
		}
		return null;
	}
	
	
	public boolean isExist(String key){
		int index=hash(key);
		ArrayList<Node<T>> bucket=slots[index];
		if (bucket==null){
			return false;
		}
		
		for (Node<T> node: bucket){
			if (node.key.equals(key)){
				return true;
			}
		}
		return false;
	}
}
