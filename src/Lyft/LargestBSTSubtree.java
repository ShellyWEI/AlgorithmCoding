package Lyft;

import Laioffer.LargestSubMatrixSum;
import LinkedIn.PhoneScreen.TreeNode;
import com.sun.deploy.panel.ITreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargestBSTSubtree {
    int maxSize = 0;
    public int largestBSTSubtree(TreeNode root) {
        findBST(root);
        return maxSize;
    }
    // return [min, max, sizeofBST]
    // node null -> -inf, +inf, 0;
    // non-bst root -> +inf, -inf, -1;
    private int[] findBST(TreeNode root) {
        if (root == null) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        }
        int[] left = findBST(root.left);
        int[] right = findBST(root.right);
        // check if current root is bst subtree
        if (left[2] == -1 || right[2] == -1 || root.value <= left[1] || root.value >= right[0]) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, -1};
        }
        int curSize = left[2] + right[2] + 1;
        maxSize = Math.max(maxSize, curSize);
        return new int[]{Math.min(root.value, left[0]), Math.max(root.value, right[1]), curSize};
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5);
        root.left.left.left = new TreeNode(2);
        root.left.left.left.left = new TreeNode(1);
        LargestBSTSubtree ts = new LargestBSTSubtree();
        ts.largestBSTSubtree(root);

    }
}
