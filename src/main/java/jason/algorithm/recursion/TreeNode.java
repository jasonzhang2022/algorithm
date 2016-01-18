package jason.algorithm.recursion;


public class TreeNode<T> {
	T val;
	TreeNode<T> left;
	TreeNode<T> right;

	TreeNode(T x) {
		val = x;
	}
	
	public String toString(){
		String ret=val.toString();
		if (left!=null || right!=null){
			ret+="(";
				if (left!=null){
					ret+=left.toString();
				}
				ret+=",";
				if (right!=null){
					ret+=right.toString();
				}
			ret+=")";
		}
		return ret;
	}
}
