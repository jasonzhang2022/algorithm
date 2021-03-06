package jason.datastructure;

import static org.junit.Assert.assertEquals;
import jason.datastructure.Trie.Entry;

import java.util.ArrayList;
import java.util.function.Consumer;

//http://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-6/
public class SuffixTree1 {
	
	
	
	public static class EndIndex {
		public int end;

		public EndIndex(int end) {
			super();
			this.end = end;
		}
		
	}

	EndIndex endIndex=new EndIndex(0);
	public static final int MAX_CHAR=256;
	//----------------temporary variable used during building
	int end=-1;
	
	//activePoint
	SuffixTreeNode activeNode;
	//the index of character in the target string, not the character itself.
	// By this way, we can look up the character and next character
	int activeEdge; 
	int activeLength=0;
	
	int remainingSuffixCount=0;
	//will have its suffixLink set in next extension.
	SuffixTreeNode lastNewNode;
	
	//------------------------permanent suffix structures
	//root of suffix tree.
	SuffixTreeNode root;
	
	/**
	 * SuffixTreeNode is actually a mix of edge and node.
	 * It has links to children node.
	 * It has information about edge label(edgeStart and end).
	 * @author jason
	 *
	 */
	public class SuffixTreeNode {
		
		/**
		 * Children node indexed by alphabet: 256 character.
		 */
		public SuffixTreeNode[] children=new SuffixTreeNode[MAX_CHAR];
		
		/*
		 * The label for the edge from parent Node to children node.
		 * The label will be s[edgeStart] to s[edgeEnd] inclusive
		 * So the edgeStart and end are index.
		 */
		public int edgeStart=-1;
		public EndIndex end=endIndex;
		
		
		/**
		 * Only useful for internal Node.
		 */
		public SuffixTreeNode suffixLink;
		
		
		/**
		 * Only useful for leaf node.
		 * The suffix this leaf edge represents.
		 */
		public int suffixIndex=-1;
		
		
		public boolean isRoot(){
			return edgeStart<0;
		}
		public boolean isInternal(){
			//root is not suffix
			return suffixIndex<0 && edgeStart>=0;
		}
		public boolean isLeaf(){
			return suffixIndex>=0;
		}
		
		public int getEdgeLength(){
			return this.end.end-edgeStart+1;
		}
		public SuffixTreeNode(){
			
		}
		
		//create a non-root node.
		public SuffixTreeNode(int start){
			super();
			this.edgeStart=start;
			this.suffixLink=root;
			this.children=new SuffixTreeNode[MAX_CHAR];
		}
		
		
	}
	

	//String this suffixTree is for.
	char[] text;
	public SuffixTree1(String s){
		this.text=s.toCharArray();
		root=new SuffixTreeNode();
		activeNode=root;
		for (int i=0; i<text.length; i++){
			//extend the tree one by one.
			extendSuffixTree(i);
		}
	}
	
	
	
	/**
	 * TODO is the pos the character index in the array or 
	 * @param pos
	 */
	protected void extendSuffixTree(int pos){
		
		//extend the end index of all leaf node.
		endIndex.end=pos;

		/*
		 * Increment remaingSuffixCount indicating that a new suffix
		 * added to the list of suffixes yet to be added to tree 
		 */
		remainingSuffixCount++;
		
		/*
		 * set to null while starting a new phase.
		 * Indicating there is no internal node waiting for its suffix link
		 */
		lastNewNode=null;
		
		
		//work on each remaining suffix.
		while (remainingSuffixCount>0){
			
			
			/*
			 * Active Length is zero, we set the desire edge we wants to start.
			 */
			if (activeLength==0){
				activeEdge=pos;
			}
			
			if (activeNode.children[text[activeEdge]]==null){
				
				
				//such edge does not exist
				//we create one. 
				SuffixTreeNode newLeafEdge=new SuffixTreeNode(pos);
				activeNode.children[text[activeEdge]]=newLeafEdge;
				
				//TODO should don't we set the suffixIndex?
				newLeafEdge.suffixIndex=pos+1-remainingSuffixCount;
				
				/*
				 * TODO: 
				 * 1. how can make sure the activeNode is turned into an internal node
				 * in this round, and it is not internal node before.
				 * 
				 * 2. if the activeNode is a newly created internal node, should 
				 * lastNewNode be set to activeNode
				 */
				if (lastNewNode!=null){
					lastNewNode.suffixLink=activeNode;
					lastNewNode=null;
				}
				
			} else {
				//the edge exists, we need to walk down.
				SuffixTreeNode edge=activeNode.children[text[activeEdge]];
				if (walkDown(edge)){
					//we didn't change the remainingSUffixCount,
					//in next loop, we will have the new activeEdge
					continue;
				}
				
				
				//implicit node. Rule three.
				if (text[edge.edgeStart+activeLength]==text[pos]){
					
					if (lastNewNode!=null){
						lastNewNode.suffixLink=activeNode;
						lastNewNode=null;
					}
					activeLength++;
					
					//once implicit node is reached, no further process is needed in
					//current stage.
					break;
				} 
				
				
				//we fall off in the middle of edge, we need to 
				// create an internal node.
				
			
				
				SuffixTreeNode newInternal=new SuffixTreeNode(edge.edgeStart);
				newInternal.end=new EndIndex(edge.edgeStart+activeLength-1);
				
				SuffixTreeNode newLeaf=new SuffixTreeNode(pos);
				newLeaf.suffixIndex=pos+1-remainingSuffixCount;
				activeNode.children[text[activeEdge]]=newInternal; 
				
				edge.edgeStart=edge.edgeStart+activeLength;
				newInternal.children[text[edge.edgeStart]]=edge;
				
				
				newInternal.children[text[pos]]=newLeaf;
				
				if (lastNewNode!=null){
					lastNewNode.suffixLink=newInternal;
				}
				lastNewNode=newInternal;		
			}
			
			 /* One suffix got added in tree, decrement the count of
	          suffixes yet to be added.*/
	        remainingSuffixCount--;
	        if (activeNode == root && activeLength > 0) //APCFER2C1
	        {
	            activeLength--;
	            activeEdge = pos - remainingSuffixCount + 1;
	        }
	        else if (activeNode != root) //APCFER2C2
	        {
	            activeNode = activeNode.suffixLink;
	        }
		}
	}
	
	
	public boolean walkDown(SuffixTreeNode node){
		if (activeLength>=node.getEdgeLength()){
			activeNode=node;
			activeLength-=node.getEdgeLength();
			activeEdge+=node.getEdgeLength();
			return true;
		}
		return false;
	}
	
