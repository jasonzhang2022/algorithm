package jason;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import jason.datastructure.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.tree.VariableHeightLayoutCache;

import org.junit.Before;
import org.junit.Test;

public class TrieTest {

	String[] inputStrings= {"jason", "ping",   "jasontyy", "jason53", "jasontfd", "", " ", "  ", "ok", "maria"};
	Trie<Boolean> trie;
	
	@Before
	public void setup() {
		
		trie=new Trie<Boolean>();
		for (String str: inputStrings) {
			trie.put(str, true);
			System.out.println("size after add '"+str+"'"+trie.size());
		}
	}
	
	@Test
	public void testSetBasic() {
		
		
		
		assertEquals(trie.size(), inputStrings.length);
		assertTrue(trie.contains("jason"));
		trie.delete("jason");
		assertEquals( trie.size(), inputStrings.length-1);
		assertNull(trie.get("jason"));
		
		trie.put("jason", true);
		assertEquals(inputStrings.length, trie.size());
		assertTrue(trie.contains("jason"));
		
		String[] sorted=Arrays.copyOf(inputStrings, inputStrings.length);
		Arrays.sort(sorted, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	
		//trie are sorted.
		ArrayList<Trie.Entry<Boolean>> entries=trie.collect();
		for (int i=0; i<inputStrings.length; i++) {
			assertEquals(sorted[i], entries.get(i).key);
		}
	}
	
	
	@Test
	public void testSetPrefix() {
		
		Trie<Boolean> trie=new Trie<Boolean>();
		for (String str: inputStrings) {
			trie.put(str, true);
		}
		assertEquals(inputStrings.length, trie.size());
		assertTrue(trie.contains("jason"));
		ArrayList<Trie.Entry<Boolean>> entries=trie.collectPrefix("jason");
		assertEquals(entries.size(),  4);
		assertEquals(entries.get(0).key, "jason");
		assertEquals(entries.get(1).key, "jason53");
		assertEquals(entries.get(2).key, "jasontfd");
		assertEquals(entries.get(3).key, "jasontyy");
		
		entries=trie.collectPrefix("jasont");
		assertEquals(entries.size(),  2);
		assertEquals(entries.get(0).key, "jasontfd");
		assertEquals(entries.get(1).key, "jasontyy");
		
		entries=trie.collectPrefix("no");
		assertEquals(entries.size(),  0);
		
		entries=trie.collectPrefix(" ");
		assertEquals(entries.size(),  2);
		assertEquals(entries.get(0).key, " ");
		assertEquals(entries.get(1).key, "  ");
		
		
	}

}
