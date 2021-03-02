package LeetCode;

public class distanceBetweenBT {
    int distance = 0;
    public int findDistance(TreeNode root, int p, int q) {
        helper(root, p, q);
        return distance;
    }
    // return height of each node, if contains p or q
    private int helper(TreeNode root, int p, int q) {
        if (root == null) {
            return 0;
        }
        int left = helper(root.left, p, q);
        int right = helper(root.right, p, q);
        if (left > 0 && right > 0) {
            distance = left + right;
        } else if (left > 0) {
            // two nodes on same branch
            if (root.val == p || root.val == q) {
                distance = left;
            }
            return left + 1;
        } else if (right > 0) {
            // two nodes on same branch
            if (root.val == p || root.val == q) {
                distance = right;
            }
            return right + 1;
        }
        // deal with leave node
        return root.val == p || root.val == q ? 1 : 0;
    }
}
