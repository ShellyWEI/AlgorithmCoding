package Amazon.Onsite;

import java.util.*;

public class SnakeGame {
    private int width;
    private int height;
    private Deque<int[]> curPosDeque;
    private Set<Integer> curIndexSet;
    private int[][] food;
    private int foodIndex;
    private int score;
    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.curPosDeque = new LinkedList<>();
        curPosDeque.offerLast(new int[]{0,0});
        this.curIndexSet = new HashSet<>();
        curIndexSet.add(0);
        this.food = food;
        this.foodIndex = 0;
        this.score = 0;
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int[] curHead = curPosDeque.peekFirst();
        int newRow = curHead[0];
        int newCol = curHead[1];
        switch (direction) {
            case "U" :
                newRow--;
                break;
            case "D":
                newRow++;
                break;
            case "L":
                newCol--;
                break;
            case "R":
                newCol++;
                break;
        }
        // check if new position valid
        if (isValidPos(newRow, newCol)) {
            curPosDeque.offerFirst(new int[]{newRow, newCol});
            curIndexSet.add(posToIndex(newRow, newCol));
        } else {
            return -1;
        }
        // if valid, check if this position has fruit
        if (food[foodIndex][0] == newRow && food[foodIndex][1] == newCol) {
            score++;
        } else {
            // if length is not increasing, remove tail
            int[] tail = curPosDeque.pollLast();
            curIndexSet.remove(posToIndex(tail[0], tail[1]));
        }
        return score;
    }

    private int posToIndex(int row, int col) {
        return row * height + col;
    }
    private boolean isValidPos(int row, int col) {
        if (row < 0 || col < 0 || row >= height || col >= width || curIndexSet.contains(posToIndex(row, col))) {
            return false;
        }
        return true;
    }
}
