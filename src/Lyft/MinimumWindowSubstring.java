package Lyft;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        Map<Character, Integer> tMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tMap.put(t.charAt(i), tMap.getOrDefault(t.charAt(i), 0) + 1);
        }
        int start = -1;
        int minStart = -1;
        int minLength = s.length() + 1; // avoid
        int matchCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char endChar = s.charAt(i);
            Integer endCharFreqInWindow = tMap.get(endChar);
            if (endCharFreqInWindow == null) {
                continue;
            } else {
                tMap.put(endChar, endCharFreqInWindow - 1);
                // from unmatch to match
                if (endCharFreqInWindow == 1) {
                    matchCount++;
                }
                if (start == -1) {
                    start = i;
                }
            }
            // minimize window if possible
            while (matchCount == tMap.size()) {
                if (i - start + 1 < minLength) {
                    minLength = i - start + 1;
                    minStart = start;
                }
                char startChar = s.charAt(start++);
                Integer cFreqInWindow = tMap.get(startChar);
                if (cFreqInWindow != null) {
                    tMap.put(startChar, cFreqInWindow + 1);
                    // from matched to unmatch since positive frequency means remaining match number
                    if (cFreqInWindow == 0) {
                        matchCount--;
                    }
                }
            }
        }
        return minLength == s.length() + 1 ? "" : s.substring(minStart, minStart + minLength - 1);
    }
    public int countMinRound(int N, int K) {
        // greedy: all-in when you have most chips
        int round = 0;
        // in the beginning we have 1 chip
        while (N > 1) {
            if (K > 0) {
                // choose bet 1
                if (N % 2 == 1) {
                    N--;
                } else {
                    N /= 2; // all-in
                    K--;
                }
            } else {
                N--; // only bet 1
            }
            round++;
        }
        System.out.println(round);
        return round;
    }
    public static void main(String[] args) {
        MinimumWindowSubstring t = new MinimumWindowSubstring();
        t.minWindow("ADOBECODEBANC","ABC");
    }
}
