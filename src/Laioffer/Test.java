package Laioffer;

import java.util.*;
public class Test {

    public static void main(String[] args) {
        double targetHashCode = 0;
        String small = "qrstuvwxyzzabcdefghijklmnopqrstu";
        String large = "abcdefghijklmnopqrstuvwxyzzabcdefghijklmnopqrstu";
        int length = small.length();
        int largePrime = 101;
        for (int i = 0; i < length; i++) {
            targetHashCode = targetHashCode * 26 / largePrime + (small.charAt(i) - 'a');
        }
        double curHashCode = 0;
        double p = Math.pow((double)26, (double)(length - 1));
        char[] array = large.toCharArray();
        int index = -1;
        for (int i = 0; i <= large.length(); i++) {
            if (i < length) {
                curHashCode = curHashCode * 26 + (array[i] - 'a');
            } else {
                if (curHashCode == targetHashCode) {
                    String prev = new String(array, i - length, length);
                    if (prev.equals(small)) {
                        index = i - length;
                    }
                }
                curHashCode = (curHashCode - (array[i - length] - 'a') * p) * 26 + (array[i] - 'a');
            }
        }
        if (curHashCode == targetHashCode) {
            String prev = new String(array, large.length() - length, length);
            if (prev.equals(small)) {
                index = large.length() - 1;
            }
        }
        System.out.print(index);
    }


}
