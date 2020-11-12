package Amazon.Onsite;

import java.util.*;

public class WordBreak {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return DFS(s, s.length(), new HashSet<>(wordDict), new HashMap<>());
    }
    private List<String> DFS(String s, int index, Set<String> set, Map<String, List<String>> map) {
        if (s.length() == 0) {
            List<String> res = new ArrayList<>();
            res.add("");
            return res;
        }
        List<String> curList = map.get(s);
        if (curList != null) {
            return curList;
        }
        // 每层构建该range的集合
        curList = new ArrayList<>();
        for (int i = index; i >= 0; i--) {
            String rest = s.substring(i, index);
            if (!set.contains(rest)) {
                continue;
            }
            List<String> prevSub = DFS(s.substring(0, i), i, set, map);
            //if (set.contains(rest)) {
            for (String each : prevSub) {
                curList.add(each + (each.isEmpty() ? "" : " ") + rest);
            }
            //}
        }
        map.put(s, curList);
        return curList;
    }
    public static void main(String[] args) {
        WordBreak t = new WordBreak();
        t.wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog"));
    }
}
