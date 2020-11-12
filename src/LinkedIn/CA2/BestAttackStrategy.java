package LinkedIn.CA2;

import java.util.*;

/**
 * given the grid of integers, each number representation a type of enemy
 * attack best order, from largest group of enemies first, return List<Posistion> in the order
 * </>*/
public class BestAttackStrategy {
    class Info implements Comparable<Info>{
        int x;
        int y;
        int size;
        Info(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.size = cnt;
        }
        @Override
        public int compareTo(Info other) {
            if (this.size == other.size) {
                return 0;
            }
            return this.size > other.size ? -1 : 1;
        }
    }
    public List<Info> attack(int[][] grid, int player) {
        int m = grid.length;
        int n = grid[0].length;
        List<Info> res = new ArrayList<>();
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == player && !visited[i][j]) {
                    res.add(BFSGenerator(grid, i, j, visited, player));
                }
            }
        }
        Collections.sort(res);
        return res;
    }
    private Info BFSGenerator(int[][] grid, int i, int j, boolean[][] visited, int player) {
        Queue<Info> queue = new LinkedList<>();
        queue.offer(new Info(i, j, 1));
        visited[i][j] = true;
        Info cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            for (int dir = 0; dir < 4; dir++) {
                if (cur.x > 0  && !visited[cur.x - 1][cur.y] && grid[cur.x - 1][cur.y] == player) {
                    queue.offer(new Info(cur.x - 1, cur.y, ++cur.size));
                    visited[cur.x - 1][cur.y] = true;
                }
                if (cur.x + 1 < grid.length && !visited[cur.x + 1][cur.y] && grid[cur.x + 1][cur.y] == player) {
                    queue.offer(new Info(cur.x + 1, cur.y, ++cur.size));
                    visited[cur.x + 1][cur.y] = true;
                }
                if (cur.y > 0  && !visited[cur.x][cur.y - 1] && grid[cur.x][cur.y - 1] == player) {
                    queue.offer(new Info(cur.x, cur.y - 1, ++cur.size));
                    visited[cur.x][cur.y - 1] = true;
                }
                if (cur.y + 1 < grid[0].length && !visited[cur.x][cur.y + 1] && grid[cur.x][cur.y + 1] == player) {
                    queue.offer(new Info(cur.x,  cur.y + 1, ++cur.size));
                    visited[cur.x][cur.y + 1] = true;
                }
            }
        }
        return cur;
    }
    public static void main(String[] args) {
        BestAttackStrategy tst = new BestAttackStrategy();
        int[][] grid = new int[][]{
                {1,1,1,1,0,1},
                {0,1,0,0,1,0},
                {0,0,1,0,1,0},
                {0,0,1,0,1,0}
        };
        List<Info> res = tst.attack(grid, 1);

    }
}
