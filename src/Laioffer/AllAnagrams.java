package Laioffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAnagrams {
    public List<Integer> allAnagrams(String sh, String lo) {
        // Write your solution here
        int windowLength = sh.length();
        Map<Character, Integer> shMap = new HashMap<>();
        for (int i = 0; i < sh.length(); i++) {
            char cur = sh.charAt(i);
            shMap.put(cur, shMap.getOrDefault(cur, 0) + 1);
        }
        int i = 0;
        int matches = 0;
        List<Integer> res = new ArrayList<>();
        while (i < lo.length()) {
            char add = lo.charAt(i);
            Integer freq = shMap.get(add);
            if (freq != null) {
                shMap.put(add, freq - 1);
                if (freq == 1) {
                    matches++;
                }
                if (freq == 0) {
                    matches--;
                }
            }
            if (i >= sh.length()) {
                char evit = lo.charAt(i - sh.length());
                Integer evitFreq = shMap.get(evit);
                if (evitFreq != null) {
                    shMap.put(evit, evitFreq + 1);
                    if (evitFreq == 0) {
                        matches--;
                    }
                    if (evitFreq == -1) {
                        matches++;
                    }
                }
                if (shMap.size() == matches) {
                    res.add(i - sh.length() + 1);
                }
            }
            i++;
        }
        return res;
    }
    public static void main(String[] args) {
        AllAnagrams test = new AllAnagrams();
        test.allAnagrams("aab", "aaaababacbbaac");
    }
}
