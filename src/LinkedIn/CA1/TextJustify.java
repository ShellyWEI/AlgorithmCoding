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
    public List<String> fullJustify(String input, int maxWidth) {
        String[] words = input.split("\\s"); // separate by white space
        StringBuilder lineString = new StringBuilder(maxWidth);
        List<String> lineWords = new ArrayList<>();
        List<String> res = new ArrayList<>();
        int curLength = 0;
        for (int i = 0; i < words.length; i++) {
            // new word can't be fitted into current line
            // can never be last line
            if (curLength + words[i].length() > maxWidth) {
                // construct new line
                int spaceTotal = maxWidth - curLength + lineWords.size();
                int spaceEach = 1;
                int spaceExtra = 0;
                if (lineWords.size() > 1) {
                    spaceEach = spaceTotal / (lineWords.size() - 1);
                    spaceExtra = spaceTotal % (lineWords.size() - 1);
                } else {
                    spaceEach = spaceTotal;
                }
                for (int j = 0; j < lineWords.size(); j++) {
                    lineString.append(lineWords.get(j));
                    // append last word don't need spaces
                    if (j == lineWords.size() - 1 && j != 0) {
                        break;
                    }
                    // otherwise, append space
                    if (j < spaceExtra) {
                        // append extra space to each slot
                        for (int k = 0; k <= spaceEach; k++) {
                            lineString.append(" ");
                        }
                    } else {
                        for (int k = 0; k < spaceEach; k++) {
                            lineString.append(" ");
                        }
                    }
                }
                res.add(lineString.toString());
                // prepare for next line
                curLength = 0;
                lineWords = new ArrayList<>();;
                lineString = new StringBuilder(maxWidth);
            }

            lineWords.add(words[i]);
            curLength += words[i].length() + 1;

            // last line
            if (i == words.length - 1 && lineWords.size() != 0) {
                int spaceLeft = maxWidth - curLength;
                for (int j = 0; j < lineWords.size(); j++) {
                    lineString.append(lineWords.get(j)).append(" ");
                    if (j == lineWords.size() - 1) {
                        for (int k = 0; k < spaceLeft; k++) {
                            lineString.append(" ");
                        }
                        if (spaceLeft < 0) {
                            lineString.setLength(maxWidth);
                        }
                    }
                }
                res.add(lineString.toString());
            }
        }
        return res;
    }
    // TODO: more clean method
    public List<String> justify(int maxWidth, String input) {
        List<String> words = Arrays.asList(input.split("\\s"));
        List<String> line = new ArrayList<>();
        Iterator<String> wordItr = words.iterator();
        List<String> res = new ArrayList<>();
        int curLength = 0;
        while (!wordItr.hasNext()) {
            String next = wordItr.next();
            if (curLength + next.length() > maxWidth) {
                res.add(process(line, curLength - line.size(), maxWidth));
                line = new ArrayList<>();
                curLength = 0;
            }
            line.add(next);
            curLength += next.length() + 1;
        }
        if (!line.isEmpty()) {
            res.add(processLast(line, curLength - 1, maxWidth));
        }
        return res;
    }
    private String processLast(List<String> words, int wordPartLength, int maxLength) {
        StringBuilder sb = new StringBuilder();
        int remainSpaces = maxLength - wordPartLength;
        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if (i < words.size() - 1) {
                sb.append(" ");
            }
        }
        if (remainSpaces > 0) {
            sb.append(appendSpace(remainSpaces));
        }
        return sb.toString();
    }
    private String process(List<String> words, int wordLength, int maxLength) {
        StringBuilder sb = new StringBuilder();
        int totalSpaces = maxLength - wordLength;
        int wordSize = words.size();
        if (wordSize > 1) {
            int spacePerSlot = totalSpaces / (wordSize - 1);
            int extraSpaces = totalSpaces % (wordSize - 1);
            for (int i = 0; i < wordSize; i++) {
                sb.append(words.get(i));
                if (i == wordSize - 1) {
                    break;
                }
                // append spaces;
                if (extraSpaces > 0) {
                    sb.append(" ");
                    extraSpaces--;
                }
                sb.append(appendSpace(spacePerSlot));
            }
        } else {
            sb.append(words.get(0)).append(appendSpace(totalSpaces));
        }
        return sb.toString();
    }
    private String appendSpace(int space) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < space; j++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}

