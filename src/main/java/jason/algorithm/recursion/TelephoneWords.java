package jason.algorithm.recursion;

import static org.junit.Assert.assertEquals;

import java.util.Stack;
import java.util.function.Consumer;

import org.junit.Test;

//http://www.geeksforgeeks.org/find-possible-words-phone-digits/
/**
 * 
 * @author jason
 *
 */
public class TelephoneWords {

	static String[] map = {"0", "1", "abc", "def", "ghi", "jkl",
            "mno", "pqrs", "tuv", "wxyz"};

	public void recursive(int[] phoneNumber, Consumer<String> consumer){
		
		char[] result=new char[phoneNumber.length];
		recursiveSub(phoneNumber, 0, consumer, result);
		
	}
	
	
	/**
	 * 
	 * @param phoneNumber phoneNumber
	 * @param index digit index to the phoneNumber
	 * @param consumer consumer used to output a final result
	 * @param result buffer to capture the result.
	 */
	//Statement 1: call in from previous/parent recursiveSub.
	public void recursiveSub(int[] phoneNumber, int index,  Consumer<String> consumer, char[] result){
		int number=phoneNumber[index];
		int len=map[number].length();
		
		//Statement 2: loop, move to sibling
		for (int i=0; i<len; i++){
			
			
			result[index]=map[number].charAt(i);
			
			//Handle current
			if (index==phoneNumber.length-1){
				//Statement 3: base case
				consumer.accept(String.valueOf(result));
			} else {
				//Statement 4: move to first children
				recursiveSub(phoneNumber,  index+1, consumer, result);
			}
		}
	}
	
	@Test
	public void testRecursive(){
		
		int[] phoneNumber={1,4,5,6,7,8,2};
		int[] count=new int[1];
		Consumer<String> consumer=s->{
			count[0]++;
			System.out.println(s);
		};
		recursive(phoneNumber, consumer);
		
		assertEquals(count[0], 972);
	}
	
	public static class Element {
		public int digitIndex;
		public int charIndex;
		public int num;
		public Element(int numIndex, int charIndex, int num) {
			super();
			this.digitIndex = numIndex;
			this.charIndex = charIndex;
			this.num=num;
		}
		
		public char getChar(){
			return map[num].charAt(charIndex);
		}
		
		
	}
	public void iterative(int[] phoneNumber, Consumer<String> consumer){

		
		char[] result=new char[phoneNumber.length];

		Stack<Element> stack=new Stack<>();
		stack.push(new Element(0, 0, phoneNumber[0]));
		
		Element current=null;
		Element prev=null;
		while (!stack.isEmpty()){
			current=stack.peek();	
			
			result[current.digitIndex]=current.getChar();
		
			//reached here through a parent->child traversal
			if (prev==null || prev.digitIndex <current.digitIndex){
				if (current.digitIndex==phoneNumber.length-1 ){
					//last element, output
					//base case
					consumer.accept(String.valueOf(result));
					if (current.charIndex<map[current.num].length()-1){
						//move to sibling.
						current.charIndex++;
					} else {
						stack.pop();
					}
				} else {
					//move to first children, recursive call.
					stack.push(new Element(current.digitIndex+1, 0, phoneNumber[current.digitIndex+1]));
				}
				prev=current;
				continue;
			}
			
			//reached here through sibling traversal. The for loop in recursion version.
			if (prev.digitIndex==current.digitIndex){
				
				if (current.digitIndex==phoneNumber.length-1 ){
					//last element, output
					//base case
					consumer.accept(String.valueOf(result));
					if (current.charIndex<map[current.num].length()-1){
						//move to sibling.
						current.charIndex++;
					} else {
						stack.pop();
					}
				} else {
					//move to first children
					//recursive call
					stack.push(new Element(current.digitIndex+1, 0, phoneNumber[current.digitIndex+1]));
				}
				prev=current;
				continue;
			}
			
			//moved here through a child->parent traversal.
			if (prev.digitIndex>current.digitIndex){
				if (current.charIndex<map[current.num].length()-1){
					//move to sibling
					current.charIndex++;
				} else{
					stack.pop();
				}
				prev=current;
				continue;
			}
		}
	}

	@Test
	public void testIterative() {

		int[] count = new int[1];
		Consumer<String> consumer = s -> {
			count[0]++;
			System.out.println(s);
		};
		// 1
		count[0] = 0;
		iterative(new int[] { 1 }, consumer);
		assertEquals(count[0], 1);
		//3
		count[0] = 0;
		iterative(new int[] { 3 }, consumer);
		assertEquals(count[0], 3);
		//1,3
		count[0] = 0;
		iterative(new int[] { 1, 3 }, consumer);
		assertEquals(count[0], 3);
		count[0] = 0;
		iterative(new int[] { 1, 4, 5, 6, 7, 8, 2 }, consumer);
		assertEquals(count[0], 972);

	}

	public void ehancedIterative(int[] phoneNumber, Consumer<String> consumer){

		
		char[] result=new char[phoneNumber.length];

		Stack<Element> stack=new Stack<>();
		for (int charIndex=0; charIndex<map[phoneNumber[0]].length(); charIndex++){
			stack.push(new Element(0, charIndex, phoneNumber[0]));
		}
		
		while (!stack.isEmpty()){
			Element current=stack.pop();
			result[current.digitIndex]=current.getChar();
			if (current.digitIndex==phoneNumber.length-1 ) {
				consumer.accept(String.valueOf(result));
			} else {
				int nextDigitIndex=current.digitIndex+1;
				
				for (int charIndex=0; charIndex<map[phoneNumber[nextDigitIndex]].length(); charIndex++){
					stack.push(new Element(nextDigitIndex, charIndex, phoneNumber[nextDigitIndex]));
				}
			}
		}
	}

	@Test
	public void testEnhancedIterative() {

		int[] count = new int[1];
		Consumer<String> consumer = s -> {
			count[0]++;
			System.out.println(s);
		};
		// 1
		count[0] = 0;
		ehancedIterative(new int[] { 1 }, consumer);
		assertEquals(count[0], 1);
		//3
		count[0] = 0;
		ehancedIterative(new int[] { 3 }, consumer);
		assertEquals(count[0], 3);
		//1,3
		count[0] = 0;
		ehancedIterative(new int[] { 1, 3 }, consumer);
		assertEquals(count[0], 3);
		
		
		count[0] = 0;
		ehancedIterative(new int[] { 1, 4, 5, 6, 7, 8, 2 }, consumer);
		assertEquals(count[0], 972);

	}
	
	
	
}
