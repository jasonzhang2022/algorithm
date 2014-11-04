package jason.solution.palindrome;

import jason.datastructure.SuffixTree;
import jason.datastructure.rmq.RMQ;

public class SuffixPalindrome {
	public static String longPalindrome(String str, RMQ rmq, char delimiter) {
		
		//do not handle the simple case.
		if (str.length()==0) {
			return "";
		}
		if (str.length()==1) {
			return str;
		}
		
		String inputString = str + delimiter + new StringBuilder(str).reverse();
		SuffixTree tree = new SuffixTree(inputString);
		/*
		 * Each element represents the start index of the substring in
		 * inputString. The suffixArray represents all substring in sorted
		 * order.
		 */
		int[] suffixArray = tree.toSuffixArray();

		// LCP
		// lcp[i]: the longest common prefix between i, and i+1;
		int[] lcp = tree.getLCP(suffixArray);
		
		for (int i=0; i<lcp.length; i++) {
			System.out.println(lcp[i]+"\t\t"+inputString.substring(suffixArray[i])+"\t\t"+inputString.substring(suffixArray[i+1]));
		}
		/*
		 * For each substring starting from i, we want to know its position in
		 * suffixarray, SuffixRank is for this purpose. For substring A starting
		 * with i, suffixRank[i] will point to A's index in suffixArray.
		 */
		int[] suffixRank = new int[suffixArray.length];
		for (int i = 0; i < suffixArray.length; i++) {
			int suffixIndex = suffixArray[i];
			suffixRank[suffixIndex] = i;
		}

		// RMQ
		rmq.init(lcp);

		// --------------plaindrome has even length

		/*
		 * str=xabcddcbay, inputstr=xabcddcbay#yabcddcbax strlen=10,
		 * inputstrlen=10+10+1=21; for substring starting from index
		 * 5(dcbay#yabcddcbax) A, we want to the longest common prefix between
		 * this substring and the reversed substring starting from the character
		 * before it in original string B.
		 * 
		 * B is dcbax which suffix starting index is length+length+1-i
		 */
		// .
		//
		int longPosition = 0;
		int longLength = 0;

		for (int i = 1; i < str.length(); i++) {
			int Aindex = i;
			int Bindex = inputString.length() - i;
			int AInSuffixArrayIndex = suffixRank[Aindex];
			int BInSuffixArrayIndex = suffixRank[Bindex];
			int start = AInSuffixArrayIndex;
			int end = BInSuffixArrayIndex;
			if (start > end) {
				start = BInSuffixArrayIndex;
				end = AInSuffixArrayIndex;
			}
			/*
			 * we want to the maxnimum value in lcp from start to end, we want
			 * loop check the LCP from i[start, start+1], ..., [end-1, end], the
			 * last index in array is end01
			 */
			end--;
			int commonLen = rmq.minimum(start, end);
			if (commonLen * 2 > longLength) {
				longLength = commonLen * 2;
				longPosition = Aindex - commonLen;
			}

		}

		// -------------------palindrome has odd length
		/*
		 * str=xabcbay inputstr=xabcbay#yabcbax strlen=7, inputstrlen=7+7+1=15;
		 * for substring starting from index 3(cbay#yabcbax) C, we want to the
		 * longest common prefix between next substring A(bay#yabcbax starting
		 * from 4) and the reversed substring starting from the character before
		 * it in original string B.
		 * 
		 * B is bax which suffix starting index is length+length+1-i
		 */
		for (int i = 1; i < str.length() - 1; i++) {

			int Aindex = i + 1;
			int Bindex = inputString.length() - i;
			int AInSuffixArrayIndex = suffixRank[Aindex];
			int BInSuffixArrayIndex = suffixRank[Bindex];
			int start = AInSuffixArrayIndex;
			int end = BInSuffixArrayIndex;
			if (start > end) {
				start = BInSuffixArrayIndex;
				end = AInSuffixArrayIndex;
			}
			/*
			 * we want to the maxnimum value in lcp from start to end, we want
			 * loop check the LCP from i[start, start+1], ..., [end-1, end], the
			 * last index in array is end01
			 */
			end--;
			int commonLen =  rmq.minimum(start, end);
			if (commonLen>0 && (commonLen * 2 + 1 > longLength)) {
				longLength = commonLen * 2 + 1;
				longPosition = i - commonLen;
			}

		}
		if (longLength==0) {
			return str.substring(0, 1);
		}
		return str.substring(longPosition, longLength+longPosition);

	}
}
