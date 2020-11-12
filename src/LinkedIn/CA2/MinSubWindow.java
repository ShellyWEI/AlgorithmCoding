package LinkedIn.CA2;

import java.util.HashMap;
import java.util.Map;

public class MinSubWindow {
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
}
