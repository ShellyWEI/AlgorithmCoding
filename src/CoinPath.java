/*
 * Given an array A (index starts at 1) consisting of N integers: A1, A2, ..., AN and an integer B.
 * The integer B denotes that from any place (suppose the index is i) in the array A, you can jump
 * to any one of the place in the array A indexed i+1, i+2, …, i+B if this place can be jumped to.
 * Also, if you step on the index i, you have to pay Ai coins. If Ai is -1, it means you can’t jump
 * to the place indexed i in the array.
 *
 * Now, you start from the place indexed 1 in the array A, and your aim is to reach the place indexed
 * N using the minimum coins. You need to return the path of indexes (starting from 1 to N) in the
 * array you should take to get to the place indexed N using minimum coins.
 *
 * If there are multiple paths with the same cost, return the lexicographically smallest such path.
 * If it's not possible to reach the place indexed N then you need to return an empty array.
 *
 * Example 1:
 * Input: [1,2,4,-1,2], 2
 * Output: [1,3,5]
 *
 * Example 2:
 * Input: [1,2,4,-1,2], 1
 * Output: []
 *
 *
 * */
import java.util.*;
public class CoinPath {

    public List<Integer> cheapestJump(int[] A, int B) {
        int[] dp = new int[A.length]; // store minimum cost to current i position
        dp[0] = A[0];
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(1);
        Map<Integer, List<Integer>> indexWithPath = new HashMap<>();
        indexWithPath.put(0, initialPath);
        for (int i = B - 1; i < A.length; i++) {
            if (A[i] < 0) {
                // it means you can't jump to position i
                dp[i] = Integer.MAX_VALUE;
                continue;
            }
            for (int j = i - B + 1; j < i; j++) {
                int curCost = dp[j] + A[i];
                if (curCost < dp[i]) {
                    dp[i] = curCost;
                    List<Integer> curPath = indexWithPath.get(i);
                    if (curPath != null) {
                        indexWithPath.remove(i);
                    }
                    List<Integer> prevPath = indexWithPath.get(j);
                    prevPath.add(i + 1);
                    indexWithPath.put(i, prevPath);
                }
            }
        }
        return indexWithPath.get(B - 1);
    }


    public static void main(String[] args) {
        CoinPath test = new CoinPath();
        int[] A = new int[]{1,2,4,-1,2};
        test.cheapestJump(A, 2);
    }
}
