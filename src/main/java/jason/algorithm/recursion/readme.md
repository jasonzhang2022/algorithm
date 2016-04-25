#Recursive function to iterative function
Every recursion could be converted to iteration with a stack. The recursion uses a function call stack itself to keep context.  The function call stack keeps previous execution context so previous call can be restored.  If we could simulate the function call stack ourselves, we can simulate recursion with iteration. We need a **stack** to simulate the call stack for recursion to iteration transformation. 

Recursion function call flow can also be viewed as multi-way function call tree.  We will use tree terminology  to describe the flow. We start from root node, and perform a DFS tree traversal. Multi-way tree is usually represented by left-children and right-sibling approach. 

We need to use some variable to keep the flow state so that we know where we are in the flow, and what is the next state when call context is restored.   The traversal could be 
 
+ parent to child (first child),
+ move from child to right sibling.
+ move from rightmost sibling to parent.

We could take some action at each of these traversal. The action could be traversal, some computation(do something related to current node) and then traversal

** The analysis here refer classes under this directory **

#Generic mechanism from Recursion to Iteration
Here I use TelephoneWords.java in this directory as an example to illustrate the concept. 

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
	
## data  keep.
The context usually is the function parameters of recursive function call so that we have enough information to process current node.
The recursive function recursiveSub has four parameters(phoneNumber, index, consumer, result).  The references for phoneNumber, consumer, and result don't change.  Iterative function doesn't need passing these parameters around. These parameters are available in context of iterative function.

We need to at least to capture the **index** parameter
so we define the class 

	public static class ContextElement {
		public int digitIndex; // the index for nums.
	}

## Call Flow context
We also need some information so we know what traversal to take next.
###Parent->child traversal: Statement 1 in recursiveSub
+ action to take : recurse to children. This mean we need to push an **ContextElement** to stack. Recursion means pushing to stack. We will execute Statement 4 above. If we reach leaf node(base case), just process the result and return 

How can we know we are from parent? If the digit index previously touched is less than current one, we are from parent. So we need to keep the a variable **prev**. 

	if (prev.digitIndex < current.digitIndex) // we are from parent
	
One special case is the initial case, where 

	prev==null

+ condition

	if (prev==null || prev.digitIndex < current.digitIndex)

### child ->parent traversal: return from Statement 4. 
+ action to take:  current subtree is processed completely. We need to process subtree rooted in next sibling (statement 2). If we are last sibling, simply return.
+ condition:

	if (prev.digitIndex >current.digitIndex ) //we are from child.
	
**The return from Statement 4** is really not a statement, but it represents a traversal from **all children to current node**.
	
How can we know the next sibling? We need to keep the index of current node in parent children. The next sibling will be currentIndex+1. We add an charIndex for this purpose. 
	
	public static class ContextElement {
		public int digitIndex; // the index for nums.
		public int charIndex;	
	}

In the recursiveSub, the for loop is the action of moving to sibling.

### We are from sibling
+ action to take: recurse to child.
+ condition 

		if (prev.digitIndex == current.digitIndex ) //we are from sibling.


#Analysis of iterative pre-order traversal of binary tree.
	
	public <T> void enhancedIterative(TreeNode<T> root, Consumer<T> consumer){
		
		if (root==null){
			return;
		}
		
		Stack<TreeNode<T>> stack=new Stack<>();
		
		TreeNode<T> current=null;
		stack.push(root);
		
		while (!stack.isEmpty()){
			current=stack.pop();
			consumer.accept(current.val);
			if (current.right!=null){
				stack.push(current.right);
			}
			if (current.left!=null){
				stack.push(current.left);
			}
		}	
	}
Why this iterative function is much simpler than generic approach?
Once we return to parent, we don't need do anything any more. We don't use the parent node. The only thing we need to do is moving to sibling. But if we can pre push all sibling in **reverse** order, we don't need the **prev** variable to compute the traversal action.

We have a version of enhancedIterative for TelephoneWords.java using this technique. The problem is that we have big stack. The stack size is 

(C0-1)*(C1-1)...*(Cn) where Ci is the children size at level i. This is not a problem for binary tree. But it is a problem for multi-way tree.


#Analysis of iterative IN ORDER traversal of binary tree.	
need to understand the node expand logic


#Analysis of iterative post-order traversal of binary tree.	
We have to use the generic iterative approach. When we traverse from child to parent, we need to take action on parent node by calling consumer.

#Tail recursion can transformed to a while loop easily without stack.

1. There is no any action after we return from child to parent.
  # There is no any process on parent node so we can add one children to stack without keep the parent.
  # there is no moving so sibling. For an input, the call flow is a **single line tree**.
2. The result from base is a the final result. 


Please see the TailRecursionFactorial.java as an example.


#Tree builder

1. To build a tree node, we first process the properties that give node's value. 
  + For TreeBuilder.java, the node's value is name.
  + For ProcessFunctionCallLog.java, the node's value is function name.
2. For this node, do we need to build a subtree or is it a leaf node?
  + For TreeBuilder,java, if the next character after name is (, it has a substree. otherwise, it is a leaf node.  If it is a leaf node, we reach the base case. If it has subtree, the node itself is complete only after subtree is built completely.
  + For ProcessFunctionCallLog.java, It has subtree if the next line starts with "START". It is leaf node if next line starts with END. We just **try** to build a start tree if possible.
3. We skip the node's separator and continue for next children node.
  + For TreeBuilder.java, the separator is ",".
  + For ProcessFunctionCallLog.java, there is no separator. we are at a position for next node automatically after this node is complete

#Build balanced BST tree from array or list
**ConvertSortedLinkedListIntoBalancedBST**
The trick is very clever.  Give a list, the issue is that we don't know where the root is located at. But we know the list's length, we know the **root position**. But we could not access the root by position index since random access is not available.  List value can be accessed by pointer(iterator). We could construct the root node if we could arrange that the pointer is already positioned at root when it is time to retrieve root value.

We divide the list into two parts by length. 
1. We build the left tree first. 
2. When it comes to build the root, iterator is already positioned correctly for us to read the root value.
3. Then we build the right tree. At this time, iterator is already positioned correctly at the beginning of the right list.

If we build right tree first, root, then left tree. This flow will not work. For array, we can build right tree, root, left tree.







