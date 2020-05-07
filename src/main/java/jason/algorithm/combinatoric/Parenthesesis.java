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
		printParenth(result,  0, totalNum, totalNum, processResult);

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
	public static void printParenth(char[] result, int offset,  int remaingLeft, int remaingRight, Consumer<String> processResult){

		if (remaingLeft==0 && remaingRight ==0){
			processResult.accept(String.valueOf(result));
			return;
		}

		// at the offset position, what we can have (, or ),
		// remainingRight can't be less than than remaingLeft. This means we have more right in before

		// can we have left.
		if (remaingLeft!=0){
			result[offset] ='(';
			printParenth(result, offset+1, remaingLeft-1, remaingRight, processResult);
		}
		// can we have right:
		if (remaingRight-1 >=remaingLeft){
			result[offset] =')';
			printParenth(result, offset+1, remaingLeft, remaingRight-1, processResult);
		}


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
