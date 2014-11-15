package jason.datastructure.heap;

public interface Heap<T> {
	// insert
	public void insert(T value, double priority);

	public T delete();

	public T get();

	public int size();
}
