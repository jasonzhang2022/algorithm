package jason.datastructure.tree;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import jason.datastructure.tree.Tree24Wiki;
import jason.datastructure.tree.Tree24Wiki.KeyValue;

import org.junit.Test;

public class RedBlackTest {

	
	Consumer<BinaryNode<String>> print=(kv)->System.out.printf("%s=%s\n", kv.key, kv.value);
	
	
	
	@Test
	public void simpleTreTest() {
		RedBlackTree tree=new RedBlackTree();
		System.out.println("---------empty tree------");
		tree.walk(print);
		
		
		for (int i=3; i>0; i--){
			tree.put("jason "+i, "ping"+i);
		}
		System.out.println("---------empty tree------");
		tree.walk(print);
		
	}
	
	
	
	@Test
	public void bigTreeTest() {
		RedBlackTree tree=new RedBlackTree();
		
		DecimalFormat formatter=new DecimalFormat("000");
		for (int i=999; i>0; i--){
			tree.put("jason"+formatter.format(i), "ping"+i);
		}
		System.out.println("---------empty tree------");
		tree.walk(print);
		ArrayList<String>  arrays=new ArrayList<String>(1000);
		Consumer<BinaryNode<String>> collector=(kv)->arrays.add(kv.value);
		tree.walk(collector);
		for (int i=1; i<=999; i++){
			assertEquals("ping"+i, arrays.get(i-1));
		}
	
		
	}
	@Test
	public void replaceTest() {
		RedBlackTree tree=new RedBlackTree();
		
		DecimalFormat formatter=new DecimalFormat("000");
		for (int i=999; i>0; i--){
			tree.put("jason"+formatter.format(i), "ping"+i);
		}
		
		assertEquals(tree.get("jason005"), "ping5");
		tree.put("jason005", "newvalue");
		assertEquals(tree.get("jason005"), "newvalue");
		
	}
	
}
