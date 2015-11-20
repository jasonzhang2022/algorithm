package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Test;

/*
 * http://www.geeksforgeeks.org/dynamic-programming-set-18-word-wrap/
 * But the formula is sum of all extra spaces
 * 
 */
public class WordWrap {

	
	public static class OneLine {
		int startIndex;
		int endIndex;
		int extraSpaceFromStartIndex;
		public OneLine(int startIndex) {
			super();
			this.startIndex = startIndex;
		}
		public OneLine(int startIndex, int endIndex,
				int extraSpaceFromStartIndex) {
			super();
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.extraSpaceFromStartIndex = extraSpaceFromStartIndex;
		}
		
	}
	
	public static OneLine[] minimalExtraSpace(String[] words, int lineLength){
		//Geeks for Geeks presents word wrap problem
		
		OneLine[] subResult=new OneLine[words.length];
		
		
		//for all the string at the end which can be fit into one line, 
		//we give a cost of zero.
		int length=-1;
		for (int i=words.length-1; i>=0; i--){
			int tempLen=length+1+words[i].length();
			if (tempLen<=lineLength){
				subResult[i]=new OneLine(i, words.length-1, 0);
				length=tempLen;
			} else {
				break;
			}
		}
		
		minimalExtraSpaceSub(words, 0, lineLength, subResult);
		
		return subResult;
		
	}
	
	
	public static int minimalExtraSpaceSub(String[] words, int offset, int lineLength, OneLine[] subResults){
		//Geeks for Geeks presents word wrap problem
		if (offset>=words.length-1){
			return 0;
		}
		
		if (subResults[offset]!=null){
			return subResults[offset].extraSpaceFromStartIndex;
		}
		
		int minExtraSpave=Integer.MAX_VALUE;
		
		int length=-1;
		OneLine oneLine=new OneLine(offset);
		for (int i=offset; i<words.length; i++){
			length=length+1+words[i].length();
			if (length<=lineLength){
				int newMinExtraSpave=minimalExtraSpaceSub(words, i+1, lineLength, subResults)+lineLength-length;
				if (newMinExtraSpave<minExtraSpave){
					oneLine.endIndex=i;
					oneLine.extraSpaceFromStartIndex=newMinExtraSpave;
				}
			} else {
				break;
			}
		}
		subResults[offset]=oneLine;
		return oneLine.extraSpaceFromStartIndex;
		
	}
	
	
	public static String outputLines(String[] words, OneLine[] result, int lineLength){
		StringBuffer sb=new StringBuffer();
		
		for (int i=0; i<result.length; ){
			int charCount=0;
			for (int j=i; j<result[i].endIndex; j++){
				sb.append(words[j]).append(" ");
				charCount+=words[j].length()+1;
			}
			
			sb.append(words[result[i].endIndex]);
			charCount+=words[result[i].endIndex].length();
			if (result[i].endIndex!=words.length-1){
				for (; charCount<lineLength; charCount++){
					sb.append(" ");
				}
			}
			sb.append("\n");
			
			i=result[i].endIndex+1;
		}
		return sb.toString();
		
	}
	
	@Test
	public void testSimple(){
		
		String[] input={"test1", "test2", "test3"};
		String expected="test1\ntest2\ntest3\n";
		String result= outputLines(input, minimalExtraSpace(input, 5), 5);
		assertEquals(expected, result);	
	}
	
	@Test
	public void testSimple1(){
		
		String[] input={"test1", "test2", "test3"};
		String expected="test1 \ntest2 \ntest3 \n";
		String result= outputLines(input, minimalExtraSpace(input, 6), 6);
		assertEquals(expected, result);	
	}
	
	@Test
	public void testSimple2(){
		
		String[] input={"test1", "test2", "test3"};
		String expected="test1 test2\ntest3\n";
		String result= outputLines(input, minimalExtraSpace(input, 11), 11);
		assertEquals(expected, result);	
	}
	
	
	
}
