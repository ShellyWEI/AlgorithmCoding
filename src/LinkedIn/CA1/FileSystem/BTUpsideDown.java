package LinkedIn.CA1.FileSystem;

import LinkedIn.PhoneScreen.TreeNode;

public class BTUpsideDown {
    public TreeNode upsideDownRecursive(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }
        TreeNode newRoot = upsideDownRecursive(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;
        return newRoot;
    }

    public TreeNode upsideDownIterative(TreeNode root) {
        TreeNode prevLeft = null;
        TreeNode prevRight = null;
        while (root != null) {
            TreeNode nextRoot = root.left;
            TreeNode curRight = root.right;
            root.left = prevRight;
            root.right = prevLeft;
            prevLeft = root;
            prevRight = curRight;
            root = nextRoot;
        }
        return prevLeft;
    }
}
