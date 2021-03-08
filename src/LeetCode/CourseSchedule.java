package LeetCode;

import java.util.*;

public class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] prereq : prerequisites) {
            if (prereq[0] == prereq[1]) {
                return false;
            }
            indegree[prereq[0]]++;
            List<Integer> neighbors = map.get(prereq[1]);
            if (neighbors == null) {
                neighbors = new ArrayList<>();
                map.put(prereq[1], neighbors);
            }
            neighbors.add(prereq[0]);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int curCourse = queue.poll();
            count++;
            List<Integer> neighbors = map.get(curCourse);
            // the cur course is not prerequisite of any other course
            if (neighbors == null) {
                continue;
            }
            for (int nei : neighbors) {
                indegree[nei]--;
                if (indegree[nei] == 0) {
                    queue.offer(nei);
                }
            }
        }
        return count == numCourses;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        ArrayList<Integer>[] list = new ArrayList[numCourses];
        // 不能使用Arrays.fill，所有的元素都会share一个reference
        for (int i = 0; i < numCourses; i++) {
            list[i] = new ArrayList<>();
        }
        for (int[] prereq : prerequisites) {
            if (prereq[0] == prereq[1]) {
                return new int[0];
            }
            indegree[prereq[0]]++;
            list[prereq[1]].add(prereq[0]);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int count = 0;
        int[] res = new int[numCourses];
        while (!queue.isEmpty()) {
            int curCourse = queue.poll();
            res[count++] = curCourse;
            List<Integer> neighbors = list[curCourse];
            for (int nei : neighbors) {
                if (--indegree[nei] == 0) {
                    queue.offer(nei);
                }
            }
        }
        return count == numCourses ? res : new int[0];
    }
}
