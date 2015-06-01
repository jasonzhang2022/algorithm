package jason.algorithm.practice;
//http://www.programcreek.com/2014/05/leetcode-zigzag-conversion-java/
public class ZigZagConversion {
	
	
	public static String convert(String input, int totalrow){
		
		char[] ret=new char[input.length()];
		
		int singleCharacterRow=totalrow/2;
		
		int retindex=0;
		for (int row=0; row<totalrow; row++){
			int stepeven=totalrow+1;
			int stepodd=totalrow+1;
			if (row==singleCharacterRow){
				if (totalrow%2==1){ //odd
					stepeven=totalrow/2+1; //index increased from a even column to an odd column
					stepodd=totalrow/2+1;
				} else {
					stepeven=totalrow/2; //index increased from a even column to an odd column
					stepodd=totalrow/2+1;
				}
			
			}
			
			int currentCharIndex=row;
			boolean even=true; //column starts from 0;
			/*
			 * how many character can this row has?
			 * 
			 * Each full column has totalrow character.
			 * each single character column has one character.
			 * 
			 * for regular row, we can have input.length/(totalrow+1)
			 * 
			 *  
			 */
			
			int numberCharacterInArow=input.length()/(totalrow+1);
			if (input.length()%(totalrow+1)!=0){
				numberCharacterInArow++;
			}
			if (row==singleCharacterRow){
				numberCharacterInArow*=2;
				if (input.length()%(totalrow+1)!=0){
					numberCharacterInArow--;
				}
			}
			
			int rowIndex=0;
			while (currentCharIndex<input.length() && rowIndex<numberCharacterInArow){
				ret[retindex++]=input.charAt(currentCharIndex);
				if (even){
					currentCharIndex+=stepeven;
					even=false;
				} else{
					currentCharIndex+=stepodd;
					even=true;
				}
				rowIndex++;
			}
		}
		return String.valueOf(ret);
	}
	
	public static String convert1(String s, int numRows) {
		if (numRows == 1)
			return s;
	 
		StringBuilder sb = new StringBuilder();
		// step
		int step = 2 * numRows - 2;
	 
		for (int i = 0; i < numRows; i++) {
			//first & last rows
			if (i == 0 || i == numRows - 1) {
				for (int j = i; j < s.length(); j = j + step) {
					sb.append(s.charAt(j));
				}
			//middle rows	
			} else {
				int j = i;
				boolean flag = true;
				int step1 = 2 * (numRows - 1 - i);
				int step2 = step - step1;
	 
				while (j < s.length()) {
					sb.append(s.charAt(j));
					if (flag)
						j = j + step1;
					else
						j = j + step2;
					flag = !flag;
				}
			}
		}
	 
		return sb.toString();
	}

}
