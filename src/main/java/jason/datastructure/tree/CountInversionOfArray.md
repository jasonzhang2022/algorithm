14.1-7
Show how to use an order-statistic tree to count the number of inversions (see Problem 2-4) in an array of size n in time O(n lg n).
We can insert the n elements into an OS-tree one by one and using OS-RANK to find the rank of ith element Xi, r(Xi),  right after it is inserted. So the number of elements which are prior to Xi in the array (Thus inserted into the tree earlier than Xi) and larger than Xi is i – r(Xi).
Thus we have: # of inversions = ∑(i=1~n) i – r(Xi).
Insertion and OS-RANK each take O(lg n) time, and so the total time for n elements is O(n lg n).


Use dynamic programming

Suppose f(n) is the number of inversion for n items.
f(n+1)=f(n) plus the inversion introduced by last items.

Suppose we already know f(n) by arranging first n item in a BST tree.
We add the n+1 item. n-rank(n) will be the inversion introduced by adding the n+1 item.


