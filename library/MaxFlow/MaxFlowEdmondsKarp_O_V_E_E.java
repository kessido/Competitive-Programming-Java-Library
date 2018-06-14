package library.MaxFlow;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MaxFlowEdmondsKarp_O_V_E_E extends MaxFlowAlgorithmFlowNetworkOnlyBuilder {

    private long bfs(Node from, final Node to) {
        Queue<Node> q = new LinkedList<>();
        from.lastVisit = iter;
        from.parent = null;
        q.add(from);
        to.parent = null;
        while (!q.isEmpty() && to.parent == null) {
            Node node = q.poll();
            for (Map.Entry<Node, Long> neigTuple : node.e.entrySet()) {
                Node neig = neigTuple.getKey();
                if (neig.lastVisit != iter) {
                    neig.lastVisit = iter;
                    neig.parent = node;
                    q.add(neig);
                }
            }
        }
        if (to.parent == null) return 0;
        long minCapacityOnThePath = Long.MAX_VALUE;
        Node node = to;
        while (node.parent != null) {
            Node parent = node.parent;
            minCapacityOnThePath = Math.min(minCapacityOnThePath, parent.e.get(node));
            node = parent;
        }
        node = to;
        while (node.parent != null) {
            Node parent = node.parent;
            parent.addFlowToNeighbor(node, minCapacityOnThePath);
            node = parent;
        }
        return minCapacityOnThePath;
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
