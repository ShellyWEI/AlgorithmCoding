package LinkedIn.CA1;

public class PrintFactors {
    public static void printFactors(int n) {
        StringBuilder sb = new StringBuilder();
        DFSPrint(n, n, 2, sb);
    }
    private static void DFSPrint(int num, int rem, int fac, StringBuilder sb) {
        if (rem == 1) {
            sb.deleteCharAt(sb.length() - 1); // delete * for printing
            System.out.println(sb.toString());
            sb.append("*"); // delete last number
            return;
        }
        for (int i = fac; i <= rem; i++) {
            if (rem % i == 0) {
                sb.append(i).append("*");
                if (i == num) {
                    sb.append(1).append("*");
                }
                DFSPrint(num,rem / i, i, sb);
                sb.deleteCharAt(sb.length() - 1);
                int digit = i;
                while (digit > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                    digit /= 10;
                }
            }
        }
    }
    public static void main(String[] args) {
        printFactors(12);
    }
}
