package jason;

import org.junit.Test;

public class BitTest {

	@Test
	public void testLeftShift() {
		int v=1;
		for (int i=0; i<10; i++) {
			int x=v<<i;
			int y=~x;
			int z=~(-x);
			System.out.println(x+":"+y+":"+z);
		}
		
	}
	
	
	public void toBinary(int y, boolean compact) {
		StringBuilder sBuilder=new StringBuilder();
		for (int i=0; i<32; i++) {
			int j=1<<i;
			if ((y&j)==0) {
				sBuilder.append("0");
			} else {
				sBuilder.append("1");
			}
		}
		if (compact) {
			System.out.println(sBuilder.reverse().toString());
			return;
		}
		for (int i=sBuilder.length()-1; i>=0; i--) {
			int position=sBuilder.length()-1-i;
			System.out.println(position+":"+sBuilder.charAt(i));
		}
		
	}

	
	@Test 
	public void bitGeneration() {
		
		toBinary(1, true);
		toBinary(2, true);
		toBinary(3, true);
		toBinary(4, true);
		int y=Integer.MAX_VALUE;
		toBinary(y, true);
		System.out.println("--------");
		y=1<<31;
		toBinary(y, true);
		System.out.println("--------");
		y=1<<30;
		toBinary(y, true);
	}
}
