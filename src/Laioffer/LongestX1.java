package Laioffer;

public class LongestX1 {
    public int largest(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] leftUp2RightDown = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    leftUp2RightDown[i][j] = matrix[i][j];
                } else {
                    if (matrix[i][j] == 1) {
                        leftUp2RightDown[i][j] = leftUp2RightDown[i - 1][j - 1] + 1;
                    }
                }
            }
        }
        int[][] rightUp2LeftDown = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == 0 || j == n - 1) {
                    rightUp2LeftDown[i][j] = matrix[i][j];
                } else {
                    if (matrix[i][j] == 1) {
                        rightUp2LeftDown[i][j] = rightUp2LeftDown[i - 1][j + 1] + 1;
                    }
                }
            }
        }
        int[][] leftDown2rightUp = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i == m - 1 || j == 0) {
                    leftDown2rightUp[i][j] = matrix[i][j];
                } else {
                    if (matrix[i][j] == 1) {
                        leftDown2rightUp[i][j] = leftDown2rightUp[i + 1][j - 1] + 1;
                    }
                }
            }
        }
        int[][] rightDown2LeftUp = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 || j == n - 1) {
                    rightDown2LeftUp[i][j] = matrix[i][j];
                } else {
                    if (matrix[i][j] == 1) {
                        rightDown2LeftUp[i][j] = rightDown2LeftUp[i + 1][j + 1] + 1;
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    int min = Math.min(Math.min(leftDown2rightUp[i][j], leftUp2RightDown[i][j]), Math.min(rightDown2LeftUp[i][j], rightUp2LeftDown[i][j]));
                    max = Math.max(max, min);
                }
            }
        }
        return max;
    }
    public static void main(String[] args) {
        LongestX1 test = new LongestX1();
        int[][] input = new int[][]{
                {1,1,0,1,1},
                {1,0,1,0,1},
                {1,0,1,1,1},
                {1,1,0,1,0},
                {0,0,0,1,1}
        };
        test.largest(input);
    }
}
