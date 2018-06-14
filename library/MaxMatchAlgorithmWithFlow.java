package library;

import library.MaxFlow.MaxFlowAlgorithm;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class MaxMatchAlgorithmWithFlow implements MaxMatchAlgorithm {
    MaxFlowAlgorithm maxFlowAlgorithm;

    public MaxMatchAlgorithmWithFlow(MaxFlowAlgorithm maxFlowAlgorithm) {
        this.maxFlowAlgorithm = maxFlowAlgorithm;
    }

    private TreeSet<Integer> alreadyAppearedInFirstGroup = new TreeSet<>();
    private TreeSet<Integer> alreadyAppearedInSecondGroup = new TreeSet<>();

    @Override
    public void add(int from, int to) {
        maxFlowAlgorithm.addEdge(from, to, 1);
        if (!alreadyAppearedInFirstGroup.contains(from)) {
            alreadyAppearedInFirstGroup.add(from);
            maxFlowAlgorithm.addEdge(Integer.MIN_VALUE, from, 1);
        }
        if (!alreadyAppearedInSecondGroup.contains(to)) {
            alreadyAppearedInSecondGroup.add(to);
            maxFlowAlgorithm.addEdge(to, Integer.MAX_VALUE, 1);
        }
    }

    @Override
    public long getMaxMatch() {
        return maxFlowAlgorithm.maxFlow(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Map<Integer, Integer> getMaxMatchDesc() {
        TreeMap<Integer, Integer> ret = new TreeMap<>();
        for (int i : alreadyAppearedInSecondGroup) {
            for (int j : alreadyAppearedInFirstGroup) {
                if (maxFlowAlgorithm.getEndFlowBetween(i, j) != 0) {
                    ret.put(j, i);
                    break;
                }
            }
        }
        return ret;
    }
}
