package jason.solution.palindrome;

import jason.datastructure.rmq.DpRMQ;
import jason.datastructure.rmq.ScanRMQ;
import jason.datastructure.rmq.SqrtPartitionRMQ;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PalindromeTest {

	public Map<String, String> inputAndResult;
	
	@Before
	public void setup() {
		inputAndResult=new HashMap<String, String>();
		//basic case
		inputAndResult.put("a", "a");
		inputAndResult.put("aa", "aa");
		
		//odd case
		inputAndResult.put("xax", "xax");
		inputAndResult.put("zxax", "xax");
		inputAndResult.put("zzxax", "xax");
		inputAndResult.put("xaxy", "xax");
		inputAndResult.put("zxaxy", "xax");
		
		inputAndResult.put("xaxzxaxz", "xaxzxax");
		
		//even case
		inputAndResult.put("abba", "abba");
		inputAndResult.put("xabbay", "abba");
		inputAndResult.put("xyabba", "abba");
		inputAndResult.put("xabbayz", "abba");
		inputAndResult.put("xaayzabba", "abba");
		
		
		inputAndResult.put("xabcdefghhgfedcbay", "abcdefghhgfedcba");
		inputAndResult.put("x1x2x3x4x5x6x7abcdefghhgfedcbay2y3y4y5y6y7y8y9y", "abcdefghhgfedcba");
	}
	
	public void test(LongestPalindromeFinder finder) {
		for(Map.Entry<String, String> entry: inputAndResult.entrySet()) {
			assertThat(finder.findLongestPalindrome(entry.getKey()), equalTo(entry.getValue()));
		}
	}
	
	
	@Test
	public void testDPFinder() {
		test(new DPPalindrome());
	}
	
	@Test
	public void testSuffixScanMQ() {
		test(new SuffixPalindrome(ScanRMQ.class, '#'));
	}
	@Test
	public void testSuffixDpMQ() {
		test(new SuffixPalindrome(DpRMQ.class, '#'));
	}
	@Test
	public void testSuffixSqrtMQ() {
		test(new SuffixPalindrome(SqrtPartitionRMQ.class, '#'));
	}
	
	

}
