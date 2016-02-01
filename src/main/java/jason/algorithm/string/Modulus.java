package jason.algorithm.string;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
/**
 * We use long type instead of int type to avoid overflow
 * @author jason
 *
 */
public class Modulus {

	//Not tail recursive
	//calculate (num^power)%mod=(num^(power-1) * num )%mod= ((num^(power-1))%mod * num%mod)%mod 
	public static long recursive(long num, int power, long mod) {
		if (power==0){
			return 1;
		}
		if (power==1){
			return num%mod;
		}
		return (num%mod * recursive(num, power-1, mod))%mod;
	}
	
	
	
	//(x*y)%mod=(x%mod*y)%mod
	public static long iterative(long value , int power, long mod){
	    long e = 1;
	    
	    for (int i = 0; i < power; i++) {
	         e = ((e * value) % mod);
	            
	    }
	        
	        return e;
	}
	
	public static long javaMod(long num, int power, long mod){
		return BigInteger.valueOf(num).pow(power).mod(BigInteger.valueOf(mod)).intValue();
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

		long result1=iterative(256,20, 3355439);
		long result2=javaMod(256,20, 3355439);
		long result3=recursive(256,20, 3355439);
		assertEquals(result1, result3);
		assertEquals(result2, result3);
	}
	
	/**
	 * If int type is used here, this test case can't pass
	 */
	@Test
	public void testBig1() {

		long result1=iterative(65536,6, 96293);
		long result2=javaMod(65536,6, 96293);
		long result3=recursive(65536,6, 96293);
		assertEquals(result1, result3);
		assertEquals(result2, result3);
	}
}
