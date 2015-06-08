package jason.algorithm.knapsack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;

public class KnapsackTest {

	@Test
	public void test01DP() {

		Set<Item> items = new HashSet<Item>();
		Item item1 = new Item(3, 5);
	
		Item item2 = new Item(8, 13);
		Item item3 = new Item(7, 12);
		Item item4 = new Item(4, 7);
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		

		KnapSack01.TempResult result=null;
		
		//no value.
		result=KnapSack01DP.max(1, items);
		assertThat(result.items, hasSize(0));
		assertThat(result.value, equalTo(0));
		
		//exact one item
		result=KnapSack01DP.max(5, items);
		assertThat(result.items, hasSize(1));
		assertThat(result.value, equalTo(3));
		
		//one item, some weight is not used.
		result=KnapSack01DP.max(6, items);
		assertThat(result.value, equalTo(3));

				
		//two items
		result=KnapSack01DP.max(12, items);
		assertThat(result.value, equalTo(7));
		
		result=KnapSack01DP.max(13, items);
		assertThat(result.value, equalTo(8));

		result=KnapSack01DP.max(14, items);
		assertThat(result.value, equalTo(8));
		
		
		result=KnapSack01DP.max(17, items);
		assertThat(result.value, equalTo(10));
		
		result=KnapSack01DP.max(18, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01DP.max(19, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01DP.max(20, items);
		assertThat(result.value, equalTo(12));
		
		result=KnapSack01DP.max(24, items);
		assertThat(result.value, equalTo(14));
		
		result=KnapSack01DP.max(25, items);
		assertThat(result.value, equalTo(15));
		
		result=KnapSack01DP.max(30, items);
		assertThat(result.value, equalTo(18));
		
		result=KnapSack01DP.max(32, items);
		assertThat(result.value, equalTo(19));
		
		result=KnapSack01DP.max(37, items);
		assertThat(result.value, equalTo(22));
		
		result=KnapSack01DP.max(40, items);
		assertThat(result.value, equalTo(22));
		
	}
	
	@Test
	public void test01DP1() {

		Set<Item> items = new HashSet<Item>();
		Item item1 = new Item(3, 5);
	
		Item item2 = new Item(8, 13);
		Item item3 = new Item(7, 12);
		Item item4 = new Item(4, 7);
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		

		KnapSack01.TempResult result=null;
		
		//no value.
		result=KnapSack01DP1.max(1, items);
		assertThat(result.items, hasSize(0));
		assertThat(result.value, equalTo(0));
		
		//exact one item
		result=KnapSack01DP1.max(5, items);
		assertThat(result.items, hasSize(1));
		assertThat(result.value, equalTo(3));
		
		//one item, some weight is not used.
		result=KnapSack01DP1.max(6, items);
		assertThat(result.value, equalTo(3));

				
		//two items
		result=KnapSack01DP1.max(12, items);
		assertThat(result.value, equalTo(7));
		
		result=KnapSack01DP1.max(13, items);
		assertThat(result.value, equalTo(8));

		result=KnapSack01DP1.max(14, items);
		assertThat(result.value, equalTo(8));
		
		
		result=KnapSack01DP1.max(17, items);
		assertThat(result.value, equalTo(10));
		
		result=KnapSack01DP1.max(18, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01DP1.max(19, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01DP1.max(20, items);
		assertThat(result.value, equalTo(12));
		
		result=KnapSack01DP1.max(24, items);
		assertThat(result.value, equalTo(14));
		
		result=KnapSack01DP1.max(25, items);
		assertThat(result.value, equalTo(15));
		
		result=KnapSack01DP1.max(30, items);
		assertThat(result.value, equalTo(18));
		
		result=KnapSack01DP1.max(32, items);
		assertThat(result.value, equalTo(19));
		
		result=KnapSack01DP1.max(37, items);
		assertThat(result.value, equalTo(22));
		
		result=KnapSack01DP1.max(40, items);
		assertThat(result.value, equalTo(22));
		
	}
	
	@Test
	public void test01() {

		Set<Item> items = new HashSet<Item>();
		Item item1 = new Item(3, 5);
	
		Item item2 = new Item(8, 13);
		Item item3 = new Item(7, 12);
		Item item4 = new Item(4, 7);
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		
		Item[] array= items.toArray(new Item[0]);

		KnapSack01.TempResult result=null;
		
		//no value.
		result=KnapSack01.max(1, array, 0);
		assertThat(result.items, hasSize(0));
		assertThat(result.value, equalTo(0));
		
		//exact one item
		result=KnapSack01.max(5, array, 0);
		assertThat(result.items, hasSize(1));
		assertThat(result.value, equalTo(3));
		
		//one item, some weight is not used.
		result=KnapSack01.max(6,  array, 0);
		assertThat(result.value, equalTo(3));

				
		//two items
		result=KnapSack01.max(12,  array, 0);
		assertThat(result.value, equalTo(7));
		
		result=KnapSack01.max(13,  array, 0);
		assertThat(result.value, equalTo(8));

		result=KnapSack01.max(14,  array, 0);
		assertThat(result.value, equalTo(8));
		
		
		result=KnapSack01.max(17,  array, 0);
		assertThat(result.value, equalTo(10));
		
		result=KnapSack01.max(18,  array, 0);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01.max(19,  array, 0);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01.max(20,  array, 0);
		assertThat(result.value, equalTo(12));
		
		result=KnapSack01.max(24,  array, 0);
		assertThat(result.value, equalTo(14));
		
		result=KnapSack01.max(25,  array, 0);
		assertThat(result.value, equalTo(15));
		
		result=KnapSack01.max(30,  array, 0);
		assertThat(result.value, equalTo(18));
		
		result=KnapSack01.max(32,  array, 0);
		assertThat(result.value, equalTo(19));
		
		result=KnapSack01.max(37,  array, 0);
		assertThat(result.value, equalTo(22));
		
		result=KnapSack01.max(40,  array, 0);
		assertThat(result.value, equalTo(22));
		
	}
	
	@Test
	public void testAllCombination() {


		Set<Item> items = new HashSet<Item>();
		Item item1 = new Item(3, 5);
	
		Item item2 = new Item(8, 13);
		Item item3 = new Item(7, 12);
		Item item4 = new Item(4, 7);
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		

		KnapSack01.TempResult result=null;
		
		//no value.
		result=KnapsackAllCombination.max(1, items);
		assertThat(result.items, hasSize(0));
		assertThat(result.value, equalTo(0));
		
		//exact one item
		result=KnapsackAllCombination.max(5, items);
		assertThat(result.items, hasSize(1));
		assertThat(result.value, equalTo(3));
		
		//one item, some weight is not used.
		result=KnapsackAllCombination.max(6, items);
		assertThat(result.value, equalTo(3));

				
		//two items
		result=KnapsackAllCombination.max(12, items);
		assertThat(result.value, equalTo(7));
		
		result=KnapsackAllCombination.max(13, items);
		assertThat(result.value, equalTo(8));

		result=KnapsackAllCombination.max(14, items);
		assertThat(result.value, equalTo(8));
		
		
		result=KnapsackAllCombination.max(17, items);
		assertThat(result.value, equalTo(10));
		
		result=KnapsackAllCombination.max(18, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapsackAllCombination.max(19, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapsackAllCombination.max(20, items);
		assertThat(result.value, equalTo(12));
		
		result=KnapsackAllCombination.max(24, items);
		assertThat(result.value, equalTo(14));
		
		result=KnapsackAllCombination.max(25, items);
		assertThat(result.value, equalTo(15));
		
		result=KnapsackAllCombination.max(30, items);
		assertThat(result.value, equalTo(18));
		
		result=KnapsackAllCombination.max(32, items);
		assertThat(result.value, equalTo(19));
		
		result=KnapsackAllCombination.max(37, items);
		assertThat(result.value, equalTo(22));
		
		result=KnapsackAllCombination.max(40, items);
		assertThat(result.value, equalTo(22));
		
	}
	
	public int totalValue(List<Item> resultsItem) {
		int totalValue = 0;
		for (Item item : resultsItem) {
			totalValue += item.value;
		}
		return totalValue;
	}

	@Test
	public void testUndounded() {

		int[] values={3, 8,7};
		int[] weights={5, 13, 12};

	
		int maxValue=0;
		// no value.
		maxValue = UnboudedKnapsack.max(values, weights,4);
		assertThat(maxValue, equalTo(0));

		// just one value
		maxValue = UnboudedKnapsack.max(values, weights,5);
		assertThat(maxValue, equalTo(3));

		// no exact value
		maxValue = UnboudedKnapsack.max(values, weights,6);
		assertThat(maxValue, equalTo(3));

		// two value, item1, item1,
		maxValue = UnboudedKnapsack.max(values, weights,10);
		assertThat(maxValue, equalTo(6));
		// multiple items, weight is in the middle.
		maxValue = UnboudedKnapsack.max(values, weights,11);
		assertThat(maxValue, equalTo(6));

		// two choice
		maxValue = UnboudedKnapsack.max(values, weights,12);
		assertThat(maxValue, equalTo(7));

		// three choice
		maxValue = UnboudedKnapsack.max(values, weights,13);
		assertThat(maxValue, equalTo(8));

		// three choices, in the middle
		maxValue = UnboudedKnapsack.max(values, weights,14);
		assertThat(maxValue, equalTo(8));

		// three choice
		maxValue = UnboudedKnapsack.max(values, weights,15);
		assertThat(maxValue,equalTo(9));

		// three choice
		maxValue = UnboudedKnapsack.max(values, weights,22);
		assertThat(maxValue, equalTo(13));

		// three choice
		maxValue = UnboudedKnapsack.max(values, weights,23);
		assertThat(maxValue, equalTo(14));
	}

}
