package jason.algorithm.dp;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TspDynamicProgrammingRecursive {

    public static double find(double[][] distance) {

        int n = distance.length;
        int finalState = ( 1<<n)-1;

        //  represents the cost from (i, a state) to the finalState.
        // a state is what node are already in the end. It is a set, not a permutation.
        double[][] cache = new double[n][1<<n];
        for (int i=0; i<n; i++){
            Arrays.fill(cache[i], -1);
        }

        return helper(0, 1, distance, cache, n, finalState);
    }

    public static double helper(int u, int fromState, double[][] distance, double[][] cache, int n, int finalState){
        if (fromState == finalState) {

            // this is very important. This is the distance from source to the first node in permutation.
            return distance[0][u];
        }
        if (cache[u][fromState]!=-1){
            return cache[u][fromState];
        }

        double minCost = Double.MAX_VALUE;

        /*
         * We want to add the remaining node set: finalState - fromState
         */
        // which node will be next end node which can be any one. This is essentially a permutation.
        // However, instead of caching result for the permutation, we cache the result for the combination set.
        // (we can't cache result for permutation, there are too many)
        // ( we pick the minimal cost for all the permutation , and cache it)
        for (int nextEnd=0; nextEnd<n; nextEnd++) {
            int nextBit = 1<<nextEnd;

            // nextEnd is already in from state
            if ((fromState & nextBit) !=0) {
                continue;
            }

            int stateForNext = fromState | nextBit;
            minCost = Math.min(minCost, helper(nextEnd, stateForNext, distance, cache, n, finalState)+distance[nextEnd][u]);
        }

        cache[u][fromState]=minCost;
        return minCost;
    }

    public static class TestCase {

        @Test
        public void test1(){
            int n = 6;
            double[][] distanceMatrix = new double[n][n];
            for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
            distanceMatrix[1][4] = distanceMatrix[4][1] = 2;
            distanceMatrix[4][2] = distanceMatrix[2][4] = 4;
            distanceMatrix[2][3] = distanceMatrix[3][2] = 6;
            distanceMatrix[3][0] = distanceMatrix[0][3] = 8;
            distanceMatrix[0][5] = distanceMatrix[5][0] = 10;
            distanceMatrix[5][1] = distanceMatrix[1][5] = 12;

            assertThat(42.0d, equalTo(find(distanceMatrix)));
        }

        @Test
        public void test2(){
            int n = 3;
            double[][] distanceMatrix = new double[n][n];
            for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
            distanceMatrix[0][1] = distanceMatrix[1][0] = 2;
            distanceMatrix[1][2] = distanceMatrix[2][1] = 3;
            distanceMatrix[2][0] = distanceMatrix[0][2] = 6;
            assertThat(11.0d, equalTo(find(distanceMatrix)));
        }

    }
}