package jason.algorithm.practice;

//LeetCode – Find Minimum in Rotated Sorted Array
public class MinimumInRotatedArray {

	public static int find(int[] input){
		
		return find(input, 0, input.length-1);
		
	}
	
	
	private static int find(int[] input, int from, int to){
		int len=to-from+1;
		int middle=(to-from+1)/2+from;
		if (len>=3){
			if (input[middle]<input[middle-1] && input[middle]<input[middle+1]){
				return input[middle];
			}
		} else {
			if (input[to]>input[from]){
				return input[from];
			} else{
				return input[to];
			}
		}
		
		//len bigger than 3, and middle is not the solution.
		
		if (input[middle]>input[0]){
			return find(input, middle+1, to);
		} else{
			return find(input, from, middle-1);
		}
	}
}
