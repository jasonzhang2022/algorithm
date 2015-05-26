package jason.algorithm.knapsack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/**
 * Recursive approach. Do not use optimal substructure.
 * @author jason
 *
 */
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
	
	public static TempResult max(int allowedWeight, Item[] items, int offset){
		if (offset>=items.length){
			//no more item
			TempResult result=new TempResult(0, 0);
			result.items=new ArrayList<>(items.length);
			return result;
		}
		Item item=items[offset];
		
		
		if (item.weight>allowedWeight){
			//item can not be in the final solution, so it is not included
			return max(allowedWeight, items, offset+1);
		}
		
		//solution:not include this item
		TempResult result1=max(allowedWeight, items, offset+1);
		
		//solution contain this item: include
		TempResult result2=max(allowedWeight-item.weight, items, offset+1);
		
		
		//which has a bigger value?
		if (result1.value>(result2.value+item.value)){
			return result1;
		} else {
			result2.weight+=item.weight;
			result2.value+=item.value;
			result2.items.add(item);
			return result2;
		}
		
	}
	
	
		
	
	
}
