package Amazon.Onsite;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> spiralTraverse(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int min = Math.min(m / 2, n / 2);
        int offset = 0;
        for (; offset <= min; offset++) {
            int hEnd = n - offset - 1; // 2
            int vEnd = m - offset - 1; // 1
            if (hEnd == offset) {
                for (int i = offset; i < m - offset; i++) {
                    res.add(matrix[i][offset]);
                }
            } else if (vEnd == offset) {
                for (int i = offset; i < n - offset; i++) {
                    res.add(matrix[offset][i]);
                }
            } else if (offset < hEnd && offset < vEnd){
                // upper
                for (int i = offset; i < hEnd; i++) {
                    res.add(matrix[offset][i]);
                }
                // right
                for (int i = offset; i < vEnd; i++) {
                    res.add(matrix[i][hEnd]);
                }
                // bottom
                for (int i = hEnd; i > offset; i--) {
                    res.add(matrix[vEnd][i]);
                }
                // left
                for (int i = vEnd; i > offset; i--) {
                    res.add(matrix[i][offset]);
                }
            }
        }

        return res;
    }
}
