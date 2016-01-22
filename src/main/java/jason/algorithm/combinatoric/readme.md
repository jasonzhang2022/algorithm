#review
+ combination with repetition: We have k stars and n-1 bar. The n-1 bar partitions the k starts in n slots. The number of stars at slot i gives the number of i<sup>th</sup> element in one combination. 
+ Combination **without** repetition can be viewed as a special case of combination with repetition.
  + Each element can appear **0 to k** time for k combination of n elements for combination **with** repetition
  + Each element can appear **0 to 1** time for k combination of n elements for combination **without** repetition

##permutation with reuse. 
This is not valid case. Suppose that we have n elements, we want to have a length of K element. At each position, we could have n choice. So the total number of permutation is n to the k<sup>th</sup>. Suppose the element are not unique. We could keep only one copy for each non-unique element. Permute after this. 

#permutation of multisets.
We have n elements. The output sequence will have n elements, too. The multiplicity of each element from input will equal to that in output.

#print Parenthesesis {}
compare this with  **permutation** with reuse without any restriction


