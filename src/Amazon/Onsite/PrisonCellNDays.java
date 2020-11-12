package Amazon.Onsite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PrisonCellNDays {
    public int[] prisonAfterNDays(int[] cells, int N) {
        Map<Integer, Integer> map = new HashMap<>();
        int original = encoding(cells);
        int day = 0;
        int next = original;
        while (day == 0 || next != original) {
            map.put(day, next);
            if (day == N) {
                break;
            }
            for (int i = 1; i < cells.length - 1; i++) {
                cells[i] = (cells[i - 1] == cells[i + 1]) ? 1 : 0;
            }
            day++;
            next = encoding(cells);
        }
        return decoding(map.get(N % map.size()), cells.length);
    }
    private int encoding(int[] num) {
        int encoding = 0;
        for (int i = 0; i < num.length; i++) {
            encoding = encoding << 1 + num[i];
        }
        return encoding;
    }
    private int[] decoding(int num, int n) {
        int[] res = new int[n];
        int index = n - 1;
        while (num > 0) {
            res[index--] = num % 2;
            num /= 2;
        }
        return res;
    }
}
