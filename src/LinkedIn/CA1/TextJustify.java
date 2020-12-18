package LinkedIn.CA1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/**
 * use StringBuilder to construct each line, List to store each line's word
 * three condition:
 * 1. new word can't fitted into current line =>
 *      convert current line to String and add to result
 *      reset StringBuilder and List
 * 2. new word fitted into current line
 * 3. last word's last line =>
 *      calculate space interval differently*/
public class TextJustify {

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> line = new ArrayList<>();
        List<String> res = new ArrayList<>();
        int prevLength = 0;
        for (String word : words) {
            int curLength = word.length();
            if (prevLength + curLength > maxWidth) {
                res.add(evenLine(line, prevLength - line.size(), maxWidth));
                line = new ArrayList<>();
                prevLength = 0;
            }
            prevLength += curLength + 1;
            line.add(word);
        }
        if (!line.isEmpty()) {
            res.add(leftCompactLine(line, prevLength - 1, maxWidth));
        }
        return res;
    }
    private String evenLine(List<String> words, int wordLength, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        if (words.size() == 1) {
            sb.append(words.get(0)).append(appendSpaces(maxWidth - wordLength));
            return sb.toString();
        }
        int basicSpacePerSlot = (maxWidth - wordLength) / (words.size() - 1);
        int NumsOfSlotNeedsExtraSpace = (maxWidth - wordLength) % (words.size() - 1);
        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if (i < NumsOfSlotNeedsExtraSpace) {
                sb.append(appendSpaces(basicSpacePerSlot + 1));
            } else if (i != words.size() - 1) {
                sb.append(appendSpaces(basicSpacePerSlot));
            }
        }
        return sb.toString();
    }
    private String leftCompactLine(List<String> words, int wordLength, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if (i != words.size() - 1) {
                sb.append(" ");
            }
        }
        sb.append(appendSpaces(maxWidth - wordLength));
        return sb.toString();
    }
    private String appendSpaces(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}

