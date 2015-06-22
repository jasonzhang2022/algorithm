package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

//http://www.programcreek.com/2014/02/leetcode-best-time-to-buy-and-sell-stock-iii-java/
/*
 * Solution in internet is good, too.
 * 
 * 
 * This solution may be wrong
 */
public class BuyAndSellStock2 {
	
	int[] input;
	public class Profit{
		int lowindex;
		int highindex;
		int minindex;
		
		public int getProfit(){
			return input[highindex]-input[lowindex];
		}
		
		
		
		public Profit()
		{
			
		}
		



		public Profit mergeBenefit(Profit right){
			
			Profit profit=new Profit();
			if (input[minindex]<input[right.minindex]){
				profit.minindex=minindex;
			} else{
				profit.minindex=right.minindex;
			}
			if (minindex!=lowindex){
				profit.lowindex=minindex;
				profit.highindex=right.highindex;	
			} else {
				profit.lowindex=lowindex;
				profit.highindex=right.highindex;
			}
			if (profit.getProfit()<getProfit()){
				//we should keep this;
				if (getProfit()<right.getProfit()){
					//we should keep right;
					profit.lowindex=right.lowindex;
					profit.highindex=right.highindex;
				} else {
					profit.lowindex=lowindex;
					profit.highindex=highindex;
				}
			} else if (profit.getProfit()<right.getProfit()){
					//we should keep right;
					profit.lowindex=right.lowindex;
					profit.highindex=right.highindex;
			} else {
				//we keep the merged one.
			}
			
			return profit;
		}
	}
	
	
	
	//http://www.programcreek.com/2014/02/leetcode-best-time-to-buy-and-sell-stock-iii-java/
	public  int maximum2Transaction(int[] input){
		this.input=input;
		Profit[] profits=new Profit[3];
		profits[0]=scanProfitOpportunity(input, 0);
		if (profits[0]==null){
			return 0;
		}
		
		profits[1]=scanProfitOpportunity(input, profits[0].highindex);
		
		if (profits[1]==null){
			return profits[0].getProfit();
		}
		
		int nextIndex=profits[1].highindex;
		while (nextIndex<input.length){
			Profit profit3=scanProfitOpportunity(input, nextIndex);
			
			if (profit3==null){
				break;
			}
			nextIndex=profit3.highindex;
			profits[2]=profit3;
			threeWayMerge(profits);
			
		}
		
		return profits[0].getProfit()+profits[1].getProfit();
	}
	
	
	
	public void threeWayMerge(Profit[] profits ){
		
		Profit left=profits[0].mergeBenefit(profits[1]);
		Profit right=profits[1].mergeBenefit(profits[2]);
		
		//how much profit reduced bty merge first and second.
		int delta1=profits[0].getProfit()+profits[1].getProfit()-left.getProfit();
		
		int delta2=profits[1].getProfit()+profits[2].getProfit()-right.getProfit();
		if (delta1<delta2){
			//we have less benefit reduced, we should merge left;
			profits[1]=profits[2];
			profits[0]=left;
			profits[2]=null;
		} else{
			//we should merge right.
			profits[1]=right;
			profits[2]=null;
		}
		return;
		
	}
	
	public Profit scanProfitOpportunity(int[] input, int offset){
		Profit profit=new Profit();
		profit.minindex=offset;
		for (int i=offset+1; i<input.length; i++){
			if (input[i]<input[profit.minindex])
			{
				profit.minindex=i;
			}
			if (input[i]>input[i-1]){
				profit.highindex=i;
				profit.lowindex=i-1;
				return profit;
			}
		}
		return null;
	}
	
	@Test 
	public void testMaximumProfit(){
		int[] input={1, 4, 5, 7, 6, 3, 2, 9};
		assertEquals(13, new BuyAndSellStock2().maximum2Transaction(input));
	}
	

	

}
