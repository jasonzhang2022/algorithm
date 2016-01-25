package jason.algorithm.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
/**
 * 
 * @author jason
 * Give a input array with N number, with the value range between 0 and k, preprocess the input array, answer
 * the question in O(1) time whether there number of value falls between a and b multiple times.
 * 
 * Question: how could be solve this problem is range is big: BST
 * 
 */
public class CountSortApp {

	int[] indices;
	int total;
	public void preprocess(int[] input, int k){
		
		//step 1 count
		indices=new int[k+1];
		for (int value: input){
			indices[value]=indices[value]+1;
		}
		
		//step 2 move count to indices
		int nextStartIndex=0;
		int prevCount=0;
		for (int i=0; i<=k; i++){
			prevCount=indices[i];
			indices[i]=nextStartIndex;
			nextStartIndex+=prevCount;
		}
		total=input.length;
	}
	
	//inclusive
	public int numberInRange(int a, int b){
		int small=Math.min(a, b);
		int big=Math.max(a, b);
		
		if (big<0){
			return 0;
		}
		if (small>=indices.length){
			return 0;
		}
		
		if (small<0){
			small=0;
		}
		if (big>=indices.length){
			big=indices.length-1;
		}
		
		int startIndex=indices[small];
		int endIndex=total;
		if (big<indices.length-1){
			endIndex=indices[big+1];
		}
		return endIndex-startIndex;
	}
	
	@org.junit.Test
	public void test(){
		CountSortApp app=new CountSortApp();
		int[] input={0, 1, 2,3,4,5,6,7, 8, 9, 10, 4,5};
		app.preprocess(input,  10);
		
		assertThat(app.numberInRange(11, 15), equalTo(0));
		assertThat(app.numberInRange(15, 11), equalTo(0));
		
		assertThat(app.numberInRange(-2, -1), equalTo(0));
		assertThat(app.numberInRange(-1, -2), equalTo(0));
		
		assertThat(app.numberInRange(0, 0), equalTo(1));
		assertThat(app.numberInRange(10, 10), equalTo(1));
		
		assertThat(app.numberInRange(4, 4), equalTo(2));
		assertThat(app.numberInRange(4, 5), equalTo(4));
		
		assertThat(app.numberInRange(3, 6), equalTo(6));

		assertThat(app.numberInRange(0, 10), equalTo(13));
		
	}
	
}
