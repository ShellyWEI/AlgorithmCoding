package Lyft;

public class PerfectSquare {
    public int numSquares(int n) {
        int[] dp = new int[n + 1]; // the minimum number of square number can sum up to i
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            int curMin = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                curMin = Math.min(curMin, 1 + dp[i - j * j]);
            }
            dp[i] = curMin;
        }
        return dp[n];
    }
}
