public class MaxRectangleSurroundedX {
    public int largest(boolean[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int result = 0;
        int M = grid.length;
        int N = grid[0].length;
        int[][] vertical = new int[M + 1][N + 1]; // record longest left side ending at each position in the grid
        int[][] horizontal = new int[M + 1][N + 1]; // record up, up[i][j] is actually mapped to grid[i - 1][j - 1]
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j]) {
                    vertical[i + 1][j + 1] = vertical[i + 1][j] + 1;
                    horizontal[i + 1][j + 1] = horizontal[i][j + 1] + 1;
                    // single line can't comprise rectangle
                    // for horizontal grid, right bottom value needs to smaller or equal to right up value => ensure bottom and up line
                    // for vertical grid, right bottom value needs to smaller or equal to left bottom value => ensure left and right line
                    int verticalSide = vertical[i + 1][j + 1];
                    int horizontalSide = horizontal[i + 1][j + 1];
                    if (verticalSide > 1 && vertical[i + 1][j + 2 - verticalSide] >= verticalSide
                        && horizontalSide > 1 && horizontal[i + 2 - horizontalSide][j + 1] >= horizontalSide) {
                        result = Math.max(result, (verticalSide + horizontalSide - 1) * 2 - 2 );
                    }
                }
            }
        }
        return result;
    }
}
