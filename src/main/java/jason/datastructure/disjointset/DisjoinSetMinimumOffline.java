package jason.datastructure.disjointset;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

/*
 * Introudction to algorithm: Question 21-1 page 582
 */
public class DisjoinSetMinimumOffline {

	
	/*
	 * This is not a typical disjointset data structure.
	 * The representative value of the tree(the root) is usually a valid value.
	 * 
	 * But in this example, the root is not a valid value (an input number), but the index of the set.
	 * During merge operation, some of this special node is left dangling in the tree. But we lose reference of those special node
	 * since we only refers a child node through index in nodes array
	 */
	public static class DisjointSetNode {
		int number;
		DisjointSetNode prev;
		DisjointSetNode next;
		DisjointSetNode parent;
		public DisjointSetNode(int number) {
			super();
			this.number = number;
		}
		
		//path compression
		public DisjointSetNode find(){
			if (this.parent==null){
				return this;
			} 
			DisjointSetNode topParent=this.parent.find();
			parent=topParent;
			return topParent;
		}
		
		
		public void mergeToRight(){
			DisjointSetNode current=this;
			DisjointSetNode prev=current.prev;
			DisjointSetNode next=current.next;
			if (prev!=null){
				prev.next=next;
				next.prev=prev;
			} else{
				next.prev=null;
			}
			
			current.parent=next;
			/*
			current.prev=null;
			current.next=null;
			*/
		}
		
	}
	
	public int[] extract(String[] input){
		
		int mlen=0;
		
		DisjointSetNode currentRoot=new DisjointSetNode(0);
		DisjointSetNode prevRoot=null;
		DisjointSetNode[] nodes=new DisjointSetNode[input.length];
		for (String num: input){
			if (num.equals("E")){
				mlen++;
				prevRoot=currentRoot;
				currentRoot=new DisjointSetNode(mlen);
				prevRoot.next=currentRoot;
				currentRoot.prev=prevRoot;
			} else {
				DisjointSetNode leafNode=new DisjointSetNode(Integer.parseInt(num));
				leafNode.parent=currentRoot;
				nodes[leafNode.number]=leafNode;
			}
		}
		int[] ret=new int[mlen+1];
		Arrays.fill(ret, -1);
		int nlen=input.length-mlen;
		for (int i=1; i<=nlen; i++){
			DisjointSetNode root=nodes[i].find();
			int j=root.number;
			
			if (j<mlen){
				ret[j]=i;
				root.mergeToRight();
			}
		}
		return ret;
	}
	
	@Test
	public void testStartWithE(){
		String[] input={"E", "1", "2"};
		int[] expected={-1};
		String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		int[] result=extract(input);
		String resultStr=Arrays.stream(result).limit(expected.length).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		assertThat(resultStr, equalTo(expectedStr));
	}
	
	@Test
	public void testEndE(){
		String[] input={"1", "2", "E"};
		int[] expected={1};
		String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		int[] result=extract(input);
		String resultStr=Arrays.stream(result).limit(expected.length).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		assertThat(resultStr, equalTo(expectedStr));
	}
	
	@Test
	public void testSomeEmptyExtractionInMiddle(){
		String[] input={"1", "E", "E", "2"};
		int[] expected={1, -1};
		String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		int[] result=extract(input);
		String resultStr=Arrays.stream(result).limit(expected.length).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		assertThat(resultStr, equalTo(expectedStr));
	}
	
	@Test
	public void testBasic(){
		String[] input={"4", "8", "E", "3", "E", "9", "2", "6", "E", "E", "E", "1", "7", "E", "5"};
		int[] expected={4, 3,2,6,8,1};
		String expectedStr=Arrays.stream(expected).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		int[] result=extract(input);
		String resultStr=Arrays.stream(result).limit(expected.length).mapToObj(a->Integer.toString(a)).collect(Collectors.joining(","));
		assertThat(resultStr, equalTo(expectedStr));
	}
}
