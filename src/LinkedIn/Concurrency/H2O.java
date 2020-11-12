package LinkedIn.Concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class H2O {
    private static int freeH;
    private static int combinedH;
    private static int freeO;
    private static Lock lock = new ReentrantLock();
    private static Condition provideH = lock.newCondition();
    private static Condition provideO = lock.newCondition();

    public static void hydrogen(String threadName) {
        System.out.println("Thread H " + threadName + " trying to get lock ");
        lock.lock();
        System.out.println("Thread H " + threadName + " get lock ");
        try {
            freeH++;
            // multiple thread provide free H, but no one is combined
            // once combined will only be 2 thread finished with run;
            // free O don't need to worry about freeing one more thread
            while (combinedH == 0) {
                if (freeH >= 2 && freeO >= 1) {
                    freeH -= 2;
                    freeO -= 1;
                    combinedH += 2;
                    provideH.signalAll();
                    provideO.signal();
                } else {
                    System.out.println("Thread H " + threadName + " await ");
                    provideH.await();
                    System.out.println("Thread H " + threadName + " continue form await ");
                }
            }
            // my is escaping from loop, need to cut my count from combined,
            // so that I can let only one thread to escape from waiting in loop
            combinedH--;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("Thread H " + threadName + " release lock ");
        }
    }
    public static void oxygen(String threadName) {
        System.out.println("Thread O " + threadName + " trying to get lock ");
        lock.lock();
        System.out.println("Thread O " + threadName + " get lock ");
        try {
            freeO++;
            if (freeO >= 1 && freeH >= 2) {
                freeH -= 2;
                freeO -= 1;
                combinedH += 2;
                provideH.signalAll();
                provideO.signal();
            } else {
                System.out.println("Thread O " + threadName + " await ");
                provideO.await();
                System.out.println("Thread O " + threadName + " continue form await ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("Thread O " + threadName + " release lock ");
        }
    }
    public static void main(String[] args) {
        for (int j = 0; j < 5; j++) {
            Thread tO = new Thread("" + j) {
                @Override
                public void run() {
                    oxygen(getName());
                }
            };
            tO.start();
        }
        for (int i = 0; i < 5; i++) {
            Thread tH = new Thread("" + i) {
                @Override
                public void run() {
                    hydrogen(getName());
                }
            };
            tH.start();
        }
    }
}
