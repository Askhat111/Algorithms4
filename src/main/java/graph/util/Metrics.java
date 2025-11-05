package graph.util;

public interface Metrics {
    void reset();
    void incPush();
    void incPop();
    void incRelax();
    long pushes();
    long pops();
    long relaxations();
    void startTimer();
    void stopTimer();
    long elapsedNanos();
}