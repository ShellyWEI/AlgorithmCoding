package Amazon.Onsite;

import java.util.ArrayList;
import java.util.List;

public class LetterCombination {

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        String[] phoneMap = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wzyx"};
        List<String> res = new ArrayList<>();
        DFS(phoneMap, digits.toCharArray(), 0, new StringBuilder(), res);
        return res;
    }
    private void DFS(String[] phoneMap, char[] digits, int index, StringBuilder sb, List<String> res) {
        if (index == digits.length) {
            res.add(sb.toString());
            return;
        }
        String curNumberChoices = phoneMap[digits[index] - '0'];
        for (char c : curNumberChoices.toCharArray()) {
            sb.append(c);
            DFS(phoneMap, digits, index + 1, sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
