package Lyft;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Asteroids {
    // stake based
    public int[] asteroidCollisionStack(int[] asteroids) {
        if (asteroids == null || asteroids.length == 0) {
            return asteroids;
        }
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < asteroids.length; ) {
            if (stack.isEmpty() || isSafe(stack.peekFirst(), asteroids[i])) {
                stack.offerFirst(asteroids[i]);
                i++;
            } else {
                int prev = stack.peekFirst();
                int cur = asteroids[i];
                // stack's peek smaller and collapse
                if (cur < 0 && prev <= -cur) {
                    if (prev == -cur) {
                        i++;
                    }
                    stack.pollFirst();
                } else {
                    i++;
                }
            }
        }
        int[] res = new int[stack.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = stack.pollLast();
        }
        return res;
    }

    // fast/slow
    public int[] asteroidCollision(int[] asteroids) {
        int slow = -1; // record asteroids will not collide, including itself
        int fast = 0; // exploring area
        while (fast < asteroids.length) {
            if (slow == -1 || isSafe(asteroids[slow], asteroids[fast])) {
                asteroids[++slow] = asteroids[fast++];
            } else {
                // unsafe, will collide
                int curAst = asteroids[fast];
                if (curAst < 0 && asteroids[slow] < -curAst) {
                    // previous collides, fast unmoved to waiting for compare with slow again
                    slow--;
                } else if (asteroids[slow] == -curAst) {
                    fast++;
                    slow--;
                } else {
                    // fast one collides
                    fast++;
                }
            }
        }
        return Arrays.copyOf(asteroids, slow + 1);
    }

    private boolean isSafe(int prevAst, int curAst) {
        return curAst > 0 || (prevAst < 0 && curAst < 0);
    }

    /**
     * Follow up 1: how many hits;
     * Follow up2: bigger mass - smaller mass
     */
    public int asteroidCollisionHits(int[] asteroids, int[] speed, int[] distance) {
        int slow = -1;
        int fast = 0; // exploring area
        int hits = 0;
        while (fast < asteroids.length) {
            if (slow == -1 || isSafe(asteroids[slow], asteroids[fast])) {
                /**
                 * Given speed and distance to the station, how many more hits would happen?
                 * Answer: add to check: slow.distance <= fast.distance && slow.speed > fast.speed
                 * */
                asteroids[++slow] = asteroids[fast++];
            } else {
                // unsafe, will collide
                int curAst = asteroids[fast];
                if (curAst < 0 && asteroids[slow] <= -curAst) {
                    if (asteroids[slow] == -curAst) {
                        fast++;
                    }
                    // curOne mass change
                    asteroids[fast] += asteroids[slow];
                    // previous collides, fast unmoved to waiting for compare with slow again
                    slow--;
                    hits++;
                } else {
                    // fast one collides
                    fast++;
                    // slow mass change
                    asteroids[slow] -= -curAst;
                }
            }
        }
        return hits;
    }

}
