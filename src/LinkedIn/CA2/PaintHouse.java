package LinkedIn.CA2;

public class PaintHouse {
    public int minCostDP(int[][] cost) {
        // override cost with updated min cost for house i with color j
        int prevMin = 0;
        int prevMinIndex = -1;
        int prevSecMin = 0;
        for (int house = 1; house < cost.length; house++) {
            int curMin = Integer.MAX_VALUE;
            int curMinIndex = -1;
            int curSecMin = Integer.MAX_VALUE;
            for (int color = 0; color < cost[0].length; color++) {
                int value = (color == prevMinIndex ? prevSecMin : prevMin) + cost[house][color];
                if (value < curMin) {
                    curSecMin = curMin;
                    curMin = value;
                    curMinIndex = color;
                } else if (value < curSecMin) {
                    curSecMin = value;
                }
            }
            prevMin = curMin;
            prevMinIndex = curMinIndex;
            prevSecMin = curSecMin;
        }
        return prevMin;
    }
}
