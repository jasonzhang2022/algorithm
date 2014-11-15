package jason.datastructure.treehash;

import static org.junit.Assert.*;
import jason.datastructure.Shuffler;
import jason.datastructure.treehash.Treap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class TreapTest {


	
	int inputLen=1000;
	@Test
	public void test() {
		ArrayList<Integer> inputsArrayList=new ArrayList<>(inputLen);
		for (int i=1; i<=inputLen; i++) {
			inputsArrayList.add(i);
		}
		Collections.shuffle(inputsArrayList);
		Treap treap=new Treap();
		//1 to 1000 inclusive.
		for (int value: inputsArrayList) {
			treap.add(value);
		}
		
		
		assertTrue(treap.contains(200));
		
		ArrayList<Integer> sorted=new ArrayList<>(inputLen);
		treap.collect(sorted);
		System.out.println(Arrays.deepToString(sorted.toArray()));
		assertEquals(inputsArrayList.size(), sorted.size());
		for (int i=1; i<=inputLen; i++) {
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
		assertEquals(sorted.size(), 990);
		for (int i=0; i<sorted.size()-1; i++) {
			assertTrue(sorted.get(i)<sorted.get(i+1));
		}
		
	}
		
	@Test
	public void testOrderStatistics() {
		int[] input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
		
		Treap treap=new Treap();
		//value from 0-999 inclusive
		for (int value: input) {
			treap.add(value);
		}
		
		assertEquals(input.length, treap.size());
		//select
		assertEquals(0, treap.select(0).intValue());
		assertEquals(50, treap.select(50).intValue());
		assertEquals(500, treap.select(500).intValue());
		assertEquals(999, treap.select(999).intValue());
		
		
		//rank
		assertEquals(0, treap.rank(0));
		assertEquals(50, treap.rank(50));
		assertEquals(500, treap.rank(500));
		assertEquals(999, treap.rank(999));
		
		//make sure everything is right after deletion
		treap.remove(100);
		treap.remove(200);
		treap.remove(300);
		assertEquals(input.length-3, treap.size());
		ArrayList<Integer> sorted=new ArrayList<>(inputLen);
		treap.collect(sorted);
		for (int i=0; i<sorted.size(); i++) {
			System.out.println(i+":"+sorted.get(i));
		}
		assertEquals(0, treap.select(0).intValue());
		assertEquals(50, treap.select(50).intValue());
		assertEquals(500, treap.select(497).intValue());
		assertEquals(999, treap.select(996).intValue());
		
		
		//rank
		assertEquals(0, treap.rank(0));
		assertEquals(50, treap.rank(50));
		assertEquals(497, treap.rank(500));
		assertEquals(996, treap.rank(999));
		
	}
	

}
