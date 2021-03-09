package LeetCode;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRoom {
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> i1[0] - i2[0]);
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((i1, i2) -> i1[1] - i2[1]);
        int count = 0;
        for (int i = 0; i < intervals.length; i++) {
            if (i == 0) {
                minHeap.offer(intervals[i]);
                count++;
            } else {
                int curStart = intervals[i][0];
                int peekEnd = minHeap.peek()[1];
                if (curStart >= peekEnd) {
                    minHeap.poll();
                }
                minHeap.offer(intervals[i]);
            }
        }
        return minHeap.size();
    }

    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> i1[0] - i2[0]);
        int prevEnd = -1;
        for (int[] interval : intervals) {
            if (interval[0] < prevEnd) {
                return false;
            }
            prevEnd = interval[1];
        }
        return true;
    }
}
