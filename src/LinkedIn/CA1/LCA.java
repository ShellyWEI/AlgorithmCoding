package LinkedIn.CA1;


import LinkedIn.PhoneScreen.TreeNode;

public class LCA {

    public TreeNode LowestCommonAncestorBST(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) {
            return null;
        }
        if (a.value < root.value && b.value < root.value) {
            return LowestCommonAncestorBT(root.left, a, b);
        }
        if (a.value > root.value && b.value > root.value) {
            return LowestCommonAncestorBT(root.right, a, b);
        }
        return root;
    }

    // assumption: a b's value different
    public TreeNode LowestCommonAncestorBT(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) {
            return null;
        }
        if (root == a) {
            return a;
        }
        if (root == b) {
            return b;
        }
        TreeNode left = LowestCommonAncestorBT(root.left, a, b);
        TreeNode right = LowestCommonAncestorBT(root.right, a, b);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    // with parent pointer
    class TreeNodeP {
        TreeNodeP left;
        TreeNodeP right;
        TreeNodeP parent;
        int value;
        TreeNodeP(int value) {
            this.value = value;
        }
    }

    public TreeNodeP LowestCommonAncestorParent(TreeNodeP a, TreeNodeP b) {
        int heightA = findHeight(a);
        int heightB = findHeight(b);
        if (heightA > heightB) {
            return mergeNode(a, b, heightA - heightB, heightB);
        } else {
            return mergeNode(b, a, heightB - heightA, heightA);
        }
    }
    private int findHeight(TreeNodeP node) {
        int count = 0;
        while (node != null) {
            node = node.parent;
            count++;
        }
        return count;
    }
    private TreeNodeP mergeNode(TreeNodeP high, TreeNodeP low, int diff, int min) {
        while (diff > 0) {
            high = high.parent;
            diff--;
        }
        // in case high and low are not in same tree
        while (high != low && min > 0) {
            low = low.parent;
            high = high.parent;
            min--;
        }
        if (high != low && min != 0) {
            return null;
        }
        return high;
    }
}
