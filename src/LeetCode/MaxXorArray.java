package LeetCode;

public class MaxXorArray {
    class TrieNode {
        TrieNode[] children;
        TrieNode() {
            children = new TrieNode[2];
        }
    }
    public int[] maximizeXor(int[] nums, int[][] queries) {
        TrieNode root = buildTrie(nums, 32);
        // find max Xor
        int[] res = new int[queries.length];
        int index = 0;
        for (int[] query : queries) {
            res[index++] = dfs(root, query[0], query[1], 0, 31);
        }
        return res;
    }
    // return valid largest xor for current root node,
    // return -1 if not find one
    private int dfs(TrieNode root, int queryNum, int queryLimit, int candidate, int height) {
        if (candidate > queryLimit) {
            return -1;
        }
        // end case, return xor
        if (height == -1) {
            return queryNum ^ candidate;
        }
        int bit = (queryNum >> height) & 1;
        // try best with 1 as xor result;
        if (root.children[bit ^ 1] != null) {
            int xor = dfs(root.children[bit ^ 1], queryNum, queryLimit, candidate | ((bit ^ 1) << height), height - 1);
            if (xor >= 0) {
                return xor;
            }
        }
        // otherwise try with 0
        if (root.children[bit] != null) {
            int xor = dfs(root.children[bit], queryNum, queryLimit, candidate | (bit << height), height - 1);
            if (xor >= 0) {
                return xor;
            }
        }
        // if above two path does not find valid xor
        return -1;
    }

    private TrieNode buildTrie(int[] nums, int len) {
        TrieNode root = new TrieNode();
        for (int num : nums) {
            TrieNode prev = root;
            for (int i = len - 1; i >= 0; i--) {
                TrieNode cur = prev.children[(num >> i) & 1];
                if (cur == null) {
                    cur = new TrieNode();
                    prev.children[(num >> i) & 1] = cur;
                }
                prev = cur;
            }
        }
        return root;
    }
}
