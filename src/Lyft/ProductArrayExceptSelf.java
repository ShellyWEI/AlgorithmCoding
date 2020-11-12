package Lyft;
/**
 * brute force解不对， contains 0 would cause total 0，
 * if preprocess ignore all 0s, use division also can be tricky,
 *      if 0 is more than once, all answer is 0;
 *      if only one 0, division on zero is multiply and other is 0*/
public class ProductArrayExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                res[i] = nums[i];
            } else {
                res[i] = res[i - 1] * nums[i];
            }
        }
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i > 0) {
                res[i] = res[i - 1] * right;
            } else {
                res[i] = right;
            }
            right *= nums[i];
        }
        return res;
    }
}
