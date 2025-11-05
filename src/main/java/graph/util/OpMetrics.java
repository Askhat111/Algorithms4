package graph.util;

public class OpMetrics implements Metrics {
    private long push, pop, relax, t0, elapsed;

    @Override public void reset() { push = pop = relax = elapsed = 0; }
    @Override public void incPush() { push++; }
    @Override public void incPop()  { pop++; }
    @Override public void incRelax(){ relax++; }
    @Override public long pushes() { return push; }
    @Override public long pops() { return pop; }
    @Override public long relaxations() { return relax; }
    @Override public void startTimer() { t0 = System.nanoTime(); }
    @Override public void stopTimer()  { elapsed = System.nanoTime() - t0; }
    @Override public long elapsedNanos() { return elapsed; }
}