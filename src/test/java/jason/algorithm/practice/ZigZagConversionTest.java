package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;
public class ZigZagConversionTest {

	@Test
	public void testSimpleOdd(){
		
		assertEquals("", ZigZagConversion.convert("", 3));
		assertEquals("1", ZigZagConversion.convert("1", 3));
		assertEquals("12", ZigZagConversion.convert("12", 3));
		assertEquals("123", ZigZagConversion.convert("123", 3));
		assertEquals("1243", ZigZagConversion.convert("1234", 3));
		assertEquals("15243", ZigZagConversion.convert("12345", 3));
		assertEquals("152463", ZigZagConversion.convert("123456", 3));
		assertEquals("1524637", ZigZagConversion.convert("1234567", 3));
		assertEquals("15246837", ZigZagConversion.convert("12345678", 3));
		assertEquals("159246837", ZigZagConversion.convert("123456789", 3));
		assertEquals("1592468a37", ZigZagConversion.convert("123456789a", 3));
	}
	@Test
	public void testSimpleOdd1(){
		
		assertEquals("", ZigZagConversion.convert1("", 3));
		assertEquals("1", ZigZagConversion.convert1("1", 3));
		assertEquals("12", ZigZagConversion.convert1("12", 3));
		assertEquals("123", ZigZagConversion.convert1("123", 3));
		assertEquals("1243", ZigZagConversion.convert1("1234", 3));
		assertEquals("15243", ZigZagConversion.convert1("12345", 3));
		assertEquals("152463", ZigZagConversion.convert1("123456", 3));
		assertEquals("1524637", ZigZagConversion.convert1("1234567", 3));
		assertEquals("15246837", ZigZagConversion.convert1("12345678", 3));
		assertEquals("159246837", ZigZagConversion.convert1("123456789", 3));
		assertEquals("1592468a37", ZigZagConversion.convert1("123456789a", 3));
	}
	
	@Test
	public void testSimpleEeven(){
		
		assertEquals("", ZigZagConversion.convert("",4));
		assertEquals("1", ZigZagConversion.convert("1",4));
		assertEquals("12", ZigZagConversion.convert("12",4));
		assertEquals("123", ZigZagConversion.convert("123",4));
		assertEquals("1234", ZigZagConversion.convert("1234",4));
		assertEquals("12354", ZigZagConversion.convert("12345",4));
		assertEquals("162354", ZigZagConversion.convert("123456",4));
		assertEquals("1627354", ZigZagConversion.convert("1234567",4));
		assertEquals("16273584", ZigZagConversion.convert("12345678",4));
		assertEquals("162735849", ZigZagConversion.convert("123456789",4));
		assertEquals("1627358a49", ZigZagConversion.convert("123456789a",4));
		assertEquals("16b27c358a49", ZigZagConversion.convert("123456789abc",4));
		assertEquals("16b27c358ad49", ZigZagConversion.convert("123456789abcd",4));
	}
	
	@Test
	public void testSimpleEeven1(){
		
		assertEquals("", ZigZagConversion.convert1("",4));
		assertEquals("1", ZigZagConversion.convert1("1",4));
		assertEquals("12", ZigZagConversion.convert1("12",4));
		assertEquals("123", ZigZagConversion.convert1("123",4));
		assertEquals("1234", ZigZagConversion.convert1("1234",4));
		assertEquals("12354", ZigZagConversion.convert1("12345",4));
		assertEquals("162354", ZigZagConversion.convert1("123456",4));
		assertEquals("1627354", ZigZagConversion.convert1("1234567",4));
		assertEquals("16273584", ZigZagConversion.convert1("12345678",4));
		assertEquals("162735849", ZigZagConversion.convert1("123456789",4));
		assertEquals("1627358a49", ZigZagConversion.convert1("123456789a",4));
		assertEquals("16b27c358a49", ZigZagConversion.convert1("123456789abc",4));
		assertEquals("16b27c358ad49", ZigZagConversion.convert1("123456789abcd",4));
	}
	@Test
	public void testSimpleEevenBig(){
		assertEquals("18293a47be5c6d", ZigZagConversion.convert("123456789abcde",6));
	}
	@Test
	public void testSimpleOddBig(){
		assertEquals("17d28e369c4a5b", ZigZagConversion.convert("123456789abcde",5));
	}
	@Test
	public void testSimple(){
		assertEquals("PAHNAPLSIIGYIR", ZigZagConversion.convert("PAYPALISHIRING",3));
	}
}
