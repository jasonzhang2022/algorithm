package jason.algorithm.knapsack;

import java.util.Arrays;

public class UnboundedKnapsackForward {
	
	
	

	public static int max(int[] values, int[] weights, int allowedWeight){
		
		int[] maxValues=new int[allowedWeight+1];
		Arrays.fill(maxValues, 0);
		
		maxValues[0]=0;
		for (int weight=0; weight<=allowedWeight; weight++){
			for (int i=0; i<values.length; i++){
				int itemWeight=weights[i];
				if (weight+itemWeight>allowedWeight){
					continue;
				}
				
				if (maxValues[weight]+values[i]>maxValues[weight+itemWeight]){
					maxValues[weight+itemWeight]=maxValues[weight]+values[i];
				}
			}
		}
		
		return maxValues[allowedWeight];
	}

}
