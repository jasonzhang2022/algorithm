package jason.datastructure.graph;

public class Vertex {

	public String name;
	public int index;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Vertex(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}
	
	public String toString() {
		return name;
	}
}
