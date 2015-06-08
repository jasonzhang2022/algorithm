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
public class KnapSack01DP1 {

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
		
		int count=0;
		ArrayList<Item> items2=new ArrayList<Item>(items);
		for (int i=0; i<items2.size(); i++) {
			values[i]=items2.get(i).value;
			weights[i]=items2.get(i).weight;
		}
		
		
		 // opt[n][w] = max value of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        int[][] totalValue = new int[values.length][allowedWeight+1];
        boolean[][] includedorNot = new boolean[values.length][allowedWeight+1];
        
        
        //initialize the first item,
        for (int w=weights[0]; w<=allowedWeight; w++){
        	totalValue[0][w]=values[0];
        	includedorNot[0][w]=true;
        }
        
        //introduce item 1 to system.
        for (int i=1; i<values.length; i++){
        	//introduce item i into the system.
        	int val=values[i];
        	int weight=weights[i];
        	
        	for (int w=1; w<=allowedWeight; w++){
        		int valueWithoutI=totalValue[i-1][w];
        		int valueWithI=Integer.MIN_VALUE;
        		if (w>=weight){
        			valueWithI=totalValue[i-1][w-weight]+val;
        		}
        		
        		if (valueWithI>valueWithoutI){
        			totalValue[i][w]=valueWithI;
        			includedorNot[i][w]=true;
        		} else{
        			totalValue[i][w]=valueWithoutI;
        			includedorNot[i][w]=false;
        		}
        	}
        	
        	
        }
      
        TempResult result=new TempResult(0, 0);
        result.value=totalValue[values.length-1][allowedWeight];
        result.items=new LinkedList<>();
        int w=allowedWeight;
        for (int itemIndex=values.length-1; itemIndex>=0; itemIndex--) {
        	if (includedorNot[itemIndex][w]) {
        		result.items.add(items2.get(itemIndex));
        		w=w-weights[itemIndex];
        	} 
        }
        
        return result;
        
	}
}
