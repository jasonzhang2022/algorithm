package jason.algorithm.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Modulus {

	//Not tail recursive
	//calculate (num^power)%mod=(num^(power-1) * num )%mod= ((num^(power-1))%mod * num%mod)%mod 
	public static int recursive(int num, int power, int mod) {
		if (power==1){
			return num%mod;
		}
		return (num%mod * recursive(num, power-1, mod))%mod;
	}
	
	
	
	//(x*y)%mod=(x%mod*y)%mod
	public static int iterative(int num, int power, int mod) {
		int t=1;
		for (int i=0; i<power; i++ ){
			t=(t*num)%mod;
		}
		return t;
	}
	
	@Test
	public void testModulo() {

		assertEquals(1, recursive(5,3, 4));
		assertEquals(0, recursive(6,3, 4));
	}
	
	
	
	@Test
	public void testIterative1() {

		assertEquals(1, iterative(5,3, 4));
		assertEquals(0, iterative(6,3, 4));
	}
	
	@Test
	public void testBig() {

		int result1=iterative(256,20, 3355439);
		int result3=recursive(256,20, 3355439);
		assertEquals(result1, result3);
	}
}
