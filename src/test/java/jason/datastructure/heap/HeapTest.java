package jason.datastructure.heap;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import jason.algorithm.util.Shuffler;

import static org.hamcrest.CoreMatchers.*;

public class HeapTest {

	int inputLen=1000;
	int[] input;
	@Before
	public void setup() {
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		
		Shuffler.shuffle(input);
	}
	
	public void testHeap(Heap<Integer> heap) {
		//insert all elements in heap
		for (int i=0; i<inputLen; i++) {
			heap.insert(input[i], (double)input[i]);
		}
		//dequeue
		for (int i=0; i<inputLen; i++) {
			int v=heap.delete();
			assertThat(v, equalTo(i));
		}	
	}
	
	@Test
	public void testBinaryHeap() {
		testHeap(new BinaryHeap<Integer>(inputLen));
	}
	
	@Test
	public void testFibonnaciHeap() {
		testHeap(new BinaryHeap<Integer>(inputLen));
	}
	
	@Test
	public void testBuildHeap() {
		int inputLen=1000;
		Node<Integer>[] input=new Node[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=new Node<Integer>(inputLen-i, inputLen-i);
		}
		
		BinaryHeap<Integer> heap=new BinaryHeap<Integer>(input);
		assertThat(heap.delete(), equalTo(1));
		assertThat(heap.delete(), equalTo(2));
		
		
		
	}

}
