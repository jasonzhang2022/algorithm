package jason.datastructure.hashtable;

public class OpenAddress<T> {

	
	int stepSize=1;
	public ChainHash.Node<T>[] slots;
	
	public OpenAddress(){
		this(16);
	}
	
	public OpenAddress(int initialCapacity){
		super();
		slots=new ChainHash.Node[initialCapacity];
	}
	protected int hash(String key){
		int hashCode=Math.abs(key.hashCode());
		
		//modulu approach
		return hashCode%slots.length;
		
		//another approach is mask approach
		//assume that the size is always of power f two.
	
	}
	
	public void put(String key, T value){
		int index=hash(key);
		int nextIndex=index;
		
		int tried=0;
		do {
			ChainHash.Node<T> nodeAtIndex=slots[nextIndex];
			if (nodeAtIndex!=null){
				//it is occupied.
				if (nodeAtIndex.key.equals(key)){
					nodeAtIndex.value=value;
					return;
				}
			} else {
				//not occupied, we insert the value
				slots[nextIndex]=new ChainHash.Node<T>(key, value);
				return;
			}
			
			//we reach this step, we could not put value at current index, we need go to next index.
			tried++;
			nextIndex=(index+tried*stepSize)%slots.length;
					
		}  while(tried<=slots.length);
		
		if (tried>slots.length){
			throw new RuntimeException("new slot is available");
		}
	}
	
	
	public T get(String key){
		int index=hash(key);
		int nextIndex=index;
		
		int tried=0;
		do {
			ChainHash.Node<T> nodeAtIndex=slots[nextIndex];
			if (nodeAtIndex!=null){
				//it is occupied.
				if (nodeAtIndex.key.equals(key)){
					return nodeAtIndex.value;
				}
			} else {
				//not occupied, we insert the value
				return null;
			}
			
			//we reach this step, we could not put value at current index, we need go to next index.
			tried++;
			nextIndex=(index+tried*stepSize)%slots.length;
		}  while(tried<=slots.length);
		
		return null;

	}
	
	
	
	
}
