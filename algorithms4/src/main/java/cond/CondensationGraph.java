package cond;

import java.util.*;

public class CondensationGraph {
    private final int sccCount;
    private final List<List<Integer>> sccGraph;

    public CondensationGraph(List<List<Integer>> originalGraph, List<List<Integer>> sccComponents) {
        sccCount = sccComponents.size();
        sccGraph = new ArrayList<>(sccCount);
        for (int i = 0; i < sccCount; i++) sccGraph.add(new ArrayList<>());
        Map<Integer, Integer> vertexToScc = new HashMap<>();
        for (int i = 0; i < sccCount; i++)
            for (int v : sccComponents.get(i))
                vertexToScc.put(v, i);
        for (int u = 0; u < originalGraph.size(); u++)
            for (int v : originalGraph.get(u)) {
                int su = vertexToScc.get(u), sv = vertexToScc.get(v);
                if (su != sv && !sccGraph.get(su).contains(sv)) sccGraph.get(su).add(sv);
            }
    }
    public List<List<Integer>> getSccGraph() { return sccGraph; }
}




