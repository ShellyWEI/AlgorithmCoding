package Laioffer;

import LinkedIn.PhoneScreen.TreeNode;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class TreeTraverse {
    public List<Integer> postorderItr(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.offerFirst(root);
        TreeNode prev = null;
        while (!stack.isEmpty()) {
            TreeNode cur = stack.peekFirst();
            if (prev == null || prev.left == cur || prev.right == cur) {
                if (cur.left != null) {
                    stack.offerFirst(cur.left);
                } else if (cur.right != null) {
                    stack.offerFirst(cur.right);
                } else {
                    stack.pollFirst();
                    res.add(cur.value);
                }
            } else if (prev == cur.left) {
                if (cur.right != null) {
                    stack.offerFirst(cur.right);
                } else {
                    stack.pollFirst();
                    res.add(cur.value);
                }
            } else if (prev == cur.right) {
                stack.pollFirst();
                res.add(cur.value);
            }
            prev = cur;
        }
        return res;
    }
//    public List<Integer> preorderItr(TreeNode root) {
//
//    }
    int max = Integer.MIN_VALUE;
    public int diameterOfBinaryTree(TreeNode root) {
        helper(root);
        return max;
    }
    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        max = Math.max(max, left + right);
        return 1 + Math.max(left, right);
    }
    public static void main(String[] args) {
        TreeTraverse t = new TreeTraverse();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        t.diameterOfBinaryTree(root);

    }
}
