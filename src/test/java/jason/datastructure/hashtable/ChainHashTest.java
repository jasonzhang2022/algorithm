package jason.datastructure.hashtable;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ChainHashTest {
	
	
	@Test
	public void TestChainHash(){
		
		
		ChainHash<String> hash=new ChainHash<String>(16);
		
		hash.put("jason", "number 1");
		hash.put("jason1", "number 2");
		hash.put("jason3", "number 3");
		
		assertEquals(hash.get("jason"), "number 1");
		assertEquals(hash.get("jason1"), "number 2");
		assertEquals(hash.get("jason3"), "number 3");
		assertNull(hash.get("jason4"));
		
		hash.resize(64);
		assertEquals(hash.get("jason"), "number 1");
		assertEquals(hash.get("jason1"), "number 2");
		assertEquals(hash.get("jason3"), "number 3");
		assertNull(hash.get("jason4"));
		
		hash.put("jason3", "number 4");
		assertEquals(hash.get("jason3"), "number 4");
	}

	@Test
	public void TestOpenAddress(){
		
		
		OpenAddress<String> hash=new OpenAddress<String>(3);
		
		hash.put("jason", "number 1");
		hash.put("jason1", "number 2");
		hash.put("jason3", "number 3");
		
		assertEquals(hash.get("jason"), "number 1");
		assertEquals(hash.get("jason1"), "number 2");
		assertEquals(hash.get("jason3"), "number 3");
		assertNull(hash.get("jason4"));
		hash.put("jason3", "number 4");
		assertEquals(hash.get("jason3"), "number 4");
		
		try {
			hash.put("jason4", "number 4");
			fail("can not add more key than 3");
		} catch (Exception e){
			
		}
		
		
	}
	

}
