[problem reference](http://ripcrixalis.blog.com/2011/02/08/clrs-chapter-14/)
#Problems 14-1: Point of Maximum Overlap

compare with this approach

Suppose that we wish to keep track of a point of maximum overlap in a set of intervals—a point that has the largest number of intervals in the database overlapping it.

1. Show that there will always be a point of maximum overlap which is an endpoint of one of the segments.
2. Design a data structure that efficiently supports the operations INTERVAL-INSERT, INTERVAL-DELETE, and FIND-POM, which returns a point of maximum overlap. (Hint: Keep a red-black tree of all the endpoints. Associate a value of +1 with each left endpoint, and associate a value of -1 with each right endpoint. Augment each node of the tree with some extra information to maintain the point of maximum overlap.)

1. Easy to proof by contradiction.
2. Keep a RB-tree of all the endpoints. We insert endpoints one by one as a sweep line scaning from left to right. With each left endpoint e, associate a value p[e] = +1 (increasing the overlap by 1). With each right endpoint e associate a value p[e] = −1 (decreasing the overlap by 1). When multiple endpoints have the same value, insert all the left endpoints with that value before inserting any of the right endpoints with that value.

Here is some intuition. Let e1, e2, . . . , en be the sorted sequence of endpoints corresponding to our intervals. Let s(i, j) denote the sum p[ei] + p[ei+1] + · · · + p[ej] for 1 ≤ i ≤ j ≤ n. We wish to find an i maximizing s(1, i ).

Each node x stores three new attributes. 
1. We store v[x] = s(l[x], r [x]), the sum of the values of all nodes in x’s subtree. 
2. We also store m[x], the maximum value obtained by the expression s(l[x], i) for any i. 
3. We store o[x] as the value of i for which m[x] achieves its maximum. For the sentinel, we define v[nil[T]] = m[nil[T]] = 0.

We can compute these attributes in a bottom-up fashion so as to satisfy the requirements of Theorem 14.1:

	v[x] = v[left[x]] + p[x] + v[right[x]] ,
	m[x] = max{
		m[left[x]] (max is in x’s left subtree, ending at some node  o(x)=o(left[x]) at left subtree. ),
		v[left[x]] + p[x] (max is at x: o(x)=x ),
		v[left[x]] + p[x] + m[right[x]] (max is in x’s right subtree, o(x)=o(right[x]) ). 
	}


detailed explanation: https://cise.ufl.edu/class/cot5405sp13/HW3_solution_sp13.pdf
	

The computation of v[x] is straightforward. The computation of m[x] bears further explanation.
Recall that it is the maximum value of the sum of the p values for the nodes in x's subtree,
starting at l[x], which is the leftmost endpoint in x.s subtree and ending at any node i in x.s
subtree. The value of i that maximizes this sum is either a node in x's left subtree, x itself, or a
node in x's right subtree. If i is a node in x's left subtree, then m[left[x]] represents a sum
starting at l[x], and hence m[x] = m[left[x]]. If i is x itself, then m[x] represents the sum of all p
values in x's left subtree plus p[x], so that m[x] = v[left[x]] + p[x]. Finally, if i is in x's right subtree, 
then m[x] represents the sum of all p values in x's left subtree, plus p[x], plus the sum of some
set of p values in x's right subtree. Moreover, the values taken from x's right subtree must start
from the leftmost endpoint in the right subtree. To maximize this sum, we need to maximize the
sum from the right subtree, and that value is precisely m[right[x]]. Hence, in this case,
m[x]=v[left[x]]+p[x] + m[right[x]].
Once we understand how to compute m[x], it is straightforward to compute o[x] from the
information in x and its two children. Thus, we can implement the operations as follows:

+ INTERVAL-INSERT: insert two nodes, one for each endpoint of the interval.
+ INTERVAL-DELETE: delete the two nodes representing the interval endpoints.
+ FIND-POM: return the interval whose endpoint is represented by o[root[T]].

Because of how we have defined the new attributes, Theorem 14.1 says that each operation
runs in O(lgn) time. In fact, FIND-POM takes only O(1) time.

##Note for POM
Sort all end points in an array. Suppose the array's length is N.

For end point k, the sum of end point from 1 to K is not stored at node corresponding to end point k. It may not stored in anywhere.
**But for any subtree root at X, x stores the information form L(X) to R(X).** 


#Analysis

Augmenting red black tree is like binary dynamic programming

Suppose we have a solution for n items, we can have a solution for n+1 item in a log(N) runtime. 

Each tree node may not be correct solution for its subtree. 
For example, for the example above, a child right tree does not a solution for POM from beginning to end of tree. But, it is right solution from its minimal value to its maximal value. This solution itself is useless. It becomes useful to derive the total correct solution by its parent.

The root node is a solution for minimal to maximal.(not just from minimal to root node itself).

#Problems 14-2: Josephus Permutation
[problem reference](http://ripcrixalis.blog.com/2011/02/08/clrs-chapter-14/)
Computation flow

1. Construct BST. Ordered person in an array can be converted to BST.
2. Define a routine which will remove a node **kth** from offset **O** in a tree of size **n**.
3. Each time we remove a node, we use next node as offset, adjust tree size to n-1, delete next kth.
4. we stop until we has only one node. 

Give a tree with size N, what is the function in step like?
Input BST, size N, offset O, K.
	rank=(O+K)%N: constant time.
	remove node at rank: logN time.
	adjust new size to N-1, offset O=rank, 


Why BST? 
**BST is a sorted array which supports random access, dynamic deletion/insertion in NlogN time**.
