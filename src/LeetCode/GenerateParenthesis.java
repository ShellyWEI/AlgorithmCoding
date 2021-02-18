package LeetCode;

import java.util.ArrayList;
import java.util.List;
/**
 * https://leetcode.com/problems/generate-parentheses/
 * */
public class GenerateParenthesis {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        DFSHelper(new StringBuilder(), n, n, res);
        return res;
    }
    private void DFSHelper(StringBuilder sb, int leftRam, int rightRam, List<String> res) {
        if (leftRam == 0 && rightRam == 0) {
            res.add(sb.toString());
            return;
        }
        if (leftRam > 0) {
            sb.append("(");
            DFSHelper(sb, leftRam - 1, rightRam, res);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (rightRam > leftRam) {
            sb.append(")");
            DFSHelper(sb, leftRam, rightRam - 1, res);
            sb.deleteCharAt(sb.length() - 1);
        }

    }
}
