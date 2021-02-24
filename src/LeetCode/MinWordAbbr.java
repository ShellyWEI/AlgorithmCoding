package LeetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Key idea:
 * - bruteforce solution 1 is to generate possible abbreviations from shortest, and compare each dict words if
 * conflicts, the combination of abbreviations are too exponentially increased when target string length grows;
 * - better approach is to pre-compute the dictionary's key position that has to remain in the abbreviation of each
 * word, for example, same prefix share with one dict and target, those digits can be marked as 0, and compress to
 * numeric part in the end. In order to perform DFS more efficiently, we marked those "key position" together in one
 * mask as candidate, and use DFS to generate the combinations that leads to the shortest result.
 *   some optimizations:
 *   i) dict length different must result in different abbreviation, we only need to calculate masks that shares the
 *   same length as target;
 *   ii) when doing DFS, the current mask's abbreviation length larger than global minimum, prune the branch;
 *   iii) better way to calculating abbreviation length is using a sliding window of 0b11, only 2 consecutive 0s
 *   would reduce the length by 1;
 *   iiii) when found a mask with no conflicts with other dictMasks (would generate unique abbreviation), DFS would
 *   stop as the method goes deeper to try different pos in 0 zones and turns in longer length.
 * */
public class MinWordAbbr {
    int minAbbrLen = Integer.MAX_VALUE;
    int res = 0;
    int maskLen = 0;

    public static void main(String[] args) {
        MinWordAbbr solution = new MinWordAbbr();
        String[] dict = {"ablade", "abdefi", "xxxxef"};
        System.out.println(solution.minAbbreviation("abcdef", dict));
    }

    public String minAbbreviation(String target, String[] dictionary) {
        Set<Integer> bitMasks = new HashSet<>();
        maskLen = target.length();
        for (String word : dictionary) {
            if (word.length() == maskLen) {
                bitMasks.add(generateBitMask(target, word));
            }
        }
        // only try mask digit with 1
        int candidate = 0;
        for (int mask : bitMasks) {
            candidate |= mask;
        }
        // find shortest candidate
        dfs(candidate, 0, 1 << (maskLen - 1), bitMasks);
        // recover to word
        return convertToString(target, res);
    }

    private void dfs(int candidate, int mask, int indexMask, Set<Integer> bitMasks) {
        int curAbbrLen = calculateAbbreviationLength(mask);
        if (curAbbrLen >= minAbbrLen) {
            return;
        }
        // validate cur mask with no conflicts with other
        // when cur mask's key pos（pos that has 1）has the corresponding pos with all 0 value in the other
        // dictMask's，the recovered string from mask must share same abbreviation with that dict string
        // thus we need to go deeper.
        boolean isMatched = false;
        for (int dictMask : bitMasks) {
            if ((dictMask & mask) == 0) {
                isMatched = true;
                break;
            }
        }
        if (isMatched) {
            for (int bit = indexMask; bit > 0; bit >>= 1) {
                if ((bit & candidate) > 0) {
                    dfs(candidate, mask | bit, bit >> 1, bitMasks);
                }
            }
        } else {
            minAbbrLen = curAbbrLen;
            res = mask;
        }
    }

    private int calculateAbbreviationLength(int mask) {
        int count = maskLen;
        // every 2 consecutive zero must reduce the final length by 1;
        for (int bit = 3; bit < (1 << maskLen); bit <<= 1 ) {
            if ((mask & bit) == 0) {
                count--;
            }
        }
        return count;
    }

    private String convertToString(String target, int mask) {
        StringBuilder sb = new StringBuilder();
        int start = 1 << (target.length() - 1);
        int countContiguousZero = 0;
        for (int i = 0; i < target.length(); i++, start >>= 1) {
            if ((start & mask) > 0) {
                if (countContiguousZero > 0) {
                    sb.append(countContiguousZero);
                }
                countContiguousZero = 0;
                sb.append(target.charAt(i));
            } else {
                countContiguousZero++;
            }
        }
        if (countContiguousZero > 0) {
            sb.append(countContiguousZero);
        }
        return sb.toString();
    }

    // pos with different char mark as 1, otherwise 0
    private int generateBitMask(String target, String word) {
        int mask = 1;
        int res = 0;
        for (int i = target.length() - 1; i >= 0; i--) {
            if (target.charAt(i) != word.charAt(i)) {
                res |= mask;
            }
            mask <<= 1;
        }
        return res;
    }
}
