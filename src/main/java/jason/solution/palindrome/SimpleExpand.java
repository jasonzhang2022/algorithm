package jason.solution.palindrome;

public class SimpleExpand implements LongestPalindromeFinder {

	public static class Result {
		int start;
		int end;
		public Result(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}
		
		public int getLen(){
			return end-start+1;
		}
		
		
	}
	
	
	
	public void expand(String input, Result result){
		
		while (result.start>0 && result.end<input.length()-1  
				&& input.charAt(result.start-1)==input.charAt(result.end+1)){
			result.start--;
			result.end++;
		}
	}
	
	
	@Override
	public String findLongestPalindrome(String input) {
		
		Result longgest=new Result(0, 0);
		
		//check odd one.
		for (int i=1; i<input.length(); i++){
			Result r=new Result(i, i);
			expand(input, r);
			if (r.getLen()>longgest.getLen()){
				longgest=r;
			}
		}
		
		//check even
		for (int i=0; i<input.length()-1; i++){
			if (input.charAt(i)==input.charAt(i+1)){
				Result r=new Result(i, i+1);
				expand(input, r);
				if (r.getLen()>longgest.getLen()){
					longgest=r;
				}	
			}
		}
		
		return input.substring(longgest.start, longgest.end+1);
	}

}
