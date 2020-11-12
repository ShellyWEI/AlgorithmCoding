package Laioffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://app.laicode.io/app/problem/63

/**
 * Set = "abb", all the subsets are ["", "a", "ab", "abb", "b", "bb"]
 */
public class AllSubsets {
    public List<String> subSets(String set) {
        char[] chars = set.toCharArray();
        Arrays.sort(chars);
        List<String> res = new ArrayList<>();
        DFS(chars, 0, new StringBuilder(), res);
        return res;
    }

    private void DFS(char[] array, int index, StringBuilder sb, List<String> res) {
        if (index == array.length) {
            res.add(sb.toString());
        }
        sb.append(array[index]);
        DFS(array, index + 1, sb, res);
        sb.deleteCharAt(sb.length() - 1);
        // deduplicate
        // 要在选择当前元素之后，直接skip到下一个即index+1的位置与当前index的char不同的地方
        // 因为第一次不选择放当前char的分支所包含的情况会反复出现，要去重只能在下一层去到一个完全不会有重复元素出现的层
        while (index + 1 < array.length && array[index + 1] == array[index]) {
            index++;
        }
        DFS(array, index + 1, sb, res);
    }

}
