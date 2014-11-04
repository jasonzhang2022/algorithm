package jason.datastructure;

public class CountSort {

	
	
	public static int[] countSort(int[] input, int min, int max) {
		int[] counts=new int[max-min+2];
		for (int value: input) {
			counts[value-min+1]++;
		}
		for (int i=1; i<(max-min+2);i++) {
			counts[i]=counts[i]+counts[i-1];
		}
		int[] results=new int[input.length];
		
		for (int value: input) {
			results[counts[value-min]]=value;
			counts[value-min]=counts[value-min]+1;
		}
		return results;
		
	}
}
