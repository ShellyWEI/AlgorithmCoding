package LinkedIn.PhoneScreen;

import java.util.*;

public class Roman {
    // stupid!!!
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>(){};
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int cur = map.get(s.charAt(i));
            int next = i  + 1 < s.length() ? map.get(s.charAt(i + 1)) : 0;
            if ( cur < next ) {
                res -= cur;
            } else {
                res += cur;
            }
        }
        return res;
    }

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int[] nums = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < nums.length && num > 0; i++) {
            while (nums[i] <= num) {
                num -= nums[i];
                sb.append(roman[i]);
            }
        }
        return sb.toString();
    }
}
