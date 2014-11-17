package jason.algorithm;

import java.util.Random;

public class Shuffler {

	//Implementing Fisher–Yates shuffle
	public static void shuffle(int[] input) {
		Random random=new Random();
		for (int i=input.length-1; i>=0; i--) {
			int j=random.nextInt(i+1);
			int temp=input[i];
			input[i]=input[j];
			input[j]=temp;
		}
	}
	
	public static void shuffle(Integer[] input) {
		Random random=new Random();
		for (int i=input.length-1; i>=0; i--) {
			int j=random.nextInt(i+1);
			int temp=input[i];
			input[i]=input[j];
			input[j]=temp;
		}
	}
}
