package jason;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import jason.algorithm.string.ReverseString;

import org.junit.Test;

public class TestReverse {

	@Test
	public void testNullinput() {
		String inputString=null;
		
		try {
			ReverseString.reverse(null);
			fail("method SHOULD not handle null input");
		} catch (NullPointerException e) {
			
		}
	}
	
	
	@Test
	public void testEdgeCase() {
		assertThat("", equalTo(ReverseString.reverse("")));
		assertThat(" ", equalTo(ReverseString.reverse(" ")));
		assertThat("  ", equalTo(ReverseString.reverse("  ")));
		assertThat("a", equalTo(ReverseString.reverse("a")));
		
		assertThat("abc", equalTo(ReverseString.reverse("abc")));
		assertThat("ab", equalTo(ReverseString.reverse("ab")));
	}
	
	@Test
	public void testSingleWord() {
		assertThat(" word", equalTo(ReverseString.reverse("word ")));
		assertThat("word ", equalTo(ReverseString.reverse(" word")));
		assertThat(" word ", equalTo(ReverseString.reverse(" word ")));
		assertThat("word1 word2", equalTo(ReverseString.reverse("word2 word1")));
		assertThat("\tword ", equalTo(ReverseString.reverse(" word\t")));
		assertThat("word   ", equalTo(ReverseString.reverse("   word")));
		assertThat("this is a sentences.", equalTo(ReverseString.reverse("sentences. a is this")));
	}

}
