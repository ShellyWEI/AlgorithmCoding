package LinkedIn.CA1;

import java.util.Arrays;

public class MultiDimArray {
    public int multiDimSum(MultidimentionSum m, int[] dimensions) {
        int curDimIndex = dimensions.length - 1;
        int[] curIndice = new int[dimensions.length];
        Arrays.fill(curIndice, 0);
        int sum = 0;
        while (curDimIndex >= 0) {
            //curDimIndexValue += 1;
            // reach cur dimension's max value ==> 0,0,6 to be 0,1,0
            if (curIndice[curDimIndex] >= dimensions[curDimIndex]) {
                curIndice[curDimIndex] = 0;
                curDimIndex--;
            } else {
                sum += m.get(curIndice);
                curIndice[curDimIndex]++;
            }
        }
        return sum;
    }
    // 2D, side length = 4; (0,0),(1,1),(2,2),(3,3)
    // 3D, side length = 4:
    // (0,0,0)...(3,3,3)
    // (0,0,3), (1,1,2), (2,2,1), (3,3 0);
    // (0,3,0)...(3,0,3);
    // (3,0,0)...(0,3,3)
    // 4D: 2^(k - 1) = 8
    // 0,0,0,0(0) 0,0,0,3(1) 0,0,3,0(2) 0,0,3,3(3) 0,3,0,0(4) 0,3,0,3(5) 0,3,3,0(6) 0,3,3,3(7)
    // 3,3,3,3(15)3,3,3,0(14)3,3,0,3(15)3,3,0,0(12)3,0,3,3(13)3,0,3,0(10)3,0,0,3(11)3,0,0,0(8)
    public int multiDiagSum(MultidimentionSum m, int sideLength, int dimension) {
        int[] indices = new int[dimension];
        int numOFDiag = (int)Math.pow(2, dimension - 1);
        int sum = 0;
        for (int i = 0; i < numOFDiag; i++) {
            // initial increment and indices array
            int index = i;
            Arrays.fill(indices, 0);
            int[] increment = new int[dimension];
            for (int count = dimension - 1; count >= 0; count--) {
                if ((index & 1) == 0) {
                    increment[count] = 1;
                } else {
                    indices[count] = sideLength - 1;
                    increment[count] = -1;
                }
                index >>= 1;
            }
            // add sum
            for (int j = 0; j < dimension; j++) {
                sum += m.get(indices);
                addArray(indices, increment);
            }
        }
        return sum;
    }
    private void addArray(int[] indices, int[] helper) {
        for (int i = 0; i < indices.length; i++) {
            indices[i] += helper[i];
        }
    }
}
