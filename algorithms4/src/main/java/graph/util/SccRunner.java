package graph.util;

import com.google.gson.*;
import scc.TarjanSCC;
import java.io.*;
import java.util.*;

public class SccRunner {
    public static void main(String[] args) throws Exception {
        String[] files = {
                "large_cycles_chains.json",
                "medium_dense_dag.json",
                "medium_multi_scc.json",
                "small_cycle_chain.json",
                "small_dag.json"
        };
        String dataDir = "data/";
        String resultsDir = "results/";

        Gson gson = new Gson();

        List<String[]> csvRows = new ArrayList<>();
        csvRows.add(new String[]{"Dataset", "SCCs", "DFSVisits", "TimingNS"});

        for (String file : files) {
            //input
            GraphJson graphData = gson.fromJson(new FileReader(dataDir + file), GraphJson.class);
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < graphData.n; i++) adj.add(new ArrayList<>());
            for (Edge e : graphData.edges) adj.get(e.u).add(e.v);

            //Tarjans SCC
            Metrics metrics = new MetricsImpl();
            TarjanSCC tarjan = new TarjanSCC(graphData.n, adj, metrics);
            List<List<Integer>> sccs = tarjan.findSCCs();

            //Output JSON results
            JsonArray sccArr = new JsonArray();
            for (List<Integer> comp : sccs) {
                JsonObject obj = new JsonObject();
                JsonArray verts = new JsonArray();
                for (int v : comp) verts.add(v);
                obj.add("vertices", verts);
                obj.addProperty("size", comp.size());
                sccArr.add(obj);
            }
            JsonObject out = new JsonObject();
            out.add("sccs", sccArr);
            out.addProperty("timing_ns", metrics.getElapsedTime("TarjanSCC"));
            out.addProperty("dfs_visits", metrics.getCounter("dfsVisits"));
            out.addProperty("dfs_edges", metrics.getCounter("dfsEdges"));

            String base = file.substring(0, file.lastIndexOf('.'));
            try (FileWriter writer = new FileWriter(resultsDir + base + "_scc_results.json")) {
                gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(out, writer);
            }

            //CSV
            csvRows.add(new String[]{
                    base,
                    Integer.toString(sccs.size()),
                    Integer.toString(metrics.getCounter("dfsVisits")),
                    Long.toString(metrics.getElapsedTime("TarjanSCC"))
            });

            System.out.println("Processed " + file + " -> " + base + "_scc_results.json");
        }

        //CSV
        try (PrintWriter writer = new PrintWriter(resultsDir + "metrics_summary.csv")) {
            for (String[] row : csvRows) {
                writer.println(String.join(",", row));
            }
        }
        System.out.println("Wrote " + resultsDir + "metrics_summary.csv for all datasets");
    }
}



