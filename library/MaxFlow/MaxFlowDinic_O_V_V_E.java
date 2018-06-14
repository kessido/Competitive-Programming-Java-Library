package library.MaxFlow;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MaxFlowDinic_O_V_V_E extends MaxFlowAlgorithmFlowNetworkOnlyBuilder {
    private long dfs(Node node, final long minCapacity, final Node to) {
        if (node.lastVisit == iter) return 0;
        if (node == to) return minCapacity;
        node.lastVisit = iter;
        for (Node node1 : node.bfsTreeNeighbors) {
            Long capacity = node.e.get(node1);
            if (capacity == null) continue;
            long res = dfs(node1, Math.min(capacity, minCapacity), to);
            if (res != 0) {
                node.addFlowToNeighbor(node1, res);
                return res;
            }
        }
        return 0;
    }


    private long bfs(Node from, final Node to) {
        Queue<Node> q = new LinkedList<>();
        from.lastVisit = iter;
        from.depth = 0;
        to.depth = -1;
        q.add(from);
        while (!q.isEmpty()) {
            Node node = q.poll();
            for (Map.Entry<Node, Long> neigTuple : node.e.entrySet()) {
                Node neig = neigTuple.getKey();
                if (neig.lastVisit != iter) {
                    neig.lastVisit = iter;
                    neig.depth = node.depth + 1;
                    q.add(neig);
                }
            }
        }
        iter++;
        q.add(from);
        from.lastVisit = iter;
        while (!q.isEmpty()) {
            Node node = q.poll();
            node.bfsTreeNeighbors.clear();
            for (Map.Entry<Node, Long> neigTuple : node.e.entrySet()) {
                Node neig = neigTuple.getKey();
                if (neig.depth == node.depth + 1) {
                    node.bfsTreeNeighbors.add(neig);
                    if (neig.lastVisit != iter) {
                        q.add(neig);
                        neig.lastVisit = iter;
                    }
                }
            }
        }

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

    private int iter = 1;

    @Override
    public long maxFlow(Node from, Node to) {
        long flow = 0;
        long flowAdd;
        do {
            ++iter;
            flowAdd = bfs(from, to);
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
