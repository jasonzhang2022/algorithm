package jason.algorithm.sort;

public class QuickSort {
	public static int[] sort(int[] input) {
		sort(input, 0, input.length-1);
		return input;
	}
	
	
	public static  void swap(int[] input, int i, int j) {
		int temp=input[i];
		input[i]=input[j];
		input[j]=temp;
	}
	
	
	
	
	
	private static void sort(int [] input, int startIndex, int endIndex) {
		//base case:
		if (startIndex>=endIndex) {
			return;
		}
		if (endIndex-startIndex<=20) {
			//for short sequenece using insert sort.
			InsertionSort.sort(input, startIndex, endIndex);
			return;
		}
		int pivotal=input[startIndex];
		
		//move pivotal to end to avoid inteference.
		swap(input, startIndex, endIndex);
		
		//if the values is <=pivotal leave it to  left, otherwise leave it to right
		//endIndex is the index where large value should be placed too.
		//startIndex the next value to be checked.
		
		int begin=startIndex;
		int end=endIndex-1;
		
		while (begin!=end) {
			if (input[begin]>pivotal) {
				swap(input, begin, end);
				end--;
			} else {
				begin++;
			}
		}
		
		//move pivotal back to its position.
		int pivotalPosition;
		if (input[begin]>pivotal) {
			swap(input, begin, endIndex);
			pivotalPosition=begin;
		} else {
			swap(input, begin+1, endIndex);
			pivotalPosition=begin+1;
		}
		
		sort(input, startIndex, pivotalPosition-1);
		sort(input, pivotalPosition+1, endIndex);
	}
}
