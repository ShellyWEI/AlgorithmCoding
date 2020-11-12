package Laioffer;

public class LongestCross1 {
    public int largest(int[][] matrix) {
        // Write your solution here
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] left2Right = new int[m][n];
        int[][] right2Left = new int[m][n];
        int[][] up2Bottom = new int[m][n];
        int[][] bottom2Up = new int[m][n];
        buildLeftUp(matrix, left2Right, up2Bottom);
        buildRightBottom(matrix, right2Left, bottom2Up);
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    int min = Math.min(Math.min(left2Right[i][j], right2Left[i][j]), Math.min(bottom2Up[i][j], up2Bottom[i][j]));
                    max = Math.max(max, min);
                }
            }
        }
        return max;
    }
    private void buildLeftUp(int[][] matrix, int[][] left2Right, int[][] up2Bottom) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == 0 && j == 0) {
                    up2Bottom[i][j] = matrix[i][j];
                    left2Right[i][j] = matrix[i][j];
                } else if (i == 0) {
                    up2Bottom[i][j] = matrix[i][j];
                    left2Right[i][j] = matrix[i][j] == 0 ? 0 : left2Right[i][j - 1] + 1;
                } else if (j == 0) {
                    left2Right[i][j] = matrix[i][j];
                    up2Bottom[i][j] = matrix[i][j] == 0 ? 0 : up2Bottom[i - 1][j] + 1;
                } else {
                    if (matrix[i][j] == 1) {
                        left2Right[i][j] = left2Right[i][j - 1] + 1;
                        up2Bottom[i][j] = up2Bottom[i - 1][j] + 1;
                    }
                }
            }
        }
    }
    private void buildRightBottom(int[][] matrix, int[][] right2Left, int[][] bottom2Up) {
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                if (i == matrix.length - 1 && j == matrix[0].length - 1) {
                    bottom2Up[i][j] = matrix[i][j];
                    right2Left[i][j] = matrix[i][j];
                } else if (i == matrix.length - 1) {
                    bottom2Up[i][j] = matrix[i][j];
                    right2Left[i][j] = matrix[i][j] == 0 ? 0 : right2Left[i][j + 1] + 1;
                } else if (j == matrix[0].length - 1) {
                    right2Left[i][j] = matrix[i][j];
                    bottom2Up[i][j] = matrix[i][j] == 0 ? 0 : bottom2Up[i + 1][j] + 1;
                } else {
                    if (matrix[i][j] == 1) {
                        right2Left[i][j] = right2Left[i][j + 1] + 1;
                        bottom2Up[i][j] = bottom2Up[i + 1][j] + 1;
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        LongestCross1 test = new LongestCross1();
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
