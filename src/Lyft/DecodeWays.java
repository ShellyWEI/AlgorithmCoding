package Lyft;

public class DecodeWays {
    public int numDecodings(String s) {
        if (s == null) {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            if (s.charAt(i - 1) != '0') {
                dp[i] += dp[i - 1];
            }
            if (i > 1 && valid(s, i - 2)) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[n];
    }
    private boolean valid(String s, int i) {
        if (i + 1 >= s.length() ) {
            return false;
        }
        int dec = s.charAt(i) - '0';
        int digit = s.charAt(i + 1) - '0';
        if (dec == 1) {
            return true;
        } else if (dec == 2 && digit >= 0 && digit <= 6) {
            return true;
        } else {
            return false;
        }
    }
}
