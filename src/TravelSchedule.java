import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TravelSchedule {
    int K;
    int N;

    public int maxVacationDays(int[][] flights, int[][] days) {
        N = flights.length;
        K = days[0].length;
        // city: the city that from city 0 flying to
        //int[] count = new int[]{0};
        PriorityQueue<Integer> maxDays = new PriorityQueue<>(1, (i1, i2) -> (i2 - i1));
        int week = 0;
        for (int city = 0; city < N; city++) {
            if (flights[0][city] == 1 || city == 0) {
                DFSCountDays(flights, city, days, week, maxDays, 0);
                //maxDays = Math.max(maxDays, count[0]);
            }
        }

        return maxDays.peek();
    }
    private void DFSCountDays(int[][] flights, int toCity, int[][] days, int week, PriorityQueue<Integer> maxDays, int dayCount) {
        if (week == K) {
            maxDays.add(dayCount);
            return;
        }
        if (days[toCity][week] == 0) {
            DFSCountDays(flights, toCity, days, week + 1, maxDays, dayCount);
        } else {
            dayCount += days[toCity][week];
            for (int i = 0; i < N; i++) {
                if (flights[toCity][i] == 1 || toCity == i) {
                    DFSCountDays(flights, i, days, week + 1, maxDays, dayCount);
                }
            }
            dayCount += days[toCity][week];
        }
    }
    public static void main(String[] args) {
        TravelSchedule test = new TravelSchedule();
        int[][] flights = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
        int[][] days = new int[][]{{1,1,1},{7,7,7},{7,7,7}};
        test.maxVacationDays(flights, days);
    }
}
