package jason.algorithm.knapsack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class KnapSack01 {

	public static class TempResult {
		int weight;
		int value;
		List<Item> items;
		public TempResult(int weight, int value) {
			super();
			this.weight = weight;
			this.value = value;
		}
	}
	
	public static TempResult max(int allowedWeight, Set<Item> items){
		//no item to select
		TempResult result=new TempResult(0, 0);
		result.items=new LinkedList<>();
		if (items.isEmpty()) {
			return result;
		}
		
		
		for (Item item: items) {
			
			Set<Item> setForChildItems=new HashSet<>(items);
			setForChildItems.remove(item);
			if (item.weight>allowedWeight) {
				continue;
			}
			TempResult childResult=max(allowedWeight-item.weight, setForChildItems);
			
			if (childResult.value+item.value>result.value) {
				result=childResult;
				childResult.value=childResult.value+item.value;
				childResult.weight=childResult.weight+item.weight;
				childResult.items.add(item);
			}
			
			
			
		}
		return result;
		
	}
		
	
	
}
