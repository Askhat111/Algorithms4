package graph.topo;

import graph.util.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {
    @Test void simpleOrder(){
        DirectedGraph g = new DirectedGraph(6);
        g.addEdge(5,2); g.addEdge(5,0); g.addEdge(4,0); g.addEdge(4,1); g.addEdge(2,3); g.addEdge(3,1);
        Metrics m = new OpMetrics();
        var order = TopologicalSort.orderKahn(g, m);
        assertEquals(6, order.size());
        int[] pos = new int[6];
        for(int i=0;i<order.size();i++) pos[order.get(i)] = i;
        for(int u=0;u<6;u++) for(var e: g.adj(u)) assertTrue(pos[u] < pos[e.to]);
        assertTrue(m.pushes()>=2);
    }

    @Test void cycleDetection(){
        DirectedGraph g = new DirectedGraph(3);
        g.addEdge(0,1); g.addEdge(1,2); g.addEdge(2,0);
        Metrics m = new OpMetrics();
        assertThrows(IllegalStateException.class, () -> TopologicalSort.orderKahn(g, m));
    }
}
