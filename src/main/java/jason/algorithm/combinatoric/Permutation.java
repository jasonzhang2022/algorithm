package jason.algorithm.combinatoric;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Permutation {
	
	
	
	public static class SubArray {
		int[] array;
		int offset;
		
		
		public SubArray(int[] array, int offset) {
			super();
			this.array = array;
			this.offset = offset;
		}
		public int get(int i){
			return array[i+offset];
		}
		public void set(int i, int value){
			array[i+offset]=value;
		}
		
		public void swap(int i, int j){
			int tmp=array[i+offset];
			array[i+offset]=array[j+offset];
			array[j+offset]=tmp;
		}
		
		public int getLength(){
			return array.length-offset;
		}
		
	}
	
	public static void permute(int[] ints, Consumer<int[]> consumer){
		SubArray subArray=new SubArray(ints, 0);
		permute(subArray, consumer);
	}
	
	
	public static void permute(SubArray input, Consumer<int[]> consumer){
		if (input.getLength()==0){
			//nothing to permute
			consumer.accept(input.array);
		}
		for (int i=0; i<input.getLength(); i++){
			//all permutation starts with i;
			input.swap(i,  0);
			
			SubArray child=new SubArray(input.array, input.offset+1 );
			
			permute(child, consumer);
			
			input.swap(0,  i);
		}
		
		
	}
	
	
	public static void permuteAvoidDuplicate(int[] ints, Consumer<int[]> consumer){
		SubArray subArray=new SubArray(ints, 0);
		permuteAvoidDuplicate(subArray, consumer);
	}
	
	
	public static void permuteAvoidDuplicate(SubArray input, Consumer<int[]> consumer){
		if (input.getLength()==0){
			//nothing to permute
			consumer.accept(input.array);
		}
		Set<Integer> processed=new HashSet<Integer>();
		for (int i=0; i<input.getLength(); i++){
			if (processed.contains(input.get(i))){
				continue;
			}
			processed.add(input.get(i));
			input.swap(i,  0);
			
			
			SubArray child=new SubArray(input.array, input.offset+1 );
			
			permuteAvoidDuplicate(child, consumer);
			input.swap(0,  i);
		}
	}
		
	

}
