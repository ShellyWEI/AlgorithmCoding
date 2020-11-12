package Lyft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IntersectionIterator implements Iterator<Integer> {
    Iterator<Integer> it1;
    Iterator<Integer> it2;
    Iterator<Integer> common;
    public IntersectionIterator(Iterator<Integer> it1, Iterator<Integer> it2) {
        this.it1 = it1;
        this.it2 = it2;
        List<Integer> commonEle = new ArrayList<>();
        Integer cur1 = it1.next();
        Integer cur2 = it2.next();
        while (cur1 != null && cur2 != null) {
            if (cur1.equals(cur2)) {
                commonEle.add(cur1);
                cur1 = it1.hasNext() ? it1.next() : null;
                cur2 = it2.hasNext() ? it2.next() : null;
            } else if (cur1 < cur2) {
                cur1 = it1.hasNext() ? it1.next() : null;
            } else {
                cur2 = it2.hasNext() ? it2.next() : null;
            }
        }
        this.common = commonEle.iterator();
    }

    /**
     * Returns the next element in the iteration (common element in the two iterators).
     */
    public boolean hasNext() {
        return common.hasNext();
    }

    /**
     * Returns true if the iteration has more elements (common elements in the two interators).
     */
    public Integer next() {
        return common.next();
    }

    public static void main(String[] args) {
        Integer[] arr1 = new Integer[]{1, 2, 3, 4};
        Integer[] arr2 = new Integer[]{1, 2, 3, 4};
        IntersectionIterator it = new IntersectionIterator(Arrays.asList(arr1).iterator(), Arrays.asList(arr2).iterator());
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); // 1
        System.out.println(it.next()); // 2
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); // 3
        System.out.println(it.next()); // 4
        System.out.println(it.hasNext()); // false
    }
}
