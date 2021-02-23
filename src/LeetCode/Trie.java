package LeetCode;

import java.util.HashMap;
import java.util.Map;

class Trie {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;
        TrieNode() {
            children = new HashMap<>();
            isWord = false;
        }
    }
    TrieNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
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

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            TrieNode next = cur.children.get(c);
            if (next == null) {
                return false;
            }
            cur = next;
        }
        return cur.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            TrieNode next = cur.children.get(c);
            if (next == null) {
                return false;
            }
            cur = next;
        }
        return cur.isWord || !cur.children.isEmpty();
    }
}

