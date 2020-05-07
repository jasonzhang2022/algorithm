package jason.datastructure.segmenttree;

import jason.algorithm.util.Shuffler;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

//Full binary tree can be represented by an array.
public class SegmentTree {
    /*
     *use an array to represent the segment tree.
     *For a node at index i, the left child is 2*i+1, the right child is 2*i+2, the parent is floor((i-1)/2)
     *leave node contains the elements from input array. All internal node contains the summary information
     *
     *How can we know the internal a node represents for.
     *We do not: we use the same logic in the building tree process so that we calculate the internal when we walk down the
     *tree.
     *
     */
    public int[] tree;
    ChildProcessor processor;
    int inputLen = 0;


    public SegmentTree(int inputLen, ChildProcessor processor) {
        this.inputLen = inputLen;

        int mostSignifiantBit = Double.valueOf(Math.floor(Math.log(inputLen) / Math.log(2))).intValue();

        if (1 << mostSignifiantBit < inputLen) {
            mostSignifiantBit++;
        }

        // although inputLength is requested, we can handle more than that.
        int numberOfLeafNodes = 1 << mostSignifiantBit;
        int numberOfInternalNodes = numberOfLeafNodes - 1;
        // this also works
       // this.inputLen = numberOfInternalNodes;

        tree = new int[numberOfInternalNodes + numberOfLeafNodes];

        this.processor = processor;


    }

    public int queryRange(int start, int end) {

        start = Math.min(start, inputLen - 1);
        end = Math.min(inputLen - 1, end);


        return queryRange(0, 0, inputLen - 1, start, end);
    }

    private int queryRange(int root, int segmentStart, int segmentEnd, int queryStart, int queryEnd) {

        if (segmentStart == segmentEnd) {
            // leaf node
            assert queryEnd == queryStart : "query is the same node";
            assert segmentStart == queryStart : "query has the same index as segment";
            return tree[root];
        }

        if (segmentStart == queryStart && segmentEnd ==queryEnd) {
            return tree[root];
        }

        int middle = (segmentEnd - segmentStart) / 2 + segmentStart;
        int leftRoot = 2 * root + 1;
        int rightRoot = 2 * root + 2;
        if (queryEnd <= middle) {
            return queryRange(leftRoot, segmentStart, middle, queryStart, queryEnd);
        }
        if (queryStart > middle) {
            return queryRange(rightRoot, middle + 1, segmentEnd, queryStart, queryEnd);
        }


        int leftValue = queryRange(leftRoot, segmentStart, middle, queryStart, middle);
        int rightValue = queryRange(rightRoot, middle + 1, segmentEnd, middle + 1, queryEnd);

        return processor.process(leftValue, rightValue);
    }

    public void update(int i, int value) {
        update(0, 0, inputLen-1, i, value);
    }


    private void update(int root, int segmentStart, int segmentEnd, int i, int value) {

        if (segmentStart == segmentEnd) {
            // leaf node
            assert i == segmentEnd : "index is correct";
            tree[root] = value;
            return;
        }


        int middle = (segmentEnd - segmentStart) / 2 + segmentStart;
        int leftRoot = 2 * root + 1;
        int rightRoot = 2 * root + 2;

        if (i <= middle) {
            update(leftRoot, segmentStart, middle, i, value);
        } else {
            update(rightRoot, middle + 1, segmentEnd, i, value);
        }
        tree[root] = processor.process(tree[leftRoot], tree[rightRoot]);
    }


    public static class TestCase {



        @Test
        public void basicTest(){
            SegmentTree segmentTree=new SegmentTree(5, new SumProcessor());
            segmentTree.update(1, 2);
            assertThat(segmentTree.queryRange(0,3), equalTo(2));
            segmentTree.update(3, 3);

            assertThat(segmentTree.queryRange(0,3), equalTo(5));
            assertThat(segmentTree.queryRange(2,4), equalTo(3));
        }



        public int scanMinimum(int[] input, int start, int end) {
            int min=Integer.MAX_VALUE;
            for (int i=start; i<=end; i++) {
                if (input[i]<min) {
                    min=input[i];
                }
            }
            return min;
        }


        @Test
        public void testMin() {
            int[] input=new int[100];
            for (int i=0; i<100; i++) {
                input[i]=i;
            }

            Shuffler.shuffle(input);
            for (int i=0; i<10; i++) {
                System.out.print(i*10+": ");
                for (int j=0; j<10; j++) {
                    System.out.print(input[i*10+j]+" ");
                }
                System.out.println("");
            }

            Random random=new Random(new Date().getTime());

            SegmentTree segmentTree=new SegmentTree(input.length, new MinimumProcessor());
            Arrays.fill(segmentTree.tree, 200);
            for (int i=0; i<input.length; i++) {
                segmentTree.update(i, input[i]);
            }

            for (int i=0; i<input.length; i++){
                assertThat(input[i], equalTo(segmentTree.queryRange(i, i)));
            }


            for (int i=0; i<20; i++) {
                int start=random.nextInt(100);
                int end=random.nextInt(100);
                if (start>end) {
                    int temp=start;
                    start=end;
                    end=temp;
                }

                int min=scanMinimum(input, start, end);
                int stmin=segmentTree.queryRange(start, end);
                System.out.println("min :("+start+","+end+")="+min);
                assertThat(min, equalTo(stmin));
            }
        }


        public int scanSum(int[] input, int start, int end) {
            int sum=0;
            for (int i=start; i<=end; i++) {
                sum+=input[i];
            }
            return sum;
        }

        @Test
        public void testSum() {
            int[] input=new int[100];
            for (int i=0; i<100; i++) {
                input[i]=i;
            }

            Shuffler.shuffle(input);
            for (int i=0; i<10; i++) {
                System.out.print(i*10+": ");
                for (int j=0; j<10; j++) {
                    System.out.print(input[i*10+j]+" ");
                }
                System.out.println("");
            }

            Random random=new Random(new Date().getTime());

            SegmentTree segmentTree=new SegmentTree(input.length, new SumProcessor());
            for (int i=0; i<input.length; i++) {
                segmentTree.update(i, input[i]);
            }
            for (int i=0; i<input.length; i++){
                assertThat(input[i], equalTo(segmentTree.queryRange(i, i)));
            }
            for (int i=0; i<20; i++) {
                int start=random.nextInt(100);
                int end=random.nextInt(100);
                if (start>end) {
                    int temp=start;
                    start=end;
                    end=temp;
                }

                int sum=scanSum(input, start, end);
                int stsum=segmentTree.queryRange(start, end);
                System.out.println(String.format("sum %d, %d, %d", start, end, i));
                assertThat(sum, equalTo(stsum));
            }



        }

    }
}
