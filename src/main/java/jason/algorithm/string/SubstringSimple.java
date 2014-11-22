package jason.algorithm.string;

import java.util.Arrays;

//BoryeMoore only with bad character rule.
public class SubstringSimple {

	public static int ALPHABET_SIZE=256;
	
	public static int[] buildBadCharacterTable(char[] pattern){
		int[] table=new int[ALPHABET_SIZE];
		Arrays.fill(table,-1);
		//the right most index of for character c
		for (int i=0; i<pattern.length; i++) {
			table[pattern[i]]=i;
		}
		
		return table;
	}
	
	
	public static int indexOf(char[]text, char[] pattern) {
		int[] badTable=buildBadCharacterTable(pattern);
		
		int k=pattern.length-1;
		
		
		while (k<text.length) {
			//check character backward from K.
			int stopIndex=checkMatch(text, pattern, k);
			if (k-stopIndex==pattern.length) {
				return stopIndex+1;
			}
			k=decideNextK(text, pattern, k, stopIndex, badTable);
		}
		
		return -1;
	}
	
	public static int decideNextK(char[] text, char[] pattern, int k, int stopIndex, int[] table) {
		int StopIndexInPattern=pattern.length-1-(k-stopIndex);
		int badChar=text[stopIndex];
		int rightMostIndex=table[badChar];
		//does not exists
		if (rightMostIndex==-1) {
			return stopIndex+pattern.length;
		} else {
			//make the right most character align with this one.
			int t=StopIndexInPattern-rightMostIndex;
			if (t<0) {
				//for negative shift, shift at least one.
				t=1;
			}
			return k+t;
			
		}
		
		
	}



	/**
	 * 
	 * @param text
	 * @param pattern
	 * @param k
	 * @return the index that stops match. This index <k.
	 *  If it is k-pattern, this is a match.  
	 * 
	 */
	public static int checkMatch(char[] text, char[] pattern, int k) {
		int i=0;
		for (; i<pattern.length; i++) {
			if (text[k-i]!=pattern[pattern.length-i-1]) {
				return k-i;
			}
		}
		return k-i;
	}
	
}
