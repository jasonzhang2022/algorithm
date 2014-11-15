package jason.datastructure.treehash;

import static org.junit.Assert.*;
import jason.datastructure.treehash.SkipList;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class SkipListTest {

	@Test
	public void test() {
		ArrayList<Integer> inputsArrayList=new ArrayList<>(100);
		for (int i=1; i<=100; i++) {
			inputsArrayList.add(i);
		}
		Collections.shuffle(inputsArrayList);
		SkipList skipList=new SkipList();
		for (int value: inputsArrayList) {
			skipList.add(value);
		}
		
		assertFalse(skipList.contains(200));
		
		ArrayList<Integer> sorted=new ArrayList<>(100);
		
		skipList.collect(sorted);
		assertEquals(inputsArrayList.size(), sorted.size());
		for (int i=1; i<=100; i++) {
			assertEquals(sorted.get(i-1).intValue(), i);
		}
		
		assertTrue(skipList.contains(98));
		for (int i=0; i<10;i++) {
			skipList.remove(i*10+3);
		}
		for (int i=0; i<10;i++) {
			assertFalse(skipList.contains(i*10+3));
		}
		sorted.clear();
		skipList.collect(sorted);
		assertEquals(sorted.size(), 90);
		for (int i=0; i<sorted.size()-1; i++) {
			assertTrue(sorted.get(i)<sorted.get(i+1));
		}
		
	}
	

}
