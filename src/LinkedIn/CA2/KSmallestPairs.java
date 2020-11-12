package LinkedIn.CA2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
/*
* Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
* Output: [[1,2],[1,4],[1,6]]
* Explanation: The first 3 pairs are returned from the sequence
* */
public class KSmallestPairs {
    // basic solution !!
    class Pair {
        int i;
        int j;
        int sum;
        Pair(int i, int j, int sum) {
            this.i = i;
            this.j = j;
            this.sum = sum;
        }
    }
    public Integer ksmallestSum(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Pair> minHeap = new PriorityQueue<>(k, (o1, o2) -> {
            if (o1.sum == o2.sum) {
                return 0;
            }
            return o2.sum > o1.sum ? -1 : 1;
        });
        minHeap.offer(new Pair(0, 0, nums1[0] * nums2[0]));
        boolean[][] visited = new boolean[nums1.length][nums2.length];
        visited[0][0] = true;
        while (k-- > 1) {
            Pair cur = minHeap.poll();
            System.out.printf("%d %d %d \n", nums1[cur.i], nums2[cur.j], cur.sum);
            if (cur.i + 1 < nums1.length && !visited[cur.i + 1][cur.j]) {
                visited[cur.i + 1][cur.j] = true;
                minHeap.offer(new Pair(cur.i + 1, cur.j, nums1[cur.i + 1] * nums2[cur.j]));
            }
            if (cur.j + 1 < nums2.length && !visited[cur.i][cur.j + 1]) {
                visited[cur.i][cur.j + 1] = true;
                minHeap.offer(new Pair(cur.i, cur.j + 1, nums1[cur.i] * nums2[cur.j + 1]));
            }
        }
        return minHeap.peek().sum;
    }
    // quicker solution
    public List<int[]> kSmallestPair(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(k, (o1, o2) -> nums1[o1[0]] * nums2[o1[1]] - nums1[o2[0]] * nums2[o2[1]]);
        for (int i = 0; i < nums1.length; i++) {
            if (minHeap.size() <= k) {
                minHeap.offer(new int[]{i, 0});
            } else {
                break;
            }
        }
        //int index = 0;
        List<int[]> res = new ArrayList<>();
        while (k-- > 0) {
            int[] curMin = minHeap.poll();
            res.add(new int[]{nums1[curMin[0]], nums2[curMin[1]]});
            System.out.printf("%d %d %d\n", nums1[curMin[0]], nums2[curMin[1]], nums1[curMin[0]] * nums2[curMin[1]]);
            if (curMin[1] < nums2.length - 1) {
                minHeap.offer(new int[]{curMin[0], curMin[1] + 1});
            }
        }

        return res;
    }

    public static void main(String[] args) {
        KSmallestPairs test = new KSmallestPairs();
        int[] a = new int[]{1,1,2,6,7};
        int[] b = new int[]{1,1,5};
        //test.ksmallestSum(a, b, 5);
        System.out.println(test.ksmallestSum(a, b, 5));
        System.out.println();
        test.kSmallestPair(a, b, 5);
    }
}
