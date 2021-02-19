package LeetCode;

import java.util.ArrayList;
import java.util.List;

public class PalindromePartition {
    public List<List<String>> partition(String s) {
        boolean[][] isPalindrome = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            isPalindrome[i][i] = true;
            for (int j = 0; j < i; j++) {
                isPalindrome[j][i] = s.charAt(i) == s.charAt(j) && ((j + 1 < i && isPalindrome[j + 1][i - 1]) || j + 1 == i);
            }
        }
        List<List<String>> res = new ArrayList<>();
        DFSHelper(s, isPalindrome, 0, new ArrayList<>(), res);
        return res;
    }
    private void DFSHelper(String s, boolean[][] isPalindrome, int index, List<String> cur, List<List<String>> res) {
        if (index == s.length()) {
            res.add(new ArrayList<>(cur));
            return;
        }
        for (int i = index; i < s.length(); i++) {
            if (isPalindrome[index][i]) {
                cur.add(s.substring(index, i + 1));
                DFSHelper(s, isPalindrome, i + 1, cur, res);
                cur.remove(cur.size() - 1);
            }
        }
    }
}
