package LinkedIn.CA1;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.ArrayList;
import java.util.List;

public class StringReplacement {
    // Method1: use StringBuilder and indexOf
    public String replaceString(String original, String pattern, String replacement) {
        StringBuilder sb = new StringBuilder();
        int fromIndex = 0;
        int matchedIndex = original.indexOf(pattern);
        while (matchedIndex != -1) {
            sb.append(original,fromIndex, matchedIndex).append(replacement);
            fromIndex = matchedIndex + pattern.length();
            matchedIndex = original.indexOf(pattern, fromIndex);
        }
        sb.append(original, fromIndex, original.length());
        return sb.toString();
    }
    // in place replacement
    public String replace (String org, String pattern, String rep) {
        if (pattern.length() >= rep.length()) {
            return replaceShorter(org, pattern, rep);
        }
        return replaceLonger(org, pattern, rep);
    }
    private String replaceShorter(String input, String source, String target) {
        char[] array = input.toCharArray();
        int slow = 0;
        int fast = 0;
        while (fast < input.length()) {
            if (fast <= input.length() - source.length() && match(input, fast, source)) {
                copy(array, slow, target);
                fast += source.length();
                slow += target.length();
            } else {
                array[slow++] = array[fast++];
            }
        }
        return new String(array, 0, slow);
    }
    private boolean match(String input, int start, String source) {
        for (int i = 0; i < source.length(); i++) {
            if (input.charAt(start + i) != source.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    private void copy(char[] array, int start, String target) {
        for (int i = 0; i < target.length(); i++) {
            array[start + i] = target.charAt(i);
        }
    }
    private String replaceLonger(String input, String source, String target) {
        List<Integer> matchIndexes = findAllMatch(input, source);
        if (matchIndexes.size() == 0) {
            return input;
        }
        char[] array = new char[input.length() + matchIndexes.size() * (target.length() - source.length())];
        int last = matchIndexes.size() - 1;
        int inputIndex = input.length() - 1;
        int resIndex = array.length - 1;
        while (inputIndex >= 0) {
            if (last >= 0 && inputIndex == matchIndexes.get(last)) {
                copy(array, resIndex - target.length() + 1, target);
                inputIndex -= source.length();
                resIndex -= target.length();
                last--;
            } else {
                array[resIndex--] = input.charAt(inputIndex--);
            }
        }
        return new String(array);
    }
    private List<Integer> findAllMatch(String input, String source) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= input.length() - source.length();) {
            if (match(input, i, source)) {
                res.add(i + source.length() - 1);
                i += source.length();
            } else {
                i++;
            }
        }
        return res;
    }
}
