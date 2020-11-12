package Uber;

import java.util.*;

public class MeetingRoom {
    public int minMeetingRooms(int[][] intervals) {
        int length = intervals.length;
        int[] start = new int[length];
        int[] end = new int[length];
        for (int i = 0; i < length; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start);
        Arrays.sort(end);
        int endMeetingIndex = 0;
        int rooms = 0;
        for (int startIndex = 0; startIndex < length; startIndex++) {
            // starting new meeting has to use another room
            if (startIndex == 0 || start[startIndex] < end[endMeetingIndex]) {
                rooms++;
            } else {
                // previous meeting ends,
                // we could reuse previous room
                endMeetingIndex++;
            }
        }
        return rooms;
    }
}
