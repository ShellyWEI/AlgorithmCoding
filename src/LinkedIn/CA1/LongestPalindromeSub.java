package LinkedIn.CA1;

public class LongestPalindromeSub {
    public static int DPFind(String s) {
        char[] array = s.toCharArray();
        int[][] dp = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] == array[i]) {
                    dp[j][i] = dp[j + 1][i - 1] + 2;
                } else {
                    dp[j][i] = Math.max(dp[j + 1][i], dp[j][i - 1]);
                }
            }
        }
        return dp[0][s.length() - 1];
    }

    public static String longestPalindrome(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int maxStart = 0;
        int maxEnd = 0;
        int diff = -1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j >= 0; j--) {
                if (s.charAt(j) == s.charAt(i)) {
                    if (j >= i - 1 || dp[j + 1][i - 1]) {
                        dp[j][i] = true;
                        if (j - i >= diff) {
                            maxStart = j;
                            maxEnd = i;
                            diff = j - i;
                        }
                    }
                }
            }
        }
        return s.substring(maxStart, maxEnd + 1);
    }

    public static void main(String[] args) {
        longestPalindrome("babad");
        System.out.println(DPFind("BBBBA"));

    }
}
