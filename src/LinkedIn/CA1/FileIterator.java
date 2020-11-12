package LinkedIn.CA1;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Follow-up: can you safely remove()
class FilterIterator<T> implements Iterator<T> {
    Predicate<T> selector;
    Iterator<T> source;
    T next = null;

    FilterIterator (Predicate<T> selector, Iterator<T> source) {
        this.selector = selector;
        this.source = source;
    }

    /** A call to next() would not throw a NoSuchElementException. */
    public boolean hasNext() {
        while (source.hasNext()) {
            T next = source.next();
            if (selector.apply(next)) {
                this.next = next;
                return true;
            }
        }
        return false;
    }

    /** Returns the next element in the underlying iterator that passes this FilterIterator's Predicate
     * @throws NoSuchElementException if there are no more elements passing the Predicate available
     */
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.next;
    }
}

/* This is a supporting interface, it does not need to be implemented */
interface Predicate<T>
{
    /** @return true if the given T passes this predicate, and false otherwise */
    boolean apply(T sample);
}