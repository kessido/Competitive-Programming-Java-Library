package library.MaxFlow;

public interface MaxFlowAlgorithm {
    long maxFlow(int from, int to);

    void addEdge(int from, int to, long capacity);

    long getEndFlowBetween(int from, int to);
}
