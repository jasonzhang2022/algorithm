package jason.algorithm.knapsack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UnboudedKnapsack {
	
	
	public static class TempResult {
		int weight;
		int value;
		LinkedList<Item> items;
		public TempResult(int weight, int value) {
			super();
			this.weight = weight;
			this.value = value;
		}
		
	}
	/*
	 * M(w)= the maximal allowed value given the the weight limitation of w.
	 * M(0)=0;
	 * M(w)=M(wi)+M(W-wi)
	 */
	
	
	public static List<Item> max(int allowedWeight, Set<Item> items){
		
		//TODO decide the max array length needed
		TempResult[] results=new TempResult[allowedWeight+1];
		
		
		LinkedList<TempResult> currentResult=new LinkedList<>();
		TempResult initialResult=new TempResult(0, 0);
		initialResult.items=new LinkedList<>(); //empty;
		results[0]=initialResult;
		
		//we already made decision for weight from [lastLastMaxResult: lastMaxResult]
		int lastMiniWeight=0;
		int lastLastMiniWeight=-1;
		while (lastMiniWeight<allowedWeight) {
			int currentMiniWeight=Integer.MAX_VALUE;
			
			for (int i=lastLastMiniWeight+1; i<=lastMiniWeight; i++) {
				TempResult result=results[i];
				if (result==null) {
					continue; //no exact optimize weight for this weight value.
				}
				for (Item item: items) {
					//extend each maximal weight by one more item.  The decision is tenatative unless the miniWight touched in this round passed it.
					int newWeight=result.weight+item.weight;
					int newValue=result.value+item.value;
					//TODO check = case
					if (newWeight<currentMiniWeight) {
						currentMiniWeight=newWeight;
					}
					
					if (newWeight>allowedWeight) {
						continue; //this is a value we are not interested.
					}
					if (results[newWeight]==null  || results[newWeight].value<newValue ) {
						//we come to an item which could be extends, but could be proved to be a bad result
						TempResult newResult=new TempResult(newWeight, newValue);
						newResult.items=new LinkedList<>(result.items);
						newResult.items.add(item);
						results[newWeight]=newResult;
						
					}
				}
			}
			
			
			//check stop case
			if (currentMiniWeight>=allowedWeight) {
				break;
			}
			
			lastLastMiniWeight=lastMiniWeight;
			lastMiniWeight=currentMiniWeight;
		}
		
		
		
		//results;
		for (int i=allowedWeight; i>=0; i--) {
			if (results[i]!=null) {
				return results[i].items;
			}
		}
		
		
		return Collections.EMPTY_LIST;
		
	}
	
	
	//power set 
}
