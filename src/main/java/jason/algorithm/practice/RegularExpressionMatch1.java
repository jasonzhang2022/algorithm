package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.junit.Test;

/*

Implement regular expression matching with support for '.' and '*'.

'.' Matches any single character.
'*' Matches zero or more of the preceding element.

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") return false
isMatch("aa","aa") return true
isMatch("aaa","aa") return false
isMatch("aa", "a*") return true
isMatch("aa", ".*") return true
isMatch("ab", ".*") return true
isMatch("aab", "c*a*b") return true
 */
/*
 * This implementation is wrong. 
 * In this implementation * matches zero or more elements.
 */
public class RegularExpressionMatch1 {
	
	

	public boolean match(String str, String pattern){
		
		//collapse multiple * to a single *;
		pattern=pattern.replaceAll("\\*+", "*");
		
		StringTokenizer tokenizer=new StringTokenizer(pattern, "*", true);
		ArrayList<String> tokens=new ArrayList<String>();
		while (tokenizer.hasMoreTokens()){
			tokens.add(tokenizer.nextToken());
		}
		
		return match(str, 0, tokens, 0);
	}
	
	
	public boolean match(String str, int offset, ArrayList<String> tokens, int tokenOffset){

		if (offset==str.length() && tokenOffset==tokens.size()){
			return true;
		}
		if (tokenOffset==tokens.size()-1 && tokens.get(tokenOffset).equals("*")){
			return true;
		}
		//all strings are consumed.
		if (offset>=str.length()){
			//has more tokens
			if (tokenOffset<tokens.size()){
				return false;
			}
		}
		//token is consumed, but there is string
		if (tokenOffset>=tokens.size() && offset<str.length()){
			return false;
		}
		
		
		String token=tokens.get(tokenOffset);
		
		if (token.equals("*")){
			while (offset<str.length()){
				if (match(str, offset, tokens, tokenOffset+1)){
					return true;
				}
				offset++;
			}
			return false;
			
		} else {
			if (!prefixed(str, offset, token)){
				return false;
			}
			return match (str, offset+token.length(), tokens, tokenOffset+1);
		}
	}
	
	public boolean prefixed(String str, int offset, String literalMatch){
		
		if (str.length()-offset<literalMatch.length()){
			return false;
		}
		
		for (int i=0; i<literalMatch.length(); i++){
			if (literalMatch.charAt(i)=='.'){
				continue;
			}
			if (literalMatch.charAt(i)!=str.charAt(offset+i)){
				return false;
			}
		}
		return true;
	}
	
	@Test 
	public void testMatch(){
		
		System.out.println("**a**".replaceAll("\\*+", "*"));
		RegularExpressionMatch1 matcher=new RegularExpressionMatch1();
		assertTrue( matcher.match("abca", "a*")); //* at the end, and match something.
		assertTrue( matcher.match("abca", "abca*")); //* at the end, match nothing.
		assertTrue( matcher.match("abca", "*")); //only *
		assertTrue( matcher.match("abca", "*abca")); //* at the begin, match nothing
		assertTrue( matcher.match("abca", "*bca")); //* at the begin, match something
		assertTrue( matcher.match("abca", "*abca*")); //* at the begin and end match nohting
		assertTrue( matcher.match("abca", "*ab.a")); //process .
		assertTrue( matcher.match("abcdefg", "ab.*de*g*")); 
	}
	

}
