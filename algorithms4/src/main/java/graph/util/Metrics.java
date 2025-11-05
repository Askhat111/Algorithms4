package graph.util;

public interface Metrics {
    void incrementCounter(String name);
    int getCounter(String name);

    void startTimer(String phase);
    void endTimer(String phase);
    long getElapsedTime(String phase);

    void reset();
}
