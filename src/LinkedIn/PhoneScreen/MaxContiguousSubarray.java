package LinkedIn.PhoneScreen;

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
}
