package Lyft;

public class MinimumWindowSubsequence {
    public String minWindow(String S, String T) {
        int sIndex = 0;
        int tIndex = 0;
        char[] array = S.toCharArray();
        int startIndex = -1;
        int minLength = S.length();
        while (sIndex < S.length()) {
            if (S.charAt(sIndex) == T.charAt(tIndex)) {
                if (tIndex == T.length() - 1) {
                    // if we find a match, check smaller size to be valid
                    int curStartIndex = sIndex;
                    while (tIndex >= 0) {
                        if (array[curStartIndex] ==  T.charAt(tIndex)) {
                            curStartIndex--;
                            tIndex--;
                        } else {
                            curStartIndex--;
                        }
                    }
                    int curLength = sIndex - curStartIndex;
                    if (curLength < minLength) {
                        minLength = curLength;
                        startIndex = curStartIndex + 1;
                    }
                    // reset sIndex to the start of minimum finding match
                    sIndex = curStartIndex + 1;
                }
                sIndex++;
                tIndex++;
            } else {
                sIndex++;
            }
        }
        return startIndex == -1 ? "" : new String(array, startIndex, minLength);
    }

    public static void main(String[] args) {
        String s = "ffynmlzesdshlvugsigobutgaetsnjlizvqjdpccdylclqcbghhixpjihximvhapymfkjxyyxfwvsfyctmhwmfjyjidnfryiyajmtakisaxwglwpqaxaicuprrvxybzdxunypzofhpclqiybgniqzsdeqwrdsfjyfkgmejxfqjkmukvgygafwokeoeglanevavyrpduigitmrimtaslzboauwbluvlfqquocxrzrbvvplsivujojscytmeyjolvvyzwizpuhejsdzkfwgqdbwinkxqypaphktonqwwanapouqyjdbptqfowhemsnsl";
        String t = "ntimcimzah";
        MinimumWindowSubsequence ts = new MinimumWindowSubsequence();
        ts.minWindow(s, t);
    }
}
