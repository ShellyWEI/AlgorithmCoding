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
    Set<Integer> set = new TreeSet<>();
    public void addIntervals(int[] interval) {
        for (int i = interval[0]; i < interval[1]; i++) {
            set.add(i);
        }
    }
    public int getTotalCoveredLength() {
        return set.size();
    }
    // TODO
}
