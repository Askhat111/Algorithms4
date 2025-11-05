package tools;

import graph.util.DirectedGraph;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class GraphvizExporter {
    public static void writeDot(DirectedGraph g, File out, String title) throws Exception {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8);
             PrintWriter pw = new PrintWriter(w)) {
            pw.println("digraph G {");
            pw.println("  rankdir=LR; node [shape=ellipse];");
            if (title != null && !title.isEmpty()) {
                String safe = title.replace("\"", "\\\"");
                pw.println("  labelloc=\"t\"; label=\"" + safe + "\";");
            }
            for (int u = 0; u < g.n(); u++) {
                for (var e : g.adj(u)) {
                    pw.printf("  %d -> %d [label=\"%d\"];%n", u, e.to, e.w);
                }
            }
            pw.println("}");
        }
    }
}