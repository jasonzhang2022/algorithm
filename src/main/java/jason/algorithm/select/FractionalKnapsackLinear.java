package jason.algorithm.select;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FractionalKnapsackLinear {
	
	public static class Item {
		int profit;
		int weight;
		double ratio;
		public Item(int profit, int weight) {
			super();
			this.profit = profit;
			this.weight = weight;
			this.ratio=((double)profit)/((double)weight);
		}
	}
	
	Random rand;
	
	public double  maximumProfit(Item[] items, int targetWeight){
		
		rand=new Random();
		int index=partition(items, 0,  items.length-1, targetWeight);
		if (index>=items.length){
			index=items.length-1;
		}
		double profit=0;
		int weightSoFar=0;
		for (int i=0; i<index; i++){
			weightSoFar+=items[i].weight;
			profit+=items[i].profit;
		}
		if (targetWeight-weightSoFar>items[index].weight){
			profit+=items[index].profit;
		} else{
			profit+=(targetWeight-weightSoFar)*items[index].ratio;
		}
		return profit;
	}
	
	public double  maximumProfitGreedy(Item[] items, int targetWeight){
		double profitSofar=0;
		int weightSofar=0;
		
		Arrays.sort(items, (a, b)->{ return a.ratio==b.ratio?0:(a.ratio>b.ratio?-1:1); });
		int index=0;
		while (weightSofar<targetWeight && index<items.length){
			Item  current=items[index];
			if (current.weight<=(targetWeight-weightSofar)){
				profitSofar+=current.profit;
				weightSofar+=current.weight;
				index++;
			} else {
				profitSofar+=((double)(targetWeight-weightSofar))/items[index].ratio;
				break;
			}
		}
		return profitSofar;
	}
	
	
	
	public int partition(Item[] items, int start, int end, double targetWeight){
		if (start>=end){
			return start;
		}
		
		int i=rand.nextInt(end-start+1)+start;
		int leftWeight=0;
		
		//partiinon by item ration;
		Item pivot=items[i];
		swap(items, i, end);
		int s=start;
		int e=end-1;
		//s is one pass left portion item;
		//small ratio to right;
		while (s!=e){
			if (items[s].ratio<pivot.ratio){
				swap(items, s, e);
				e--;
			} else{
				leftWeight+=items[s].weight;
				s++;
			}
		}
		//s to end is element stritly less than pivot.
		//start to s-1 is greater than or equal to pivot
		if (items[s].ratio<pivot.ratio){
			swap(items, s, end);
			leftWeight+=items[s].weight;
			s++;
		} else {
			//s could be end+1;
			leftWeight+=items[s].weight;
			s++;
			swap(items, s, end);
			leftWeight+=items[s].weight;
			s++;
		}
		
		if (leftWeight<targetWeight){
			return partition(items, s, end, targetWeight-leftWeight);
			
		} else if (leftWeight==targetWeight){
			return s-1;
		} else {
			return partition(items, start, s-1, targetWeight);
		}
	}

	
	
	public void swap(Item[] items, int i, int j){
		Item temp=items[i];
		items[i]=items[j];
		items[j]=temp;
	}
	
	
	public String calulateProfit(Item[] items, int targetWeight, int index){
		if (index>=items.length){
			index=items.length-1;
		}
		double profit=0;
		int weightSoFar=0;
		for (int i=0; i<index; i++){
			weightSoFar+=items[i].weight;
			profit+=items[i].profit;
		}
		if (targetWeight-weightSoFar>items[index].weight){
			profit+=items[index].profit;
		} else{
			profit+=(targetWeight-weightSoFar)*items[index].ratio;
		}
		String profitString=String.format("%.2f", profit);
		return profitString;
	}
	
	@Test
	public void testCanHaveAllItems(){
		Item[] items=new Item[1];
		items[0]=new Item(3,3);
		int targetWeight=5;
		double profit1=maximumProfit(items, targetWeight);
		double profit2=maximumProfitGreedy(items, targetWeight);
		assertThat(String.format("%.2f", profit1), equalTo(String.format("%.2f", profit2)));
	}
	
	@Test
	public void testCanHaveAllItems1(){
		Item[] items=new Item[2];
		items[0]=new Item(3,3);
		items[1]=new Item(3,2);
		int targetWeight=5;
		double profit1=maximumProfit(items, targetWeight);
		double profit2=maximumProfitGreedy(items, targetWeight);
		assertThat(String.format("%.2f", profit1), equalTo(String.format("%.2f", profit2)));
	}
	
	@Test
	public void test(){
		Item[] items=new Item[3];
		items[0]=new Item(3,3); //ratio 1
		items[1]=new Item(2,4); //ratio 0.5
		items[2]=new Item(6,2); //ratio 2
		
		int targetWeight=5;
		
		double profit1=maximumProfit(items, targetWeight);
		double profit2=maximumProfitGreedy(items, targetWeight);
		System.out.printf("%.2f %.2f\n", profit1, profit2);
		assertThat(String.format("%.2f", profit1), equalTo(String.format("%.2f", profit2)));
	}
}
