package Amazon.Onsite;

import java.util.Arrays;

public class CoinChange {
    // find minimum combination of coins
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int n = coins.length;
        int inf = Integer.MAX_VALUE;
        // dp[i][j]: min coins to make up j amount with coins choices from i to n - 1
        int[][] dp = new int[n + 1][amount + 1];
        Arrays.fill(dp[n], inf);
        dp[n][0] = 0;
        for (int i = n - 1; i >= 0; i--) {
            // ONLY use coins[i]
            for (int j = 0; j <= amount; j++) {
                // each time we don't use current coins, inheritance from previous state
                dp[i][j] = dp[i + 1][j];
                if (j >= coins[i]) {
                    if (dp[i][j - coins[i]] < inf) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i]] + 1);
                    }
                }
            }
        }
        return dp[0][amount];
    }
    // find total combinations
    public int coinChange2(int[] coins, int amount) {
        Arrays.sort(coins);
        int n = coins.length;
        // dp[i][j]: total combination to make up j amount with coins choices from i to n - 1
        int[][] dp = new int[n][amount + 1];
        for (int i = 1; i <= n; i++) {
            // ONLY use coins[i]
            for (int j = 0; j <= amount; j++) {
                // each time we don't use current coins, inheritance from previous state
                if (j >= coins[i - 1]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[0][amount];
    }

    public static void main(String[] args) {
        CoinChange t = new CoinChange();
        int num = t.coinChange(new int[]{1,2,5}, 6);
        System.out.println(num);
    }
}
