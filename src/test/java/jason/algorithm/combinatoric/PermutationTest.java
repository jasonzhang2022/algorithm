package jason.algorithm.combinatoric;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Test;
public class PermutationTest {

	public static class Count {
		int count;
	}
	
	@Test
	public void TestPermutationN(){
		String input="ABCD";
		int expectedPermutation=4*3*2*1;
		Count c=new Count();
		
		Consumer<int[]> consumer= a->{
			for (int index: a){
				System.out.print(input.charAt(index));
			}
			System.out.print("\n");
			c.count++;
		};
		Permutation.permute(new int[]{0, 1,2,3}, consumer);
		
		assertEquals(c.count, expectedPermutation);
	}
	
	@Test
	public void TestPermutationNK(){
		String input="ABCDEFGH";
		int n=input.length();
		int k=3;
		
		int expectedPermutation=8*7*6;
		
		Set<String> results=new HashSet<>();
		char[] buf=new char[3];
		
		Consumer<int[]> permutationConsumer=a->{
			for (int i=0; i<a.length; i++)
			{
				buf[i]=input.charAt(a[i]);
			}
			String r=String.valueOf(buf);
			results.add(r);
			System.out.println(r);
		};
		
		Consumer<int[]> combinationConsumer= a->{
			Permutation.permute(a, permutationConsumer);
		};
		
		Combination.combination(n, k, combinationConsumer);
		assertEquals(results.size(), expectedPermutation);
		
		
	}
}
