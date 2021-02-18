package LinkedIn.IDI2.DataStructure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class StreamProcessing {
    /**
     * return current time in microseconds
     */
    private static long getCurrentTime() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

    // Messages are <key, value> pairs
    private static class Msg
    {
        long m_key;  // unique
        long m_val;
    };

    private static class LinkedNode {
        long createTimeInMicrosecs;
        Msg msg;
        LinkedNode prev;
        LinkedNode next;

        LinkedNode(long createTimeInMicrosecs, Msg msg, LinkedNode prev, LinkedNode next) {
            this.createTimeInMicrosecs = createTimeInMicrosecs;
            this.msg = msg;
            this.prev = prev;
            this.next = next;
        }
    }

    private static class DoubleLinkedList {
        LinkedNode head;
        LinkedNode tail;
        DoubleLinkedList(LinkedNode head, LinkedNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    // This is the sliding window that needs to be implemented.
    class Window
    {
        int timeWindowInMicrosecs;
        ConcurrentHashMap<Long, LinkedNode> latestValue;
        final DoubleLinkedList doubleLinkedList;

        // Use double since we need to calculate avg and avoid overflow
        double valueSum = 0.0;
        int msgCount = 0;

        /**
         * the RW lock is used to protect concurrent updates on the running stats
         */
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        Thread cleanupBackgroundThread;

        // Define a window of a certain size in microsecond granularity
        Window(int nMicrosecs) {
            this.timeWindowInMicrosecs = nMicrosecs;
            this.latestValue = new ConcurrentHashMap<>();
            this.doubleLinkedList = new DoubleLinkedList(null, null);
            this.cleanupBackgroundThread = new Thread(new ExpiredMessageCleanUpService(timeWindowInMicrosecs, doubleLinkedList, latestValue));
            this.cleanupBackgroundThread.start();
        }

        public void close() {
            this.cleanupBackgroundThread.interrupt();
        }

        // add a new incoming message
        private void addMsg(Msg m) {
            long createTimeInMicrosecs = getCurrentTime();
            LinkedNode newNode = new LinkedNode(createTimeInMicrosecs, m, null, null);

            /**
             * Acquired write lock to lock both the linked list and the concurrent hashmap; otherwise, there can
             * be race condition because multiple threads are calling addMsg at the same time:
             * Assuming A= data structure A, B= data structure B
             * In thread 1: A: k1 = v1, B: k1 = v1
             * In thread 2: A: k1 = v2, B: k1 = v2
             *
             * Without lock protection, execution order can be:
             * A: k1 = v1, A: k1 = v2, B: k1 = v2, B: k1 = v1
             * The end view is: A: k1 = v2, B: k1 = v1 (race condition)
             *
             * With protection, we can guarantee either thread 1 finishes first, then thread 2 finishes; or thread 2 -> thread 1,
             * so the end view is either (A: k1 = v2, B: k1 = v2) or (A: k1 = v1, B: k1 = v1)
             */
            // Add node to queue
            if (doubleLinkedList.tail == null) {
                // empty list, lock the entire queue
                synchronized (doubleLinkedList) {
                    /**
                     * Must update the hashmap first; otherwise there will be race condition:
                     * if the linkedlist is updated first, the tail will refer to a different object, which
                     * allows other to hold the lock and enter critical section
                     *
                     * In another word, always update the tail reference at the end
                     */
                    latestValue.put(m.m_key, newNode);

                    doubleLinkedList.head = newNode;
                    doubleLinkedList.tail = newNode;
                }
            } else {
                // add to tail
                synchronized (doubleLinkedList.tail) {
                    latestValue.put(m.m_key, newNode);

                    newNode.prev = doubleLinkedList.tail;
                    doubleLinkedList.tail.next = newNode;
                    doubleLinkedList.tail = newNode;
                }
            }

            rwLock.writeLock().lock();
            try {
                // update the running stats
                valueSum += m.m_val;
                msgCount++;
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        // get a message given a key. If the message does not exist or message is older than the window size, return NULL
        // it's *important* to be correct
        private Msg getMsg(long key) {
            LinkedNode node = latestValue.get(key);
            if (node == null) {
                return null;
            } else {
                return isExpiredMsg(node.createTimeInMicrosecs, this.timeWindowInMicrosecs) ? null : node.msg;
            }
        }

        // get the average of message values in the window. Like getMsg, it's important to be correct.
        private Double getAvg() {
            rwLock.readLock().lock();
            try {
                return msgCount == 0 ? 0 : valueSum/msgCount;
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    private static class ExpiredMessageCleanUpService implements Runnable {
        long TTLinMicrosecs;
        DoubleLinkedList linkedList;
        ConcurrentHashMap latestValue;

        ExpiredMessageCleanUpService(long TTLinMicrosecs, DoubleLinkedList linkedList, ConcurrentHashMap latestValue) {
            this.TTLinMicrosecs = TTLinMicrosecs;
            this.linkedList = linkedList;
            this.latestValue = latestValue;
        }

        @Override
        public void run() {
            while (true) {
                if (linkedList.tail == null) {
                    sleep(TTLinMicrosecs);
                } else {
                    if (!isExpiredMsg(linkedList.head.createTimeInMicrosecs, TTLinMicrosecs)) {
                        /**
                         * (linkedList.head.createTimeInMicrosecs + TTLinMicrosecs) is the time when the first msg expires in future
                         * Instead of busy polling, we can simply wait until the time the first msg expires
                         */
                        sleep(linkedList.head.createTimeInMicrosecs + TTLinMicrosecs - getCurrentTime());
                    }
                }

                while (linkedList.head != null) {
                    if (!isExpiredMsg(linkedList.head.createTimeInMicrosecs, TTLinMicrosecs)) {
                        // This round of clean up is completed
                        break;
                    }

                    if (linkedList.head != linkedList.tail) {
                        /**
                         * No need to lock in this case; only the cleaning background service will touch the head
                         */
                        /**
                         * No need to clean up hashtable if we are removing the last value of the specific key, because
                         * we have defensive code in getMsg() where we check the creation time of the msg
                         */
                        LinkedNode newHead = linkedList.head.next;
                        newHead.prev = null;
                        linkedList.head = newHead;
                    } else {
                        /**
                         * There is only one element in the queue, lock the clean up operation since addMsg is also locking it
                         */
                        synchronized (linkedList.tail) {
                            linkedList.head = null;
                            linkedList.tail = null;
                        }
                    }
                }
            }
        }

        private static void sleep(long sleepTimeInMicrosecs) {
            try {
                Thread.sleep(TimeUnit.MICROSECONDS.toMillis(sleepTimeInMicrosecs));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isExpiredMsg(long createTimeInMicrosecs, long TTLinMicrosecs) {
        return getCurrentTime() - createTimeInMicrosecs > TTLinMicrosecs;
    }
}
