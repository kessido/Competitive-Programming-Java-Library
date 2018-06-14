package library.MaxFlow;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class MaxFlowAlgorithmFlowNetworkOnlyBuilder implements MaxFlowAlgorithm {
    @Override
    public long maxFlow(int from, int to) {
        return maxFlow(getNode(from), getNode(to));
    }

    abstract long maxFlow(Node from, Node to);

    @Override
    public long getEndFlowBetween(int from, int to) {
        return getEndFlowBetween(getNode(from), getNode(to));
    }

    public abstract long getEndFlowBetween(Node from, Node to);


    private TreeMap<Integer, Node> hashMap = new TreeMap<>();


    private Node getNode(int index) {
        Node fromNode = hashMap.get(index);
        if (fromNode == null) {
            fromNode = new Node(index);
            hashMap.put(index, fromNode);
        }
        return fromNode;
    }

    @Override
    public void addEdge(int from, int to, long capacity) {
        Node fromNode = getNode(from);
        Node toNode = getNode(to);
        fromNode.addEdge(toNode, capacity);
    }

    public static class Node implements Comparable<Node> {
        int index;
        int lastVisit = 0;
        Node parent;
        int depth;
        ArrayList<Node> bfsTreeNeighbors = new ArrayList<>();

        Node(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(index, o.index);
        }

        TreeMap<Node, Long> e = new TreeMap<>();

        void addEdge(Node node, long capacity) {
            if (capacity != 0)
                e.put(node, capacity);
        }

        void addFlowToNeighbor(Node node, long flow) {
            long i = e.get(node) - flow;
            if (i == 0) {
                e.remove(node);
            } else {
                e.put(node, i);
            }
            Long ii = node.e.get(this);
            if (ii == null)
                i = 0;
            else
                i = ii;
            i += flow;
            node.e.put(this, i);
        }
    }
}
