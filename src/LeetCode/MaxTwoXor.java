package LeetCode;

/**
 * Key:
 * if numA ^ numB = XorSum, then numA ^ XorSum = numB.
 * Build number trie, and assume from largest to trace from trie root,
 * if a child note does not exist, means the ideal case(bit is 1) in the current bit
 * can not form an existing number, and must use 0 instead.
 * (and 0 path must exist as the length for the binary numbers are same)
 * */
public class MaxTwoXor {
    class TrieNode {
        TrieNode[] children;
        public TrieNode() {
            children = new TrieNode[2];
        }
    }

    public int findMaximumXOR(int[] nums) {
        // find max num;
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(num, maxNum);
        }
        int maxLen = Integer.toBinaryString(maxNum).length();
        TrieNode root = buildTrie(nums, maxLen);
        // find max xor
        int maxXor = 0;
        for (int num : nums) {
            TrieNode parent = root;
            int curXor = 0;
            for (int i = maxLen - 1; i >= 0; i--) {
                int bit = (num >> i) & 1;
                // 1 would be max, if 1 does not exist,
                // means the ideal max xor does not have corresponding num in the array
                if (parent.children[bit ^ 1] != null) {
                    curXor += (1 << i);
                    parent = parent.children[bit ^ 1];
                } else {
                    parent = parent.children[bit];
                }
            }
            maxXor = Math.min(maxXor, curXor);
        }
        return maxXor;
    }

    private TrieNode buildTrie(int[] nums, int len) {
        TrieNode root = new TrieNode();
        for (int num : nums) {
            TrieNode parent = root;
            for (int i = len - 1; i >= 0; i--) {
                int bit = (num >> i) & 1;
                TrieNode cur = parent.children[bit];
                if (cur == null) {
                    parent.children[bit] = new TrieNode();
                }
                parent = parent.children[bit];
            }
        }
        return root;
    }
}
