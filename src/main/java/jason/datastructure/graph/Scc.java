package jason.datastructure.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;

public class Scc {

    //http://www.geeksforgeeks.org/strongly-connected-components/
    //Kosaraju’s algorithm
    public List<Set<String>> findScc(AjacentGraph graph) {
        Stack<Integer> stack = new Stack<>();

        int[] colors = new int[graph.vertices.verticesArray.size()];

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == 0) {
                dfs(i, graph, colors, u -> stack.push(u));
            }
        }

        List<Set<String>> result = new LinkedList<>();
        Arrays.fill(colors, 0);
        AjacentGraph transposed = graph.transpose();

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (colors[u] != 0) {
                continue;
            }
            Set<String> scc = new HashSet<>();
            dfs(u, transposed, colors, v -> scc.add(graph.vertices.getVertexByIndex(v).name));
            result.add(scc);
        }
        return result;
    }

    private void dfs(int u, AjacentGraph graph, int[] colors, Consumer<Integer> afterTask) {
        colors[u] = 1;
        for (Edge edge : graph.ajacentList.get(u)) {
            int v = edge.to;
            if (colors[v] == 0) {

                dfs(v, graph, colors, afterTask);
            }
        }
        afterTask.accept(u);
    }

    public static class StackTest {

        @Test
        public void testSccCompute() {
            AjacentGraph g = new AjacentGraph();
            g.addVertex("a");
            g.addVertex("b");
            g.addVertex("c");
            g.addVertex("d");
            g.addVertex("e");
            g.addVertex("f");
            g.addVertex("g");
            g.addVertex("h");

            g.initGraph();
            g.addEdge("a", "b", 1);
            g.addEdge("b", "c", 1);
            g.addEdge("b", "e", 1);
            g.addEdge("e", "a", 1);
            g.addEdge("e", "f", 1);
            g.addEdge("b", "f", 1);

            g.addEdge("f", "g", 1);
            g.addEdge("g", "f", 1);
            g.addEdge("c", "g", 1);
            g.addEdge("c", "d", 1);
            g.addEdge("d", "c", 1);
            g.addEdge("g", "h", 1);
            g.addEdge("d", "h", 1);
            g.addEdge("h", "h", 1);

            List<Set<String>> result = new Scc().findScc(g);
            String resultStr = result.stream().map(s -> s.stream().collect(Collectors.joining(","))).collect(Collectors.joining("|"));
            System.out.println(resultStr);
            String expected = "a,b,e|c,d|f,g|h";
            assertThat(resultStr, equalTo(expected));

        }
    }


    int[] low;
    int[] disc;
    Stack<Integer> stack;
    int time;
    List<String> result;
    int[] colors;

    public void findSccUseLowDiscUtil(AjacentGraph graph, int u) {

        //grey
        colors[u] = 1;
        low[u] = disc[u] = time++;

        stack.push(u);
        for (Edge edge : graph.ajacentList.get(u)) {
            int v = edge.to;

            //first time saw this
            if (colors[v] == 0) {
                findSccUseLowDiscUtil(graph, v);
                low[u] = Math.min(low[u], low[v]);
            } else if (colors[v] == 1) {
                //ancestor
                low[u] = Math.min(low[u], disc[v]);
            } else {
                // cross edge. Don't care.
            }
        }
        //black
        colors[u] = 2;


        if (low[u] == disc[u]) {
            Set<String> scc = new HashSet<>();
            while (stack.peek() != u && !stack.isEmpty()) {
                scc.add(graph.vertices.getVertexByIndex(stack.pop()).name);
            }
            if (!stack.isEmpty()) {
                scc.add(graph.vertices.getVertexByIndex(stack.pop()).name);
            }
            result.add(scc.stream().sorted().collect(Collectors.joining(",")));
        }

    }

    public void findSccUseLowDisc(AjacentGraph graph) {
        int size = graph.vertices.verticesArray.size();
        colors = new int[size];
        low = new int[size];
        disc = new int[size];
        result = new LinkedList<>();
        stack = new Stack<>();
        time = 0;

        for (int i = 0; i < size; i++) {
            if (colors[0] == 0) {
                findSccUseLowDiscUtil(graph, i);
            }
        }
    }

    public static class TarjanTest {
        @Test
        public void testSccComputeUsingLowDsic() {
            AjacentGraph g = new AjacentGraph();
            g.addVertex("a");
            g.addVertex("b");
            g.addVertex("c");
            g.addVertex("d");
            g.addVertex("e");
            g.addVertex("f");
            g.addVertex("g");
            g.addVertex("h");

            g.initGraph();
            g.addEdge("a", "b", 1);
            g.addEdge("b", "c", 1);
            g.addEdge("b", "e", 1);
            g.addEdge("e", "a", 1);
            g.addEdge("e", "f", 1);
            g.addEdge("b", "f", 1);

            g.addEdge("f", "g", 1);
            g.addEdge("g", "f", 1);
            g.addEdge("c", "g", 1);
            g.addEdge("c", "d", 1);
            g.addEdge("d", "c", 1);
            g.addEdge("g", "h", 1);
            g.addEdge("d", "h", 1);
            g.addEdge("h", "h", 1);

            Scc scc = new Scc();
            scc.findSccUseLowDisc(g);
            String resultStr = scc.result.stream().collect(Collectors.joining("|"));
            System.out.println(resultStr);
            String expected = "h|f,g|c,d|a,b,e";
            assertThat(resultStr, equalTo(expected));


        }
    }


    /*
     * 22.5:
     * Give an O (V + E)-time algorithm to compute the component graph of a directed
graph G = (V, E). Make sure that there is at most one edge between two vertices
in the component graph your algorithm produces.
     */
    public AjacentGraph componentGraph(AjacentGraph g) {
        int n = g.vertices.verticesArray.size();
        List<Set<String>> result = findScc(g);


        AjacentGraph ret = new AjacentGraph();
        for (int i = 0; i < result.size(); i++) {
            ret.addVertex(new Integer(i).toString());
        }
        ret.initGraph();

        //a map from vertex index to scc
        int[] scc = new int[n];
        int i = 0;
        for (Set<String> strs : result) {
            for (String name : strs) {
                scc[g.vertices.getVertexByName(name).index] = i;
            }
            i++;
        }

        //extra data structure outside graph to assistant graph building
        Set<String> sccedge = new HashSet<>();
        g.ajacentList.forEach(edges -> edges.forEach(edge -> {
            int from = edge.from;
            int to = edge.to;
            if (scc[from] == scc[to]) {
                return;
            }
            int sccfrom = scc[from];
            int sccto = scc[to];
            String key = String.format("%d_%d", sccfrom, sccto);
            if (sccedge.contains(key)) {
                //already one edge like this
                return;
            }
            ret.addEdge(sccfrom, sccto);
            sccedge.add(key);

        }));

        return ret;
    }


    public static class CollapseTestCase {
        @Test
        public void testSccCollapse() {
            AjacentGraph g = new AjacentGraph();
            g.addVertex("a");
            g.addVertex("b");
            g.addVertex("c");
            g.addVertex("d");
            g.addVertex("e");
            g.addVertex("f");
            g.addVertex("g");
            g.addVertex("h");

            g.initGraph();
            g.addEdge("a", "b", 1);
            g.addEdge("b", "c", 1);
            g.addEdge("b", "e", 1);
            g.addEdge("e", "a", 1);
            g.addEdge("e", "f", 1);
            g.addEdge("b", "f", 1);

            g.addEdge("f", "g", 1);
            g.addEdge("g", "f", 1);
            g.addEdge("c", "g", 1);
            g.addEdge("c", "d", 1);
            g.addEdge("d", "c", 1);
            g.addEdge("g", "h", 1);
            g.addEdge("d", "h", 1);
            g.addEdge("h", "h", 1);

            //an extra dege from first component to third
            g.addEdge("a", "f", 1);

            AjacentGraph scc = new Scc().componentGraph(g);
            //a,b,e|c,d|f,g|h
            assertThat(scc.vertices.verticesArray.size(), equalTo(4));
            assertThat(scc.getEdges(0), hasSize(2));
            assertThat(scc.getEdges(1), hasSize(2));
            assertThat(scc.getEdges(2), hasSize(1));
            assertThat(scc.getEdges(3), hasSize(0));


        }
    }

}
