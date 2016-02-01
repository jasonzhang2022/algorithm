package jason.algorithm.string;

public class RabinKarp {

	public static int radix=Character.MAX_VALUE + 1;
	public static int prime= 96293;

	
	public static int indexOf(char[] text, char[] pattern){
	
		int m=pattern.length;
		int n=text.length;
		int constant1=(int)Modulus.javaMod(radix, m, prime);
		
		long patternHash=0;
		long textSubstringHash=0;
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
				 textSubstringHash= (((textSubstringHash* (radix%prime))%prime +prime 
						 - ((text[substringStartIndex]%prime) *constant1)%prime)%prime
						 +text[substringStartIndex+m]%prime)%prime;
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
