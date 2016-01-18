package jason.algorithm.recursion;

public class TailRecursionFactorial {

	public int factorial(int i){
		return factorialTailRecursion(i-1, i);
	}
	
	public int factorialTailRecursion(int i, int productSofar){
		if (i==1){
			return productSofar;
		}
		productSofar*=i;
		return factorialTailRecursion(i-1, productSofar);
	}
	
	
	public int iterative(int i){
		int productSoFar=1;
		
		//base case j=1, we stop the loop j>0
		for (int j=i; j>0; j--){
			productSoFar*=j;
		}
		return productSoFar;
	}
}
