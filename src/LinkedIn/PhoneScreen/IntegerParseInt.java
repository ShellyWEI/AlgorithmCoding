package LinkedIn.PhoneScreen;

/*Input: "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
             Thefore INT_MIN (−231) is returned.
*/

public class IntegerParseInt {
    public static Integer parseInt(String str) {
        str = str.trim();
        if (str == null || str.length() == 0) {
            return 0;
        }
        boolean neg = false;
        int limit = Integer.MAX_VALUE;
        int index = 0;
        char first = str.charAt(index);
        if (first < '0' || first > '9') {
            if (first == '-') {
                neg = true;
                limit = Integer.MIN_VALUE;
            } else if (first != '+') {
                return 0;
            }

            if (str.length() == 1) { // only one '+' or '-'
                return 0;
            }
            index++;
        }
        int prevSum = 0;
        for (int i = index; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (cur >= '0' && cur <= '9') {
                int digit = cur - '0';
                //int sum = prevSum * 10 + digit;
                if (prevSum > Integer.MAX_VALUE / 10 || prevSum == Integer.MAX_VALUE / 10 && digit >= Integer.MAX_VALUE % 10) {
                    return limit;
                }
                prevSum = prevSum * 10 + digit;
            } else {
                break;
            }
        }
        return neg? -prevSum : prevSum;
    }
    public static void main(String[] args) {
        parseInt("2147483648");
    }
}
