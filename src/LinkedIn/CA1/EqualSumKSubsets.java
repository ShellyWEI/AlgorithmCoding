package LinkedIn.CA1;

public class EqualSumKSubsets {
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
    }
    /*
    * use DFS to find a subset's sum is equal to targetSum, mark visited position,
    * return true if found one.
    * */
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
