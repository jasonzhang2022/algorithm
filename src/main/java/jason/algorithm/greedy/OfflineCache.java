package jason.algorithm.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class OfflineCache {

	
	
	public int miss(char[] input, int cacheLen){
		
		int n=input.length;
		//assume ASII character
		int[] prevIndicesForChar=new int[256];
		int[] next=new int[n];
		Arrays.fill(prevIndicesForChar,  -1);
		Arrays.fill(next,  Integer.MAX_VALUE);
		
		//linear
		for (int i=0; i<input.length; i++){
			char c=input[i];
			if (prevIndicesForChar[c]==-1){
				//first time see this character
				prevIndicesForChar[c]=i;
			} else{
				int prevIndex=prevIndicesForChar[c];
				next[prevIndex]=i;
				prevIndicesForChar[c]=i;
			}
		}
		//reuse prevIndicesForChar as currentIndicesForChar
		Map<Character, Boolean> cache=new HashMap<>();
		PriorityQueue<Character> evictQueue=new PriorityQueue<>((a, b)->{
			int aindex=prevIndicesForChar[a];
			int bindex=prevIndicesForChar[b];
			int nextAIndex=next[aindex];
			int nextBIndex=next[bindex];
			
			int ret=0;
			if (nextAIndex!=nextBIndex){
				ret= nextAIndex>nextBIndex?-1:1;
			}
		
			
			//System.out.printf(" %c at %d=%d, %c at %d =%d->%d\n", a, aindex,  nextAIndex,  b, bindex,nextBIndex, ret);
			return ret;
		});
		int miss=0;
		for (int i=0; i<n; i++){
			char c=input[i];
			prevIndicesForChar[c]=i;
			if (cache.containsKey(c)){
				//why this, we need a increase priority operation
				evictQueue.remove(c);
				evictQueue.offer(c);
				continue;
			}
			
			miss++;
			cache.put(c, true);
			evictQueue.offer(c);
			//missed
			if (evictQueue.size()==cacheLen+1){
				//evict is needed
				char evictC=evictQueue.poll();
				//System.out.printf("evitc %c at %d\n", evictC, i);
				cache.remove(evictC);
			} 
		}
		
		return miss;
	}
	
	@Test
	public void test() {
		
		String input="abcdcbfda";
		int expected=6;
		
		int result=miss(input.toCharArray(), 3);
		assertThat(result, equalTo(expected));
		
	}
}
