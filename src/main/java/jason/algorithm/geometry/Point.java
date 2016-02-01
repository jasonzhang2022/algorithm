package jason.algorithm.geometry;

public class Point{
	double x;
	double y;
	public Point(double d, double e) {
		super();
		this.x = d;
		this.y = e;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result +(int) x;
		result = prime * result + (int)y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	public String toString(){
		return String.format("(%f,%f)", x, y);
	}
	
	public Point minus(Point p){
		return new Point(x-p.x, y-p.y);
	}
	public Point add(Point p){
		return new Point(x+p.x, y+p.y);
	}
	public double dot(Point p){
		return x*p.x+y*p.y;
	}
	
	public double cross(Point p){
		return x*p.y-p.x*y;
	}
	
	public double distance(Point p){
		return Math.sqrt((x-p.x)*(x-p.x) +  (y-p.y)*(y-p.y));
	}
	
	public double magnitude(Point p){
		return distance(p);
	}
	public Point middle(Point p){
		return new Point((x+p.x)/2, (y+p.y)/2);
	}
	
}