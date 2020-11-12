package LinkedIn.PhoneScreen;

public class TreeNode {
    public int value;
    public int size;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int value) {
        this.value = value;
        left = null;
        right = null;
        size = 1;
    }

    public boolean valueEquals(TreeNode another) {
        return this.value == another.value;
    }
}
