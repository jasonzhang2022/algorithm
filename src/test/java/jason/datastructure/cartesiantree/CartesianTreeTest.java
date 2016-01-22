package jason.datastructure.cartesiantree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.PriorityQueue;

import jason.algorithm.util.Shuffler;
import jason.datastructure.cartesiantree.CartesianTree.Node;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class CartesianTreeTest {

	public int inOrder(Node node, int[] output, int nextIndex, int[] input) {
		if (node==null) {
			return nextIndex;
		}
		int myIndex=inOrder(node.leftNode, output, nextIndex, input);
		output[myIndex++]=input[node.index];
		return inOrder(node.rightNode, output, myIndex, input);
	}
	
	
	public void assertHeapOrderProperty(Node node, int[] input) {
		if (node==null) {
			return;
		}
		if (node.leftNode!=null) {
			if (input[node.index]>input[node.leftNode.index]) {
				throw new RuntimeException("Some node has value bigger than left child");
			}
		}
		if (node.rightNode!=null) {
			if (input[node.index]>input[node.rightNode.index]) {
				throw new RuntimeException("Some node has value bigger than right child");
			}
		}
		
		assertHeapOrderProperty(node.leftNode, input);
		assertHeapOrderProperty(node.rightNode, input);
	}
	
	public void testLen(int len) {
		int[] input=new int[len];
		for (int i=0; i<input.length; i++) {
			input[i]=i;
		}
		
		Shuffler.shuffle(input);
		testInput(input);
	}
	
	public void testInput(int[] input) {
		System.out.println(Arrays.toString(input));
		
		//PriorityQueue<Integer> heaPriorityQueue=new PriorityQueue<>(Arrays.asList(ArrayUtils.toObject(input)));
		CartesianTree cartesianTree=new CartesianTree(input);
		
		
		int[] output=new int[input.length];
		inOrder(cartesianTree.root, output, 0, input);
		System.out.println(Arrays.toString(output));
		Arrays.equals(input, output);
		assertHeapOrderProperty(cartesianTree.root, input);
	}

	
	@Test
	public void test() {
		testLen(100);
		
		
	}
	
	@Test
	public void testStatic() {
		int[] input= {7, 0, 6, 9, 8, 1, 5, 2, 3, 4};
		testInput(input);
		
	}

	
}
