package library;

public class MinRangeTree<VALUE extends Comparable<VALUE>> {
    long minKey, midKey, maxKey;
    VALUE minValue = null;
    MinRangeTree<VALUE> left = null, right = null;

    public MinRangeTree(long minKey, long maxKey) {
        this.minKey = minKey;
        this.maxKey = maxKey;
        midKey = (minKey + maxKey);
        if (midKey % 2 != 0) midKey--;
        midKey /= 2;
    }

    public void addValue(long key, VALUE value) {
        if (minValue == null) minValue = value;
        else if (minValue.compareTo(value) > 0) minValue = value;
        if (key <= midKey) {
            if (minKey != maxKey) {
                if (left == null) left = new MinRangeTree<>(minKey, midKey);
                left.addValue(key, value);
            }
        } else {
            if (minKey != maxKey) {
                if (right == null) right = new MinRangeTree<>(midKey + 1, maxKey);
                right.addValue(key, value);
            }
        }
    }

    public VALUE getMinValue(long startRange, long endRange) {
        startRange = Math.max(minKey, startRange);
        endRange = Math.min(maxKey, endRange);
        if (endRange < startRange) return null;
        if (startRange == minKey && endRange == maxKey) return minValue;
        VALUE res = null;
        if (left != null) res = left.getMinValue(startRange, endRange);
        if (right != null) {
            VALUE res1 = right.getMinValue(startRange, endRange);
            if (res1 != null) {
                if (res == null)
                    res = res1;
                else {
                    if (res1.compareTo(res) < 0) res = res1;
                }
            }
        }
        return res;
    }

    public VALUE getMinValue() {
        return minValue;
    }
}
