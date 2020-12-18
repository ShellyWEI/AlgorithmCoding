package LinkedIn.CA1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

// A => 00 C => 01 G => 10 T=> 11
// if 10-length DNA repeated => 2 * 10 as index size
// 2^20 B == 1M size array to store result
// 16-length: 2^32 (4G) is java integer max length, as array max size
// 10~16: use map instead
// bigger: trie

public class RepeatedDNA {
    private static final char[] DECODE = new char[]{'A', 'C', 'G', 'T'};
    public static void printRepeatedSequence(Reader chromosome, int length) throws IOException {
        chromosome = new BufferedReader(chromosome);
        int mask = (1 << (length * 2)) - 1;
        Scanner sc = new Scanner(chromosome);

    }
    public static List<String> findRepeatedDnaSequences(String s) {
        Map<String, Integer> encode = new HashMap<String, Integer>(){
            {
                put("A", 0);
                put("C", 1);
                put("G", 2);
                put("T", 3);
            }
        };

        int mask = (1 << 20) - 1;
        int sequence = 0;
        int[] freq = new int[1 << 20];
        int index = 0;
        while (index <= 8) {
            sequence = (sequence << 2) + encode.get(s.charAt(index++));
        }
        List<String> res = new ArrayList<>();
        for (int i = index; i < s.length(); i++) {
            sequence = ((sequence << 2) + encode.get(s.charAt(i))) & mask;
            freq[sequence]++;
            if (freq[sequence] == 2) {
                res.add(s.substring(i - 9, i + 1));
            }
        }
        return res;
    }
    public static void main(String[] args) {
        findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT");
    }
}
