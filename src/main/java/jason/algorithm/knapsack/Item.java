package jason.algorithm.knapsack;

public class Item {
	int value;
	int weight;
	
	public Item(int value, int weight) {
		super();
		this.value = value;
		this.weight = weight;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
