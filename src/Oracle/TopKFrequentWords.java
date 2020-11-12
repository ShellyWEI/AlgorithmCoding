package Oracle;

import java.net.Inet4Address;
import java.util.*;

public class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(k, (o1, o2) -> {
            if (o1.getValue().equals(o2.getValue())) {
                return o2.getKey().compareTo(o1.getKey());
            }
            return o1.getValue().compareTo(o2.getValue());
        });
        for (String word : words) {
            Integer count = map.getOrDefault(word, 0) + 1;
            map.put(word, count);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else {
                if (entry.getValue() >= minHeap.peek().getValue()) {
                    minHeap.offer(entry);
                    if (entry.getValue() > minHeap.peek().getValue()) {
                        minHeap.poll();
                    }
                }
            }
        }
        List<String> res = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            res.add(minHeap.poll().getKey());
        }
        Collections.reverse(res);
        return res.subList(0, k);
    }
    public static void main(String[] args) {
        String[] words = new String[]{"i", "love", "leetcode", "i", "love", "coding"};
        TopKFrequentWords t = new TopKFrequentWords();
        t.topKFrequent(words, 3);
    }
}
