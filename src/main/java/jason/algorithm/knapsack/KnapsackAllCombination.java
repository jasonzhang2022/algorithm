package jason.algorithm.knapsack;

import jason.algorithm.knapsack.KnapSack01.TempResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class KnapsackAllCombination {

	
	
	public static TempResult max(int allowedWeight, Set<Item> items) {
		
		List<TempResult> results=new LinkedList<TempResult>();
		
		TempResult result0=new TempResult(0, 0);
		result0.items=new LinkedList<Item>();
		results.add(result0);
		
		TempResult maxValueResult=result0;
		for (Item item: items){
			List<TempResult> resultIncludeNewItem=new LinkedList<>();
			for (TempResult previousResult: results){
				if (item.weight+previousResult.weight<=allowedWeight){
					TempResult newResult=addAndClone(previousResult, item);
					if (newResult.value>maxValueResult.value){
						maxValueResult=newResult;
					}
					resultIncludeNewItem.add(newResult);
				}
			}
			
			results.addAll(resultIncludeNewItem);
		}
		
		return maxValueResult;
		
	}
	
	
	public static TempResult addAndClone(TempResult result, Item item){
		TempResult ret=new TempResult(result.weight+item.weight, result.value+item.value);
		
		ret.items=new LinkedList<>();
		ret.items.addAll(result.items);
		ret.items.add(item);
		return ret;
		
		
	}
}
