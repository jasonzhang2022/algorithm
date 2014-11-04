package jason.solution.integermultiple;

import java.util.ArrayList;

public class ClassisMultiplier implements Multiplier{


	
	
	@Override
	public String multiple(String op1, String op2) {
		
		if (op1.length()==0 || op2.length()==0) {
			throw new RuntimeException("bad inputs");
		}
		
		ArrayList<Byte> results=new ArrayList<>(op1.length()+op2.length()+10);
		String rop1=new StringBuilder(op1).reverse().toString();
		String rop2=new StringBuilder(op2).reverse().toString();
		
		for (int i=0; i<rop1.length(); i++) {
			for (int j=0; j<rop2.length(); j++) {
				
				int startPosition=i+j;
				int result=Byte.parseByte(rop1.substring(i, i+1)) *Byte.parseByte(rop2.substring(j, j+1));
				int lowDigit=result%10;
				int highDigit=result/10;
				Helper.addPosition(results, startPosition, (byte) lowDigit);
				Helper.addPosition(results, startPosition+1, (byte) highDigit);
			}
		}
				
		StringBuilder sBuilder=new StringBuilder();
		boolean seeNonZero=false;
		for (int i=results.size()-1; i>=0; i--) {
			if (seeNonZero) {
				sBuilder.append(String.valueOf(results.get(i)));
			} else {
				if (results.get(i)!=0) {
					sBuilder.append(String.valueOf(results.get(i)));
					seeNonZero=true;
				}
			}
			
		}
		
		String result= sBuilder.toString();
		if (result.length()==0) {
			result="0";
		}
		System.out.println(op1+"*"+op2+"="+result);
		return result;
	}

}
