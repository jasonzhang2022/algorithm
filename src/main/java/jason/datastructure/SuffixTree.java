package jason.datastructure;

import java.util.ArrayList;

public class SuffixTree extends Trie<Integer> {

	String inputString;

	public SuffixTree(String str) {
		inputString = str;
		for (int i = 0; i < str.length(); i++) {
			put(str.substring(i), i);
		}
	}

	public int[] toSuffixArray() {
		ArrayList<Entry<Integer>> entries = collect();
		int[] suffixArray = new int[entries.size()];
		for (int i = 0; i < entries.size(); i++) {
			suffixArray[i] = entries.get(i).value;
		}
		return suffixArray;
	}

	public int[] getLCP(int[] suffixArray) {
		// LCP
		// lcp[i]: the longest common prefix between i, and i+1;
		int[] lcp = new int[suffixArray.length - 1];
		for (int j = 1; j < suffixArray.length; j++) {
			lcp[j - 1] = SuffixTree.commonPrefixLen(inputString.substring(suffixArray[j]), inputString.substring(suffixArray[j - 1]));
		}
		return lcp;
	}

	public static int commonPrefixLen(String left, String right) {
		int ret = 0;
		for (int i = 0; i < left.length() && i < right.length(); i++) {
			if (left.charAt(i) == right.charAt(i)) {
				ret = i + 1;
			} else {
				break;
			}
		}
		return ret;
	}

}
