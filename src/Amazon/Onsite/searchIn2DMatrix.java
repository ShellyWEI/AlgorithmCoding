package Amazon.Onsite;

// https://leetcode.com/problems/search-a-2d-matrix-ii/
/**
 * The tricky thing about the problem is we can't find a direction to search:
 * For example, if a value is smaller than m[i][j], it's possible to search upper and left side of [i, j],
 * so we use row sorted and column sorted feature to find a way to narrow down the direction to search,
 * starting with [0, n - 1], if smaller, only col--, if greater row++
 * or either [m - 1, 0], if smaller, row--, if greater col++;
 * The reason we can do this, is we will never be in the middle of somewhere wondering which direction i should choose,
 * if you decide to switch direction, if only have one option left, since the other option is the way you come from.
 * That's the reason we have to cross the direction to search.*/
public class searchIn2DMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int col = n - 1;
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }
}
