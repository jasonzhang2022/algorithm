package jason;

import static org.junit.Assert.*;
import jason.datastructure.Treap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class TreapTest {


	@Test
	public void testSimple() {
		int len=20;
		ArrayList<Integer> inputsArrayList=new ArrayList<>(100);
		for (int i=1; i<=len; i++) {
			inputsArrayList.add(i);
		}
		System.out.println(Arrays.toString(inputsArrayList.toArray()));
		Collections.shuffle(inputsArrayList);
		Treap treap=new Treap();
		for (int value: inputsArrayList) {
			treap.add(value);
		}
		
		assertFalse(treap.contains(200));
		
		ArrayList<Integer> sorted=new ArrayList<>(100);
		
		treap.collect(sorted);
		assertEquals(inputsArrayList.size(), sorted.size());
		System.out.println(Arrays.toString(sorted.toArray()));
		
	}
	
	
	@Test
	public void test() {
		ArrayList<Integer> inputsArrayList=new ArrayList<>(100);
		for (int i=1; i<=100; i++) {
			inputsArrayList.add(i);
		}
		Collections.shuffle(inputsArrayList);
		Treap treap=new Treap();
		for (int value: inputsArrayList) {
			treap.add(value);
		}
		
		assertFalse(treap.contains(200));
		
		ArrayList<Integer> sorted=new ArrayList<>(100);
		
		treap.collect(sorted);
		assertEquals(inputsArrayList.size(), sorted.size());
		for (int i=1; i<=100; i++) {
			assertEquals(sorted.get(i-1).intValue(), i);
		}
		
		assertTrue(treap.contains(98));
		for (int i=0; i<10;i++) {
			treap.remove(i*10+3);
		}
		for (int i=0; i<10;i++) {
			assertFalse(treap.contains(i*10+3));
		}
		sorted.clear();
		treap.collect(sorted);
		assertEquals(sorted.size(), 90);
		for (int i=0; i<sorted.size()-1; i++) {
			assertTrue(sorted.get(i)<sorted.get(i+1));
		}
		
	}
	

}
