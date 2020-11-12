package LinkedIn.CA1;

import java.util.ArrayList;
import java.util.List;

// must be O(n) solution
public class Influencer {
    public int findInfluencer(boolean[][] followingMatrix) {
        int candidate = 0;
        for (int i = 1; i < followingMatrix.length; i++) {
            // if candidate don't know i, means i can't be candidate either
            // but candidate knows i, i can be possible candidate
            if (followingMatrix[candidate][i]) {
                candidate = i;
            }
        }
        String s = new String("a");
        s.substring(0, 1);
        List<String> res = new ArrayList<>();
        // but we still need to verify candidate, because we only check followers bigger than itself
        for (int j = 0; j < followingMatrix.length; j++) {
            if (candidate != j && (followingMatrix[candidate][j] || !followingMatrix[j][candidate])) {
                return -1;
            }
        }
        return candidate;
    }

}
