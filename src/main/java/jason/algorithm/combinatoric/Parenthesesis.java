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

	/*
	 * This sis like generata all ordered permutation given n { , and n } but
	 * with one restriction that the } can not be placed with a { before it.
	 */
	private static void solution1(int input, Consumer<String> processResult) {
		char[] scratch = new char[input * 2];
		addNextChar(input, input, scratch, input, processResult);
	}

	private static void addNextChar(int leftRemaining, int rightRemaining,
			char[] scratch, int n, Consumer<String> processResult) {
		if (leftRemaining == 0 && rightRemaining == 0) {
			processResult.accept(String.valueOf(scratch));
			return;
		}

		int index = n * 2 - leftRemaining - rightRemaining;

		// at each position we have two choices : add { or }.

		// add }
		if (rightRemaining > 0 // we have } left
				&& rightRemaining > leftRemaining // there are unbalanaced { in
													// left
		) {
			scratch[index] = '}';
			addNextChar(leftRemaining, rightRemaining-1, scratch, n,
					processResult);
		}

		// add { if we can.
		if (leftRemaining > 0) {
			scratch[index] = '{';
			addNextChar(leftRemaining - 1, rightRemaining, scratch, n,
					processResult);
		}

	}

	private static void printOutput(int input, Consumer<String> processResult) {

		String str = "{";

		// No of spaces filled till now

		int spacesCovered = 1;

		// No of open braces filled till now

		int noOfOpenBraces = 1;

		// noOfCloseBraces = spacesCovered - noOfOpenBraces

		printParenth(str, spacesCovered, noOfOpenBraces, input, processResult);

	}

	public static void printParenth(String str, int spacesCovered,
			int noOfOpenBraces, int input, Consumer<String> processResult) {

		int noOfCloseBraces = spacesCovered - noOfOpenBraces;

		if (noOfOpenBraces == input) {

			for (int i = 1; i <= 2 * input - spacesCovered; i++) {

				str = str + "}";

			}
			processResult.accept(str);
		} else {

			if (noOfCloseBraces < noOfOpenBraces) {

				int tempSpaceCovered = spacesCovered;

				printParenth(str + "}", ++tempSpaceCovered, noOfOpenBraces,
						input, processResult);

			}

			printParenth(str + "{", ++spacesCovered, ++noOfOpenBraces, input,
					processResult);

		}

	}

	@Test
	public void test() {
		int[] count = new int[1];
		Consumer<String> processResult = (s) -> {
			count[0]++;
			System.out.println(s);
		};

		printOutput(3, processResult);
		System.out.println("-----");
		int firstCount = count[0];
		count[0] = 0;
		solution1(3, processResult);
		int secondCount = count[0];
		assertEquals(firstCount, secondCount);
	}
}
