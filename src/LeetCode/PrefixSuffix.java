package LeetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixSuffix {
    class TrieNode {
        List<Integer> prefix;
        List<Integer> suffix;
        Map<Character, TrieNode> children;
        TrieNode() {
            prefix = new ArrayList<>();
            suffix = new ArrayList<>();
            children = new HashMap<>();
        }
    }
    TrieNode root;

    public PrefixSuffix(String[] words) {
        root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            TrieNode pre = root;
            TrieNode suf = root;
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                pre = addWord(i, word.charAt(j), pre, true);
                suf = addWord(i, word.charAt(word.length() - 1 - j), suf, false);
            }
        }
    }

    private TrieNode addWord(int index, char c, TrieNode cur, boolean isPrefix) {
        TrieNode next = cur.children.get(c);
        if (next == null) {
            next = new TrieNode();
            cur.children.put(c, next);
        }
        if (isPrefix) {
            next.prefix.add(index);
        } else {
            next.suffix.add(index);
        }
        return next;
    }

    public int f(String prefix, String suffix) {
        List<Integer> prefixIndexs = searchWord(prefix, true);
        if (prefixIndexs == null) {
            return -1;
        }
        List<Integer> suffixIndexs = searchWord(suffix, false);
        if (suffixIndexs == null) {
            return -1;
        }
        int pre = prefixIndexs.size() - 1;
        int suf = suffixIndexs.size() - 1;
        while (pre >= 0 || suf >= 0) {
            int curPre = prefixIndexs.get(pre);
            int curSuf = suffixIndexs.get(suf);
            if (curPre == curSuf) {
                return curPre;
            } else if (curPre < curSuf) {
                suf--;
            } else {
                pre--;
            }
        }
        return -1;
    }

    private List<Integer> searchWord(String word, boolean isPrefix) {
        TrieNode cur = root;
        if (isPrefix) {
            for (char c : word.toCharArray()) {
                TrieNode next = cur.children.get(c);
                if (next == null) {
                    return null;
                }
                cur = next;
            }
        } else {
            for (int j = word.length() - 1; j >= 0; j--) {
                TrieNode next = cur.children.get(word.charAt(j));
                if (next == null) {
                    return null;
                }
                cur = next;
            }
        }
        return isPrefix ? cur.prefix : cur.suffix;
    }
}
