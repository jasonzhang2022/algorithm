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

public class Tree24Test {

	Consumer<Tree24.KeyValue<String>> consumer = kv -> System.out.println(kv.key + " ="
			+ kv.value);
	
	DecimalFormat myFormatter = new DecimalFormat("000");

	@Test
	public void testEmptyTree() {
		Tree24<String> tree = new Tree24<>();

		tree.processTree(consumer);

	}

	// only root is populated.
	@Test
	public void testInsertionSimple() {
		Tree24<String> tree = new Tree24<>();

		for (int i = 10; i > 7; i--) {
			tree.insert("jason" + myFormatter.format(i), "jason" + i);
		}
		tree.processTree(consumer);
	}

	// full second level
	@Test
	public void testInsertionSecond() {
		Tree24<String> tree = new Tree24<>();

		for (int i = 10; i > 0; i--) {
			tree.insert("jason" + myFormatter.format(i), "jason" + i);
		}
		tree.processTree(consumer);
	}

	// full second level
	@Test
	public void testInsertionLarge() {
		Tree24<String> tree = new Tree24<>();

		for (int i = 999; i > 0; i--) {
			tree.insert("jason" + myFormatter.format(i), "jason" + i);
		}
		tree.processTree(consumer);
		
		ArrayList<String> values=new ArrayList<>(1000);
		Consumer<Tree24.KeyValue<String>> collector = kv -> values.add(kv.value);
		tree.processTree(collector);
		for (int i=0; i<999; i++){
			assertEquals(values.get(i), "jason"+(i+1));
		}
	}

}
