package jason.algorithm.combinatoric;

import java.util.ArrayList;

/*
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 For example, given n = 3, a solution set is:

 "((()))", "(()())", "(())()", "()(())", "()()()"
 */
public class Parenthesesis {


	public static void main(String[] args) {
		
		printOutput(3);
	}

	private static void printOutput(int input) {

		String str = "{";

		// No of spaces filled till now

		int spacesCovered = 1;

		// No of open braces filled till now

		int noOfOpenBraces = 1;

		// noOfCloseBraces = spacesCovered - noOfOpenBraces

		printParenth(str, spacesCovered, noOfOpenBraces, input);

	}

	public static void printParenth(String str, int spacesCovered,
			int noOfOpenBraces, int input) {

		int noOfCloseBraces = spacesCovered - noOfOpenBraces;

		if (noOfOpenBraces == input) {

			for (int i = 1; i <= 2 * input - spacesCovered; i++) {

				str = str + "}";

			}

			System.out.println(str);

		} else {

			if (noOfCloseBraces < noOfOpenBraces) {

				int tempSpaceCovered = spacesCovered;

				printParenth(str + "}", ++tempSpaceCovered, noOfOpenBraces,
						input);

			}

			printParenth(str + "{", ++spacesCovered, ++noOfOpenBraces, input);

		}

	}

}
