package scc;

import graph.util.Metrics;
import java.util.*;

public class TarjanSCC {
    private final int V;
    private final List<List<Integer>> adj;
    private int time = 0;
    private final int[] disc;
    private final int[] low;
    private final boolean[] stackMember;
    private final Stack<Integer> stack;
    private final Metrics metrics;
    private final List<List<Integer>> sccComponents;

    public TarjanSCC(int V, List<List<Integer>> adj, Metrics metrics) {
        this.V = V;
        this.adj = adj;
        this.metrics = metrics;
        disc = new int[V];
        low = new int[V];
        stackMember = new boolean[V];
        stack = new Stack<>();
        sccComponents = new ArrayList<>();
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
        Arrays.fill(stackMember, false);
    }

    public List<List<Integer>> findSCCs() {
        metrics.startTimer("TarjanSCC");
        for (int i = 0; i < V; i++) {
            if (disc[i] == -1) dfs(i);
        }
        metrics.endTimer("TarjanSCC");
        return sccComponents;
    }

    private void dfs(int u) {
        metrics.incrementCounter("dfsVisits");
        disc[u] = low[u] = time++;
        stack.push(u);
        stackMember[u] = true;
        for (int v : adj.get(u)) {
            metrics.incrementCounter("dfsEdges");
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (stackMember[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
        if (low[u] == disc[u]) {
            List<Integer> currSCC = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                stackMember[w] = false;
                currSCC.add(w);
            } while (w != u);
            sccComponents.add(currSCC);
        }
    }
}




