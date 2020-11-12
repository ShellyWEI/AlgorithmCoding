package Laioffer;

import java.util.*;
public class DictionaryWord {
    public static boolean canBreak(String input, String[] dict) {
        // Write your solution here
        Set<String> set = new HashSet<>(Arrays.asList(dict));
        int n = input.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int j = 1; j <= n; j++) {
            for (int i = 0; i < j; i++) {

                    String cur = input.substring(i, j);
                    if (set.contains(cur) && dp[i]) {
                        dp[j] = true;
                        break;
                    }

            }
        }
        return dp[n];
    }
    public static void main(String[] args) {
        String[] dict = new String[]{"leet","code","i"};
        canBreak("leetcode", dict);
    }
}
