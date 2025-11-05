package graph.dagsp;

import graph.util.DirectedGraph;
import graph.util.Metrics;
import graph.topo.TopologicalSort;
import java.util.*;

public class DAGPaths {
    public static class Result {
        public final int[] dist;
        public final int[] prev;
        public final long timeNs;
        public final long relaxations;
        Result(int[] d, int[] p, long t, long r){ this.dist=d; this.prev=p; this.timeNs=t; this.relaxations=r; }
        public List<Integer> buildPath(int t){
            List<Integer> path = new ArrayList<>();
            if(dist[t]==INF) return path;
            for(int v=t; v!=-1; v=prev[v]) path.add(v);
            Collections.reverse(path);
            return path;
        }
    }

    public static final int INF = 1_000_000_000;

    public static Result shortest(DirectedGraph g, int s, Metrics m){
        var order = TopologicalSort.orderKahn(g, m);
        int n = g.n();
        int[] dist = new int[n]; Arrays.fill(dist, INF); dist[s]=0;
        int[] prev = new int[n]; Arrays.fill(prev, -1);
        long before = System.nanoTime();
        for(int u : order){
            if(dist[u]==INF) continue;
            for(var e : g.adj(u)){
                int nd = dist[u] + e.w;
                if(nd < dist[e.to]){ dist[e.to]=nd; prev[e.to]=u; m.incRelax(); }
            }
        }
        long after = System.nanoTime();
        return new Result(dist, prev, after-before, m.relaxations());
    }

    public static Result longest(DirectedGraph g, int s, Metrics m){
        var order = TopologicalSort.orderKahn(g, m);
        int n = g.n();
        int[] dist = new int[n]; Arrays.fill(dist, -INF); dist[s]=0;
        int[] prev = new int[n]; Arrays.fill(prev, -1);
        long before = System.nanoTime();
        for(int u : order){
            if(dist[u]==-INF) continue;
            for(var e : g.adj(u)){
                int nd = dist[u] + e.w;
                if(nd > dist[e.to]){ dist[e.to]=nd; prev[e.to]=u; m.incRelax(); }
            }
        }
        long after = System.nanoTime();
        return new Result(dist, prev, after-before, m.relaxations());
    }

    public static Result criticalPath(DirectedGraph g, Metrics m){
        var order = TopologicalSort.orderKahn(g, m);
        int n = g.n();
        int[] indeg = g.indegrees();
        int[] dist = new int[n]; Arrays.fill(dist, -INF);
        int[] prev = new int[n]; Arrays.fill(prev, -1);

        for(int i=0;i<n;i++) if(indeg[i]==0) dist[i]=0;
        long before = System.nanoTime();
        for(int u: order){
            if(dist[u]==-INF) continue;
            for(var e: g.adj(u)){
                int nd = dist[u] + e.w;
                if(nd > dist[e.to]){ dist[e.to]=nd; prev[e.to]=u; m.incRelax(); }
            }
        }
        long after = System.nanoTime();
        int best = 0; for(int i=1;i<n;i++) if(dist[i]>dist[best]) best=i;
        return new Result(dist, prev, after-before, m.relaxations());
    }
}