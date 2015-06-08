package jason;

import static org.junit.Assert.assertEquals;
import jason.solution.integermultiple.ClassisMultiplier;
import jason.solution.integermultiple.GridMultipler;
import jason.solution.integermultiple.Helper;
import jason.solution.integermultiple.KaratsubaMultiplier1;
import jason.solution.integermultiple.Multiplier;

import java.math.BigInteger;

import org.junit.Test;

public class BigIntegerTest {

	
	@Test
	public void testAddition() {
		assertEquals(new BigInteger(Helper.add("0", "0")), new BigInteger("0"));
		assertEquals(new BigInteger(Helper.add("0", "1")), new BigInteger("1"));
		assertEquals(new BigInteger(Helper.add("1", "2")), new BigInteger("3"));
		assertEquals(new BigInteger(Helper.add("1", "9")), new BigInteger("10"));
		assertEquals(new BigInteger(Helper.add("1", "10")), new BigInteger("11"));
		BigInteger op1=new BigInteger("345678901234567899");
		BigInteger op2=new BigInteger("123567890622456678");
		assertEquals(new BigInteger(Helper.add(op1.toString(), op2.toString())), op1.add(op2));
	}
	
	@Test
	public void testMinus() {
		assertEquals(new BigInteger(Helper.minus("0", "0")), new BigInteger("0"));
		assertEquals(new BigInteger(Helper.minus("1", "0")), new BigInteger("1"));
		assertEquals(new BigInteger(Helper.minus("5", "2")), new BigInteger("3"));
		assertEquals(new BigInteger(Helper.minus("9", "9")), new BigInteger("0"));
		assertEquals(new BigInteger(Helper.minus("13", "9")), new BigInteger("4"));
		assertEquals(new BigInteger(Helper.minus("133", "19")), new BigInteger("114"));
		BigInteger op1=new BigInteger("345678901234567899");
		BigInteger op2=new BigInteger("123567890622456678");
		assertEquals(new BigInteger(Helper.minus(op1.toString(), op2.toString())), op1.subtract(op2));
	}
	
	public void test(Multiplier multiplier) {

		assertEquals(new BigInteger(multiplier.multiple("0", "0")), new BigInteger("0"));
		assertEquals(new BigInteger(multiplier.multiple("0", "12345")), new BigInteger("0"));
		assertEquals(new BigInteger(multiplier.multiple("3", "5")), new BigInteger("15"));
		assertEquals(new BigInteger(multiplier.multiple("1", "32")), new BigInteger("32"));
		assertEquals(new BigInteger(multiplier.multiple("32", "1")), new BigInteger("32"));
		assertEquals(new BigInteger(multiplier.multiple("432", "7")), new BigInteger(new Integer(432*7).toString()));
		
		BigInteger op1=new BigInteger("345678901234567899");
		BigInteger op2=new BigInteger("123567890622456678");
		BigInteger rBigInteger=op1.multiply(op2);
		assertEquals(new BigInteger(multiplier.multiple(op1.toString(), op2.toString())), rBigInteger);
		
	}
	
	@Test
	public void testClassic() {
		test(new ClassisMultiplier());
	}
	
	@Test
	public void testGrid() {
		test(new GridMultipler());
	}
	
	@Test
	public void testKaratsuba1() {
		test(new KaratsubaMultiplier1());
	}
	
}
