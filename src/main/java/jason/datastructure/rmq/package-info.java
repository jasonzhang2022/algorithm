
/**
 * several Range Minimum Query implementation.
 * @author jason.zhang
 * 
 * 
 * ScanRMQ: 
 * 	no proprocess. Minimum is scanned as is needed.  Query is time-consuming if minimum is queried repeatedly.
 * 
 * DpRMQ: minimum is precomputed for all possible (start, end) combination. 
 * 	      Precompute time is O(N^2), query time is O(1). consume O(N^2) space.
 * SparseTable: 
 * 		minimum is precomputed for almost all possible combination. Precompute Time. N*logN, space is N*logN. Query time is logN.
 * 		Compared with DpRMQ, it uses less process time, but query takes more time.
 * 
 *     Hierarchical structure. Keep overlapped range. Keep more information than segmentTree. 
 *  
 * SqrtPartitionRMQ:  
 *  	Partition query into equal parts, each part has sqrt(N) elements. 
 *  	Preprocess time O(N), Query time is O(sqrt(N)).
 *  	
 *  	Not keep overlapped range: Flat structure.
 *      
 *  SegmentTree, 
 *  	Process time  is linear Q(N) One for each tree Node, lookup time is O(logN)
 *  
 *  	Compare with sqrtParition, query is more efficient. But Consuming more space.
 *  
 *  	Hierarchical strucutre. Keep overlapped range.
 *  
 *  if input is small, any algorithm works.
 *  
 *  If the input is bigger, DpRQM is not practical since too much storage space is used. However, if space is not an issue, and 
 *  query is performed repeated, DpRMQ provides best query performances.
 *  
 *  SegmentTree is most practical solution for big input.
 *  
 *  
 */
package jason.datastructure.rmq;