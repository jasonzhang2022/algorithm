package jason.algorithm.dp;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

/*
 * http://www.comp.leeds.ac.uk/roger/HiddenMarkovModels/html_dev/viterbi_algorithm/s1_pg1.html
 * check the content bar at left side for preface
 * 
 */
public class VibertiAlgorithm {
	
	double[][] probabilities;
	public int[] getMostProbabeSequence(double[][] transitionMatrix, double[][] confusionMatrix, double[] initialMatrix, int[] observedSequence){

		int numOfHiddenState=transitionMatrix[0].length;
		
		/*
		 * [i][j]=The probability of having observedSequence[i] at time i with ending state[j]
		 */
		probabilities=new double[observedSequence.length][numOfHiddenState];
		/*
		 * [i][j] from where we get the i,j
		 */
		int[][] track=new int[observedSequence.length][numOfHiddenState];
		
		//initialize the first observation.
		int initialObservation=observedSequence[0];
		for (int hiddenState=0; hiddenState<numOfHiddenState; hiddenState++){
			probabilities[0][hiddenState]=initialMatrix[hiddenState]*confusionMatrix[hiddenState][initialObservation];
		}
		
		for (int time=1; time<observedSequence.length; time++){
			int observedState=observedSequence[time];
			
			for (int hiddenState=0; hiddenState<numOfHiddenState; hiddenState++){
				//for each hidden state, we have n possible path
				double prob=0;
				for (int fromState=0; fromState<numOfHiddenState; fromState++){
					double tempProb=probabilities[time-1][fromState]*transitionMatrix[fromState][hiddenState];
					if (tempProb>prob){
						track[time][hiddenState]=fromState;
						prob=tempProb;
					}
				}
				prob*=confusionMatrix[hiddenState][observedState];
				probabilities[time][hiddenState]=prob;
			}
		}
		
		//pick the last state with the biggest probability
		int biggestState=-1;
		double biggestProb=0;
		for (int hiddenState=0; hiddenState<numOfHiddenState; hiddenState++){
			if (probabilities[observedSequence.length-1][hiddenState]>biggestProb){
				biggestProb=probabilities[observedSequence.length-1][hiddenState];
				biggestState=hiddenState;
			}
		}
		
		int[] bestPath=new int[observedSequence.length];
		bestPath[observedSequence.length-1]=biggestState;
		int currentState=biggestState;
		for (int time=observedSequence.length-1; time>0; time--){
			int fromState=track[time][currentState];
			bestPath[time-1]=fromState;
			currentState=fromState;
		}
		
		return bestPath;
	}
	
	
	@Test
	public void test(){
		String[] hiddenStates={"Sunny", "Cloudy", "Rainy"};
		String[] obserdStates={"Dry", "Dryish", "Damp", "Soggy"};
		double[] initialMatrix={0.63, 0.17, 0.20};
		double[][] transitionMatrix={
				{0.500, 0.250, 0.250},
				{0.375, 0.125, 0.375},
				{0.125, 0.675, 0.375}
		};
		double[][] confusionMatrix={
				{0.60, 0.20, 0.15, 0.05},
				{0.25, 0.25, 0.25, 0.25},
				{0.05, 0.10, 0.35, 0.50}
		};

		int[] observedSequence={1,3,2,3};
		int[] bestPath= getMostProbabeSequence(transitionMatrix, confusionMatrix, initialMatrix, observedSequence);
		Arrays.stream(observedSequence).forEach(i->System.out.printf("%s->", obserdStates[observedSequence[i]]));
		System.out.println("");
		Arrays.stream(bestPath).forEach(i->System.out.printf("%s->", hiddenStates[i]));
	}

	
	//https://en.wikipedia.org/wiki/Viterbi_algorithm
	@Test
	public void testWiki(){
		String[] hiddenStates={"Healthy", "Fever"};
		String[] obserdStates={"normal", "cold", "dizzy"};
		double[] initialMatrix={0.6, 0.4};
		double[][] transitionMatrix={
				{0.7, 0.3},
				{0.4, 0.6}
		};
		double[][] confusionMatrix={
				{0.5, 0.4, 0.1},
				{0.1, 0.3, 0.6}
		};
		//'normal', 'cold', 'dizzy'
		int[] observedSequence={0, 1, 2};
		int[] bestPath= getMostProbabeSequence(transitionMatrix, confusionMatrix, initialMatrix, observedSequence);
		Arrays.stream(observedSequence).forEach(i->System.out.printf("%s->", obserdStates[observedSequence[i]]));
		System.out.println("");
		Arrays.stream(bestPath).forEach(i->System.out.printf("%s->", hiddenStates[i]));
		System.out.println("");
		double prob=probabilities[2][bestPath[observedSequence.length-1]];
		String probStr=String.format("%.5f", prob);
		assertEquals("0.01512", probStr);
		System.out.println(prob);
	}

}
