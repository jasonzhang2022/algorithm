package jason.datastructure.tree;

import static org.junit.Assert.*;
import jason.algorithm.Shuffler;
import jason.datastructure.tree.Treap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import org.junit.Test;

public class TreapTest {


	DecimalFormat formatter=new DecimalFormat("000");
	int inputLen=1000;
	@Test
	public void test() {
		ArrayList<Integer> inputsArrayList=new ArrayList<>(inputLen);
		for (int i=0; i<inputLen; i++) {
			inputsArrayList.add(i);
		}
		Collections.shuffle(inputsArrayList);
		Treap<Integer> treap=new Treap<Integer>();
		//0 to 999 inclusive.
		for (int value: inputsArrayList) {
			treap.put(formatter.format(value), value);
		}
		
		
		assertNotNull(treap.get("200"));
		ArrayList<Integer>  arrays=new ArrayList<Integer>(1000);
		Consumer<BinaryNode<Integer>> collector=(kv)->arrays.add(kv.value);
		treap.walk(collector);
		

		assertEquals(arrays.size(), inputsArrayList.size());
		for (int i=0; i<inputLen; i++) {
			assertEquals(arrays.get(i).intValue(), i);
		}
		
		assertNotNull(treap.get("098"));
		for (int i=0; i<10;i++) {
			treap.remove(formatter.format(i*10+3));
		}
		for (int i=0; i<10;i++) {
			assertNull(treap.get(formatter.format(i*10+3)));
		}
		arrays.clear();
		treap.walk(collector);
		for (int i=0; i<arrays.size()-1; i++) {
			assertTrue(arrays.get(i)<arrays.get(i+1));
		}
		
	}
		
	
	
	@Test
	public void testRemove() {
		int inputLen=5;
		ArrayList<Integer> inputsArrayList=new ArrayList<>(inputLen);
		for (int i=0; i<inputLen; i++) {
			inputsArrayList.add(i);
		}
		Collections.shuffle(inputsArrayList);
		Treap<Integer> treap=new Treap<Integer>();
		//0 to 999 inclusive.
		for (int value: inputsArrayList) {
			treap.put(formatter.format(value), value);
		}
		;
		for (int i=0; i<inputLen;i++) {
			treap.remove(formatter.format(i));
			assertNull(treap.get(formatter.format(i)));
		}
		
	}
		

}
