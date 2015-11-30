package jason.algorithm;

import java.util.Iterator;
import java.util.LinkedList;

public class NumberIncrease {
	
	public static void main(String[] args){
		LinkedList<Integer> nums=new LinkedList<>();
		nums.add(1);
		LinkedList<Integer> prev=nums;
		for (int i=0; i<20; i++){
			
			Iterator<Integer> prevIter=prev.iterator();
			while (prevIter.hasNext()){
				System.out.print(prevIter.next());
			}
			System.out.println("");
			
			LinkedList<Integer> current=new LinkedList<Integer>();
			//initialization
			Iterator<Integer> iter=prev.iterator();
			int startNum=iter.next();
			int count=1;
			while (iter.hasNext()){
				int nextNum=iter.next();
				if (nextNum==startNum){
					count++;
				} else{
					current.add(count);
					current.add(startNum);
					startNum=nextNum;
					count=1;
				}
			}
			current.add(count);
			current.add(startNum);
			prev=current;
		}
	}

}
