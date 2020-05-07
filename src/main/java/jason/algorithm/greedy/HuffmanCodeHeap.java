package jason.algorithm.greedy;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class HuffmanCodeHeap {


    public static class Node {
        Node left;
        Node right;

        char c =0;
        int freq;

        public Node(char c, int freq){
            this.c = c;
            this.freq = freq;
        }
    }



    public static void traverse(Node node, String prefix, Map<Character, String> map) {
        if (node.left==null && node.right==null){
            map.put(node.c, prefix);
            return;
        }

        if (node.left!=null){
            traverse(node.left, prefix+"0", map);
        }
        if (node.right!=null) {
            traverse(node.right, prefix+"1", map);
        }
    }

    public static Map<Character, String> computeCode(int[] freq, char[] chars){
        PriorityQueue<Node> pq = new PriorityQueue<>( (a, b)->Integer.compare(a.freq, b.freq));
        for (int i=0; i<chars.length; i++){
            pq.offer(new Node(chars[i], freq[i]));
        }

        while (pq.size() >1) {
            Node min1 = pq.poll();
            Node min2 = pq.poll();

            Node node = new Node((char)0, min1.freq+ min2.freq);
            node.left = min1;
            node.right = min2;
            pq.offer(node);
        }

        Node root = pq.poll();

        Map<Character, String> map = new HashMap<>();
        traverse(root, "", map);
        return map;
    }

    public static class TestCase {
        @Test
        public void test() {
            char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
            int freq[] = {5, 9, 12, 13, 16, 45};

            Map<Character, String> result = computeCode(freq, chars);
            for (Character c : result.keySet()) {
                System.out.printf("%c: %s\n", c, result.get(c));
            }
            assertThat(result.get('f'), equalTo("0"));
            assertThat(result.get('a'), equalTo("1100"));
            assertThat(result.get('c'), equalTo("100"));

        }
    }
}