	public void  walk(Consumer<SuffixTreeNode> consumer){
		
		walkNode(root, consumer);
		
	}
	
	
	protected void walkNode(SuffixTreeNode node, Consumer<SuffixTreeNode> consumer){
		for (int i=0; i<MAX_CHAR; i++){
			if (node.children[i]!=null){
				walkNode(node.children[i], consumer);
				consumer.accept(node.children[i]);
			}
		}
	}
	
	
	
	public void trimLeafNode(int index){
		trimLeafNode(root, index);
	}
	
	protected void trimLeafNode(SuffixTreeNode node, int index){
		if (node==null){
			return;
		}
		if (node.isLeaf()){
			if (node.edgeStart<=index){
				node.end=new EndIndex(index);
			}
			return;
		}
		
		for (int i=0; i<node.children.length; i++){
			trimLeafNode(node.children[i], index);
		}
	}
	
	//TODO longest common sequence.
	
	
	
	
	
	public int applicationHasPattern(char[] chars){
		
		SuffixTreeNode currentNode=root;
		int charsOffset=0;
		
		SuffixTreeNode foundEdge=null;
		
		outter:
		while (charsOffset<chars.length){
			if (currentNode.children[chars[charsOffset]]==null){
				//can not walk down
				return -1;
			}
			SuffixTreeNode edge=currentNode.children[chars[charsOffset]];
			int j=charsOffset;
			for (int i=edge.edgeStart; i<=edge.end.end; i++){
				j=i-edge.edgeStart+charsOffset;
				if (j<chars.length){
					if (chars[j]!=text[i]){
						
						//unequal character
						return -1;
					} else {
						continue; //equal character
					}
				} else {
					//j is bigger than chars length;
					//we stop in the half way
					foundEdge=edge;
					break outter;
				}
			}
			
			//we finish this edge and all matched.
			//we go to next edge.
			charsOffset=j+1;
			currentNode=edge;
			if (charsOffset==chars.length){
				foundEdge=edge;
			}
		}
	
		return collectMinPrefix(foundEdge, Integer.MAX_VALUE);
	}
	
	
	protected int collectMinPrefix(SuffixTreeNode node, int min){
		if (node.isLeaf()){
			if (node.suffixIndex<min){
				return node.suffixIndex;
			} else {
				return min;
			}
		}
		
		
		for (int i=0; i<node.children.length; i++){
			if (node.children[i]!=null){
				min=collectMinPrefix(node.children[i], min);
			}
		}
		
		return min;
	}
	
	
	public static class TempResult {
		int pathLength;
		SuffixTreeNode Edge;
	}
	
	public String applicationLongestRepeatedString(){
		
		TempResult tempResult=new TempResult();
		tempResult.pathLength=0;
		tempResult.Edge=root;
		
		
		collectLongestInternalNode(root, tempResult, 0);
		
		if (tempResult.pathLength==0){
			return null;
		}
		
		String t=String.valueOf(text);
		return t.substring(tempResult.Edge.suffixIndex, tempResult.Edge.edgeStart);
	}
	
	
	/**
	 * 
	 * @param node 
	 * @param result
	 * @param pathLength the length of the suffix length when traverse to node.
	 */
	public void collectLongestInternalNode(SuffixTreeNode node, TempResult result, int pathLength){
		
		if (node==null){
			return;
		}
		if (node.isLeaf()){
			int pathLengthToParentNode=pathLength-(node.end.end-node.edgeStart+1);
			if (pathLengthToParentNode>result.pathLength){
				result.pathLength=pathLengthToParentNode;
				result.Edge=node;
			}
			return;
		}
		
		for (int i=0; i<node.children.length; i++){
			if (node.children[i]!=null){
				collectLongestInternalNode(node.children[i], result, pathLength+(node.children[i].end.end-node.children[i].edgeStart+1));
			}
		}
	}
	
	public int[] toSuffixArray() {
		int[] results=new int[text.length];
		int[] count=new int[1];
		count[0]=0;
		walkNode(root, (node)->{
			if (node.isLeaf()){
				results[count[0]]=node.suffixIndex;
				count[0]++;
			}
		});
		
		return results;
	}

}
