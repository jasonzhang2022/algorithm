package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

//http://www.programcreek.com/2013/02/leetcode-longest-substring-without-repeating-characters-java/
public class LongestStringWithoutRepeatCharacter {

	public static int find(String str){
		if (str==null || str.length()==0){
			return 0;
		}
		int[] charTable=new int[256];
		
		Arrays.fill(charTable, -1);
		int max=1;
		charTable[str.charAt(0)]=0;
		int startIndex=0;
		for (int i=1; i<str.length(); i++){
			if (charTable[str.charAt(i)]!=-1){
				//exists
				//end current substring
				int newLength=i-1-startIndex+1;
				if (newLength>max){
					max=newLength;
				}
				int oldIndex=charTable[str.charAt(i)];
				for (int j=startIndex; j<=oldIndex; j++){
					charTable[j]=-1;
				}
				startIndex=oldIndex+1;
				charTable[str.charAt(i)]=i;
			} else {
				charTable[str.charAt(i)]=i;
			}
		}
		if (str.length()-startIndex>max){
			max=str.length()-startIndex;
		}
		
		return max;
	}
	
	@Test
	public void test(){
		assertEquals(1, find("aaaa"));
		assertEquals(3, find("abcabcbb"));
		assertEquals(7, find("abcdeafg"));
		
		assertEquals(8, find("xyabcdeafgh"));
	}
	
}
