package jason.algorithm.string;

import java.util.Arrays;

public class FiniteAutomata {

	public static int ALPHABET_SIZE = 256;
	static int[][] machine=null;
	public static void process(char[] pattern) {
		machine=new int[pattern.length+1][ALPHABET_SIZE];
		
		for (int state=0; state<pattern.length; state++){
			char nextc=pattern[state];
			Arrays.fill(machine[state], 0);
			for (char c=0; c<ALPHABET_SIZE; c++){
				if (c==nextc){
					machine[state][c]=state+1;
				} else{
					//go back state;
					pattern[state]=c;
					machine[state][c]=retrieveBackState(pattern, state);
					pattern[state]=nextc;
				}
			}
		}
		
	}
	
	
	public static int retrieveBackState(char[] pattern, int prevState){
		int prefixLen=prevState+1;
		int subPrefixLen=prevState;
		
		outer:
		while (subPrefixLen>0){
			for (int i=subPrefixLen-1; i>=0; i--){
				char subPrefixC=pattern[i];
				char prefixC=pattern[prefixLen-1- (subPrefixLen-1 -i) ];
				if (subPrefixC!=prefixC){
					subPrefixLen--;
					break outer;
				}
			}
			//we have a match
			return subPrefixLen;
		}
		return 0;
	}
	
	public static int indexOf(char[] text, char[] pattern) {
		process(pattern);
		
		int currentState=0;
		for (int i=0; i<text.length; i++){
			char c=text[i];
			int nextState=machine[currentState][c];
			if (nextState==pattern.length){
				return i+1-pattern.length;
			}
			currentState=nextState;
		}
		return -1;
	}

}
