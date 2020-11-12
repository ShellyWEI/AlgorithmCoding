package LinkedIn.CA1;

import LinkedIn.PhoneScreen.TreeNode;

import java.util.*;

public class SerializeAndDeserializeBT {
    /**
     * Generalized BT
     * */
    // Method1: pre-order serialize tree
    public String serializeR(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeRHelper(root, sb);
        return sb.toString();
    }

    private void serializeRHelper(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null,");
            return;
        }
        sb.append(root.value).append(",");
        serializeRHelper(root.left, sb);
        serializeRHelper(root.right, sb);
    }

    public TreeNode deserializeR(String data) {
       Scanner s = new Scanner(data);
       s.useDelimiter(",");
        return deserializeRHelper(s);
    }

    private TreeNode deserializeRHelper(Scanner s) {
        if (!s.hasNext()) {
            return null;
        }
        String next = s.next();
        if (next.equals("null")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(next));
        root.left = deserializeRHelper(s);
        root.right = deserializeRHelper(s);
        return root;
    }

    /**
     * BST
     * */
    public String serializeBST(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString();
    }

    private void preOrder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }
        sb.append(root.value).append(',');
        preOrder(root.left, sb);
        preOrder(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserializeBST(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        return buildBST(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode buildBST(Queue<String> queue, int min, int max) {
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        String value = queue.peek();
        int valueNum = Integer.parseInt(value);
        if (valueNum < min || valueNum > max) {
            return null;
        }
        queue.poll();
        TreeNode newNode = new TreeNode(valueNum);
        newNode.left = buildBST(queue, min, valueNum);
        newNode.right = buildBST(queue, valueNum, max);
        return newNode;
    }

    /**
     * N-ary Tree
     * */
}
