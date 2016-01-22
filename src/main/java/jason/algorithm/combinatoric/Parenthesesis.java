package jason.algorithm.combinatoric;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.junit.Test;

/*
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 For example, given n = 3, a solution set is:

 "((()))", "(()())", "(())()", "()(())", "()()()"
 */
public class Parenthesesis {

	/**
	 * 
	 * @param totalNum the number of each {, and } need to be outputed
	 * @param processResult
	 */
	private static void printOutput(int totalNum, Consumer<String> processResult) {

		char[] result=new char[totalNum*2];
		printParenth(result, 0, 0, totalNum, processResult);

	}
	/*
	 * Compare this againt { and } permutation
	 * 
	 * This is like generating all ordered permutation given n { , and n } but
	 * with one restriction that the } can not be placed with a { before it.
	 * 
	 * If we output equal number of { and }, the next char has to be {
	 * if we have k space need filling, and we k unbalanced {, the remaining k elements has to be } 
	 */
	public static void printParenth(char[] result, int numOfLeftBrace,
			int numOfrightBrace, int totalNum, Consumer<String> processResult) {

			if (numOfLeftBrace==totalNum){
				//if we have k space need filling, and we have k unbalanced {, the remaining k elements has to be }
				for (int i=numOfrightBrace+1; i<=totalNum; i++){
					result[numOfLeftBrace+i-1]='}';
				}
				processResult.accept(new String(result));
				return;
			}
			
			if (numOfLeftBrace==numOfrightBrace){
				//if we output equal number of { and }, the next char has to be {
				result[numOfLeftBrace+numOfrightBrace]='{';
				printParenth(result, numOfLeftBrace+1, numOfrightBrace, totalNum, processResult);
				return;
			}
			
			//char at this position can be { or }
			//case 1: use {
			result[numOfLeftBrace+numOfrightBrace]='{';
			printParenth(result, numOfLeftBrace+1, numOfrightBrace, totalNum, processResult);
			
			//case 2: use }
			result[numOfLeftBrace+numOfrightBrace]='}';
			printParenth(result, numOfLeftBrace, numOfrightBrace+1, totalNum, processResult);
	}

	@Test
	public void test() {
		int[] count = new int[1];
		Consumer<String> processResult = (s) -> {
			count[0]++;
			System.out.println(s);
		};

		System.out.printf("----------%d\n", 1);
		printOutput(1, processResult);
		assertEquals(count[0], 1);
		
		count[0]=0;
		System.out.printf("----------%d\n", 2);
		printOutput(2, processResult);
		assertEquals(count[0], 2);
		
		
		count[0]=0;
		System.out.printf("----------%d\n", 3);
		printOutput(3, processResult);
		assertEquals(count[0], 5);
	}
}
