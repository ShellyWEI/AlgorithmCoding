package Google;

public class SegmentTree {
    class TreeNode {
        TreeNode left;
        TreeNode right;
        int start;
        int end;
        int value;
        TreeNode (int start, int end, int value) {
            this.value = value;
            this.start = start;
            this.end = end;
        }
    }
    public SegmentTree(TreeNode root, int[] array) {

    }
}
