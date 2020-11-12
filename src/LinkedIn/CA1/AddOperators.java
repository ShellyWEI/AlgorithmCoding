package LinkedIn.CA1;

import java.util.ArrayList;
import java.util.List;

// num = "3456237490", target = 91
public class AddOperators {
    public List<String> addOperators(String num, int target) {
        char[] nums = num.toCharArray();
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        helper(nums, sb, 0, 0, 0, target, res);
        return res;
    }
    private void helper(char[] nums, StringBuilder sb, int index, long sofarSum, long last, int target, List<String> res) {
        if (index == nums.length) {
            if (last == target) {
                res.add(sb.toString());
            }
            return;
        }
        for (int i = index; i < nums.length; i++) {
            // 051 can be ignored since duplicate res, but 0 can be considered
            if (nums[index] == '0' && i != index) {
                break;
            }
            int length = sb.length();
            String cur = new String(nums, index, i - index + 1);
            long curNum = Long.parseLong(cur);
            if (index == 0) {
                helper(nums, sb.append(cur), i + 1, curNum, curNum, target, res);
            } else {
                helper(nums, sb.append("+").append(cur), i + 1, sofarSum + curNum, curNum, target, res);
                sb.setLength(length);
                helper(nums, sb.append("-").append(cur), i + 1, sofarSum - curNum, -curNum, target, res);
                sb.setLength(length);
                helper(nums, sb.append("*").append(cur), i + 1, sofarSum - last + last * curNum, last * curNum, target, res);
                sb.setLength(length);
                if (curNum != 0) {
                    helper(nums, sb.append("-").append(cur), i + 1, sofarSum - last + last / curNum, last / curNum, target, res);
                    sb.setLength(length);
                }
            }
        }
    }
}