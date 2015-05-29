package jason.algorithm.knapsack;


//http://www.mathcs.emory.edu/~cheung/Courses/323/Syllabus/DynProg/knapsack2.html

public class UnboudedKnapsack {	
	/*
	 * M(w)= the maximal allowed value given the the weight limitation of w.
	 * M(0)=0;
	 * M(w)=M(wi)+M(W-wi)
	 */
	
	
	public static int max(int[] values, int[] weights, int allowedWeight){
		
		/*
		 * maxValue[i] is the max value for allowedWeight=i;
		 */
		int[] maxValue=new int[allowedWeight+1];
		maxValue[0]=0; //maxValue is zero when weight is zero.
		for (int w=1; w<=allowedWeight; w++){
			
			int wMaxValue=0;
			
			//we calculate the maximal value for allowedWeight=w;
			for(int item=0; item<values.length; item++){
				
				//could the solution contain item 
				
				if (weights[item]>w){
					//item can not be contained since itself is more heavy than w;
					continue;
				}
				
				//if we have item i for the solution, what is the maximal value?
				
				int maxVallueIfItemIncluded=maxValue[w-weights[item]]+values[item];
				if (maxVallueIfItemIncluded>wMaxValue){
					wMaxValue=maxVallueIfItemIncluded;
				}
			}
			maxValue[w]=wMaxValue;
		}
		
		return maxValue[allowedWeight];
	
		
	}
	
}
