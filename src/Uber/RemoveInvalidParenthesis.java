package Uber;

import java.util.*;
public class RemoveInvalidParenthesis {
    public List<String> removeInvalidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<String>();
        }
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        List<String> res = new ArrayList<>();
        DFSRemove(s, new StringBuilder(), 0, 0, left, 0,  right, res);
        return res;
    }
    private void DFSRemove(String s, StringBuilder sb, int index,
                           int leftBuilt, int leftNeedRemoved,
                           int rightBuilt, int rightNeedRemoved,
                           List<String> res) {
        // prune
        if (rightBuilt > leftBuilt) {
            return;
        }
        if (index == s.length()) {
            if (leftNeedRemoved == 0 && rightNeedRemoved == 0) {
                res.add(sb.toString());
            }
            return;
        }
        char curChar = s.charAt(index);
        if (curChar == '(') {
            // adding duplicate together since contiguous parenthesis makes no difference when adding one by one
            // but removing can't do bunch since only removes needed number
            int duplicate = 1;
            while (index + duplicate < s.length() && s.charAt(index + duplicate) == '(') {
                duplicate++;
            }
            sb.append(s, index, index + duplicate);
            DFSRemove(s, sb, index + duplicate, leftBuilt + duplicate, leftNeedRemoved, rightBuilt, rightNeedRemoved, res);
            sb.delete(sb.length() - duplicate, sb.length());
            // remove one by one until total number reached
            // this way only removes starting contiguous needed removed
            if (leftNeedRemoved > 0) {
                DFSRemove(s, sb, index + 1, leftBuilt, leftNeedRemoved - 1, rightBuilt, rightNeedRemoved, res);
            }
        } else if (curChar == ')') {
            int duplicate = 1;
            while (index + duplicate < s.length() && s.charAt(index + duplicate) == ')') {
                duplicate++;
            }
            sb.append(s, index,index + duplicate);
            DFSRemove(s, sb, index + duplicate, leftBuilt, leftNeedRemoved, rightBuilt + duplicate, rightNeedRemoved, res);
            sb.delete(sb.length() - duplicate, sb.length());

            if (rightNeedRemoved > 0) {
                DFSRemove(s, sb, index + 1, leftBuilt, leftNeedRemoved, rightBuilt, rightNeedRemoved - 1, res);
            }
        } else {
            sb.append(curChar);
            DFSRemove(s, sb, index + 1, leftBuilt, leftNeedRemoved, rightBuilt, rightNeedRemoved, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    public static void main(String[] args) {
        RemoveInvalidParenthesis test = new RemoveInvalidParenthesis();
        test.removeInvalidParentheses(")((()x)");
    }
}
