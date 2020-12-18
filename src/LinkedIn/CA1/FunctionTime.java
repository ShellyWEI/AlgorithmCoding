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
        JobInfo(String name, int start) {
            this.name = name;
            this.start = start;
        }
    }
    // @return int[]{inclusiveTime, exclusiveTime} for jobName
    // method 1: stack O(n)
    // method 2: Union find by Rank O(log n) for one MakeSet, m Set should be O(mlogn)
    public void parseJobTime(String[] input, String objName, int[] result) {
        Deque<JobInfo> jobs = new LinkedList<>();
        int inclusiveTime = 0;
        int firstLevelInclusiveTime = 0;
        for (String log : input) {
            String[] parts = log.split(",");
            // if consider target obj calls itself, A,start,0 -> A,start,1
            // ignore second call
            if (parts[0].equals(objName)) {
                // if consider target obj calls itself, A,start,0 -> A,start,1
                // ignore second call
                    if (parts[1].equals("start")) {
                        jobs.offerFirst(new JobInfo(parts[0], Integer.parseInt(parts[2])));
                    } else if (parts[1].equals("end")) {
                        inclusiveTime += Integer.parseInt(parts[2]) - jobs.pollFirst().start;
                    }

            } else if (!jobs.isEmpty()) {
                if (jobs.peekFirst().name.equals(objName) && parts[1].equals("start")) {
                    jobs.offerFirst(new JobInfo(parts[0], Integer.parseInt(parts[2])));
                } else if (jobs.peekFirst().name.equals(parts[0]) && parts[1].equals("end")) {
                    firstLevelInclusiveTime += Integer.parseInt(parts[2]) - jobs.pollFirst().start;
                }
            }
        }
        result[0] = inclusiveTime;
        result[1] = inclusiveTime - firstLevelInclusiveTime;
    }

    public static void main (String[] args) {
//        String[] input = new String[]{
//            "A,start,10",
//            "A,start,20",
//            "C,start,30",
//            "C,end,40",
//            "A,end,50",
//            "A,end,60",
//            "B,start,70",
//            "A,start,80",
//            "A,end,90",
//            "B,end,100"
//        };
//        FunctionTime f = new FunctionTime();
//        int[] res = new int[2];
//        f.parseJobTime(input, "A", res);
//        System.out.println("Inclusive time: " + res[0]);
//        System.out.println("exclusive time: " + res[1]);
        byte value = new Integer(500).byteValue();
        value = new Integer(33).byteValue();
    }

    // leetcode: find all jobs exclusive time
    //["0:start:0","1:start:2","1:end:5","2:start:6","2:end:6","0:end:7"]
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


}
