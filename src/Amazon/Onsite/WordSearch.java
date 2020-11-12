package Amazon.Onsite;

import java.util.ArrayList;
import java.util.List;

public class WordSearch {

    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        char[] chs = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, chs, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, char[] words, int idx, int x, int y) {
        if (idx == words.length) {
            return true;
        }
        if (x < 0 || x == board.length || y < 0 || y == board[0].length) {
            return false;
        }
        if (board[x][y] != words[idx]) {
            return false;
        }
        board[x][y] ^= 256;
        boolean exist = dfs(board, words, idx + 1, x, y + 1) ||
                dfs(board, words, idx + 1, x, y - 1) || dfs(board, words, idx + 1, x + 1, y) ||
                dfs(board, words, idx + 1, x - 1, y);
        board[x][y] ^= 256;
        return exist;
    }

    public static void main(String[] args) {
        char[][] b = new char[][]{
                {'a', 'a'},
        };
        WordSearch t = new WordSearch();
        //t.exist(b, "AAAAB");
        t.findWords(b, new String[]{"aa"});
    }

    /**
     * array matrix with words list, find all words in the matrix
     * lower alphabet only
     * */
    class TrieNode {
        TrieNode[] next;
        String word;
        TrieNode() {
            this.next = new TrieNode[26];
            this.word = null;
        }
    }
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, res);
            }
        }
        return res;
    }
    private void dfs(char[][] board, int i, int j, TrieNode root, List<String> res) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return;
        }
        char c = board[i][j];
        if (c == '*' || root == null || root.next[c - 'a'] == null) {
            return;
        }
        root = root.next[c - 'a'];
        if (root.word != null) {
            res.add(root.word);
            root.word = null; // deduplicate
            // can't return here, because we still need to search if 'word' has 'words' in string
        }
        board[i][j] = '*'; // mark as visited
        dfs(board, i - 1, j, root, res);
        dfs(board, i + 1, j, root, res);
        dfs(board, i, j - 1, root, res);
        dfs(board, i, j + 1, root, res);
        board[i][j] = c;
    }
    private TrieNode buildTrie(String[] words) {
        TrieNode dummy = new TrieNode();
        for (String word : words) {
            TrieNode cur = dummy;
            for (char c : word.toCharArray()) {
                TrieNode next = cur.next[c - 'a'];
                if (next == null) {
                    next = new TrieNode();
                    cur.next[c - 'a'] = next;
                }
                cur = next;
            }
            cur.word = word;
        }
        return dummy;
    }
}
