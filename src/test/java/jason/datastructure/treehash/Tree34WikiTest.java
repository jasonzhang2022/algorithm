package jason.datastructure.treehash;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import jason.datastructure.treehash.Tree24Wiki.KeyValue;

import org.junit.Test;

public class Tree34WikiTest {

	
	Consumer<KeyValue<String>> print=(kv)->System.out.printf("%s=%s\n", kv.key, kv.value);
	
	
	
	@Test
	public void Simpletest() {
		Tree24Wiki tree=new Tree24Wiki();
		System.out.println("---------empty tree------");
		tree.walk(print);
		
		
		for (int i=3; i>0; i--){
			tree.put("jason "+i, "ping"+i);
		}
		System.out.println("---------empty tree------");
		tree.walk(print);
		
	}
	
	@Test
	public void Simpletest1() {
		Tree24Wiki tree=new Tree24Wiki();
		
		DecimalFormat formatter=new DecimalFormat("000");
		for (int i=999; i>0; i--){
			tree.put("jason"+formatter.format(i), "ping"+i);
		}
		ArrayList<String>  arrays=new ArrayList<String>(1000);
		Consumer<KeyValue<String>> collector=(kv)->arrays.add(kv.value);
		tree.walk(collector);
		for (int i=1; i<=999; i++){
			assertEquals("jason"+i, arrays.get(i));
		}
	
		
	}
	
}
