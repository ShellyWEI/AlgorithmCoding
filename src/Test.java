import java.util.*;
public class Test {
    public static void main(String[] args) {
        char c = 'a';
        //System.out.println((char)(c + 1));
        StringBuilder sb = new StringBuilder();
        sb.append((char)(c + 1));
        List<String> rs = new ArrayList<>(new HashSet<>());
        HashSet<String> res = new HashSet<>();
        System.out.println(sb.toString());
//        addMessage(sb);
//        System.out.println(sb.toString());

    }
    private static void addMessage(StringBuilder sb) {
        sb.append("append");
    }
    public static String rearrangeString(String s, int k) {
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> map = new HashMap<>();
        char[] array = s.toCharArray();
        for (char c : array) {
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(map.size(), (m1, m2) -> (m2.getValue() - m1.getValue()));
        maxHeap.addAll(map.entrySet());
        Queue<Map.Entry<Character, Integer>> waitQueue = new LinkedList<>();
        while (!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> cur = maxHeap.poll();
            sb.append(cur.getKey());
            cur.setValue(cur.getValue() - 1);
            waitQueue.offer(cur);

            if (waitQueue.size() >= k) {

                if (waitQueue.peek().getValue() > 0) {
                    maxHeap.offer(waitQueue.poll());
                }
            }
        }
        if (sb.length() < s.length()) {
            return new String();
        }
        return sb.toString();
    }
}
