package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//TODO: check faster solution
public class PalindromePairs {
    class TrieNode {
        Map<Character, TrieNode> children;
        List<Integer> indexes; // all words index at current node
        int endIndex; // record word index which ends at current node
        TrieNode() {
            children = new HashMap<>();
            indexes = new ArrayList<>();
            endIndex = -1;
        }
    }
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        TrieNode root = buildTrie(words);
        for (int i = 0; i < words.length; i++) {
            TrieNode prev = root;
            char[] word = words[i].toCharArray();
            for (int j = word.length - 1; j >= 0; j--) {
                // leftover in the word: (trieWord)[(checkRemaining)(word[i]LastMatchingPart)]
                if (prev.endIndex != -1 && prev.endIndex != i && isPalindrome(word, 0, word.length - 1 - words[prev.endIndex].length())) {
                    res.add(Arrays.asList(prev.endIndex, i));
                }
                prev = prev.children.get(word[j]);
                // no palindrome found
                if (prev == null) {
                    break;
                }
            }
            // leftover in the node: [(tireWordFirstMatchingPart)(checkingRemaining)](word[i])
            // and maybe remaining part is empty
            if (prev != null) {
                for (int index : prev.indexes) {
                    if (index != i && isPalindrome(words[index].toCharArray(), words[i].length(), words[index].length() - 1)) {
                        res.add(Arrays.asList(index, i));
                    }
                }
            }
        }
        return res;
    }
    private boolean isPalindrome(char[] word, int i, int j) {
        while (i < j) {
            if (word[i] != word[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            TrieNode prev = root;
            prev.indexes.add(i);
            for (char c : words[i].toCharArray()) {
                TrieNode cur = prev.children.get(c);
                if (cur == null) {
                    cur = new TrieNode();
                    prev.children.put(c, cur);
                }
                cur.indexes.add(i);
                prev = cur;
            }
            prev.endIndex = i;
        }
        return root;
    }
}
