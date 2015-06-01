package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.IdentityHashMap;
import java.util.Map;

import org.junit.Test;

public class TestIdentityHashMap {

	public static class Temp{
		int value=3;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + value;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Temp other = (Temp) obj;
			if (value != other.value)
				return false;
			return true;
		}
		
	}
	
	@Test
	public void test(){
		
		Temp temp1=new Temp();
		Temp temp2=new Temp();
		
		Map<Temp, Integer> map=new IdentityHashMap<Temp, Integer>();
		map.put(temp1, 3);
		assertTrue(map.containsKey(temp1));
		assertEquals(map.get(temp1).intValue(), 3);
		
		assertFalse(map.containsKey(temp2));
		
		map.put(temp2, 5);
		assertEquals(map.get(temp1).intValue(), 3);
		assertEquals(map.get(temp2).intValue(), 5);
	}
}
