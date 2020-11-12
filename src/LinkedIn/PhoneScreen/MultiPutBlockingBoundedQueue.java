package LinkedIn.PhoneScreen;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface MultiPutBlockingQueue {
    public void init(int capacity) throws Exception;
    public Object get() throws Exception;
    public void put(Object obj) throws Exception;
    public void multiput(List objs) throws Exception;
}

// expected to be used in a Producer-Consumer pattern
/*Follow-up:
* talking about different locking strategies and how to improve concurrency*/
public class MultiPutBlockingBoundedQueue implements MultiPutBlockingQueue{
    private int capacity;
    private Queue<Object> queue;
    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;
    private boolean isInit;

    /* if called more than once or if the passed capacity <= 0, throws an exception*/
    @Override
    public void init (int capacity) throws Exception {
        if (isInit) {
            throw new Exception("Already init");
        }

        queue = new LinkedList<>();
        if (capacity <= 0) {
            throw new Exception("Capacity <= 0");
        }
        this.capacity = capacity;
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
        isInit = true;
    }

    /* get the next item from the queue, throw exception if not initialized*/
    @Override
    public Object get() throws Exception {
//        if (queue == null) {
//            throw new NullPointerException();
//        }
        if (!isInit) {
            throw new NullPointerException();
        }
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.awaitUninterruptibly();
            }
            Object item = queue.poll();
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    /*put the item to the tail of the queue*/
    @Override
    public void put(Object obj) throws Exception {
        if (queue == null) {
            throw new NullPointerException();
        }
        lock.lock();
        try {
            while (queue.size() >= capacity) {
                notFull.awaitUninterruptibly();
            }
            queue.offer(obj);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /* put the list of items in sequence without mixing the items from other put or multiput calls.
    * The list can be more than the capacity */
    @Override
    public void multiput(List objs) throws Exception {
        lock.lock();
        try {
            for (Object obj : objs) {
                while (queue.size() >= capacity) {
                    notFull.awaitUninterruptibly();
                }
                queue.offer(obj);
                notEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}