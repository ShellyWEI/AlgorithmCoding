package LinkedIn.PhoneScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

interface SortedIterator<E extends Comparable<E>> extends Iterator<E> {
    E next();
    boolean hasNext();
}
// follow up:
// 1. query running median and
// 2. query running mean and mode
public class StreamMerge {
    static <E extends  Comparable<E>> SortedIterator<E> merge(List<SortedIterator<E>> iterators) {
        List<Integer> res = new LinkedList<>();
        List[] charIndex = new ArrayList[4];
        return null;
    }

}
