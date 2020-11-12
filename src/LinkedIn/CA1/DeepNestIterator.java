package LinkedIn.CA1;

import java.util.*;

// Follow-up:
// use both recursive and stack-based way;
// implement remove();
public class DeepNestIterator<T> implements Iterator<T> {
    Deque<Iterator<Data<T>>> deque = new LinkedList<>();
    boolean hasNext;
    T nextT;
    // Constructor: stack-based
    public DeepNestIterator(Collection<Data<T>> c) {
        if (c != null) {
            deque.offer(c.iterator());
        } else {
            throw new NullPointerException("Collection is null");
        }
    }

    public T next() {
        if (hasNext()) {
            hasNext = false; // so that each next() triggers hasNext every time
            return nextT;
        } else {
            throw new NoSuchElementException("No next Element");
        }
    }

    // multi-call hasNext() shouldn't change iterator position
    public boolean hasNext() {
        while (!hasNext) {
            if (deque.isEmpty()) {
                hasNext = false;
                break;
            }
            Iterator<Data<T>> nextItr = deque.peek();
            if (!nextItr.hasNext()) {
                deque.poll();
            } else {
                Data<T> next = nextItr.next();
                if (next.isCollection()) {
                    deque.offer(next.getCollection().iterator());
                }
                else {
                    nextT = next.getElement();
                    hasNext = true;
                }
            }
        }
        return hasNext;
    }
    // same element can't be remove() multiple times;
    public void remove() {
        if (nextT == null) {
            throw new UnsupportedOperationException("No element to remove");
        }
        nextT = next();
    }

}
// TODO: recursive way
class RecursiveDeepNestedIterator<T> implements Iterator<T> {
    private Iterator<Data<T>> itr;
    private Iterator<T> hepler;
    private T nextT;
    private boolean hasNext;

    RecursiveDeepNestedIterator(Collection<Data<T>> c) {
        if (c == null) {
            itr = null;
        } else {
            itr = c.iterator();
        }
    }

    public T next() {
        if (hasNext()) {
            hasNext = false;
            hepler = null;
            return nextT;
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean hasNext() {
        while (!hasNext) {
            // start to iterate over a sub-collection<T>
            if (hepler != null && hepler.hasNext()) {
                hasNext = true;
            } else if (itr == null || !itr.hasNext()) {
                // collection is null or reach the end of collection
                break;
            } else {
                // helper reaches the end of last data<T>
                // or reset by next() to find next T since last T has been returned
                Data<T> next = itr.next();
                if (next.isCollection()) {
                    hepler = new RecursiveDeepNestedIterator(next.getCollection());
                } else {
                    nextT = next.getElement();
                    hasNext = true;
                }
            }
        }
        return hasNext;
    }
}

interface Data<T> {
    // Does this Data hold a collection?
    boolean isCollection();
    // Returns the collection contained by this Data, or null if it is a single element
    Collection<Data<T>> getCollection();
    // Returns the single element contained by this Data, or null if it is a collection
    T getElement();
}

// TODO: CHECK RECURSIVE METHOD ON LEETCODE

