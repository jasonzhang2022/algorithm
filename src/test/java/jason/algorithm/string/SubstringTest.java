package jason.algorithm.string;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SubstringTest {

	public static class Result {
		public int foundIndex;
		public String needle;
		public Result(int foundIndex, String needle) {
			super();
			this.foundIndex = foundIndex;
			this.needle = needle;
		}
		
	}
	
	public HashMap<String, Result> testData=new LinkedHashMap<>();
	
	@Before
	public void setup() {
		
		//-------------single character.
		testData.put("a", new Result(-1, "b"));
		testData.put("aa", new Result(-1, "b"));
		
		//find at beginning
		testData.put("a", new Result(0, "a"));
		testData.put("aa", new Result(0, "a"));
		
		
		//find at end.
		testData.put("ba", new Result(1, "a"));
		testData.put("bba", new Result(2, "a"));

		//find in the middle
		testData.put("bac", new Result(1, "a"));
		testData.put("bbac", new Result(2, "a"));
		
		
		//------------------multiple character
		//negative case
		testData.put("ab", new Result(-1, "ax"));
		testData.put("aab", new Result(-1, "ax"));
	
		testData.put("axb", new Result(0, "ax"));
		testData.put("axab", new Result(0, "ax"));
		
		
		testData.put("abax", new Result(2, "ax"));
		testData.put("aabax", new Result(3, "ax"));
		
		
		testData.put("xaxb", new Result(1, "ax"));
		testData.put("xaaxb", new Result(2, "ax"));
		
		
		
		//big input and pattern
		testData.put("atgfuohgjewyhrehghrfghrabcdefgtagahhdjhjfgef", new Result(23, "abcdefg"));
		
		
		testData.put("abacadabrabracabracadabrabrabracad1", new Result(14, "abracadabra"));
		testData.put("abacadabrabracabracadabrabrabracad2", new Result(8, "rab"));
		testData.put("abacadabrabracabracadabrabrabracad3", new Result(-1, "bcara"));
		testData.put("abacadabrabracabracadabrabrabracad4", new Result(23, "rabrabracad"));
		testData.put("abacadabrabracabracadabrabrabracad5", new Result(0, "abacad"));
		
	}
	
	@Test 
	public void testSimple() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.println(result.needle);
			int k=SubstringSimple.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
			
		}
		
		
	}
	
	
	//http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm#section2
	@Test
	public void testBo() {
		char[] p="ababaa".toCharArray();
		int[] b=new int[p.length+1];
		int i=0, j=-1;
	    b[i]=j;
	    
	    /*
	     * j is the length of widest border of p[0:i-1]=b[i]
	     * 
	     * we try to calculate the border of i+1 here.
	     * 
	     * 
	     * First, can we extend the border by p[i]=p[j], then b[i+1]=j+1;
	     * this is theorem 2.
	     * 
	     *  If s is the widest border of x, the next-widest border r of x is obtained as the widest border of s etc.
	     *  
	     * If we could not, we check the border for p[0:b[j]],
	     * this is theorem 1.
	     */
	    
	    while (i<p.length)
	    {
	        while (j>=0 && p[i]!=p[j]) {
	        	j=b[j];
	        }
	        i++; j++;
	        b[i]=j;
	    }
	}

	@Test 
	public void testBoryeMoore() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.println(result.needle);
			int k=BoryeMoore.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
			
		}
	}
	@Test 
	public void testKMP() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.printf("%s, %s\n",text, result.needle);
			int k=KMP.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
			
		}
	}
	
	@Test 
	public void tesRabinKarp() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.printf("%s, %s\n",text, result.needle);
			int k=RabinKarp.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
			
		}
	}
	
	@Test 
	public void testFA() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.printf("%s, %s\n",text, result.needle);
			int k=FiniteAutomata.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
			
		}
	}
	
	@Test 
	public void tesRabinKarp3() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.printf("%s, %s\n",text, result.needle);
			RabinKarp1 rk=new RabinKarp1(result.needle);
			int k=rk.search(text);
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
			
		}
	}
	
	
	
	@Test 
	public void testHoorspool() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.printf("text=%s, needle=%s\n", text, result.needle);
			int k=Hoorspool.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
		}
	}
	@Test 
	public void testSunday() {
		for (String text: testData.keySet()) {
			Result result=testData.get(text);
			System.out.printf("text=%s, needle=%s\n", text, result.needle);
			int k=Sunday.indexOf(text.toCharArray(), result.needle.toCharArray());
			assertEquals(k, result.foundIndex);
			if (k!=-1) {
				assertEquals(result.needle, text.substring(k, k+result.needle.length()));
			}
		}
	}
}
