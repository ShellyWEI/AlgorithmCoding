package Amazon.Onsite;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestTimeBuyAndSell {
    // at most one transaction
    public int maxProfit(int[] prices) {
        // 本质上就是找最大difference的连续subarray
        int cur = 0;
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            // accumulative result, if cur > 0, means earn profits
            cur += prices[i] - prices[i - 1];
            // if i sell and can't earn profits, reset the buy
            if (cur < 0) {
                cur = 0;
            }
            max = Math.max(max, cur);
        }
        return max;
    }
    // more than one transactions, but buy only after buying new one
    public int maxProfitMultiple(int[] prices) {
        // 寻找几个连续increasing subarray的和
        // 如果像上一题一样，一个subarray中间有变动，那总和一定没有拆分的卖profit大
        int cur = 0;
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                cur += prices[i] - prices[i - 1];
            } else {
                sum += cur;
                cur = 0;
            }
        }
        sum += cur;
        return sum;
    }
    // at most 2 transactions
    class ListNode {
        int value;
        ListNode next;
        ListNode (int value) {
            this.value = value;
            next = null;
        }
    }
    public int maxProfit2(int[] prices) {
        return 0;
    }
    // multiple transactions but each with cooldown time
    public int maxProfitWithCoolDown(int[] prices) {
        int[] holdStock = new int[prices.length]; // ith day I have stock's profits, from either ith day buy or previous buy
        int[] sellStock = new int[prices.length]; // ith day I sell stock's max profits
        holdStock[0] = -prices[0];
        sellStock[0] = 0;
        for (int i = 1; i < prices.length; i++) {
            // 第一天很特殊，不能卖，所以第一天和第二天只能选低价的一天买入持有
            if (i == 1) {
                holdStock[i] = Math.max(holdStock[i - 1], -prices[i - 1]);
            } else {
                // i > 2 持有股票either前一天持有 or 两天前卖掉再在当天买入
                holdStock[i] = Math.max(holdStock[i - 1], sellStock[i - 2] - prices[i]);
            }
            // 卖掉股票状态的最大值either前一天刚卖 or 昨天持有股票的基础上今天卖出
            // 例外是第一天和第二天，第一天base case上面列出，不能卖，所以第二天如果要卖只能是第一天买第二天卖，
            // 但如果第二天卖亏了，不如跟着第一天的状态不卖当做第1天卖出的cool down，因为第一天卖出profit是0，第二天也可以是0
            sellStock[i] = Math.max(holdStock[i - 1] + prices[i], sellStock[i - 1]);
        }
        return sellStock[prices.length - 1];
    }
}
