package LinkedIn.CA1;

import LinkedIn.PhoneScreen.TreeNode;

public class BinarySearchTree {
    TreeNode root;

    public BinarySearchTree(TreeNode root) {
        // size recorded with children count, not including root itself
        this.root = buildWithSize(root);
    }

    public TreeNode kSmallest(TreeNode root, int k) {
        if (k <= 0 || k > root.size) {
            return null;
        }
        if (root.left != null) {
            if (k <= root.left.size ) {
                return kSmallest(root.left, k);
            } else if (k == root.left.size + 1) {
                return root;
            } else {
                return kSmallest(root.right, k - 1 - root.left.size);
            }
        } else {
            if (k == 1) {
                return root;
            }
            return kSmallest(root.right, k - 1);

        }
    }

    private TreeNode buildWithSize(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = buildWithSize(root.left);
        TreeNode right = buildWithSize(root.right);
        int leftSize = left == null ? 0 : left.size;
        int rightSize = right == null ? 0 : right.size;
        root.size += leftSize + rightSize;
        return root;
    }

    public TreeNode insert(TreeNode root, int val) {
        insertHelper(root, new TreeNode(val));
        return root;
    }

    private void insertHelper(TreeNode root, TreeNode newNode) {
        root.size += 1;
        if (newNode.value < root.value) {
            if (root.left == null) {
                root.left = newNode;
                return;
            } else {
                insertHelper(root.left, newNode);
            }
        }
        if (newNode.value > root.value) {
            if (root.right == null) {
                root.right = newNode;
                return;
            } else {
                insertHelper(root.right, newNode);
            }
        }
    }

    private TreeNode delete(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key == root.value) {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else if (root.right.left == null) {
                root.right.left = root.left;
                return root.right;
            } else {
                TreeNode newNode = deleteSmallest(root.right);
                newNode.left = root.left;
                newNode.right = root.right;
                return newNode;
            }
        } else if (key < root.value) {
            root.size -= 1;
            root.left = delete(root.left, key);
        } else {
            root.size -= 1;
            root.right = delete(root.right, key);
        }
        return root;
    }

    private TreeNode deleteSmallest(TreeNode root) {
        while (root.left.left != null) {
            root = root.left;
        }
        TreeNode smallest = root.left;
        root.left = root.left.right;
        return smallest;
    }
}
