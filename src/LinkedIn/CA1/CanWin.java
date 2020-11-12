package LinkedIn.CA1;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.HashMap;
import java.util.Map;

public class CanWin {
    Map<Integer, Boolean> stateRecorder;
    public boolean canIWin(int limit, int target) {
        int sum = (1 + limit) * limit / 2;
        if (sum < target) {
            return false;
        }
        if (target > 0) {
            return true;
        }
        stateRecorder = new HashMap<>();
        return canWinWithTarget(target, limit, 0);
    }
    private Boolean canWinWithTarget(int rem, int n, int state) {
        if (rem <= 0) {
            return false;
        }
        Boolean curRes = stateRecorder.get(state);
        if (curRes == null) {
            for (int i = n - 1; i >= 0; i--) {
                if ((state&(1 << i)) == 0) {
                    // if choose i has already beat the rival --> I win;
                    // otherwise, recursively find the rival lose --> I win;
                    if (rem < i + 1 || !canWinWithTarget(rem - i - 1, n, state | (1 << i))) {
                        stateRecorder.put(state, true);
                        return true;
                    }
                }
            }
            curRes = false;
            stateRecorder.put(state, false);
        }

        return curRes;
    }
}
