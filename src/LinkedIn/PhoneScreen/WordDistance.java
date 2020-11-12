package LinkedIn.PhoneScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDistance {
    // map-based solutions: use map to store other words index
    Map<String, List<Integer>> map;
    public WordDistance(String[] words) {
        map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (!map.containsKey(words[i])) {
                map.put(words[i], new ArrayList<>());
            }
            map.get(words[i]).add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> list1 = map.get(word1);
        List<Integer> list2 = map.get(word2);
        if (list1 == null || list2 == null) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        // use two pointers to compare
        for (int i = 0, j = 0; i < list1.size() && j < list2.size();) {
            Integer index1 = list1.get(i);
            Integer index2 = list2.get(j);
            if (index1 < index2) {
                min = Math.min(min, index2 - index1);
                i++;
            } else {
                min = Math.min(min, index1 - index2);
                j++;
            }
        }
        return min;
    }

    // linear method
    public int shortestWordDistance(String[] words, String word1, String word2) {
        String curWord = null;
        int startIndex = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.equalsIgnoreCase(word1) || word.equalsIgnoreCase(word2)) {
                if (curWord == null) {
                    curWord = word;
                }
                if (curWord == word) {
                    startIndex = i;
                } else {
                    min = Math.min(min, i - startIndex);
                    startIndex = i;
                    curWord = word;
                }
            }
        }
        return min;
    }

    public static void main(String[] args) {
        String[] input = {"a", "a", "c", "b"};
        WordDistance wd = new WordDistance(input);
        wd.shortestWordDistance(input, "a", "b");
    }
}
