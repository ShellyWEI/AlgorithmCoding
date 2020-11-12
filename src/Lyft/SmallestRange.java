package Lyft;

import java.util.*;

public class SmallestRange {
    List<List<Integer>> nums;
    class Position implements Comparable<Position> {
        int i; // ith List
        int j; // jth in the List
        int value;
        Position(int i, int j) {
            this.i = i;
            this.j = j;
            this.value = getValue();
        }
        public Integer getValue() {
            return nums.get(i).get(j);
        }
        @Override
        public int compareTo(Position another) {
            return this.getValue().compareTo(another.getValue());
        }
    }
    public int[] smallestRange(List<List<Integer>> nums) {
        this.nums = nums;
        LinkedList<Position> deque = new LinkedList<>();
        PriorityQueue<Position> minHeap = new PriorityQueue<>(nums.size());
        int curMaxInHeap = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            Position cur = new Position(i, 0);
            minHeap.offer(cur);
            curMaxInHeap = Math.max(curMaxInHeap, cur.value);
        }
        int interval = Integer.MAX_VALUE;
        int[] res = new int[2];
        while (minHeap.size() == nums.size()) {
            Position curMin = minHeap.poll();
            int curDiff = curMaxInHeap - curMin.value;
            if (curDiff < interval) {
                interval = curDiff;
                res[0] = curMin.value;
                res[1] = curMaxInHeap;
            }
            if (curMin.j + 1 < nums.get(curMin.i).size()) {
                Position newPos = new Position(curMin.i, curMin.j + 1);
                minHeap.offer(newPos);
                curMaxInHeap = Math.max(curMaxInHeap, newPos.value);
            }
        }
        return res;
    }
    public static void main(String[] args) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(Arrays.asList(4,10,15,24,26));
        res.add(Arrays.asList(0,9,12,20));
        res.add(Arrays.asList(5,18,22,30));
        SmallestRange ts = new SmallestRange();
        ts.smallestRange(res);
    }
}
