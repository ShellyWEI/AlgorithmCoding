package Laioffer;

import java.util.Arrays;

public class MazeGenerate {
    enum Dir {
        Up(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);
        int deltaX;
        int deltaY;
        Dir(int x, int y) {
            this.deltaX = x;
            this.deltaY = y;
        }
        int moveX(int x, int times) {
            return x + times * deltaX;
        }
        int moveY(int y, int times) {
            return y + times * deltaY;
        }
    }

    public int[][] maze(int n) {
        // Write your solution here.
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(grid[i], 1);
        }
        grid[0][0] = 0;
        DFS(grid, 0, 0);
        return grid;
    }
    private void DFS(int[][] grid, int i, int j) {
        Dir[] dirs = Dir.values();
        shuffle(dirs);
        for (Dir dir : dirs) {
            int nextX = dir.moveX(i, 2);
            int nextY = dir.moveY(j, 2);
            if (isValid(grid, nextX, nextY)) {
                grid[dir.moveX(i, 1)][dir.moveY(j, 1)] = 0;
                grid[nextX][nextY] = 0;
                DFS(grid, nextX, nextY);
                grid[dir.moveX(i, 1)][dir.moveY(j, 1)] = 1;
                grid[nextX][nextY] = 1;
            }
        }
    }
    private void shuffle(Dir[] dirs) {
        for (int i = 0; i < dirs.length; i++) {
            int rand = (int)(Math.random() * (dirs.length - i));
            Dir tmp = dirs[i];
            dirs[i] = dirs[i + rand];
            dirs[i + rand] = tmp;
        }
    }
    private boolean isValid(int[][] grid, int i, int j) {
        return i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && grid[i][j] == 1;
    }
}
