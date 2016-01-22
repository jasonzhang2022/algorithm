package jason.algorithm.sort;

import org.junit.Before;

import jason.algorithm.util.Shuffler;

public class TestSetup {

	protected int inputLen=1000000;
	protected int[] input;
	@Before
	public void setup() {
		input=new int[inputLen];
		for (int i=0; i<inputLen; i++) {
			input[i]=i;
		}
		Shuffler.shuffle(input);
	}
	
}
