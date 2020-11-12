package Amazon.OA;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KClosetPoints {
    public int[][] kClosest(int[][] points, int K) {
        int length = points.length;
        int[][] res = new int[K][2];
        if (points == null || points.length == 0 || points[0].length != 2) {
            return res;
        }
        if (length <= K) {
            return points;
        }
        PriorityQueue<int[]> dis = new PriorityQueue<>(K, new Comparator<int[]>(){
            @Override
            public int compare(int[] i1, int[] i2) {
                int dis1 = i1[0] * i1[0] + i1[1] * i1[1];
                int dis2 = i2[0] * i2[0] + i2[1] * i2[1];
                if (dis1 == dis2) {
                    return 0;
                }
                return dis1 < dis2 ? -1 : 1;
            }
        });
        for (int[] point : points) {
            dis.offer(point);
        }
        for (int i = 0;i < K; i++) {
            res[i] = dis.poll();
        }
        return res;
    }
}
