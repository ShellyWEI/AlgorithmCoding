package Laioffer;

import java.util.*;

public class MedianTracker {
    PriorityQueue<Integer> first;
    PriorityQueue<Integer> second;
    public MedianTracker() {
        // add new fields and complete the constructor if necessary.
        this.first = new PriorityQueue<>((Comparator.reverseOrder()));
        this.second = new PriorityQueue<>();
    }

    public void read(int value) {
        // write your implementation here.
        if (second.size() == 0 || value < second.peek()) {
            first.offer(value);
            if (first.size() > second.size() + 1) {
                Integer max = first.poll();
                second.offer(max);
            }
        } else {
            second.offer(value);
            if (first.size() < second.size()) {
                Integer min = second.poll();
                first.offer(min);
            }
        }
    }

    public Double median() {
        // write your implementation here.
        if (first.size() == second.size() + 1) {
            return new Double(first.peek());
        } else if (first.size() == 0) {
            return null;
        } else if (first.size() == second.size()) {
            return ((first.peek() + second.peek()) / 2.0);
        }
        return null;
    }
}
