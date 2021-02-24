package LeetCode;

import java.util.HashMap;
import java.util.Map;

class WordDictionary {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;
        TrieNode() {
            this.children = new HashMap<>();
            this.isWord = false;
        }
    }
    TrieNode root;

    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            TrieNode next = cur.children.get(c);
            if (next == null) {
                next = new TrieNode();
                cur.children.put(c, next);
            }
            cur = next;
        }
        cur.isWord = true;
    }

    public boolean search(String word) {
        return helper(word.toCharArray(), 0, root);
    }

    private boolean helper(char[] word, int index, TrieNode cur) {
        for (int i = index; i < word.length; i++) {
            TrieNode next = cur.children.get(word[i]);
            if (next == null) {
                if (word[i] == '.') {
                    for (char c : cur.children.keySet()) {
                        if (helper(word, i + 1, cur.children.get(c))) {
                            return true;
                        }
                    }
                }
                return false;
            } else {
                cur = next;
            }
        }
        return cur.isWord;
    }
}
