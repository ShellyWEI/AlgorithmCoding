package LinkedIn.CA1;

import java.util.Arrays;

public class ValidTriangle {
    public int validTriangleCount(int[] nums) {
        int count = 0;
        Arrays.sort(nums);
        for (int index = nums.length - 1; index >= 2; index--) {
            if (nums[index] <= 0) {
                continue;
            }
            int left = 0;
            int right = index - 1;
            while (left < right) {
                if (nums[left] + nums[right] > nums[index]) {
                    count += right - left;
                    right--;
                } else {
                    left++;
                }
            }
        }
        return count;
    }
}
