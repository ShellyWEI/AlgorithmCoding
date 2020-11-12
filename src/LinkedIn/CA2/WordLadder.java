package LinkedIn.CA2;

import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }
        Set<String> visited = new HashSet<>();
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        int length = 0;
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            // pick smaller size to generate neighbors
            if (beginSet.size() > endSet.size()) {
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }
            Set<String> next = new HashSet<>();
            for (String ele : beginSet) {
                visited.add(ele);
                for (String word : wordList) {
                    if (!visited.contains(word) && isOneEditDistance(ele, word)) {
                        //visited.add(word);
                        next.add(word);
                        if (endSet.contains(word)) {
                            return length + 1;
                        }
                    }
                }
            }
            beginSet = next;
            length++;
        }
        return 0;
    }
    // toooo slow
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        Set<String> visited = new HashSet<>();
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        int length = 1;
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            // pick smaller size to generate neighbors
            if (beginSet.size() > endSet.size()) {
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }
            Set<String> next = new HashSet<>();
            for (String ele : beginSet) {
                visited.add(ele);
                char[] word = ele.toCharArray();
                for (int i = 0; i < ele.length(); i++) {
                    for (char ch = 'a'; ch <= 'z'; ch++ ) {
                        word[i] = ch;
                        String cur = String.valueOf(word);
                        if (endSet.contains(cur)) {
                            return length + 1;
                        }
                        if (wordList.contains(cur) && !visited.contains(cur)) {
                            next.add(cur);
                        }
                    }
                }
            }
            beginSet = next;
            length++;
        }
        return 0;
    }
    private boolean isOneEditDistance(String one, String two) {
        int diff = 0;
        for (int i = 0; i < one.length(); i++) {
            if (one.charAt(i) != two.charAt(i)) {
                diff++;
            }
            if (diff > 1) {
                return false;
            }
        }
        return diff == 1;
    }
    public static void main (String[] args) {
        String[] wordList = new String[]{"hot","dot","dog","lot","log","cog"};
        WordLadder test = new WordLadder();
        test.ladderLength("hit", "cog", Arrays.asList(wordList));
    }
}
