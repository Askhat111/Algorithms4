package tools;

import graph.util.DirectedGraph;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatasetIO {
    private static final Pattern N_PAT = Pattern.compile("\"n\"\\s*:\\s*(\\d+)");
    private static final Pattern EDGE_PAT = Pattern.compile("\\[\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*]");

    public static DirectedGraph readJson(File f) throws Exception {
        String s = Files.readString(f.toPath(), StandardCharsets.UTF_8);
        if (s.startsWith("\uFEFF")) s = s.substring(1);

        Matcher mn = N_PAT.matcher(s);
        int n = -1;
        if (mn.find()) {
            n = Integer.parseInt(mn.group(1));
        } else {
            int idx = s.indexOf("\"n\"");
            if (idx >= 0) {
                String sub = s.substring(idx);
                Matcher m2 = Pattern.compile("(\\d+)").matcher(sub);
                if (m2.find()) n = Integer.parseInt(m2.group(1));
            }
        }

        if (n <= 0) {
            System.out.println("=== JSON HEAD (no 'n' found) ===");
            System.out.println(s.substring(0, Math.min(200, s.length())));
            System.out.println("================================");
            throw new IllegalArgumentException("Field \"n\" not found");
        }

        DirectedGraph g = new DirectedGraph(n);
        Matcher me = EDGE_PAT.matcher(s);
        boolean any = false;
        while (me.find()) {
            int u = Integer.parseInt(me.group(1));
            int v = Integer.parseInt(me.group(2));
            int w = Integer.parseInt(me.group(3));
            g.addEdge(u, v, w);
            any = true;
        }
        if (!any) throw new IllegalArgumentException("No edges parsed");
        return g;
    }
}