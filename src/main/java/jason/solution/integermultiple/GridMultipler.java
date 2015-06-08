package jason.solution.integermultiple;

public class GridMultipler  implements Multiplier{

	@Override
	public String multiple(String op1, String op2) {
	
		
		StringBuilder sb=new StringBuilder(op1.length()+op2.length()+2);
		char offset='0';
		for (int i=0; i<op1.length(); i++){
			for (int j=0; j<op2.length(); j++){
				
				int position=op1.length()-i-1 +op2.length()-j-1;
				
				int first=op1.charAt(i)-offset;
				int second=op2.charAt(j)-offset;
				int result=first*second;
				Adder.add(sb, String.valueOf(result), position);
				
				
				
			}
		}
		return sb.toString();
	}

}
