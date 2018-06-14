package library.MaxFlow;

import java.util.Map;

public class MaxFlowFordFulkerson_O_E_times_MAXFLOWVALUE extends MaxFlowAlgorithmFlowNetworkOnlyBuilder {

    private long dfs(Node node, final long minCapacity, final Node to) {
        if (node.lastVisit == iter) return 0;
        if (node == to) return minCapacity;
        node.lastVisit = iter;
        for (Map.Entry<Node, Long> node1 : node.e.entrySet()) {
            long res = dfs(node1.getKey(), Math.min(node1.getValue(), minCapacity), to);
            if (res != 0) {
                node.addFlowToNeighbor(node1.getKey(), res);
                return res;
            }
        }
        return 0;
    }

    private int iter = 1;

    @Override
    public long maxFlow(Node from, Node to) {
        long flow = 0;
        long flowAdd;
        do {
            ++iter;
            flowAdd = dfs(from, Long.MAX_VALUE, to);
            flow += flowAdd;
        }
        while (flowAdd != 0);
        return flow;
    }

    public long getEndFlowBetween(Node from, Node to) {
        Long flow = to.e.get(from);
        if (flow == null) return 0;
        return flow;
    }
}
