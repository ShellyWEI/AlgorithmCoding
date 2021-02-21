package LeetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSearchII {
    class TrieNode {
        Map<Character, TrieNode> children;
        String word; // only store word at the end node
        TrieNode() {
            this.children = new HashMap<>();
        }
    }
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, root, i, j, res);
            }
        }
        return res;
    }
    private void dfs(char[][] board, TrieNode root, int i, int j, List<String> res) {
        // out of bound or end of trie branch
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || root == null) {
            return;
        }
        char c = board[i][j];
        // visited or word does not exist
        if (c == '/' || root.children.get(c) == null) {
            return;
        }
        TrieNode cur = root.children.get(c);
        // find the word in the dict
        if (cur.word != null) {
            res.add(cur.word);
            cur.word = null; // deduplicate if board contains same word at different pos
        }
        board[i][j] = '/'; // mark as visited for cur depth path to avoid loop
        dfs(board, cur, i - 1, j, res);
        dfs(board, cur, i, j - 1, res);
        dfs(board, cur, i + 1, j, res);
        dfs(board, cur, i, j + 1, res);
        board[i][j] = c;
        // optimization, very smart! prune to avoid duplicate visits for same word
        if (cur.children.isEmpty()) {
            root.children.remove(c);
        }
    }
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                TrieNode next = cur.children.get(c);
                if (next == null) {
                    next = new TrieNode();
                    cur.children.put(c, next);
                }
                cur = next;
            }
            cur.word = word;
        }
        return root;
    }
}
