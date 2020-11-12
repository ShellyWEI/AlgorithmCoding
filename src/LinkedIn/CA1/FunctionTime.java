package LinkedIn.CA1;

import java.util.*;

// union-find is the best approach
/*Input:
n = 2
logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
Output: [3, 4]
Explanation:
Function 0 starts at the beginning of time 0, then it executes 2 units of time and reaches the end of time 1.
Now function 1 starts at the beginning of time 2, executes 4 units of time and ends at time 5.
Function 0 is running again at the beginning of time 6, and also ends at the end of time 6, thus executing for 1 unit of time.
So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.
*/
public class FunctionTime {
    private static class JobInfo {
        String name;
        int start;
        int end;
        JobInfo(String name, int start) {
            this.name = name;
            this.start = start;
            this.end = 0;
        }
    }
    // method 1: stack O(n)
    //["0:start:0","0:start:2","0:end:5","0:start:6","0:end:6","0:end:7"]
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        if (logs == null || n == 0 || logs.size() == 0) {
            return result;
        }
        Deque<Integer> stack = new LinkedList<>();
        int prevTime = 0;
        for (int i = 0; i < logs.size(); i++) {
            String[] parts = logs.get(i).split(":");
            int id = Integer.parseInt(parts[0]);
            int time = Integer.parseInt(parts[2]);
            if (parts[1].equalsIgnoreCase("start")) {
                if (!stack.isEmpty()) {
                    result[stack.peek()] += time - prevTime;
                }
                stack.push(id);
                prevTime = time;
            } else {
                result[stack.pop()] += (time + 1) - prevTime;
                prevTime = time + 1;
            }
        }
        return result;
    }

    // method 2: Union find by Rank O(log n) for one MakeSet, m Set should be O(mlogn)


}
