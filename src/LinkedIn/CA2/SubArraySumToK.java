package LinkedIn.CA2;

import java.util.HashMap;
import java.util.Map;

public class SubArraySumToK {
    public int countSubarraySumToK(int[] array, int target) {
        int prefixSum = 0;
        int count = 0;
        Map<Integer, Integer> sunFreq = new HashMap<>();
        // base: prefix sum is zero
        sunFreq.put(0, 1);
        for (int num : array) {
            prefixSum += num;
            // before cur index, how many indexes has prefix-target
            count += sunFreq.getOrDefault(prefixSum - target, 0);
            // put back current prefix sum freq
            sunFreq.put(prefixSum, sunFreq.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }
}
