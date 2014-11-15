/**
 * Fibonacci heap is a mergeable heap structure supporting decrease-key
 * (not increase-key)
 * 
 * BinaryHeap is standard structure implementing Priority Queue. 
 * It has a compact structure and low constant. 
 * Insertion and deletion take O(N). Retrieving min takes O(1) time.
 * 
 * Both Fibobacci and Binomial heap are merge heap. Merging two heaps can be performed very quick.
 *  Fibonacci heap is a mergeable heap. Its  insertion takes O(1) time, better than binary heap.
 *  Its decrease-key takes amortized O(1) time, better than Binary time. 
 *  The decrease-key function is used in Dijkstra algorithm(single source shortest path).
 *  
 *  But 1)fibonacci map has big constant. 2) decrease-key is not required in most scenario so it is not 
 *  chosen as a general-purpose implementation. 
 *   
 *  
 * 
 * 
 */
package jason.datastructure.heap;
