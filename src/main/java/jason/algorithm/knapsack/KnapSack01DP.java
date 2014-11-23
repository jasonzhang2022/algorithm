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
		int[] profit=new int[items.size()];
		int[] weight=new int[items.size()];
		
		int count=0;
		ArrayList<Item> items2=new ArrayList<Item>(items);
		for (int i=0; i<items2.size(); i++) {
			profit[i]=items2.get(i).value;
			weight[i]=items2.get(i).weight;
		}
		
		
		 // opt[n][w] = max profit of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        int[][] totalProfile = new int[profit.length+1][allowedWeight+1];
        boolean[][] includedorNot = new boolean[profit.length+1][allowedWeight+1];
        
        //We introduce item one by one to the system.
        //For each introduced item, we consider the maximal profit
        //for weight=[0:allowedWeight]
        for (int i=0; i<profit.length; i++) {
        	//introduce item i into the system.
        	int prof=profit[i];
        	int weigh=weight[i];
        	int itemIndex=i+1;
        	
        	for (int w=1; w<=allowedWeight; w++) {
        		
        		//does not include i; i-1 is calculated already
        		int profit1=totalProfile[itemIndex-1][w];
        		
        		//take profile.
        		int profit2=Integer.MIN_VALUE;
        		if (w-weigh>=0) {
        			profit2=totalProfile[itemIndex-1][w-weigh]+prof;
        		}
        		
        		if (profit1>profit2) {
        			includedorNot[itemIndex][w]=false;
        			totalProfile[itemIndex][w]=profit1;
        		} else {
        			includedorNot[itemIndex][w]=true;
        			totalProfile[itemIndex][w]=profit2;
        		}
        	}
        }
		
      
        TempResult result=new TempResult(0, 0);
        result.value=totalProfile[profit.length][allowedWeight];
        result.items=new LinkedList<>();
        int w=allowedWeight;
        for (int itemIndex=profit.length; itemIndex>0; itemIndex--) {
        	if (includedorNot[itemIndex][w]) {
        		result.items.add(items2.get(itemIndex-1));
        		w=w-weight[itemIndex-1];
        	} 
        }
        
        return result;
        
	}
}
