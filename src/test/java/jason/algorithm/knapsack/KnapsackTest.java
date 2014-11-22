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
		

		KnapSack01.TempResult result=null;
		
		//no value.
		result=KnapSack01.max(1, items);
		assertThat(result.items, hasSize(0));
		assertThat(result.value, equalTo(0));
		
		//exact one item
		result=KnapSack01.max(5, items);
		assertThat(result.items, hasSize(1));
		assertThat(result.value, equalTo(3));
		
		//one item, some weight is not used.
		result=KnapSack01.max(6, items);
		assertThat(result.value, equalTo(3));

				
		//two items
		result=KnapSack01.max(12, items);
		assertThat(result.value, equalTo(7));
		
		result=KnapSack01.max(13, items);
		assertThat(result.value, equalTo(8));

		result=KnapSack01.max(14, items);
		assertThat(result.value, equalTo(8));
		
		
		result=KnapSack01.max(17, items);
		assertThat(result.value, equalTo(10));
		
		result=KnapSack01.max(18, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01.max(19, items);
		assertThat(result.value, equalTo(11));
		
		result=KnapSack01.max(20, items);
		assertThat(result.value, equalTo(12));
		
		result=KnapSack01.max(24, items);
		assertThat(result.value, equalTo(14));
		
		result=KnapSack01.max(25, items);
		assertThat(result.value, equalTo(15));
		
		result=KnapSack01.max(30, items);
		assertThat(result.value, equalTo(18));
		
		result=KnapSack01.max(32, items);
		assertThat(result.value, equalTo(19));
		
		result=KnapSack01.max(37, items);
		assertThat(result.value, equalTo(22));
		
		result=KnapSack01.max(40, items);
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

		Set<Item> items = new HashSet<Item>();
		Item item1 = new Item(3, 5);
		Item item2 = new Item(8, 13);
		Item item3 = new Item(7, 12);
		items.add(item1);
		items.add(item2);
		items.add(item3);

		List<Item> resultsItems = null;

		// no value.
		resultsItems = UnboudedKnapsack.max(4, items);
		// item1, item1
		assertThat(resultsItems, hasSize(0));
		assertThat(totalValue(resultsItems), equalTo(0));

		// just one value
		resultsItems = UnboudedKnapsack.max(5, items);
		// item1, item1
		assertThat(resultsItems, hasSize(1));
		assertThat(totalValue(resultsItems), equalTo(3));

		// no exact value
		resultsItems = UnboudedKnapsack.max(6, items);
		// item1, item1
		assertThat(resultsItems, hasSize(1));
		assertThat(totalValue(resultsItems), equalTo(3));

		// two value, item1, item1,
		resultsItems = UnboudedKnapsack.max(10, items);
		assertThat(resultsItems, hasSize(2));
		assertThat(resultsItems, hasItem(item1));
		assertThat(totalValue(resultsItems), equalTo(6));

		// multiple items, weight is in the middle.
		resultsItems = UnboudedKnapsack.max(11, items);
		// item1, item1
		assertThat(resultsItems, hasSize(2));
		assertThat(resultsItems, hasItem(item1));
		assertThat(totalValue(resultsItems), equalTo(6));

		// two choice
		resultsItems = UnboudedKnapsack.max(12, items);
		assertThat(resultsItems, hasSize(1));
		assertThat(resultsItems, hasItem(item3));
		assertThat(totalValue(resultsItems), equalTo(7));

		// three choice
		resultsItems = UnboudedKnapsack.max(13, items);
		assertThat(resultsItems, hasSize(1));
		assertThat(resultsItems, hasItem(item2));
		assertThat(totalValue(resultsItems), equalTo(8));

		// three choices, in the middle
		resultsItems = UnboudedKnapsack.max(14, items);
		assertThat(resultsItems, hasSize(1));
		assertThat(resultsItems, hasItem(item2));
		assertThat(totalValue(resultsItems), equalTo(8));

		// three choice
		resultsItems = UnboudedKnapsack.max(15, items);
		assertThat(resultsItems, hasSize(3));
		assertThat(totalValue(resultsItems), equalTo(9));

		// three choice
		resultsItems = UnboudedKnapsack.max(22, items);
		assertThat(totalValue(resultsItems), equalTo(13));

		// three choice
		resultsItems = UnboudedKnapsack.max(23, items);
		assertThat(totalValue(resultsItems), equalTo(14));
	}

}
