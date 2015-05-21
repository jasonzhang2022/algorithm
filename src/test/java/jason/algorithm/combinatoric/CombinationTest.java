package jason.algorithm.combinatoric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import jason.algorithm.Shuffler;

import org.junit.Test;
public class CombinationTest {

	public static class Count {
		int count;
	}
	
	@Test
	public void TestCombination(){
		String input="ABCDEFGH";
		int n=input.length();
		int k=3;
		
		int expectedCombination=n*(n-1)*(n-2)/3/2;
		Count c=new Count();
		
		Consumer<int[]> consumer= a->{
			for (int index: a){
				System.out.print(input.charAt(index));
			}
			System.out.print("\n");
			c.count++;
		};
		
		Combination.combination(n, k, consumer);
		assertEquals(expectedCombination, c.count);
	}
}
