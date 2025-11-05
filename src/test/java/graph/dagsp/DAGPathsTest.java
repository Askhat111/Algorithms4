package graph.dagsp;

import graph.util.*;
import graph.topo.TopologicalSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DAGPathsTest {
    @Test void shortestAndLongest() {
        DirectedGraph g = new DirectedGraph(5);
        g.addEdge(0,1,2); g.addEdge(0,2,4); g.addEdge(1,3,7); g.addEdge(2,3,1); g.addEdge(3,4,3);
        Metrics m = new OpMetrics();
        var sp = DAGPaths.shortest(g, 0, m);
        assertEquals(0, sp.dist[0]);
        assertEquals(8, sp.dist[4]);
        assertEquals(4, sp.buildPath(4).size());
        var lp = DAGPaths.longest(g, 0, m);
        assertEquals(12, lp.dist[4]);
    }

    @Test void criticalPathMultipleSources() {
        DirectedGraph g = new DirectedGraph(6);
        g.addEdge(0,1,3); g.addEdge(1,4,4);
        g.addEdge(5,2,5); g.addEdge(2,3,2); g.addEdge(3,4,5);
        Metrics m = new OpMetrics();
        var res = DAGPaths.criticalPath(g, m);
        assertEquals(12, res.dist[4]);
    }
}