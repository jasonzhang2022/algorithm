package jason.datastructure.heap;

public class Node<T> {

	T mElem; // Element being stored here
	double mPriority; // Its priority

	public Node() {
		super();
	}
	
	public Node(T mElem, double mPriority) {
		super();
		this.mElem = mElem;
		this.mPriority = mPriority;
	}

	public T getValue() {
		return mElem;
	}

	public void setValue(T mElem) {
		this.mElem = mElem;
	}

	public double getmPriority() {
		return mPriority;
	}

	public void setmPriority(double mPriority) {
		this.mPriority = mPriority;
	}

}
