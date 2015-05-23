package jason;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.string.ReverseStringRecursive;

import org.junit.Test;

public class TestReverseCursive {
	
	
	@Test
	public void testEdgeCase() {
		assertThat("", equalTo(ReverseStringRecursive.reverse("")));
		assertThat(" ", equalTo(ReverseStringRecursive.reverse(" ")));
		assertThat("  ", equalTo(ReverseStringRecursive.reverse("  ")));
		assertThat("a", equalTo(ReverseStringRecursive.reverse("a")));
		
		assertThat("abc", equalTo(ReverseStringRecursive.reverse("abc")));
		assertThat("ab", equalTo(ReverseStringRecursive.reverse("ab")));
	}
	
	@Test
	public void testSingleWord() {
		assertThat(" word", equalTo(ReverseStringRecursive.reverse("word ")));
		assertThat("word ", equalTo(ReverseStringRecursive.reverse(" word")));
		assertThat(" word ", equalTo(ReverseStringRecursive.reverse(" word ")));
		assertThat("word1 word2", equalTo(ReverseStringRecursive.reverse("word2 word1")));
		assertThat("\tword ", equalTo(ReverseStringRecursive.reverse(" word\t")));
		assertThat("word   ", equalTo(ReverseStringRecursive.reverse("   word")));
		assertThat("this is a sentences.", equalTo(ReverseStringRecursive.reverse("sentences. a is this")));
	}

}
