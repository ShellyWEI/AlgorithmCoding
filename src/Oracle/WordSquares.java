package Oracle;

import LinkedIn.PhoneScreen.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Input:
 * ["area","lead","wall","lady","ball"]
 *
 * Output:
 * [
 *   [ "wall",
 *     "area",
 *     "lead",
 *     "lady"
 *   ],
 *   [ "ball",
 *     "area",
 *     "lead",
 *     "lady"
 *   ]
 * ]
 * */
public class WordSquares {
//    class TrieNode {
//        TrieNode[] next;
//        int size; // next nodes' size
//        String word;
//        TrieNode () {
//            next = new TrieNode[26];
//        }
//    }
//
//    public List<List<String>> wordSquares(String[] words) {
//        int wordLength = words[0].length();
//        // build Trie
//        TrieNode root = buildTrie(words);
//        List<List<String>> res = new ArrayList<>();
//        dfs(root, 0, wordLength, new ArrayList<>(), res);
//        return res;
//    }
//
//    private void dfs(TrieNode curNode, int curIndex, int wordLength, List<String> curList, List<List<String>> res) {
//        if (curIndex == wordLength) {
//            res.add(curList);
//            return;
//        }
//        if (curNode.size == 0) {
//            return;
//        }
//
//    }
//
//    private TrieNode buildTrie (String[] words) {
//        TrieNode root = new TrieNode();
//        for (String s : words) {
//            TrieNode cur = root;
//            for (char c : s.toCharArray()) {
//                TrieNode next = cur.next[c - 'a'];
//                if (next == null) {
//                    next = new TrieNode();
//                    cur.next[c - 'a'] = next;
//                    cur.size++;
//                }
//                cur = next;
//            }
//            cur.word = s;
//        }
//        return root;
//    }
class Node {
    List<String> list = new ArrayList<>();
    Node[] child = new Node[26];
}

    List<List<String>> res = new ArrayList<>();

    public List<List<String>> wordSquares(String[] words) {
        Node root = new Node();
        for (String word : words) {
            Node cur = root;
            for (Character ch : word.toCharArray()) {
                cur.list.add(word);
                if (cur.child[ch - 'a'] == null) {
                    cur.child[ch - 'a'] = new Node();
                }
                cur = cur.child[ch - 'a'];
            }
        }
        dfs(new ArrayList<>(), root, words[0].length());
        return res;
    }

    void dfs(List<String> item, Node root, int n) {
        // System.out.println("item: " + item);
        if (item.size() == n) {
            res.add(new ArrayList<>(item));
            return;
        }
        List<String> valid = findPre(root, item);
        // System.out.println("valid: " + valid);
        for (String s : valid) {
            item.add(s);
            dfs(item, root, n);
            item.remove(item.size() - 1);
        }
    }

    List<String> findPre(Node root, List<String> item) {
        Node cur = root;
        int size = item.size();
        for (String s : item) {
            int idx = s.charAt(size) - 'a';
            if (cur.child[idx] == null) {
                return new ArrayList<String>();
            }
            cur = cur.child[idx];
        }
        return cur.list;
    }


    public static void main(String[] args) {
        String[] words = new String[]{
                "area","lead","wall","lady","ball"
        };
        WordSquares t = new WordSquares();
        t.wordSquares(words);
    }
}
