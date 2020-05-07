package jason.algorithm.practice;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaximumSubArrayProduct {


    //http://www.programcreek.com/2014/03/leetcode-maximum-product-subarray-java/
    //solution above
    public static int maxProductScan(int[] A) {

        // this is a global answer.
        int max = Integer.MIN_VALUE;


        /**
         * Answer for a subarray ends at a particular index.
         * All the product values ending with last index are in this range.
         * lastMin and lasMax are two extreme values.
         */
        // This is the mininum for a subarray ending at last index;
        int lastMin = 1;
        // This is the maximum for a subarray ending with last index
        int lastMax = 1;

        for (int a : A) {

            /*
             * For an array, ending with a. We have several possibility
             * 1. continue from one of extreme value before.
             *    a) The new range is defined by the extreme range before. We
             *      may need to flip sign.
             * 2. starting a new array with myself.
             */
            int maxWithBeforeEndWitha = Math.max(a * lastMax, a * lastMin);
            int minWithBeforeEndWitha = Math.min(a * lastMax, a * lastMin);

            int currentMax = Math.max(maxWithBeforeEndWitha, a);
            int currentMin = Math.min(minWithBeforeEndWitha, a);

            if (currentMax > max) {
                max = currentMax;
            }
            lastMin = currentMin;
            lastMax = currentMax;

        }
        return max;


    }

    public static class TestCaseScan {
        @Test
        public void testMaxproduct() {

            //simple test case
            assertEquals(-1, maxProductScan(new int[]{-1}));

            assertEquals(2, maxProductScan(new int[]{2}));

            assertEquals(6, maxProductScan(new int[]{-2, -3}));
            assertEquals(6, maxProductScan(new int[]{2, 3}));

            assertEquals(4, maxProductScan(new int[]{-2, 4}));

            //simple test case: mixed sign
            assertEquals(6, maxProductScan(new int[]{2, 3, -2, 4}));
            assertEquals(24, maxProductScan(new int[]{2, 3, 4, -2}));
            assertEquals(24, maxProductScan(new int[]{-2, -3, -2, 4}));
            assertEquals(24, maxProductScan(new int[]{-2, -3, 4, -2}));


            assertEquals(2 * 3 * 2 * 2 * 4, maxProductScan(new int[]{2, 3, -2, -2, 4}));

            //second half is bigger than first half
            //2*3*2*3=36
            //3*2*4*5=120
            assertEquals(120, maxProductScan(new int[]{2, 3, -2, -3, -2, 4, 5}));

            assertEquals(2, maxProductScan(new int[]{-1, 0, 2}));

        }
    }

    public static int maxProductBruteForce(int[] A) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < A.length; i++) {
            for (int l = 0; l < A.length; l++) {
                if (i + l < A.length) {
                    int product = calProduct(A, i, l);
                    max = Math.max(product, max);
                }

            }

        }
        return max;
    }

    public static int calProduct(int[] A, int i, int j) {
        int result = 1;
        for (int m = i; m <= j; m++) {
            result = result * A[m];
        }
        return result;
    }

    @Test
    public void testMaxproductBruteForce() {

        //simple test case
        assertEquals(-1, maxProductBruteForce(new int[]{-1}));

        assertEquals(2, maxProductBruteForce(new int[]{2}));

        assertEquals(6, maxProductBruteForce(new int[]{-2, -3}));
        assertEquals(6, maxProductBruteForce(new int[]{2, 3}));

        assertEquals(4, maxProductBruteForce(new int[]{-2, 4}));

        //simple test case: mixed sign
        assertEquals(6, maxProductBruteForce(new int[]{2, 3, -2, 4}));
        assertEquals(24, maxProductBruteForce(new int[]{2, 3, 4, -2}));
        assertEquals(24, maxProductBruteForce(new int[]{-2, -3, -2, 4}));
        assertEquals(24, maxProductBruteForce(new int[]{-2, -3, 4, -2}));


        assertEquals(2 * 3 * 2 * 2 * 4, maxProductBruteForce(new int[]{2, 3, -2, -2, 4}));

        //second half is bigger than first half
        //2*3*2*3=36
        //3*2*4*5=120
        assertEquals(120, maxProductBruteForce(new int[]{2, 3, -2, -3, -2, 4, 5}));


    }


}
