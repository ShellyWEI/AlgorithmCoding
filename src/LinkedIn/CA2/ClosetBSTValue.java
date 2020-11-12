package LinkedIn.CA2;

import LinkedIn.PhoneScreen.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class ClosetBSTValue {
    // in-order traversal
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new LinkedList<>();
        inOrderFind(root, res, target, k);
        return res;
    }
    private void inOrderFind(TreeNode root, List<Integer> res, double target, int k) {
        if (root == null) {
            return;
        }
        inOrderFind(root.left, res, target, k);
        if (res.size() <= k) {
            res.add(root.value);
        } else if (Math.abs(root.value - target) < Math.abs(res.get(0) - target)) {
            res.remove(0);
            res.add(root.value);
        } else {
            return;
        }
        inOrderFind(root.right, res, target, k);
    }
}
