[problem reference](http://ripcrixalis.blog.com/2011/02/08/clrs-chapter-14/)
Problems 14-1: Point of Maximum Overlap
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
<div style="background-color:grey>
>v[x] = v[left[x]] + p[x] + v[right[x]] ,
>m[x] = max{
>m[left[x]] (max is in x’s left subtree),
>v[left[x]] + p[x] (max is at x),
>v[left[x]] + p[x] + m[right[x]] (max is in x’s right subtree). }
</div>
Once we understand how to compute m[x], it is straightforward to compute o[x] from the information in x and its two children.
FIND-POM: return the interval whose endpoint is represented by o[root[T]].
Because of how we have deÞned the new attributes, Theorem 14.1 says that each operation runs in O(lg n) time. In fact, FIND-POM takes only O(1) time.

#Analysis

Augmenting red black tree is like binary dynamic programming

Suppose we have a solution for n items, we can have a solution for n+1 item in a log(N) runtime. 

Each tree node may not be correct solution for its subtree. 
For example, for the example above, a child right tree does not a solution for POM from beginning to end of tree. But, it is right solution from its minimal value to its maximal value. This solution itself is useless. It becomes useful to derive the total correct solution by its parent.

The root node is a solution for minimal to maximal.(not just from minimal to root node itself).


