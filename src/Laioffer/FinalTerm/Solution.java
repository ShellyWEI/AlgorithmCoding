package Laioffer.FinalTerm;

import LinkedIn.PhoneScreen.TreeNode;
import apple.laf.JRSUIUtils;
import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;

import java.util.*;

public class Solution {
    public List<String> generateAllPermutations(String input) {
        List<String> res = new ArrayList<>();
        DFS(input.toCharArray(), 0, new StringBuilder(), res);
        return res;
    }
    public void DFS(char[] array, int index, StringBuilder sb, List<String> res) {
        if (index == array.length) {
            res.add(sb.toString());
            return;
        }
        sb.append(array[index]);
        // not add '_'
        DFS(array, index + 1, sb, res);
        // add '_'
        if (index < array.length - 1) {
            sb.append(' ');
            DFS(array, index + 1, sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.deleteCharAt(sb.length() - 1);
    }
    public static void main(String[] args) {
        Solution s = new Solution();
        //s.generateAllPermutations("abc");
        s.squareNumbers(18);
    }

    public boolean isCousin(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int countFind = 0;
        while (countFind == 0 && !queue.isEmpty()) {
            int size = queue.size();
            TreeNode parent = null;
            for (int i = 0; i < size; i++) {
                TreeNode curNode = queue.poll();
                if (curNode.left != null) {
                    if (curNode.left == a || curNode.left == b) {
                        countFind++;
                        parent = curNode;
                    }
                    queue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    if (curNode.right == a || curNode.right == b) {
                        countFind++;
                        // same parent
                        if (parent == curNode) {
                            return false;
                        } else {
                            parent = curNode;
                        }
                    }
                    queue.offer(curNode.left);
                }
            }
            // they are not in same layer;
            if (countFind == 1) {
                return false;
            }
        }
        return true;
    }

    public int squareNumbers(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int curMin = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                curMin = Math.min(curMin, 1 + dp[i - j * j]);
            }
            dp[i] = curMin;
        }
        return dp[n];
    }

    public boolean canReorderToChainCircle(String[] lists) {
        if (lists == null || lists.length == 0) {

            return false;
        }
        return DFSSwap(lists, 0);
    }
    private boolean DFSSwap(String[] lists, int index) {
        if (index == lists.length - 1) {
            return true;
        }
        int curWordLength = lists[index].length();
        for (int i = index; i < lists.length; i++) {
            if (index == 0 || lists[index].charAt(curWordLength - 1) == lists[i].charAt(0)) {
                swap(lists, i, index);
                if (DFSSwap(lists, index + 1)) {
                    return true;
                }
                swap(lists, i, index);
            }
        }
        return false;
    }
    private void swap(String[] array, int left, int right) {
        if (left == right) {
            return;
        }
        String tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }
}
