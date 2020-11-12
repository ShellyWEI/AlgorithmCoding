package LinkedIn.CA1;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// key to this problem is not looking and comparing past, but dealt with future messages
public class MessageBurstProcessor {
    Long quietPeriod;
    private volatile Object currentPending;
    public MessageBurstProcessor(long quietPeriod) {
        this.quietPeriod = quietPeriod;
        currentPending = null;
    }
    // Return true if message is identified as last message in a burst
    // this method has a problem:
    // each process() will sleep at least a quiet period to , and this is not good if other messages comes immediately.
    public boolean process(Object message) throws Exception {
        currentPending = message;
        Thread.sleep(quietPeriod);
        return currentPending == message;
    }
    private Lock lock = new ReentrantLock();
    private Condition curBit = lock.newCondition();
    public boolean processLock(Object message) throws Exception {
        try {
            lock.lock();
            curBit.signalAll();
            return curBit.await(quietPeriod, TimeUnit.MILLISECONDS);
        } finally {
            lock.unlock();
        }
    }
    // identify if current message is Nth last bit
    List<Condition> activeNBit;
    public MessageBurstProcessor(long quietPeriod, int N) {
        this.quietPeriod = quietPeriod;
        for (int i = 0; i < N; i++) {
            activeNBit.add(lock.newCondition());
        }
    }
    public boolean processN(Object message) throws Exception {
        try {
            lock.lock();
            for (Condition c : activeNBit) {
                c.signal();
                if (!c.await(quietPeriod, TimeUnit.MILLISECONDS)) {
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
