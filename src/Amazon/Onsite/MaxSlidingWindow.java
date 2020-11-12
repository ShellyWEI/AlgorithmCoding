package Amazon.Onsite;

import java.util.Deque;
import java.util.LinkedList;

public class MaxSlidingWindow {
    class MonotonicQueue {
        Deque<Integer> queue = new LinkedList<>();

        // offer number in descending order
        // if greater number comes in, poll previous all first
        // in this way, we guarantee the first element is greatest one in the queue
        public void offer (int num) {
            // 必须严格大于，因为poll会在另一处发生，如果此时把之前相同的value poll掉，
            // 另一处check查到相同的值会以为是之前的max
            while (!queue.isEmpty() && queue.peekLast() < num) {
                queue.pollLast();
            }
            queue.offerLast(num);
        }

        public Integer poll() {
            return queue.pollFirst();
        }

        public Integer max() {
            return queue.peekFirst();
        }
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        MonotonicQueue queue = new MonotonicQueue();
        for (int i = 0 ; i < nums.length; i++) {
            queue.offer(nums[i]);
            if (i >= k - 1) {
                // max value can be exceed window size
                if (nums[i - k + 1] == queue.max()) {
                    queue.poll();
                }
                res[i - k + 1] = queue.max();
            }
        }
        return res;
    }
}
