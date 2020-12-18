package LinkedIn.CA1;

import java.util.*;
// 很傻的问题。。
/*
* Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II.
The number twenty seven is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII.
Instead, the number four is written as IV. Because the one is before the five we subtract it making four.
The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9.
X can be placed before L (50) and C (100) to make 40 and 90.
C can be placed before D (500) and M (1000) to make 400 and 900.
Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.*/
public class RomanNumber {
    Map<String, Integer> symbolToValue = generateSymbolToValue();

    //Map<Integer, String> valueToSymbol = generateValueToSymbol();

    private Map<String, Integer> generateSymbolToValue() {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        return map;
    }

    public int romanToInt(String roman) {
        Scanner s = new Scanner(roman);
        String prev = null;
        int res = 0;
        int prevVal = 0;
        while (s.hasNext()) {
            String cur = s.next();
            if (prev != null) {
                int curVal = symbolToValue.get(cur);
                if (prevVal < curVal) {
                    res += (curVal - prevVal);
                } else {
                    // 49 := XLIX
                    res += curVal;
                }
            } else {
                prev = cur;
                prevVal = symbolToValue.get(cur);
            }
        }
        return res;
    }

    public String intToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"}; // 千位
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; // 百位
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}; // 十位
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; // 个位
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
    }
}
