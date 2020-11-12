package LinkedIn.CA1;

import java.util.*;
// a, b ascending sorted list
public class IntersectionAndUnion {
    public List<Integer> union(List<Integer> a, List<Integer> b) {
       Iterator<Integer> aItr = a.iterator();
       Iterator<Integer> bItr = b.iterator();
       Integer aNext = aItr.next();
       Integer bNext = bItr.next();
       List<Integer> res = new ArrayList<>();
       while (aNext != null || bNext != null) {
           if (aNext != null && (bNext == null || aNext <= bNext)) {
               res.add(aNext);
               aNext = aItr.hasNext() ? aItr.next() : null;
           } else {
               res.add(bNext);
               bNext = bItr.hasNext() ? bItr.next() : null;
           }
       }
       return res;
    }

    public List<Integer> intersect(List<Integer> a, List<Integer> b) {
        Iterator<Integer> aItr = a.iterator();
        Iterator<Integer> bItr = b.iterator();
        Integer aNext = aItr.next();
        Integer bNext = bItr.next();
        List<Integer> res = new ArrayList<>();
        while (aNext != null && bNext != null) {
            if (aNext < bNext) {
                aNext = aItr.hasNext() ? aItr.next() : null;
            } else if (aNext > bNext) {
                bNext = bItr.next();
            } else {
                res.add(aNext);
                aNext = aItr.hasNext() ? aItr.next() : null;
                bNext = bItr.hasNext() ? bItr.next() : null;
            }
        }
        return res;
    }
}
