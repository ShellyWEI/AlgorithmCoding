package LinkedIn.CA1;

// question: positive integers?
//
public class EqualSumKSubsets {
    public static void main(String[] args) {
        EqualSumKSubsets e = new EqualSumKSubsets();
        System.out.println(e.canPartitionKSubsets(new int[]{129,17,74,57,1421,99,92,285,1276,218,1588,215,369,117,153,22}, 3));
    }
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        if (totalSum % k != 0) {
            return false;
        }
        int targetSum = totalSum / k;
        return DFS(nums, targetSum, k, 0,0, new boolean[nums.length]);
        // LTE solution
        //return helper(nums, targetSum, k, 0, 0);
    }
    // use permutation idea to re-construct array to ***|**|*** so each part can have equal sum
    // time complexity n! + (n - 1)! + (n - 2)! + ....
    private boolean helper(int[] nums, int target, int remaining, int prevSum, int index) {
        if (remaining == 0) {
            return true;
        }
        if (prevSum == target) {
            return helper(nums, target, remaining - 1, 0, index);
        } else if (prevSum > target) {
            return false;
        } else {
            for (int i = index; i < nums.length; i++) {
                swap(nums, i, index);
                if (helper(nums, target, remaining, prevSum + nums[index], index + 1)) {
                    return true;
                }
                swap(nums, i, index);
            }
            return false;
        }

    }
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    /**
    * use DFS to find a subset's sum is equal to targetSum, mark visited position,
    * return true if found one.
    **/
    private boolean DFS(int[] nums, int targetSum, int k, int curSum, int index, boolean[] visited) {
        // each part sum * k = total, so no need to check if all elements are visited
        if (k == 0) {
            return true;
        }
        // if a bucket is found, start all over again
        if (curSum == targetSum) {
            return DFS(nums, targetSum, k - 1, 0, 0, visited);
        } else if (curSum > targetSum) {
            // if nums contains negative integers, ignore this part.
            return false;
        } else {
            for (int i = index; i < nums.length; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    if (DFS(nums, targetSum, k, curSum + nums[i], i + 1, visited)) {
                        return true;
                    }
                    visited[i] = false;
                }
            }
        }
        // k != 0 && all elements are visited
        return false;
    }
}
