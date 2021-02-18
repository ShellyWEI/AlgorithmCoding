package LinkedIn.CA2;

import LinkedIn.PhoneScreen.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeLeaves {
    public List<List<TreeNode>> findAllLeaves(TreeNode root) {
        List<List<TreeNode>> res = new ArrayList<>();
        findLeaves(root, res);
        return res;
    }
    private Integer findLeaves(TreeNode root, List<List<TreeNode>> res) {
        if (root == null) {
            return -1;
        }
        int leftHeight = findLeaves(root.left, res);
        int rightHeight = findLeaves(root.right, res);
        int curHeight = Math.max(leftHeight, rightHeight) + 1;
        if (curHeight == res.size()) {
            res.add(new ArrayList<>());
        }
        res.get(curHeight).add(root);
        return curHeight;
    }
}
