package graph.topo;

import graph.util.DirectedGraph;
import graph.util.Metrics;
import java.util.*;
public class TopologicalSort {
    public static List<Integer> orderKahn(DirectedGraph g, Metrics m){
        m.reset(); m.startTimer();
        int n = g.n();
        int[] indeg = g.indegrees();
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<n;i++) if(indeg[i]==0){ q.add(i); m.incPush(); }
        List<Integer> order = new ArrayList<>(n);
        while(!q.isEmpty()){
            int u = q.remove(); m.incPop();
            order.add(u);
            for(var e : g.adj(u)){
                if(--indeg[e.to]==0){ q.add(e.to); m.incPush(); }
            }
        }
        m.stopTimer();
        if(order.size()!=n) throw new IllegalStateException("Graph has a cycle; not a DAG");
        return order;
    }

    public static List<Integer> orderDfs(DirectedGraph g, Metrics m){
        m.reset(); m.startTimer();
        int n = g.n();
        boolean[] vis = new boolean[n];
        List<Integer> out = new ArrayList<>(n);
        for(int i=0;i<n;i++) if(!vis[i]) dfs(i,g,vis,out,m);
        Collections.reverse(out);
        m.stopTimer();
        return out;
    }
    private static void dfs(int u, DirectedGraph g, boolean[] vis, List<Integer> out, Metrics m){
        vis[u]=true; m.incPush();
        for(var e: g.adj(u)) if(!vis[e.to]) dfs(e.to,g,vis,out,m);
        out.add(u); m.incPop();
    }
}