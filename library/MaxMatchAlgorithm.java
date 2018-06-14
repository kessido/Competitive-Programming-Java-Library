package library;

import java.util.Map;

public interface MaxMatchAlgorithm {
    void add(int from, int to);

    long getMaxMatch();

    Map<Integer, Integer> getMaxMatchDesc();
}
