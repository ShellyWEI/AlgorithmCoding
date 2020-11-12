package Laioffer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FourSum {
    public boolean exist(int[] array, int target) {
        // Write your solution here
        Arrays.sort(array);
        Map<Integer, int[]> map = new HashMap<>(); // sum2pair(left, right)
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int curPairSum = array[i] + array[j];
                int[] prevPair = map.get(target - curPairSum);
                if (prevPair != null && prevPair[1] < i) {
                    return true;
                }
                int[] prevSamePair = map.get(curPairSum);
                if (!map.containsKey(curPairSum) || j < prevSamePair[1]) {
                    map.put(curPairSum, new int[]{i, j});
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[] a = new int[]{3,1,6,2,5,9,4};
        FourSum t = new FourSum();
        t.exist(a, 22);
    }
}
