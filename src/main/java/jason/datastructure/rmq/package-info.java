
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
 * 			
 *       Consume too much space. Not practical for big input.
 *       
 *SqrtPartitionRMQ:  
 *  	Partition query into equal parts, each part has sqrt(N) elements. 
 *  	Preprocess time O(N), Query time is O(sqrt(N)).
 *  	
 *  	Not keep overlapped range: Flat structure.
 *	    Very decent: less preprocess timee, less space, resonable query time.
 *  
 * SparseTable: 
 * 		minimum is precomputed for almost all possible combination. Precompute Time. N*logN, space is N*logN. Query time is constant.
 * 		Compared with DpRMQ, it uses less process time, less space, and constant query time.
 * 
 *     Hierarchical structure. Keep overlapped range. Keep more information than segmentTree. 
 *  
 *      
 *  SegmentTree, 
 *  	Process time  is linear Q(N) One for each tree Node, lookup time is O(logN)
 *  
 *  	Compare with SparseTable. It has better preprocess time, less space, but query takes long.
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