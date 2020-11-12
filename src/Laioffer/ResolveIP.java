package Laioffer;

import java.util.*;

public class ResolveIP {
    public List<String> Restore(String ip) {
        // Write your solution here
        List<String> res = new ArrayList<>();
        DFS(ip.toCharArray(), 0, 0, new StringBuilder(), res);
        return res;
    }
    private void DFS(char[] array, int index, int level, StringBuilder sb, List<String> res) {
        if (level == 4) {
            if (index == array.length) {
                res.add(sb.substring(0, sb.length() - 1));
            }
            return;
        }

        for (int offset = 1; offset <= 3 && index + offset <= array.length; offset++) {
            if (array[index] == '0' && offset > 1) {
                return;
            }
            String cur = new String(array, index, offset);
            int num = Integer.parseInt(cur);
            if (num <= 255) {
                sb.append(cur).append('.');
                DFS(array, index + offset, level + 1, sb, res);
                sb.delete(sb.length() - offset - 1, sb.length());
            }
        }
    }
    public static void main(String[] args) {
        String s = "10809010";
        ResolveIP r = new ResolveIP();
        List<String> res = r.Restore(s);
        for (String ss : res) {
            System.out.println(ss);
        }
    }
}
