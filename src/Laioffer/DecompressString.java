package Laioffer;

public class DecompressString {
    public String decompress(String input) {
        int length = input.length();
        char[] array = input.toCharArray();
        int end = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                int freq = c - '0';
                if (freq == 0) {
                    end--;
                    length -= 2;
                } else if (freq > 1) {
                    array[end++] = array[i];
                    length += (freq - 2);
                } else {
                    length -= 1;
                }
            } else {
                array[end++] = array[i];
            }
        }
        char[] res = new char[length];
        int index = length - 1;
        end -= 1;
        while (end >= 0) {
            if (Character.isDigit(array[end])) {
                char c = array[end - 1];
                int freq = array[end] - '0';
                while (freq > 1) {
                    res[index--] = c;
                    freq--;
                }
            } else {
                res[index--] = array[end];
            }
            end--;
        }
        return new String(res);
    }
    public static void main(String[] args) {
        DecompressString test = new DecompressString();
        String s = test.decompress("a1b0c2d3");
    }
}
