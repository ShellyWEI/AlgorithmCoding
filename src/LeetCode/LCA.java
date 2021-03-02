package LeetCode;

import apple.laf.JRSUIUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LCA {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }
    /**
     * LCA of BST
     * */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else {
            return root;
        }
    }

    /**
     * standard LCA
     * */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        } else if (left == null) {
            return right;
        } else {
            return left;
        }
    }

    /**
     * p or q does not exist, return null
     * */
    boolean findP = false;
    boolean findQ = false;
    public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode res = helper(root, p, q);
        return findP && findQ ? res : null;
    }
    private TreeNode helper(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        TreeNode left = helper(root.left, p, q);
        TreeNode right = helper(root.right, p, q);
        if (root == p) {
            findP = true;
            return root;
        }
        if (root == q) {
            findQ = true;
            return root;
        }
        if (left != null && right != null) {
            return root;
        } else if (left == null) {
            return right;
        } else {
            return left;
        }
    }

    /**
     * with parent node
     * */
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
    public Node lowestCommonAncestor(Node p, Node q) {
        int heightP = height(p);
        int heightQ = height(q);
        if (heightP < heightQ) {
            return mergeNode(q, heightQ - heightP, p);
        } else {
            return mergeNode(p, heightP - heightQ, q);
        }
    }
    private Node mergeNode(Node base, int diff, Node another) {
        while (diff > 0) {
            base = base.parent;
            diff--;
        }
        while (base != another) {
            base = base.parent;
            another = another.parent;
        }
        return base;
    }
    private int height(Node node) {
        int res = 0;
        while (node != null) {
            res++;
            node = node.parent;
        }
        return res;
    }

    /**
     * LCA of multiple nodes
     * */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        Set<TreeNode> set = new HashSet<>(Arrays.asList(nodes));
        return helper(root, set);
    }
    private TreeNode helper(TreeNode root, Set<TreeNode> set) {
        if (root == null || set.contains(root)) {
            return root;
        }
        TreeNode left = helper(root.left, set);
        TreeNode right = helper(root.right, set);
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }
}
