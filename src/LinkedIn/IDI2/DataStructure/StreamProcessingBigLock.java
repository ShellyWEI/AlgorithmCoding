package LinkedIn.IDI2.DataStructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StreamProcessingBigLock {
    /**
     * return current time in microseconds
     */
    private static long getCurrentTime() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

    // Messages are <key, value> pairs
    private static class Msg {
        long m_key;  // unique
        long m_val;
    };

    private static class LinkedNode {
        long createTimeInMicrosecs;
        Msg msg;

        LinkedNode(long createTimeInMicrosecs, Msg msg) {
            this.createTimeInMicrosecs = createTimeInMicrosecs;
            this.msg = msg;
        }
    }

    // This is the sliding window that needs to be implemented.
    class WindowAvg {
        int timeWindowInMicrosecs;
        ConcurrentHashMap<Long,  LinkedNode> latestValue;
        ConcurrentLinkedQueue<LinkedNode> queue;

        // Use double since we need to calculate avg and avoid overflow
        double valueSum = 0.0;
        int msgCount = 0;

        /**
         * the RW lock is used to protect concurrent updates on all shared objects
         */
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        Thread cleanupBackgroundThread;

        // Define a window of a certain size in microsecond granularity
        WindowAvg(int nMicrosecs) {
            this.timeWindowInMicrosecs = nMicrosecs;
            this.latestValue = new ConcurrentHashMap<>();
            this.queue = new ConcurrentLinkedQueue<>();
            this.cleanupBackgroundThread = new Thread(new ExpiredMessageCleanUpService(timeWindowInMicrosecs, queue, latestValue));
            this.cleanupBackgroundThread.start();
        }

        public void close() {
            this.cleanupBackgroundThread.interrupt();
        }

        // add a new incoming message
        private void addMsg( Msg m) {
            long createTimeInMicrosecs = getCurrentTime();
            LinkedNode newNode = new LinkedNode(createTimeInMicrosecs, m);

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
            rwLock.writeLock().lock();
            try {
                latestValue.put(m.m_key, newNode);
                queue.offer(newNode);
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
            /**
             * I don't think we need read lock here; ConcurrentHashMap is already thread safe, there is no race condition
             * in the addMsg() function when multiple threads call addMsg(), so the value read here is correct.
             */
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
        ConcurrentLinkedQueue<LinkedNode> linkedList;
        ConcurrentHashMap latestValue;

        ExpiredMessageCleanUpService(long TTLinMicrosecs, ConcurrentLinkedQueue<LinkedNode> linkedList, ConcurrentHashMap latestValue) {
            this.TTLinMicrosecs = TTLinMicrosecs;
            this.linkedList = linkedList;
            this.latestValue = latestValue;
        }

        @Override
        public void run() {
            while (true) {
                if (linkedList.isEmpty()) {
                    sleep(TTLinMicrosecs);
                } else {
                    LinkedNode oldestMsg = linkedList.peek();
                    if (!isExpiredMsg(oldestMsg.createTimeInMicrosecs, TTLinMicrosecs)) {
                        /**
                         * (linkedList.head.createTimeInMicrosecs + TTLinMicrosecs) is the time when the first msg expires in future
                         * Instead of busy polling, we can simply wait until the time the first msg expires
                         */
                        sleep(oldestMsg.createTimeInMicrosecs + TTLinMicrosecs - getCurrentTime());
                    }
                }

                while (!linkedList.isEmpty()) {
                    if (!isExpiredMsg(linkedList.peek().createTimeInMicrosecs, TTLinMicrosecs)) {
                        // This round of clean up is completed
                        break;
                    }
                    // clean up the oldest message
                    linkedList.poll();
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
