package LinkedIn.PhoneScreen;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    Map<Integer, Integer> map;
    /** Initialize your data structure here. */
    public TwoSum() {
        map = new HashMap<>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        map.put(number, map.getOrDefault(number, 0) + 1);
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        for (Map.Entry<Integer, Integer> i : map.entrySet()) {
            int another = value - i.getKey();
            if ((map.containsKey(another) && i.getKey() != another)
            || (i.getKey() == another && i.getValue() > 1)) {
                return true;
            }
        }
        return false;
    }
}
