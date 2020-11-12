import java.util.*;
/*
* Input:
formula = "K4(ON(SO3)2)2SO4"
Output: "K4N2O14S4"
Explanation:
The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 5}.
* */

public class NumberOfAtoms {
    class Helper {
        Map<String, Integer> map;
        int endIndex;
        Helper(Map<String, Integer> map, int endIndex) {
            this.map = map;
            this.endIndex = endIndex;
        }
    }
    public String countOfAtoms(String formula) {
        Map<String, Integer> result = new HashMap<>();
        Queue<Map<String, Integer>> queue = new LinkedList<>();
        result = buildMap(formula, 0).map;
        return buildString(result);
    }
    private Helper buildMap(String s, int startIndex) {
        Map<String, Integer> curMap = new HashMap<>();
        int endIndex = s.length();
        for (int i = startIndex; i < s.length(); i++) {
            char curChar = s.charAt(i);
            // end cur map building
            if (curChar == ')') {
                if (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    return new Helper(adjustMapByTimes(curMap, Integer.parseInt(s.substring(i + 1, i + 2))), i + 1);
                }
                return new Helper(curMap, i);
            }
            // reccursion starts
            if (curChar == '(') {
                Helper helper = buildMap(s, i + 1);
                Map<String, Integer> restMap = helper.map;
                i = helper.endIndex;
                curMap = mergeMap(curMap, restMap);
            }
            // single atom
            if (Character.isUpperCase(curChar)) {
                // single atom with 1
                if (i + 1 < s.length() && Character.isUpperCase(s.charAt(i + 1))) {
                    curMap.put(s.substring(i, i + 1), curMap.getOrDefault(s.substring(i, i + 1), 0) + 1);
                } else {
                    // single atom with n
                    endIndex = i;
                    while (endIndex + 1 < s.length() && Character.isDigit(s.charAt(endIndex + 1))) {
                        endIndex++;
                    }
                    curMap.put(s.substring(i, i + 1), curMap.getOrDefault(s.substring(i, i + 1), 0) + Integer.parseInt(s.substring(i + 1, endIndex)));
                    i = endIndex - 1;
                }
            }
            // double char atom
            if (Character.isLowerCase(curChar)) {
                // with 1
                if (i + 1 < s.length() && Character.isUpperCase(s.charAt(i + 1))) {
                    curMap.put(s.substring(i - 1, i + 1), curMap.getOrDefault(s.substring(i - 1, i + 1), 0) + 1);
                } else {
                // with n
                    endIndex = i;
                    while (endIndex + 1 < s.length() && Character.isDigit(s.charAt(endIndex + 1))) {
                        endIndex++;
                    }
                    curMap.put(s.substring(i - 1, i + 1), curMap.getOrDefault(s.substring(i - 1, i + 1), 0) + Integer.parseInt(s.substring(i + 1, endIndex)));
                    i = endIndex - 1;
                }
            }
        }
        return new Helper(curMap, endIndex);
    }
    private Map<String, Integer> adjustMapByTimes(Map<String, Integer> map, int times) {
        for (String key : map.keySet()) {
            map.put(key, map.get(key) * times);
        }
        return map;
    }
    private Map<String, Integer> mergeMap(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> res = new HashMap<>();
        for (String key : map1.keySet()) {
            res.put(key, map1.get(key) + map2.getOrDefault(key, 0));
        }
        for (String key : map2.keySet()) {
            if (!res.containsKey(key)) {
                res.put(key, map2.get(key));
            }
        }
        return res;
    }
    private String buildString(Map<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                sb.append(entry.getKey());
            } else {
                sb.append(entry.getKey() + String.valueOf(entry.getValue()));

            }
        }
        return sb.toString();
    }
}
