package LinkedIn.PhoneScreen;

import java.util.HashMap;
import java.util.Map;

public class MaxContiguousSubarray {
    // max sum
    public int maxSumSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int curSum = 0;
        for (int num : nums) {
            curSum += num;
            max = Math.max(max, curSum);
            if (curSum <= 0) {
                curSum = 0;
            }
        }
        return max;
    }

    // max product
    public int maxProductSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = 1; // record previous contiguous max
        int min = 1; // record previous contiguous min
        int result = Integer.MIN_VALUE;
        for (int num : nums) {
            int curMax;
            int curMin;
            /*
            * if num < 0:
            *  if curMax < 0 && curMin < 0, -> curMax = max(num, curMax * num); curMin = min(num, curMin * num)
            *  if curMax > 0 && curMin < 0, -> curMax = Max(num, curMin * num); curMin = min(num, curMax * num)
            *  if curMax > 0 && curMin > 0, ->
            * if num > 0:
            *  if curMax < 0 && curMin < 0, -> curMax = num
            *  if curMax > 0; -> curMax = curMax * num
            * */
            // find current contiguous max and min if sign changes
            curMax = Math.max(max * num, min * num);
            curMin = Math.min(max * num, min * num);
            // if max and min need to use new subarray
            max = Math.max(num, curMax);
            min = Math.min(num, curMin);
            // update history max;
            result = Math.max(max, result);
        }
        return result;
    }

    // continuous subarray sum equals target * n (integer)
    public boolean checkSubarraySum(int[] num, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int prefixSum = 0;
        for (int i = 0 ; i < num.length; i++) {
            prefixSum += num[i];
            int cur = prefixSum;
            if (k != 0) {
                cur = prefixSum % k;
            }
            Integer prevIndex = map.get(cur);
            if (prevIndex != null) {
                if (i - prevIndex > 1) {
                    return true;
                }
            } else {
                map.put(cur, i);
            }
        }
        return false;
    }
}
