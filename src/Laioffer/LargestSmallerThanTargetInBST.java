package Laioffer;

import LinkedIn.PhoneScreen.TreeNode;

public class LargestSmallerThanTargetInBST {
    public int largestSmaller(TreeNode root, int target) {
        // Write your solution here
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        if (target <= root.value) {
            return largestSmaller(root.left, target);
        } else {
            return Math.max(root.value, largestSmaller(root.right, target));
        }
    }
}
