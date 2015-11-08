package jason.algorithm.select;

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
	public double median(Item[] items){
		return partition(items, 0, items.length-1, 0.5);
	}
	
	
	/*
	 * Assume that endIndex-offset>1
	 */
	public  double partition(Item[] items, int offset, int endIndex, double leftWeightMax){
		
		//end case
		if (endIndex==offset){
			
			System.out.println(Arrays.toString(Arrays.stream(items).mapToDouble(item->{return item.weight;}).toArray()));
			System.out.printf("offset: %d endIndex: %d weightMax: %f\n", offset, endIndex, leftWeightMax);
			
			
			if (items[offset].weight<leftWeightMax){
				//all items' weight <= offset is less than leftWeightMax, but if add next item
				//it is over so next item should be median.
				if (offset+1>=items.length){
					throw new RuntimeException("there is no median item");
				} else  {
					return items[offset+1].weight;
				}
				
			} else{
				//all items' weight below offset is less than leftWeightMax, but if add this item weight, it is over leftWeightMax.
				//this item must be the median
				return items[offset].weight;
			}
		}
		if (endIndex<offset){
			throw new RuntimeException("can not be found");
		}
		
		
		//why plus 2, so the endIndex can be pivotIndex, too.
		int pivotIndex=rand.nextInt(endIndex-offset+1)+offset;
		
		Item pivot=items[pivotIndex];
		//leave the middle item to end.
		swap(items, pivotIndex, endIndex);
		
		
		double leftWeight=0;
		int leftIndex=offset; //next item to inspect
		int rightIndex=endIndex-1; //put larger item
		
		while (leftIndex!=rightIndex){
			if (items[leftIndex].value>pivot.value){
				swap(items, leftIndex, rightIndex);
				rightIndex--;
			} else {
				leftWeight+=items[leftIndex].weight;
				leftIndex++;
			}
		}
		
		int partitionIndex=0;
		if (items[leftIndex].value>pivot.value){
			swap(items, leftIndex, endIndex);
			partitionIndex=leftIndex;
		} else {
			swap(items, leftIndex+1, endIndex);
			leftWeight+=items[leftIndex].weight;
			partitionIndex=leftIndex+1;
		}
		
		if (leftWeight<leftWeightMax) {
			//Is it possible we have a infinite loop here. partitionIndex==offset
			return partition(items, partitionIndex, endIndex, leftWeightMax-leftWeight);
		} else {
			//partitionIndex-1 could be less than offset
			return partition(items, offset, partitionIndex-1, leftWeightMax);
		}
		
	}
	
	
	
	public static void swap(Item[] items, int i, int j){
		Item temp=items[i];
		items[i]=items[j];
		items[j]=temp;
	}
	

	
	
	
	@Test
	public void testTwoElementsBiggerFirst() {
		Item[] items=new Item[2];
		//only one items with median bigger than 1
		items[0]=new Item(0.1, 0.6);
		items[1]=new Item(0.2, 0.4);
		
		 WeightedMedian wm=new WeightedMedian();
		 assertTrue(0.6== wm.median(items));

	}
	
	@Test
	public void testTwoElementsBiggerLater() {
		Item[] items=new Item[2];
		//only one items with median bigger than 1
		items[0]=new Item(0.3, 0.6);
		items[1]=new Item(0.2, 0.4);
		
		 WeightedMedian wm=new WeightedMedian();
		 assertTrue(0.6==wm.median(items));
	}
	
	
	@Test
	public void test() {
		Item[] items=new Item[7];
		//only one items with median bigger than 1
		items[0]=new Item(0.1, 0.1);
		items[1]=new Item(0.35, 0.35);
		
		items[2]=new Item(0.05, 0.05);
		items[3]=new Item(0.1, 0.1);
		
		items[4]=new Item(0.15, 0.15);
		items[5]=new Item(0.05, 0.05);
		
		items[6]=new Item(0.2, 0.2);
		
		 WeightedMedian wm=new WeightedMedian();
		 double median=wm.median(items);
		 System.out.println(median);
		 assertTrue(0.2==median);

	}

}
