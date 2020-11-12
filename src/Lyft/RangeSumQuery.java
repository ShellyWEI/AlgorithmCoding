package Lyft;

public class RangeSumQuery {
    int[][] colSum;

    public RangeSumQuery(int[][] matrix) {
        colSum = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i > 0) {
                    colSum[i][j] = colSum[i - 1][j] + matrix[i][j];
                } else {
                    colSum[i][j] = matrix[i][j];
                }
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int i = col1; i <= col2; i++) {
            if (row1 > 0) {
                sum += (colSum[row2][i] - colSum[row1 - 1][i]);
            } else {
                sum += colSum[row2][i];
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        int[][] matrix = {
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}
        };
        RangeSumQuery obj = new RangeSumQuery(matrix);
        int param_1 = obj.sumRegion(2, 1, 4, 3);
    }
}
