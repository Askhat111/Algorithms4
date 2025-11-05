package app;

import graph.util.DirectedGraph;
import tools.DatasetIO;
import tools.GraphvizExporter;
import java.io.File;

public class GenerateGraphs {
    public static void main(String[] args) throws Exception {
        new File("graphs").mkdirs();

        generate("dag_small_01");
        generate("dag_medium_01");
        generate("dag_dense_01");
        generate("dag_wide_01");

        System.out.println("All .gv files are generated in /graphs");
    }

    private static void generate(String name) throws Exception {
        File input = new File("data/" + name + ".json");
        if (!input.exists()) {
            System.out.println("Missing: " + input.getAbsolutePath());
            return;
        }
        DirectedGraph g = DatasetIO.readJson(input);
        File out = new File("graphs/" + name + ".gv");
        GraphvizExporter.writeDot(g, out, name);
        System.out.println("Generated: " + out.getAbsolutePath());
    }
}