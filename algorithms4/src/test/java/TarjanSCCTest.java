import graph.util.Metrics;
import graph.util.MetricsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scc.TarjanSCC;

import java.util.*;
public class TarjanSCCTest {

    @Test
    public void testSingleCycle() {
        int n = 3;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);

        Metrics metrics = new MetricsImpl();
        TarjanSCC tarjan = new TarjanSCC(n, graph, metrics);
        List<List<Integer>> sccs = tarjan.findSCCs();

        Assertions.assertEquals(1, sccs.size(), "One strongly connected component");
        Assertions.assertEquals(3, sccs.get(0).size(), "Component size is 3");

        Assertions.assertTrue(metrics.getCounter("dfsVisits") >= n);
        Assertions.assertTrue(metrics.getCounter("dfsEdges") >= n);
    }

    @Test
    public void testDAG() {
        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(3);

        Metrics metrics = new MetricsImpl();
        TarjanSCC tarjan = new TarjanSCC(n, graph, metrics);
        List<List<Integer>> sccs = tarjan.findSCCs();

        Assertions.assertEquals(n, sccs.size(), "Each node is its own SCC");
    }
}
