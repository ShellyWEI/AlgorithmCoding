package Lyft;

public class Rotate90Clockwise {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int half = n / 2;
        rotateHelper(0, half, n, matrix);
    }
    private void rotateHelper(int offset, int half, int n, int[][] matrix) {
        int curLength = n - 2 * offset - 1;
        if (curLength <= 0) {
            return;
        }
        int[] temp = new int[curLength];
        // store left, bottom -> left, right -> bottom, upper -> right, rewrite upper with stored left
        for (int j = 0; j < curLength; j++) {
            temp[j] = matrix[n - offset - 1 - j][offset];
            matrix[n - offset - 1 - j][offset] = matrix[n - offset - 1][n - 1 - offset - j];
            matrix[n - offset - 1][n - 1 - offset - j] = matrix[offset + j][n - offset - 1];
            matrix[offset + j][n - offset - 1] = matrix[offset][offset + j];
            matrix[offset][offset + j] = temp[j];
        }
        rotateHelper(offset + 1, half, n, matrix);
    }
}
