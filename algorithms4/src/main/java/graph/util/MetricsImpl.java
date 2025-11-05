package graph.util;

import java.util.HashMap;
import java.util.Map;

public class MetricsImpl implements Metrics {
    private final Map<String, Integer> counters = new HashMap<>();
    private final Map<String, Long> startTimes = new HashMap<>();
    private final Map<String, Long> elapsedTimes = new HashMap<>();

    @Override
    public void incrementCounter(String name) {
        counters.put(name, counters.getOrDefault(name, 0) + 1);
    }

    @Override
    public int getCounter(String name) {
        return counters.getOrDefault(name, 0);
    }

    @Override
    public void startTimer(String phase) {
        startTimes.put(phase, System.nanoTime());
    }

    @Override
    public void endTimer(String phase) {
        Long start = startTimes.get(phase);
        if (start != null) {
            long elapsed = System.nanoTime() - start;
            elapsedTimes.put(phase, elapsedTimes.getOrDefault(phase, 0L) + elapsed);
        }
    }

    @Override
    public long getElapsedTime(String phase) {
        return elapsedTimes.getOrDefault(phase, 0L);
    }

    @Override
    public void reset() {
        counters.clear();
        startTimes.clear();
        elapsedTimes.clear();
    }
}

