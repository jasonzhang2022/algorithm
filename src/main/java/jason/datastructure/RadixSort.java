package jason.datastructure;

import java.util.LinkedList;

public class RadixSort {

	static boolean sortToRadix(int[] input, LinkedList<Integer>[]buckets, int position) {
		boolean ret=false;
		int positionMinusOne=position/10;
		for (int value: input) {
			if (value>=position) {
				ret=true;
			}
			int digit=value%position/positionMinusOne;
			buckets[digit].add(value);
		}
		return ret;
		
	}
	
	public static int[] LCD(int[] input) {
		//initialize digit bucket
		LinkedList<Integer>[] buckets=new LinkedList[10];
		for (int i=0; i<10; i++) {
			buckets[i]=new LinkedList<Integer>();
		}
		
		int position=10;
		boolean hasNumberWithSuchPosition=false;
		do {
			hasNumberWithSuchPosition=sortToRadix(input, buckets, position);
			int index=0;
			for (int i=0; i<10; i++) {
				for(int value: buckets[i]) {
					input[index++]=value;
				}
				buckets[i].clear();
			}
			
			position*=10;
		} while (hasNumberWithSuchPosition);
		
		return input;
	}
	
	
}
