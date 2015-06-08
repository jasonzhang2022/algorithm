package jason.solution.integermultiple;

import static org.junit.Assert.*;

import org.junit.Test;

public class Adder {
	
	public static String add(String op1, String op2){
		//we could prepend 100 zero to avoid oveflow.
		StringBuilder sb=new StringBuilder(op1);
		add(sb, op2, 0);
		return sb.toString();
	}	
	public static String add1(String op1, String op2){
		//we could prepend 100 zero to avoid oveflow.
		StringBuilder sb=new StringBuilder("00000000000000000000000000000000");
		sb.append(op1);
		add(sb, op2, 0);
		for (int i=0; i<sb.length(); i++){
			if (sb.charAt(i)!='0'){
				return sb.substring(i);
			}
		}
		return "0";
	}

	
	
	public static void add(StringBuilder op1, String op2, int posFromEnd){
		
		for (int i=op2.length()-1; i>=0; i--){
			char c=op2.charAt(i);
			add(op1, c, posFromEnd++);
		}
		
		
		
	}
	
	public static void add(StringBuilder op1, char digit, int posFromEnd){
		while (op1.length()-1-posFromEnd<0){
			op1.insert(0, '0');
		}
		int offset='0';
		int value=digit-offset;
		int sd=op1.charAt(op1.length()-1-posFromEnd)-offset;
		if (value+sd<10){
			char c=(char)(offset+value+sd);
			op1.setCharAt(op1.length()-1-posFromEnd, c);
		} else {
			int remain=value+sd-10+offset;
			op1.setCharAt(op1.length()-1-posFromEnd,(char) remain);
			
			add(op1, '1', posFromEnd+1);
		}
		
	}
	
	
	@Test 
	public void testAdder(){
		assertEquals("10020", add("9999", "21"));
		assertEquals("10020", add("21", "9999"));
		
		assertEquals("10020", add1("9999", "21"));
		assertEquals("10020", add1("21", "9999"));
		
	}

}
