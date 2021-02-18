package LinkedIn.CA2;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

// i <= k, when k+1 comes, k/(k+1) * 1/k = 1/(k+1) ==> k+1 replace i, so k/(k+1) ==> i will not replace by k+1;
//          k+2        k/(k+2) * 1/k = 1/(k+2) ==> k+2 replace i, so (k+1)/(k+2) ==> i will not replace by k+2;
//          n ==> i will not replace by n is (n-1)/n; k/(k+1) * (k+1)/(k+2) * ... * (n-1)/n = k/n
// i > K, when i comes, k/i means i will remain;
//            i+1 , k/(i+1) * 1/k = 1/(i+1) means i will be replaced by i+1; so i/(i+1) means i will not replaced by i+1;
//            n, k/n * 1/k = 1/n ==> i will be replaced by n; (n-1)/n is i will remain when n comes;
//           k/i * i/(i+!) * ... * (n-1)/n = k/n
public class ResevoirSampling {
    int index;
    int K;
    int[] samplePool;
    public ResevoirSampling(int K) {
        this.index = 0;
        this.K = K;
        samplePool = new int[K];
    }
    public void sample(Integer next) {
        if (index < K) {
            samplePool[index] = next;
        } else {
            Random r = new Random();
            int p = r.nextInt(index + 1);
            if (p < K) {
                samplePool[p] = next;
            }
        }
        index++;
    }

}
