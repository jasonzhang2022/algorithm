package jason.algorithm.select;

//https://en.wikipedia.org/wiki/Weighted_median
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;
/**
 * What do we learn from this problem?
 * What partition do we select after partition? 
 * kth element: we use length of partition.
 * weighted median: we use the weight of the partition.
 * 
 * @author jason
 *
 */
public class WeightedMedian {
	
	public static class Item {
		double value;
		double weight;
		public Item(double value, double weight) {
			super();
			this.value = value;
			this.weight = weight;
		}
		
		
	}
	
	
	Random rand=new Random();
	public Item median(Item[] items){
		return partition(items, 0, items.length-1, 0);
	}
	
	
	/*
	 * Assume that endIndex-offset>1
	 */
	public  Item partition(Item[] items, int offset, int endIndex, double leftPercentage ){

		//base case
		if (offset==endIndex){
			// only has only choice.
			return items[offset];
		}

		// pivotal is [offset, endIndex)
		int pivotalIndex = rand.nextInt(endIndex-offset) + offset;
		Item pivotal = items[pivotalIndex];
		swap(items, pivotalIndex, endIndex);

		int start = offset;
		int end = endIndex-1;

		double leftP = leftPercentage;

		while (start<=end) {
			if (items[start].value <pivotal.value) {
				leftP +=items[start].weight;
				start++;
			} else {
				swap(items, start, end);
				end--;
			}
		}

		swap(items, start, endIndex);

		// middle is divide point.
		// all items at the left side <= middle
		// all items at the right side >= middle
		int middle = start;
		if (leftP<0.5 && leftP+items[middle].weight>=0.5){
			return items[middle];
		}

		// based one our loop condition
		// middle > start; Middle can == end

		// which side should be go?
		if (leftP<0.5) {
			// we need more weight from right side
			return partition(items, middle, endIndex, leftPercentage);
		} else {
			// we need to remove more weight at left side
			// middle -1 will be >=start.
			// The order between offset and middle-1 is undefined.
			return partition(items, offset, middle-1, leftPercentage);
		}
	}
	
	
	
	public static void swap(Item[] items, int i, int j){
		Item temp=items[i];
		items[i]=items[j];
		items[j]=temp;
	}
	


	public static class TestCase {
		@Test
		public void testTwoElementsBiggerFirst() {
			Item[] items = new Item[2];
			//only one items with median bigger than 1
			items[0] = new Item(0.1, 0.6);
			items[1] = new Item(0.2, 0.4);

			WeightedMedian wm = new WeightedMedian();
			assertTrue(0.6 == wm.median(items).weight);

		}

		@Test
		public void testTwoElementsBiggerLater() {
			Item[] items = new Item[2];
			//only one items with median bigger than 1
			items[0] = new Item(0.3, 0.6);
			items[1] = new Item(0.2, 0.4);

			WeightedMedian wm = new WeightedMedian();
			assertTrue(0.6 == wm.median(items).weight);
		}


		@Test
		public void test() {
			Item[] items = new Item[7];
			//only one items with median bigger than 1
			items[0] = new Item(0.1, 0.1);
			items[1] = new Item(0.35, 0.35);

			items[2] = new Item(0.05, 0.05);
			items[3] = new Item(0.1, 0.1);

			items[4] = new Item(0.15, 0.15);
			items[5] = new Item(0.05, 0.05);

			items[6] = new Item(0.2, 0.2);

			WeightedMedian wm = new WeightedMedian();
			double median = wm.median(items).weight;
			System.out.println(median);
			assertTrue(0.2 == median);

		}
	}

}
