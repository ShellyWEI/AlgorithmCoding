package LinkedIn.CA1;

import java.util.*;

public class IntervalCoverage {
    // leetcode: merge intervals
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, Comparator.comparing((int[] i)-> i[0]));
        int left = intervals[0][0];
        int curRight = intervals[0][1];
        List<int[]> res = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (intervals[i][0] > curRight) {
                res.add(new int[]{left, curRight});
                left = intervals[i][0];
                curRight = intervals[i][1];
            } else if (intervals[i][1] > curRight){
                curRight = intervals[i][1];
            }
        }
        res.add(new int[]{left, curRight});
        int[][] resArray = new int[res.size()][2];
        int i = 0;
        for (int[] r : res) {
            resArray[i++] = r;
        }
        return resArray;
    }
    // merge-in-add method: favor add less, do more in add
    // O(n) to add, O(1) to getLength
    Set<Integer> set = new TreeSet<>();
    public void addIntervals(int[] interval) {
        for (int i = interval[0]; i < interval[1]; i++) {
            set.add(i);
        }
    }
    public int getTotalCoveredLength() {
        return set.size();
    }

    // O(logn) to add, O(n) to getLength
    TreeMap<Integer, Integer> map = new TreeMap<>();
    public void addIntervals(int from, int to) {
        Integer fromInterval = map.floorKey(from);
        Integer toInterval = map.floorKey(to);
        if (fromInterval != null && map.get(fromInterval) > from) {
            from = fromInterval;
        }
        if (toInterval != null && map.get(toInterval) > to) {
            to = map.get(toInterval);
        }
        map.put(from, to);
        NavigableMap<Integer, Integer> subMap = map.subMap(from, false, to, false);
        subMap.clear();
    }

    public int getTotalCoveredLength2() {
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            res += entry.getValue() - entry.getKey();
        }
        return res;
    }

    public void removeRange(int from, int to) {
        Integer fromInterval = map.floorKey(from);
        Integer toInterval = map.floorKey(to);
        if (fromInterval != null && map.get(fromInterval) > from) {
            map.put(fromInterval, from);
        }
        if (toInterval != null && map.get(toInterval) > to) {
            map.put(map.get(toInterval), to);
        }
        map.subMap(from, true, to, false).clear();
    }

    public static void main(String[] args) {
        IntervalCoverage intervalCoverage = new IntervalCoverage();
        intervalCoverage.addIntervals(1, 1);
        intervalCoverage.addIntervals(8, 9);
        intervalCoverage.addIntervals(6, 7);
        intervalCoverage.addIntervals(7, 7);
        System.out.println(intervalCoverage.getTotalCoveredLength2());
    }
}
