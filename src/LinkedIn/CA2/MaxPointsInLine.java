package LinkedIn.CA2;

public class MaxPointsInLine {
    
    // 辗转相除法求最大公约数
    int gcd(int x, int y) {
        int m = x % y;
        if (m == 0) {
            return x;
        }
        return gcd(y, m);
    }
}
