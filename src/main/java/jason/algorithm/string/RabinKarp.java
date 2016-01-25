package jason.algorithm.string;

public class RabinKarp {

	public static int radix=256;
	public static int prime=3355439;

	
	public static int indexOf(char[] text, char[] pattern){
	
		int m=pattern.length;
		int n=text.length;
		int constant=Modulus.iterative(radix, m-1, prime);
		
		int patternHash=0;
		int textSubstringHash=0;
		for (int i=0; i<m; i++){
			//(patternHash*Radix+char)%prime
			patternHash=(patternHash*radix+pattern[i])%prime;
			textSubstringHash=(textSubstringHash*radix+text[i])%prime;
		}
		int substringStartIndex=0;
		while (substringStartIndex+m<=n){
			if (patternHash==textSubstringHash){
				if (patternMatches(text, substringStartIndex, pattern)){
					return substringStartIndex;
				}
			}
			//rolling hash
			if (substringStartIndex+m+1<=n){
				//remove high order
				int v1=(textSubstringHash + prime -text[substringStartIndex]*constant%prime) %prime;
				
				textSubstringHash=(v1*radix+text[substringStartIndex+m])%prime;
			} else{
				break;
			}
			substringStartIndex++;
			
			
		}
		
		return -1;
		
		
	}
	
	
	public static boolean patternMatches(char[] text, int offset, char[] pattern){
		for (int i=0; i<pattern.length; i++){
			if (pattern[i]!=text[offset+i]){
				return false;
			}
		}
		return true;
	}
}
