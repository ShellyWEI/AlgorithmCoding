package Amazon.OA;

import java.util.*;

public class PartitionLabel {
    public List<Integer> partitionLabels(String S) {
        //Map<Character, int[]> mapIndex = buildMap(S.toCharArray());
        int[] lastIndex = new int[26];
        for (int i = 0; i < S.length(); i++) {
            lastIndex[S.charAt(i) - 'a'] = i;
        }
        //List<Integer> res = mergeIntevals(lastIndex);
        List<Integer> res = new ArrayList<>();
        int prevStart = -1;
        int prevEnd = -1;
        for (int i = 0; i < S.length(); i++) {
            int curEnd = lastIndex[S.charAt(i) - 'a'];
            if (i > prevEnd) {
                if (prevEnd != -1) {
                    res.add(prevEnd - prevStart + 1);
                }
                prevStart = i;
            }
            prevEnd = Math.max(prevEnd, curEnd);
        }
        res.add(prevEnd - prevStart + 1);
        return res;
    }
//    private Map<Character, int[]> buildMap(char[] array) {
//        Map<Character, int[]> map = new HashMap<>();
//        for (int i = 0; i < array.length; i++) {
//            int[] idx = map.get(array[i]);
//            if (idx == null) {
//                idx = new int[2];
//                idx[0] = i;
//                idx[1] = i;
//                map.put(array[i], idx);
//            } else {
//                idx[1] = i;
//            }
//        }
//        return map;
//    }
    private List<Integer> mergeIntevals(Map<Character, int[]> map) {
        List<Integer> res = new ArrayList<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(map.size(), ((o1, o2) -> {
            if (o1[0] == o2[0]) {
                if (o1[1] == o2[1]) {
                    return 0;
                }
                return o1[1] < o2[1] ? -1 : 1;
            }
            return o1[0] < o2[0] ? -1 : 1;
        }));
        minHeap.addAll(map.values());
        int curStart = -1;
        int curEnd = -1;
        while (!minHeap.isEmpty()) {
            int[] curInteval = minHeap.poll();
            if (curEnd == -1 || curInteval[0] > curEnd) {
                if (curEnd != -1) {
                    res.add(curEnd - curStart + 1);
                }
                curStart = curInteval[0];
            }
            curEnd = Math.max(curEnd, curInteval[1]);
        }
        res.add(curEnd - curStart + 1);
        return res;
    }
    public static void main(String[] args) {
        PartitionLabel test = new PartitionLabel();
        test.partitionLabels("caedbdedda");
    }
}
