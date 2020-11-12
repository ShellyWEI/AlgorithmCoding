package Amazon.Onsite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

//https://leetcode.com/problems/rearrange-string-k-distance-apart/
public class RearrangeStringkDistance {
    class Freq implements Comparable<Freq>{
        char c;
        Integer count;
        Freq (char c) {
            this.c = c;
            this.count = 1;
        }
        @Override
        public int compareTo(Freq another) {
            return another.count.compareTo(this.count);
        }
    }
    public String rearrangeString(String s, int k) {
        // build frequency string
        // s only contains lowercase letters
        Freq[] countMap = new Freq[26];
        for (char c : s.toCharArray()) {
            Freq cur = countMap[c - 'a'];
            if (cur == null) {
                countMap[c - 'a'] = new Freq(c);
            } else {
                cur.count++;
            }
        }
        Arrays.sort(countMap);
        char[] res = new char[s.length()];
        for (Freq charPair : countMap) {

        }
        return null;
    }
}
