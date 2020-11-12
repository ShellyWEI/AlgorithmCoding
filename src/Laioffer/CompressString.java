package Laioffer;

public class CompressString {
    public String compress(String input) {
        // Write your solution here
        if (input == null || input.isEmpty()) {
            return input;
        }
        char[] array = input.toCharArray();
        int slow = 0;
        int fast = 0;
        int count = 1;
        int countForOne = 0;
        while (fast < array.length) {
            if (fast > 0 && array[fast] == array[fast - 1]) {
                count++;
            } else {
                if (fast > 0 && count == 1) {
                    countForOne++;
                }
                if (count >= 2) {
                    //array[slow++] = Character.forDigit(count, 10);
                    slow += copyDigit(array, slow, count);
                    count = 1;
                }
                array[slow++] = array[fast];
            }
            if (fast == array.length - 1) {
                if (count == 1) {
                    countForOne++;
                } else {
                    slow += copyDigit(array, slow, count);
                }
            }
            fast++;
        }
        char[] res = new char[slow + countForOne];
        int i = res.length - 1;
        for (int j = slow - 1; j >= 0; j--) {
            if (Character.isDigit(array[j])) {
                res[i--] = array[j];
            } else {
                if (j == slow - 1 || !Character.isDigit(array[j + 1])) {
                    res[i--] = '1';
                }
                res[i--] = array[j];
            }
        }
        return new String(res);
    }
    private int copyDigit(char[] array, int index, int num) {
        int copy = num;
        int digit = 1;
        while (copy / 10 > 0) {
            digit++;
            copy /= 10;
        }
        for (int i = digit - 1; i >= 0; i--) {
            array[index + i] = (char)(num % 10 + '0');
            num /= 10;
        }
        return digit;
    }
    public static void main(String[] args) {
        CompressString test = new CompressString();
        String s = test.compress("abbcccde");
    }
}
