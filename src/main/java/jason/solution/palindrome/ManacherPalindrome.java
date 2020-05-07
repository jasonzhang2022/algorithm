package jason.solution.palindrome;

import java.util.Arrays;

//http://tarokuriyama.com/projects/palindrome2.php
/*
 * Idea: 
 * 1. it has filling character to handle the odd case and event uniformly.  Suppose original length of palindrome is n, we will have n+1 fillin chatacter. 
 * The total number of characters is 2n+1. It is odd. We have uniformly odd palindrome
 * 
 * palLen[i]=longest palindrome length at each side for palindrome centered at i 
 * 
 * 2. It proceeds progressively. For each position at i, the palindrome before i are already decided. 
 * We leverage palindrome information before i. 
 * 
 * 3. palLen[i] gives the palindrome length at each side centered at i. If we fold the string around i symmetrically,
 * palLen[i] character at each side matches.
 * 
 * 4.  Once we make final decision (palLen[i]) by extending palindrome centered at i, we can move to next character i+1. 
 *  But instead of moving to i+1, we can make some intelligent decision based on information existing for palLen[k] 
 *  where k<=i
 *   See the setRightValue for detailed description
 *   
 *  
 *   
 * 
 */

/*
 * Also check this question: http://www.programcreek.com/2014/05/leetcode-minimum-size-subarray-sum-java/
 * Given an array of n positive integers and a positive integer s, findUsingArray the minimal length of a subarray of which the sum ≥ s. If there isn't one, return 0 instead.

For example, given the array [2,3,1,2,4,3] and s = 7, the subarray [4,3] has the minimal length of 2 under the problem constraint.

Both solution has this characteristics.
We walk the string/array for a solution: An intermediate solution (may not be the final solution) can be leveraged to
 speed next solution.
 
  
In BoryeMoore pattern search: partial match (match successful so far) is used to speed up walking.   


 */

public class ManacherPalindrome implements LongestPalindromeFinder {

	char delimiter;

	public ManacherPalindrome(char delimiter) {
		super();
		this.delimiter = delimiter;
	}

	public void expand(int[] patLen, char[] chars, int center) {
		do {
			int currentPatLen = patLen[center];
			
			if (center - currentPatLen - 1>=0 && 
					center + currentPatLen + 1<chars.length &&
					(chars[center - currentPatLen - 1] == chars[center + currentPatLen + 1])  
					) {
				patLen[center] = currentPatLen + 1;
			} else {
				break;
			}
		} while (true);
	}

	/**
	 * The next position that should be checked for palindrome.
	 *  Before this function is called, all palindrome length for i<=center are known
	 * @param patLen
	 * @param chars
	 * @param center
	 * @return
	 */
	
	public int setRightValue(int[] patLen, char[] chars, int center) {
		int palLen = patLen[center];
		for (int i = 1; i <= palLen; i++) {
			int leftPosition = center - i;
			int rightPosition = center + i;
			int edgeDistance = palLen - i;

			//case 4 in class description.
			
			//leftPalLen is the palindrome length for center=leftPosition
			int leftPalLen = patLen[leftPosition];
			
			if (leftPalLen < edgeDistance) {
				//try to fold the string around center and visualize the situation.
				/*
				 * palLen[rightPosition] can not be longer than palLen[leftPosition]
				 * 
				 * If it is, palLen[leftPosition] should be extended in the first place.
				 * 
				 * So here we already have the longest palindrome length for rightPosition
				 * we can move to i+1 to see whether it is the potential center.
				 * 
				 * 
				 */
				patLen[rightPosition] = leftPalLen;
			} else if (leftPalLen > edgeDistance) {
				//try to fold the string around center and visualize the situation.
				/*
				 * palLen[rightPosition] can not be longer than edgeDistance.
				 * 
				 * If it is, palLen[center] should be extended in the first place.
				 * 
				 * So here we already have the longest palindrome length for rightPosition
				 * we can move to i+1 to see whether it is the potential center.
				 * 
				 * 
				 */
				patLen[rightPosition] = edgeDistance;
			} else {

				// patLen[rightPosition]>=edgeDistance. Finallly we findUsingArray one
				// position we are not sure about this palindrome length,
				// this is a center with a palindrome which can be extended potentially.
				// but the minimal palindrome length is edgeDistance.
				patLen[rightPosition] = edgeDistance;
				return rightPosition;
			}

		}
		return center + palLen + 1;
	}

	@Override
	public String findLongestPalindrome(String input) {
		//insert delmiter character
		char[] chars = new char[input.length() * 2 + 1];
		for (int i = 0; i < input.length(); i++) {
			chars[i * 2] = delimiter;
			chars[i * 2 + 1] = input.charAt(i);
		}
		chars[chars.length - 1] = delimiter;

		int[] palLen = new int[chars.length];

		
		int currentCenter = 0;
		while (currentCenter < chars.length) {
			
			//extend the pattern
			expand(palLen, chars, currentCenter);
			
			//decide the next center character: jump instead of moving just one.
			currentCenter = setRightValue(palLen, chars, currentCenter);
		}

		int maxPosition = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < palLen.length; i++) {
			if (palLen[i] > max) {
				maxPosition = i;
				max=palLen[i];
			}
		}

		char[] results = Arrays.copyOfRange(chars, maxPosition - max, maxPosition + max + 1);
		StringBuilder sBuilder = new StringBuilder();
		for (char c : results) {
			if (c != delimiter) {
				sBuilder.append(c);
			}
		}

		return sBuilder.toString();
	}

}
