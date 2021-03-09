package LeetCode;
import java.util.*;

public class SequenceConstruction {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        int n = org.length;
        Map<Integer, Integer> indegree = new HashMap<>();
        ArrayList<Integer>[] neiList = new ArrayList[n + 1];
        for (int i = 0; i < n + 1; i++) {
            neiList[i] = new ArrayList<>();
        }
        for (List<Integer> seq : seqs) {
            indegree.putIfAbsent(seq.get(0), 0);
            for (int i = 1; i < seq.size(); i++) {
                // seqs must be subsequence of org, so it can not have nums not in org
                if (seq.get(i - 1) < 0 || seq.get(i - 1) > n || seq.get(i) < 0 || seq.get(i) > n) {
                    return false;
                }
                neiList[seq.get(i - 1)].add(seq.get(i));
                indegree.merge(seq.get(i), 1, Integer::sum);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer,Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            // multiple possibilities means no unique path
            if (size > 1) {
                return false;
            }
            int cur = queue.poll();
            if (count >= n || org[count] != cur) {
                return false;
            }
            count++;
            for (int nei : neiList[cur]) {
                Integer degree = indegree.get(nei);
                if (degree - 1 == 0) {
                    queue.offer(nei);
                }
                indegree.put(nei, degree - 1);
            }
        }
        return count == n;
    }
}
