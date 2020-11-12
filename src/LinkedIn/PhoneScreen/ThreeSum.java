package LinkedIn.PhoneScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] array, int targetSum) {
        Arrays.sort(array);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            // pass 2, 2, 2... only need first 2
            if (i > 0 && array[i] == array[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = array.length - 1;

            while (left < right) {
                int temp = array[i] + array[left] + array[right];
                if (temp == targetSum) {
                    result.add(Arrays.asList(array[i], array[left], array[right]));
                    left++;
                    while (left < right && array[left] == array[left - 1]) {
                        left++;
                    }
                } else if (temp < targetSum) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
