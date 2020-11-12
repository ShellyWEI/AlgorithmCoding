package Oracle;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RangeModule {
    TreeMap<Integer, Integer> map; // start index -> end index, each entry with no overlap with each other
    public RangeModule() {
        this.map = new TreeMap<>();
    }

    public void addRange(int left, int right) {
        if (left >= right) {
            return;
        }
        Map.Entry<Integer, Integer> prevRange = map.floorEntry(left);
        Map.Entry<Integer, Integer> nextRange = map.floorEntry(right);
        // no overlap with previous range and next range
        if (prevRange == null && nextRange == null) {
            map.put(left, right);
        } else if (prevRange == null || prevRange.getValue() < left) {
            // no overlap with previous range
            map.put(left, Math.max(nextRange.getValue(), right));
        } else {
            // overlap with previous range
            map.put(prevRange.getKey(), Math.max(nextRange.getValue(), right));
        }
        // remove intermediate range
        Map<Integer, Integer> intermediateIntervals = map.subMap(left, false, right, true);
        map.keySet().removeAll(new HashSet<>(intermediateIntervals.keySet()));
    }

    public boolean queryRange(int left, int right) {
        Map.Entry<Integer, Integer> prevRange = map.floorEntry(left);
        if (prevRange != null && right <= prevRange.getValue()) {
            return true;
        } else {
            return false;
        }
    }

    public void removeRange(int left, int right) {
        if (left >= right) {
            return;
        }
        Map.Entry<Integer, Integer> prevRange = map.floorEntry(left);
        Map.Entry<Integer, Integer> nextRange = map.floorEntry(right);
        // no overlap with previous range and next range
        if (prevRange == null && nextRange == null) {
            return;
        }
        if (prevRange != null && prevRange.getValue() > left) {
            // no overlap with previous range
            map.put(prevRange.getKey(), left);
        }
        if (nextRange != null && nextRange.getKey() < right){
            // overlap with previous range
            map.put(right, nextRange.getValue());
        }
        // remove intermediate range
        Map<Integer, Integer> intermediateIntervals = map.subMap(left, true, right, false);
        map.keySet().removeAll(intermediateIntervals.keySet());
    }

    public static void main(String[] args) {
        RangeModule r = new RangeModule();
        r.addRange(6, 8);
        r.removeRange(7, 8);
        r.removeRange(8, 9);
        r.addRange(8,9);
        r.removeRange(1, 3);
        r.addRange(1, 8);
        r.queryRange(2, 4);
        r.queryRange(2, 9);
        r.queryRange(4, 6);
    }
}

