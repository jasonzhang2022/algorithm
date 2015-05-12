package jason.algorithm.sort;

import java.util.Arrays;
import java.util.function.Function;

public class RadixSort_LCD_Count {

	public static class extractDigit {
		int digitBase;
		public extractDigit(int d){
			this.digitBase=d;
		}
		
		public int extract(int number){
			return number%digitBase/(digitBase/10);
		}
		
		public boolean hasMoreDigit(int number){
			return number/digitBase>0;
		}
		
		
	}
	public static boolean countSort(int[] input, int[] results, int[] count, extractDigit ex) {
		boolean hasMoreDigit=false;
		for(int obj: input){
			int key=ex.extract(obj);
			if (ex.hasMoreDigit(obj)){
				hasMoreDigit=true;
			}
			
			count[key]=count[key]+1;
		}
		
		
		int prevCount=count[0];
		count[0]=0;
		int currentCount=0;
		for (int i=1; i<count.length; i++){
			currentCount=count[i];
			count[i]=count[i-1]+prevCount;
			prevCount=currentCount;
		}
		
		
		for (int obj: input){
			int key=ex.extract(obj);
			results[count[key]]=obj;
			count[key]++;
		}
		
		return hasMoreDigit;
	}

	public static int[] sort(int[] input){
		
		int[] output=new int[input.length];
		int[] count=new int[10];
		boolean hasMoreDigit=false;
		int digitBase=10;
		extractDigit ex=new extractDigit(digitBase);
		do {
			Arrays.fill(count, 0);
			hasMoreDigit=countSort(input, output, count, ex);
			int[] temp=output;
			output=input;
			input=temp;
			ex.digitBase*=10;
		}while (hasMoreDigit);
		
		return input;
		
	}
}
