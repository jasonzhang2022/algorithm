package jason.algorithm.knapsack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jason.algorithm.knapsack.KnapSack01.TempResult;
/**
 * Compare this with all-pair shortest path problem.
 * @author jason.zhang
 *
 */
public class KnapSack01DP {

	/**
	 * This implementation also illustrates an important principle:
	 * 	transform the data Set here into a data structure to facilitate or improve computation
	 * 
	 *  It is best if we could transform it into primitive array type. In this case, the computation 
	 *  is better.
	 * @param allowedWeight
	 * @param items
	 * @return
	 */
	public static TempResult max(int allowedWeight, Set<Item> items) {
		int[] values=new int[items.size()];
		int[] weights=new int[items.size()];
		

		ArrayList<Item> items2=new ArrayList<Item>(items);
		for (int i=0; i<items2.size(); i++) {
			values[i]=items2.get(i).value;
			weights[i]=items2.get(i).weight;
		}
		
        int[][] totalValue = new int[values.length+1][allowedWeight+1];
        boolean[][] includedorNot = new boolean[values.length+1][allowedWeight+1];
        
        //We introduce item one by one to the system.
        //For each introduced item, we consider the maximal profit
        //for weight=[0:allowedWeight]
        for (int i=0; i<values.length; i++) {
        	//introduce item i into the system.
        	int val=values[i];
        	int weight=weights[i];
        	
        	int itemIndex=i+1;
        	
        	for (int w=1; w<=allowedWeight; w++) {
        		
        		//Zero copy of ith item; i-1 is calculated already
        		int profit1=totalValue[itemIndex-1][w];
        		
        		//one copy of ith item.
        		int profit2=Integer.MIN_VALUE;
        		if (w-weight>=0) {
        			//for a particular targeted weight, add item i does not overflow the targeted weight.
        			profit2=totalValue[itemIndex-1][w-weight]+val;
        		}
        		
        		if (profit1>profit2) {
        			includedorNot[itemIndex][w]=false;
        			totalValue[itemIndex][w]=profit1;
        		} else {
        			includedorNot[itemIndex][w]=true;
        			totalValue[itemIndex][w]=profit2;
        		}
        	}
        }
		
      
        TempResult result=new TempResult(0, 0);
        result.value=totalValue[values.length][allowedWeight];
        result.items=new LinkedList<>();
        int w=allowedWeight;
        for (int itemIndex=values.length; itemIndex>0; itemIndex--) {
        	if (includedorNot[itemIndex][w]) {
        		result.items.add(items2.get(itemIndex-1));
        		w=w-weights[itemIndex-1];
        	} 
        }
        
        return result;
        
	}
}
