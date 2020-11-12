package LinkedIn.PhoneScreen;

import java.util.ArrayList;
import java.util.List;

// using two list method
public class LevelBT {
    public List<List<String>> printTree(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        int height = getHeight(root);
        List<List<String>> res = new ArrayList<>();
        List<String> layer = new ArrayList<>();
        int length = (int)(Math.pow(2, height)) - 1;
        for (int j = 0; j < length; j++) {
            layer.add("");
        }
        for (int i = 0; i < height; i++) {
            res.add(new ArrayList<String>(layer));
        }
        print(res, 0, height, 0, length - 1, root);
        return res;
    }
    private void print (List<List<String>> res, int level, int height, int left, int right, TreeNode root) {
        if (level == height || root == null) {
            return;
        }
        int mid = (left + right) / 2;
        res.get(level).set(mid, Integer.toString(root.value));
        print(res, level + 1, height, left, mid, root.left);
        print(res, level + 1, height, mid + 1, right, root.right);
    }
    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
}
