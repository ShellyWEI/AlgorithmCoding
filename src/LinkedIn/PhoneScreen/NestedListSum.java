package LinkedIn.PhoneScreen;

import java.util.*;

// reverse depth
public class NestedListSum {
    // [1, [4, [6]]]:
    // 每层遍历的时候把前一次的结果加一遍：第一遍 1； 第二遍 1 + （1 + 4） = 6； 第三遍 6 + （6 + 6） = 18
    public int depthSumInverse(List<NestedInteger> nestedList) {
        List<NestedInteger> next = nestedList;
        int unweightedSum = 0;
        int depthSum = 0;
        while(next != null && next.size() != 0) {
            List<NestedInteger> newNext = new ArrayList<>();
            for (NestedInteger n : next) {
                if (n.isInteger()) {
                    unweightedSum += n.getInteger();
                } else {
                    newNext.addAll(n.getList());
                }
            }
            depthSum += unweightedSum;
            next = newNext;
        }
        return depthSum;
    }
    // two pass:
    // get height first and do normal DFS with height-1
    public int twoPass(List<NestedInteger> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return twoPassDFSTraverse(list, twoPassGetHeight(list));
    }
    private int twoPassDFSTraverse(List<NestedInteger> list, int height) {
        int sum = 0;
        for (NestedInteger n : list) {
            if (n.isInteger()) {
                sum += n.getInteger() * height;
            } else {
                sum += twoPassDFSTraverse(n.getList(), height - 1);
            }
        }
        return sum;
    }
    private int twoPassGetHeight(List<NestedInteger> list) {
        int max = 0;
        for (NestedInteger n : list) {
            if (n.isInteger()) {
                max = Math.max(max, 1);
            } else {
                max = Math.max(max, twoPassGetHeight(n.getList()) + 1);
            }
        }
        return max;
    }
    // one pass:
    // use normal ways to deduct, (max + 1) * every integer - normal depthSum
    int maxDepth = 0;
    int flatSum = 0;
    public int onePass(List<NestedInteger> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }

        return (maxDepth + 1) * flatSum - onePassDFSTraverse(list, 1);
    }
    private int onePassDFSTraverse(List<NestedInteger> list, int depth) {
        int depthSum = 0;
        for (NestedInteger n : list) {
            if (n.isInteger()) {
                maxDepth = Math.max(maxDepth, depth);
                depthSum += n.getInteger() * depth;
                flatSum += n.getInteger();
            } else {
                maxDepth = Math.max(maxDepth, depth + 1);
                depthSum += onePassDFSTraverse(n.getList(), depth + 1);
            }
        }
        return depthSum;
    }


}
interface NestedInteger {
    // @return true if this LinkedIn.CA2.PhoneScreen.NestedInteger holds a single integer, rather than a nested list.
    boolean isInteger();

    // @return the single integer that this LinkedIn.CA2.PhoneScreen.NestedInteger holds, if it holds a single integer
    // Return null if this LinkedIn.CA2.PhoneScreen.NestedInteger holds a nested list
    Integer getInteger();

    // @return the nested list that this LinkedIn.CA2.PhoneScreen.NestedInteger holds, if it holds a nested list
    // Return null if this LinkedIn.CA2.PhoneScreen.NestedInteger holds a single integer
    List<NestedInteger> getList();
}