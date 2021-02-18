package LinkedIn.CA2;

import java.util.HashMap;
import java.util.Map;

public class MinSubWindow {
    // wrong!!!
    public String minWindow(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        Map<Character, Integer> tFreq = new HashMap<>();
        for (char c : t.toCharArray()) {
            tFreq.put(c, tFreq.getOrDefault(c, 0) + 1);
        }
        int start = 0;
        int minLeft = 0;
        int minLength = s.length() + 1;
        int size = tFreq.size();
        for (int i = 0; i <= s.length(); i++) {
            while (size == 0) {
                if (i - start < minLength) {
                    minLength = i - start;
                    minLeft = start;
                }
                // now begin with new starting point and add back
                char startChar = s.charAt(start++);
                Integer startFreq = tFreq.get(startChar);
                if (startFreq == null) {
                    continue;
                } else {
                    startFreq++;
                }
                if (startFreq > 0) {
                    size++;
                }
                tFreq.put(startChar, startFreq);
            }
            if (i == s.length()) {
                break;
            }
            char curChar = s.charAt(i);
            Integer curFreq = tFreq.get(curChar);
            if (curFreq != null) {
                tFreq.put(curChar, curFreq - 1);
                if (curFreq == 1) {
                    size--;
                }
            }
        }
        return minLength == s.length() + 1 ? "" : s.substring(minLeft, minLeft + minLength);
    }

    public String minWindow2(String S, String T) {
        if (S.length() == 0 || T.length() == 0) {
            return "";
        }
        int right = 0;
        int minLen = Integer.MAX_VALUE;
        String result = "";

        while (right < S.length()) {
            int tIndex = 0;
            // use fast pointer to find the last character of T in S
            while (right < S.length()) {
                if (S.charAt(right) == T.charAt(tIndex)) {
                    tIndex++;
                }
                if (tIndex == T.length()) {
                    break;
                }
                right++;
            }

            // if right pointer is over than boundary
            if (right == S.length()) {
                break;
            }

            // use another slow pointer to traverse from right to left until find first character of T in S
            int left = right;
            tIndex = T.length() - 1;
            while (left >= 0) {
                if (S.charAt(left) == T.charAt(tIndex)) {
                    tIndex--;
                }
                if (tIndex < 0) {
                    break;
                }
                left--;
            }
            // if we found another subsequence with smaller length, update result
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                result = S.substring(left, right + 1);
            }
            // WARNING: we have to move right pointer to the next position of left pointer, NOT the next position
            // of right pointer
            right = left + 1;
        }
        return result;
    }
}
