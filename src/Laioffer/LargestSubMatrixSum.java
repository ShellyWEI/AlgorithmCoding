package Laioffer;

public class LargestSubMatrixSum {
    public int largest(int[][] matrix) {
        // Write your solution here
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] up2Bottom = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    up2Bottom[i][j] = matrix[i][j];
                } else {
                    up2Bottom[i][j] = up2Bottom[i - 1][j] + matrix[i][j];
                }
            }
        }
        int maxSum = up2Bottom[0][0];
        for (int upper = 0; upper < m; upper++) {
            for (int lower = upper; lower < m; lower++) {
                int curSum = 0;
                for (int col = 0; col < n; col++) {
                    int curColSum;
                    if (upper == lower) {
                        curColSum = up2Bottom[upper][col];
                    } else {
                        curColSum = up2Bottom[lower][col] - up2Bottom[upper][col];
                    }
                    if (curSum < 0) {
                        curSum = curColSum;
                    } else {
                        curSum += curColSum;
                    }
                    maxSum = Math.max(maxSum, curSum);
                }
            }
        }
        return maxSum;
    }
    public static void main(String[] args) {
        LargestSubMatrixSum test = new LargestSubMatrixSum();
        int[][] input = new int[][]{
                {-4,2,-1,0,2},{2,3,2,1,-3},{-3,-3,-2,2,4},{1,1,2,-2,5},{-4,0,1,1,-4}
        };
        test.largest(input);
    }
}
