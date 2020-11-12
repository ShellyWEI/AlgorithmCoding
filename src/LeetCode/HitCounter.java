package LeetCode;

import java.util.TreeMap;
/**
 * // hit at timestamp 1.
 * counter.hit(1);
 *
 * // hit at timestamp 2.
 * counter.hit(2);
 *
 * // hit at timestamp 3.
 * counter.hit(3);
 *
 * // get hits at timestamp 4, should return 3.
 * counter.getHits(4);
 *
 * // hit at timestamp 300.
 * counter.hit(300);
 *
 * // get hits at timestamp 300, should return 4.
 * counter.getHits(300);
 *
 * // get hits at timestamp 301, should return 3.
 * counter.getHits(301); */
class HitCounter {
    TreeMap<Integer, Integer> timePrefixCounter;

    /** Initialize your data structure here. */
    public HitCounter() {
        this.timePrefixCounter = new TreeMap<>();
        timePrefixCounter.put(0, 0);
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        Integer prefix = timePrefixCounter.floorKey(timestamp);
        timePrefixCounter.put(timestamp, prefix + 1);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        Integer curFloor = timePrefixCounter.floorKey(timestamp);
        Integer fiveMinBefore = timestamp > 300 ? timePrefixCounter.floorKey(timestamp - 300) : 0;
        return timePrefixCounter.get(curFloor) - timePrefixCounter.get(fiveMinBefore);
    }

    public static void main(String[] args) {
        HitCounter obj = new HitCounter();
        obj.hit(1);
        obj.hit(2);
        obj.hit(3);
        obj.getHits(4);
        obj.hit(300);
        obj.getHits(300);
        obj.getHits(301);
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
