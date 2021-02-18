package LinkedIn.CA2;

import java.util.*;

public class Palindrome {
    public Set<String> findPalindromes(String s) {
        Set[][] res = new Set[s.length()][s.length()];

        // generate all the standlone chars, add it into the dp sets;
        for (int i = 0; i < s.length(); i++) {
            res[i][i] = new HashSet<>();
            res[i][i].add(s.charAt(i));
        }

        for (int i = s.length()-2;  i >= 0; i--) {
            for (int j = i+1; j < s.length(); j++) {
                Set<String> currSet = (res[i][j] == null)? new HashSet<>(): res[i][j];
                if (s.charAt(i) == s.charAt(j)) {
                    Set<String> innerSet = (i+1 < j-1)? new HashSet<>():res[i+1][j-1];
                    currSet.addAll(innerSet);
                    for (String str : innerSet) {
                        String newPalindrome = s.charAt(i) + str + s.charAt(i);
                        currSet.add(newPalindrome);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(s.charAt(i)).append(s.charAt(i));
                    currSet.add(sb.toString());
                } else {
                    Set<String> innerSetHead = (i+1 < j)? new HashSet<>():res[i+1][j];
                    Set<String> innerSetTail = (i < j-1)? new HashSet<>():res[i][j-1];
                    currSet.addAll(innerSetHead);
                    currSet.addAll(innerSetTail);
                }
            }
        }
        return res[0][s.length()-1];
    }

    public int countPalindromicSubsequences(String S) {
        Set<String> set = new HashSet<>();
        TreeSet[] charIndex = new TreeSet[4];
        for (int i = 0; i < 4; i++) {
            charIndex[i] = new TreeSet<Integer>();
        }
        for (int i = 0; i < S.length(); i++) {
            charIndex[S.charAt(i) - 'a'].add(i);
        }
        Integer[][] rangeCount = new Integer[S.length() + 1][S.length() + 1];
        return findRangePalindromeCount(rangeCount, charIndex, 0, S.length());
    }

    private int findRangePalindromeCount(Integer[][] dp, TreeSet[] indexSet, int left, int right) {
        if (left >= right) {
            return 0;
        }
        if (dp[left][right] != null) {
            return dp[left][right];
        }
        // find cur character's smaller range
        int curFreq = 0;
        for (TreeSet curSet : indexSet) {
            if (curSet.size() == 0) {
                continue;
            }
            if ((int)curSet.first() > right || (int)curSet.last() < left) {
                continue;
            }
            // keep [start, end) if (2,3) => newLeft = 3, newRight = 2, will miss some cases
            Integer nextLeft = (Integer)curSet.ceiling(left);
            Integer prevRight = (Integer)curSet.lower(right);
            if (nextLeft != null && prevRight != null && nextLeft <= right) {
                // a
                curFreq++;
                // aa, a..a
                if (nextLeft < prevRight) {
                    curFreq++;
                }
                curFreq += findRangePalindromeCount(dp, indexSet, nextLeft + 1, prevRight);
            }
        }
        dp[left][right] = curFreq;
        return dp[left][right];
    }

    static class Range {
        int start;
        int end;
        Range(int i, int j) {
            this.start = i;
            this.end = j;
        }
    }
    // TODO: why treemap?
    private Map<Character, List<Integer>> indexMap; // character mapping with its indexes
    private Map<String, List<String>> rangePalindromesMap; // can't use Range as key, new same
    public Set<String> findPalindrome(String s) {
        char[] array = s.toCharArray();
        indexMap = new TreeMap<>();
        for (int i = 0; i < s.length(); i++) {
            List<Integer> curList = indexMap.get(array[i]);
            if (curList == null) {
                curList = new ArrayList<>();
                indexMap.put(array[i], curList);
            }
            curList.add(i);
        }
        // store substring and
        rangePalindromesMap = new HashMap<>();
        Set<String> res = new HashSet<>();
        res.addAll(findRangePalindromes(s, new Range(-1, s.length())));
        return res;
    }
    // return
    private List<String> findRangePalindromes(String s, Range curRange) {
        String curSubstring = s.substring(curRange.start + 1,curRange.end);
        List<String> curRes = rangePalindromesMap.get(s);
        // if previous have results, just return
        if (curRange != null) {
            return curRes;
        }
        curRes = new ArrayList<>();
        for (Map.Entry<Character, List<Integer>> entry : indexMap.entrySet()) {
            String curChar = Character.toString(entry.getKey());
            List<Integer> idxs = entry.getValue();
            if (curRange.end <= idxs.get(0) || curRange.start >= idxs.get(idxs.size() - 1)) {
                continue;
            }
            Range innerRange = defineInnerRange(idxs, curRange);
            // a, aa, a(...)a
            if (innerRange.start <= innerRange.end) {
                curRes.add(curChar);
                if (innerRange.start < innerRange.end) {
                    curRes.add(curChar + curChar);
                    List<String> subPalindrome = findRangePalindromes(s, innerRange);
                    for (String sub : subPalindrome) {
                        curRes.add(curChar + sub + curChar);
                    }
                }
            }

        }
        rangePalindromesMap.put(curSubstring, curRes);
        return curRes;
    }
    // find next available range for this character
    private Range defineInnerRange(List<Integer> indexes, Range range) {
        int left = 0;
        int right = indexes.size() - 1;
        // find first index bigger than start
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (indexes.get(mid) <= range.start) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        int start = indexes.get(left);
        // find last index smaller than end
        right = indexes.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (indexes.get(mid) >= range.end) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        int end = indexes.get(right);
        return new Range(start, end);
    }
    public static void main(String[] args) {
        Palindrome test = new Palindrome();

        test.countPalindromicSubsequences("bccb");
    }
}
