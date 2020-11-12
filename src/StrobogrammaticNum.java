import java.util.*;

/*
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.

Example:

Input: low = "50", high = "100"
Output: 3
Explanation: 69, 88, and 96 are three strobogrammatic numbers.
Note:
Because the range might be a large number, the low and high numbers are represented as string.
*/
public class StrobogrammaticNum {
    private final char[][] pairs = {
            {'0', '0'},
            {'1', '1'},
            {'8', '8'},
            {'6', '9'},
            {'9', '6'}
    };
    public int strobogrammaticInRange(String low, String high) {
        int[] count = new int[]{0};
        for (int i = low.length(); i <= high.length(); i++) {
            char[] array = new char[i];
            DFSBuild(low, high, array, 0, i - 1, count);
        }

        return count[0];
    }
    private void DFSBuild(String low, String high, char[] arrayForString, int left, int right, int[] count) {
        if (left > right) {
            String string = new String(arrayForString);
            if ( ( (string.length() == low.length()) && (string.compareTo(low) < 0) )  ||
            ( (string.length() == high.length()) && (string.compareTo(high) > 0) ) ) {
                return;
            }
            count[0]++;
            return;
        }
        for (char[] pair : pairs) {
            if (left == 0 && pair[0] == '0') {
                continue;
            }
            if (left == right && pair[0] != pair[1]) {
                continue;
            }
            arrayForString[left] = pair[0];
            arrayForString[right] = pair[1];
            DFSBuild(low, high, arrayForString, left + 1, right - 1, count);
        }
    }
    public static void main(String[] args) {
        StrobogrammaticNum test = new StrobogrammaticNum();
        test.strobogrammaticInRange("50", "100");

    }

}
