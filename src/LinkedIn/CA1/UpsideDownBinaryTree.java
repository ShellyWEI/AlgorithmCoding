package LinkedIn.CA1;

import LinkedIn.PhoneScreen.TreeNode;

public class UpsideDownBinaryTree {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            return root;
        }
        TreeNode newRoot = upsideDownBinaryTree(root.left);
        TreeNode left = root.left;
        left.left = root.right;
        left.right = root;
        root.left = null;
        root.right = null;
        return newRoot;
    }
}
