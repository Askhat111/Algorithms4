package graph.util;

import java.util.*;

public class DirectedGraph {
    public static class Edge { public final int to; public final int w; public Edge(int to, int w){this.to=to;this.w=w;} }

    private final int n;
    private final List<List<Edge>> adj;

    public DirectedGraph(int n){
        this.n = n;
        this.adj = new ArrayList<>(n);
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }
    public int n(){ return n; }
    public void addEdge(int u, int v){ addEdge(u,v,1); }
    public void addEdge(int u, int v, int w){ adj.get(u).add(new Edge(v,w)); }
    public List<Edge> adj(int u){ return adj.get(u); }

    public int[] indegrees(){
        int[] indeg = new int[n];
        for(int u=0;u<n;u++) for(Edge e:adj.get(u)) indeg[e.to]++;
        return indeg;
    }
}