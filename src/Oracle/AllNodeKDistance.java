package Oracle;

import LinkedIn.PhoneScreen.TreeNode;

import java.util.*;

public class AllNodeKDistance {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new ArrayList<>();
        dfs(root, target, K, res);
        return res;
    }
    // return distance to target while this path having target,
    // otherwise -1
    private int dfs(TreeNode root, TreeNode target, int K, List<Integer> res) {
        // cur-path didn't find target
        if (root == null || K < 0) {
            return -1;
        }
        // cur node is target, search in subtree to find distance K nodes
        // return distance to node = 0
        if (root == target) {
            collectK(root, K, res);
            return 0;
        }
        int left = dfs(root.left, target, K, res);
        int right = dfs(root.right, target, K, res);
        // left subtree has target
        if (left >= 0) {
            // cur root node's distance to target = K
            if (left + 1 == K) {
                res.add(root.value);
            }
            // collect other side subtree, here distance should minus 2 (bypass root)
            collectK(root.right, K - left - 2, res);
            return left + 1;
        }
        // same as above
        if (right >= 0) {
            if (right + 1 == K) {
                res.add(root.value);
            }
            collectK(root.left, K - right - 2, res);
            return right + 1;
        }
        return -1;
    }
    private void collectK(TreeNode root, int K, List<Integer> res) {
        if (K < 0) {
            return;
        }
        if (K == 0) {
            res.add(root.value);
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int distance = 0;
        while (!queue.isEmpty() && distance < K) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    queue.offer(cur.left);
                    if (distance == K - 1) {
                        res.add(cur.left.value);
                    }
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                    if (distance == K - 1) {
                        res.add(cur.right.value);
                    }
                }
            }
            distance++;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode target = new TreeNode(5);
        root.left = target;
        root.right = new TreeNode(1);
        target.left = new TreeNode(6);
        target.right = new TreeNode(2);
        target.right.left = new TreeNode(7);
        target.right.right = new TreeNode(4);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        AllNodeKDistance allNodeKDistance = new AllNodeKDistance();
        allNodeKDistance.distanceK(root, target, 2);
    }
}
