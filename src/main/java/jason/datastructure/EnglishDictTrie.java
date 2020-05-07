package jason.datastructure;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.junit.Test;

public class EnglishDictTrie {
	char value;
	public EnglishDictTrie[] children;
	
	public EnglishDictTrie(char value){
		this.value=value;
	}
	
	public void addWord(char[] word, int offset){
		char c=word[offset++];
		int index=c-'a';
		if (children[index]==null){
			EnglishDictTrie child=new EnglishDictTrie(c);
			children[index]=child;
		}
		if (offset<word.length){
			EnglishDictTrie child=children[index];
			if (child.children==null){
				child.children=new EnglishDictTrie[26];
			}
			child.addWord(word, offset);
		}
	}
	
	
	public void findWord(char[] word, int offset, StringBuilder prefix, Consumer<String> consumer){
		if (offset==word.length){
			consumer.accept(prefix.toString());
			return;
		}
		char c=word[offset++];
		int index=c-'a';
		if (children==null || children[index]==null){
			return;
		}
		EnglishDictTrie child=children[index];
		child.findWord(word, offset, prefix.append(c), consumer);
	}
	
	//findUsingArray word with one missing letter.
	//Find in dictionary which has less than one letter than target. Other letter has the same order.
	
	public void findMissingLetterWord(char[] word, int offset, Consumer<String> consumer) {
		if (offset==word.length){
			//we reach the end, we can't miss any word.
			return;
		}
		
		char c=word[offset++];
		int index=c-'a';
		if (children==null || children[index]==null){
			//we could not findUsingArray current character at offset.
			//we can miss this letter
			StringBuilder sb=new StringBuilder();
			sb.append(word, 0, offset-1);
			findWord(word, offset, sb, consumer);
			return;
		}
		
		//we found the character, we have two options.
		//option 1. miss this letter, 
		StringBuilder sb=new StringBuilder();
		sb.append(word, 0, offset-1);
		findWord(word, offset, sb, consumer);
		
		
		//option 2. use this letter, and let downstream to miss the letter
		children[index].findMissingLetterWord(word, offset, consumer);
	}
	
	
	

}
