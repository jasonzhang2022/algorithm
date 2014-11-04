package jason.solution.integermultiple;

import java.util.ArrayList;

public class KaratsubaMultiplier1 implements Multiplier {

	

	@Override
	public String multiple(String op1, String op2) {
		if (op1.length()+op2.length()<=8) {
			//we do not have a overflow, multiple directly.
			return new Integer(Integer.parseInt(op1)*Integer.parseInt(op2)).toString();
		}
		
		//assign M=4 to avoid overflow
		//x=X1*10^4+X0
		//Y=Y1*10^4+Y0
		String X1, X0, Y1, Y0;
		if (op1.length()<=4) {
			X0=op1;
			X1="0";
		} else {
			X1=op1.substring(0, op1.length()-4);
			X0=op1.substring(op1.length()-4);
		}
		
		if (op2.length()<=4) {
			Y0=op2;
			Y1="0";
		} else {
			Y1=op2.substring(0, op2.length()-4);
			Y0=op2.substring(op2.length()-4);
		}
		
		//z2=X1*Y1,  Z0=X0*Y0, Z1=(X1+X0)(Y1+Y0)-Z2-Z0
		//XY=Z2*10^8+Z1*0^4+Z0
		String Z0=multiple(X0, Y0);
		String Z2=multiple(X1, Y1);
		String Z1=Helper.minus(Helper.minus(multiple(Helper.add(X1, X0), Helper.add(Y1, Y0)), Z2), Z0);
		
		ArrayList<Byte> resultsArrayList=Helper.toByteArray(Z0);
		Helper.add(resultsArrayList, Z1, 4);
		Helper.add(resultsArrayList, Z2, 8);
		return Helper.outputNumber(resultsArrayList);
		
	}

}
