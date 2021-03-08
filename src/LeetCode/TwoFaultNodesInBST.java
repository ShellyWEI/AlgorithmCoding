package LeetCode;

/**
 * two abnormal nodes in ascending inorder traversal, the symptom is a descending slope.
 * Might be only one abnormal part if there are two adjacent nodes
 * */
public class TwoFaultNodesInBST {
    TreeNode faultNode1;
    TreeNode faultNode2;
    TreeNode prev;
    public void recoverTree(TreeNode root) {
        inOrderFindNode(root);
        int temp = faultNode1.val;
        faultNode1.val = faultNode2.val;
        faultNode2.val = temp;
    }

    private void inOrderFindNode(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderFindNode(root.left);
        if (prev != null && prev.val > root.val) {
            faultNode2 = root;
            if (faultNode1 == null) {
                faultNode1 = prev;
            } else {
                // find two fault nodes
                return;
            }
        }
        prev = root;
        inOrderFindNode(root.right);
    }
}
