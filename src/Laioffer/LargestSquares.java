package Laioffer;

/**
 * The input is a matrix of points. Each point has one of the following values:
 0 - there is no match to its right or bottom.
 1 - there is a match to its right.
 2 - there is a match to its bottom.
 3 - there is a match to its right, and a match to its bottom.*/
public class LargestSquares {
    public int largestSquareOfMatches(int[][] matrix) {
        // Write your solution here
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] h = new int[m + 1][n + 1];
        int[][] v = new int[m + 1][n + 1];
        int max = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if ((matrix[i][j] & 1) != 0) {
                    h[i][j] = h[i][j + 1] + 1;
                }
                if ((matrix[i][j] & 0b10) != 0) {
                    v[i][j] = v[i + 1][j] + 1;
                }
                if ((matrix[i][j] & 0b11) != 0) {
                    int length = Math.min(h[i][j], v[i][j]);
                    while (length >= 1) {
                        if (h[i + length - 1][j] >= length && v[i][j + length - 1] >= length) {
                            max = Math.max(length, max);
                        }
                        length--;
                    }
                }
            }
        }
        return max;
    }
}
