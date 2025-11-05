import cond.CondensationGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

public class CondensationGraphTest {

    @Test
    public void testSimpleCondensation() {

        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);
        graph.get(2).add(3);

        List<List<Integer>> sccComponents = new ArrayList<>();
        sccComponents.add(Arrays.asList(0, 1, 2));
        sccComponents.add(Collections.singletonList(3));

        CondensationGraph condensationGraph = new CondensationGraph(graph, sccComponents);
        List<List<Integer>> condGraph = condensationGraph.getSccGraph();

        Assertions.assertEquals(2, condGraph.size());
        Assertions.assertEquals(1, condGraph.get(0).size());
        Assertions.assertEquals(0, condGraph.get(1).size());
        Assertions.assertEquals(1, (int) condGraph.get(0).get(0));
    }
}


