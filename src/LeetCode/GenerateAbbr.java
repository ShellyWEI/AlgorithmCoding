package LeetCode;

import java.util.ArrayList;
import java.util.List;

public class GenerateAbbr {
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        DFSHelper(new StringBuilder(), word, 0, -1, res);
        return res;
    }
    private void DFSHelper(StringBuilder sb, String word, int index, int prevNum, List<String> res) {
        if (index == word.length()) {
            res.add(sb.toString());
            return;
        }
        // remain char
        sb.append(word.charAt(index));
        DFSHelper(sb, word, index + 1, -1, res);
        sb.deleteCharAt(sb.length() - 1);

        // turn into integer
        if (prevNum == -1) {
            sb.append("1");
            DFSHelper(sb, word, index + 1, 1, res);
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb = deleteNum(sb, prevNum);
            sb.append(prevNum + 1);
            DFSHelper(sb, word, index + 1, prevNum + 1, res);
            sb = deleteNum(sb, prevNum + 1);
            sb.append(prevNum);
        }
    }
    private StringBuilder deleteNum(StringBuilder sb, int num) {
        while (num > 0) {
            sb.deleteCharAt(sb.length() - 1);
            num /= 10;
        }
        return sb;
    }
}
