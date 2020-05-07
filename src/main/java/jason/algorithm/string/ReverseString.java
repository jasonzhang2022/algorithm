package jason.algorithm.string;


/**
 * Reverse every word in the string, but keep order of the word in the string
 * @author jason
 *
 */
public class ReverseString {

	static void rotate(StringBuilder sb, int start, int end) {
		for (int i=start, j=end; i!=j &&i<j; i++,j--) {
			char temp=sb.charAt(j);
			sb.setCharAt(j, sb.charAt(i));
			sb.setCharAt(i, temp);
		}
	}
	
	public static String reverse(String input) {
		StringBuilder sb=new StringBuilder(input).reverse();
		
		//findUsingArray word one by one and rotate.
		int start=0;
		int end=0;
		for (start=0; start<sb.length(); start++) {
			if (Character.isWhitespace(sb.charAt(start))){
				continue;
			}
			//we found start.
			
			for (end=start; end<sb.length(); end++) {
				if (Character.isWhitespace(sb.charAt(end))) {
					break;
				}
			}
			//we found an end or we reach to the end of string.
			rotate(sb, start, end-1);
			start=end;
		}
		return sb.toString();
	}
}
