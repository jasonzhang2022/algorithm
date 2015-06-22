package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaximumSubArrayProduct {

	//if we have zero, we reset maxXXXWithLastElement
	//assume that no element is zero.
	public static int maximumSubarrayProduct(int[] input){
		
		//remove simple case to simplify process
		if (input.length==1){
			return input[0];
		}
		int allProduct=1;
		for (int i=0; i<input.length; i++){
			allProduct*=input[i];
		}
		
		int max=Integer.MIN_VALUE;
		if (allProduct>0){
			return allProduct;
		}
		
		//all value is negative
		int left=0;
		int right=input.length-1;
		//find the right most index which gives us an positive number.
		for (; right>=0; right--){
			allProduct=allProduct/input[right];
			if(allProduct>0){
				max=Math.max(allProduct, max);
				right--;
				break;
			}
		}
		
		if (right==-1){
			//we can not have a positive number if subarray starting from zero.
			//only one element is included;
			allProduct=input[0];
			max=allProduct;
			right=0;
		}
		
		//the max value is input[0]*input[1]*...*input[right];
		int leftOver=allProduct;
		int newLeft=left;
		for (; newLeft<=right; newLeft++){
			leftOver=leftOver/input[newLeft];
			if ( (leftOver>0 && allProduct<0) || (leftOver<0 && allProduct>0)){
				//switch signed;
				newLeft++;
				break;
			}
		}
		
		int newAllValue=leftOver;
		for (int j=right+1; j<input.length; j++){
			newAllValue*=input[j];
		}
		max=Math.max(newAllValue, max);
		
		
		return max;
		
	}
	
	
	@Test
	public void test(){
		
		//simple test case
		assertEquals(-1, maximumSubarrayProduct(new int[]{-1}));
		
		assertEquals(2, maximumSubarrayProduct(new int[]{2}));
		
		assertEquals(6, maximumSubarrayProduct(new int[]{-2, -3}));
		assertEquals(6, maximumSubarrayProduct(new int[]{2, 3}));
		
		assertEquals(4, maximumSubarrayProduct(new int[]{-2, 4}));
		
		//simple test case: mixed sign
		assertEquals(6, maximumSubarrayProduct(new int[]{2,3,-2,4}));
		assertEquals(24, maximumSubarrayProduct(new int[]{2,3,4, -2}));
		assertEquals(24, maximumSubarrayProduct(new int[]{-2,-3,-2,4}));
		assertEquals(24, maximumSubarrayProduct(new int[]{-2,-3,4, -2}));
		
		
		assertEquals(2*3*2*2*4, maximumSubarrayProduct(new int[]{2,3,-2, -2, 4}));
		
		//second half is bigger than first half
		//2*3*2*3=36
		//3*2*4*5=120
		assertEquals(120, maximumSubarrayProduct(new int[]{2, 3, -2, -3, -2, 4, 5}));
		
		
		//two sign switch
		assertEquals(45, maximumSubarrayProduct(new int[]{2, -2, 3, -3, -5}));
		
	}
	
	
	//http://www.programcreek.com/2014/03/leetcode-maximum-product-subarray-java/
	//solution above
	public  static int maxProduct(int[] A) {
	    if(A==null || A.length==0)  
	        return 0;  
	 
	    int maxLocal = A[0];  
	    int minLocal = A[0];  
	    int global = A[0];  
	 
	    for(int i=1; i<A.length; i++){  
	        int temp = maxLocal;  
	        maxLocal = Math.max(Math.max(A[i]*maxLocal, A[i]), A[i]*minLocal);  
	        minLocal = Math.min(Math.min(A[i]*temp, A[i]), A[i]*minLocal);  
	        global = Math.max(global, maxLocal);  
	    }  
	    return global;
	}
	
	@Test
	public void testMaxproduct(){
		
		//simple test case
		assertEquals(-1, maxProduct(new int[]{-1}));
		
		assertEquals(2, maxProduct(new int[]{2}));
		
		assertEquals(6, maxProduct(new int[]{-2, -3}));
		assertEquals(6, maxProduct(new int[]{2, 3}));
		
		assertEquals(4, maxProduct(new int[]{-2, 4}));
		
		//simple test case: mixed sign
		assertEquals(6, maxProduct(new int[]{2,3,-2,4}));
		assertEquals(24, maxProduct(new int[]{2,3,4, -2}));
		assertEquals(24, maxProduct(new int[]{-2,-3,-2,4}));
		assertEquals(24, maxProduct(new int[]{-2,-3,4, -2}));
		
		
		assertEquals(2*3*2*2*4, maxProduct(new int[]{2,3,-2, -2, 4}));
		
		//second half is bigger than first half
		//2*3*2*3=36
		//3*2*4*5=120
		assertEquals(120, maxProduct(new int[]{2, 3, -2, -3, -2, 4, 5}));
		
		
		//two sign switch
		assertEquals(45, maximumSubarrayProduct(new int[]{2, -2, 3, -3, -5}));
		
	}
	
	public static int maxProductBruteForce(int[] A) {
	    int max = Integer.MIN_VALUE;
	 
	    for(int i=0; i<A.length; i++){
	        for(int l=0; l<A.length; l++){
	            if(i+l < A.length){
	                int product = calProduct(A, i, l);
	                max = Math.max(product, max);
	            }
	 
	        }
	 
	    }
	    return max;
	}
	 
	public static int calProduct(int[] A, int i, int j){
	    int result = 1;
	    for(int m=i; m<=j; m++){
	        result = result * A[m];
	    }
	    return result;
	}
	
	@Test
	public void testMaxproductBruteForce(){
		
		//simple test case
		assertEquals(-1, maxProductBruteForce(new int[]{-1}));
		
		assertEquals(2, maxProductBruteForce(new int[]{2}));
		
		assertEquals(6, maxProductBruteForce(new int[]{-2, -3}));
		assertEquals(6, maxProductBruteForce(new int[]{2, 3}));
		
		assertEquals(4, maxProductBruteForce(new int[]{-2, 4}));
		
		//simple test case: mixed sign
		assertEquals(6, maxProductBruteForce(new int[]{2,3,-2,4}));
		assertEquals(24, maxProductBruteForce(new int[]{2,3,4, -2}));
		assertEquals(24, maxProductBruteForce(new int[]{-2,-3,-2,4}));
		assertEquals(24, maxProductBruteForce(new int[]{-2,-3,4, -2}));
		
		
		assertEquals(2*3*2*2*4, maxProductBruteForce(new int[]{2,3,-2, -2, 4}));
		
		//second half is bigger than first half
		//2*3*2*3=36
		//3*2*4*5=120
		assertEquals(120, maxProductBruteForce(new int[]{2, 3, -2, -3, -2, 4, 5}));
		
		
		//two sign switch
		assertEquals(45, maximumSubarrayProduct(new int[]{2, -2, 3, -3, -5}));
		
	}
	
	
}
