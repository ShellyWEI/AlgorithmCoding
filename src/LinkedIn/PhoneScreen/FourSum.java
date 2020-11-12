package LinkedIn.PhoneScreen;

import java.util.HashMap;
import java.util.Map;

public class FourSum  {
    class Pair {
        int left;
        int right;
        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
    public boolean existFourSum(int[] array, int targetSum) {
        Map<Integer, Pair> map = new HashMap<>();
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                int pairSum = array[i] + array[j];
                // avoid duplicate pairSum by making another pair has no crossing index with current pair
                if (map.containsKey(targetSum - pairSum) && map.get(targetSum - pairSum).right < j) {
                    return true;
                }
                if (!map.containsKey(pairSum)) {
                    map.put(pairSum, new Pair(j, i));
                }
            }
        }
        return false;
    }
}
