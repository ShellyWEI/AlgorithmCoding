package Lyft;

public class RegularExpression {
    // dp ways
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1]; // if s[i,n] matches p[i,m]
        for (int i = n ; i >= 0; i--) {
            for (int j = m ; j >= 0; j--) {
                if (j == m) {
                    // p "" only matches s ""
                    dp[i][j] = i == n;
                } else {
                    char curP = p.charAt(j);
                    // p第二个字符是*
                    if (j + 1 < m && p.charAt(j + 1) == '*') {
                        //可以匹配s中空字符，就是直接跳过p中i和i+1的位置
                        //或者s和p第一个字符一样并且s的i+1开始能和p的j位置开始匹配上，比如s=aaa, p=a*
                        dp[i][j] = dp[i][j + 2] || (i != n && match(s.charAt(i), curP) && dp[i + 1][j]);
                    } else {
                        // 第二个字符不是* 只能实打实匹配了
                        dp[i][j] = i != n && match(s.charAt(i), curP) && dp[i + 1][j + 1];
                    }
                }
            }
        }
        return dp[0][0];
    }
    private boolean match(char c, char p) {
        return p == '.' || c == p;
    }
    // dfs + mem
    // same as above
    public boolean isMatchDFSMem(String s, String p) {
        Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
        return DFS(memo, s, 0, p, 0);
    }
    private boolean DFS(Boolean[][] memo, String s, int sIndex, String p, int pIndex) {
        if (pIndex == p.length()) {
            return sIndex == s.length();
        }
        if (memo[sIndex][pIndex] != null) {
            return memo[sIndex][pIndex];
        }
        char curP = p.charAt(pIndex);
        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            memo[sIndex][pIndex] = DFS(memo, s, sIndex, p, pIndex + 2) ||
                    (sIndex != s.length() && match(s.charAt(sIndex), curP) && DFS(memo, s, sIndex + 1, p, pIndex));
        } else {
            memo[sIndex][pIndex] = sIndex != s.length() && match(s.charAt(sIndex), curP) && DFS(memo, s, sIndex + 1, p, pIndex + 1);
        }
        return memo[sIndex][pIndex];
    }
}
