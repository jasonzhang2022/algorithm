package jason.algorithm.string;

import java.util.Arrays;

public class ReverseStringRecursive {

	
	public static String reverse(String input) {
		char[] inputc=input.toCharArray();
		char[] output=new char[inputc.length];
		
		reverse(inputc, output, output.length, 0);
		
		return String.valueOf(output);
		
	}
	public static void reverse(char[] input, char[] output, int outputOffset, int inputOffset) {
		
		int start=inputOffset;
		int end=inputOffset;
		
		//handle any white space at beginning
		while (end<input.length && Character.isWhitespace(input[end])){
			end++;
		}
		
		//copy, not include end index
		int length=end-start;
		outputOffset=outputOffset-length;
		System.arraycopy(input, start, output, outputOffset, length);
		start=end;
		
		while (end<input.length && !Character.isWhitespace(input[end])){
			end++;
		}
		length=end-start;
		outputOffset=outputOffset-length;
		System.arraycopy(input, start, output, outputOffset, length);
		
		if (end<input.length){
			reverse(input, output, outputOffset, end);
		}
	}
	
	
	
	
	
	
	
	
}
