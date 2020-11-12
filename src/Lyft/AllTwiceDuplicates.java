package Lyft;

import java.util.ArrayList;
import java.util.List;

// using O(n) time and O(1) space
// 1 <= i <= n
public class AllTwiceDuplicates {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int actualIndex = Math.abs(nums[i]) - 1;
            if (nums[actualIndex] < 0) {
                res.add(actualIndex);
            } else {
                nums[actualIndex] = -nums[actualIndex];
            }
        }
        return res;
    }
}
