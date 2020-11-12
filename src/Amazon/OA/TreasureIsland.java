package Amazon.OA;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TreasureIsland {
    public int minStep(char[][] grid) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'X') {
                    int curMin = BFSFindPath(grid, i, j);
                    if (curMin != -1) {
                        min = Math.min(min, curMin);
                    }
                }
            }
        }
        return min;
    }
    class Cell implements Comparable<Cell>{
        int i;
        int j;
        Integer distance;
        Cell(int i, int j, int distance) {
            this.i = i;
            this.j = j;
            this.distance = distance;
        }
        @Override
        public int compareTo(Cell another) {
            return this.distance.compareTo(another.distance);
        }
    }
    private int BFSFindPath(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<Cell> minHeap = new LinkedList<>();
        minHeap.offer(new Cell(i, j, 0));
        visited[i][j] = true;
        while (!minHeap.isEmpty()) {
            Cell cur = minHeap.poll();
            if (grid[cur.i][cur.j] == 'S') {
                return cur.distance;
            }
            int left = cur.i - 1;
            if (cur.i > 0 && !visited[left][cur.j] && grid[left][cur.j] != 'D') {
                minHeap.offer(new Cell(left, cur.j, cur.distance + 1));
                visited[left][cur.j] = true;
            }
            int up = cur.j - 1;
            if (cur.j > 0 && !visited[cur.i][up] && grid[cur.i][up] != 'D') {
                minHeap.offer(new Cell(cur.i, up, cur.distance + 1));
                visited[cur.i][up] = true;
            }
            int right = cur.i + 1;
            if (cur.i + 1 < grid.length && !visited[right][cur.j] && grid[right][cur.j] != 'D') {
                minHeap.offer(new Cell(right, cur.j, cur.distance + 1));
                visited[right][cur.j] = true;
            }
            int down = cur.j + 1;
            if (cur.j > 0 && !visited[cur.i][down] && grid[cur.i][down] != 'D') {
                minHeap.offer(new Cell(cur.i, down, cur.distance + 1));
                visited[cur.i][down] = true;
            }
        }
        return -1;
    }
}
