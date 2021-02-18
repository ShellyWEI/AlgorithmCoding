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
        lock.lock();
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
                    System.out.println("H signal all H");
                    provideH.signalAll();
                    System.out.println("H end signal all H");
                    System.out.println("H signal one O");
                    provideO.signal();
                    System.out.println("H end signal one O");
                } else {
                    System.out.println("H start waiting");
                    provideH.await();
                    System.out.println("H end waiting");
                }
            }
            // my is escaping from loop, need to cut my count from combined,
            // so that I can let only one thread to escape from waiting in loop
            combinedH--;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


//        // ------------------
//        try {
//            lock.lock();
//            freeH++;
//            if (freeH >= 2 && freeO >= 1) {
//                freeH--;
//                provideH.signal();
//                provideO.signal();
//            } else {
//                System.out.println("H start waiting");
//                provideH.await();
//                freeH--;
//                System.out.println("H end waiting");
//            }
//        } catch (Exception e) {
//            // what do you want me to do, professor?
//        } finally {
//            lock.unlock();
//        }
    }

    public static void oxygen(String threadName) {
        lock.lock();
        try {
            freeO++;
            if (freeO >= 1 && freeH >= 2) {
                freeH -= 2;
                freeO -= 1;
                combinedH += 2;
                System.out.println("O signal all H");
                provideH.signalAll();
                System.out.println("O end signal all H");
                System.out.println("O signal one O");
                provideO.signal();
                System.out.println("O end signal one O");
            } else {
                System.out.println("O start waiting");
                provideO.await();
                System.out.println("O end waiting");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


//        // ------------------
//        try {
//            lock.lock();
//            freeO++;
//            if (freeH >= 2 && freeO >= 1) {
//                freeO--;
//                provideH.signal();
//                provideH.signal();
//            } else {
//                provideO.await();
//                freeO--;
//            }
//        } catch (Exception e) {
//            // what do you want me to do, professor?
//        } finally {
//            lock.unlock();
//        }
    }
    public static void main(String[] args) {
//        for (int j = 0; j < 5; j++) {
//            Thread tO = new Thread("" + j) {
//                @Override
//                public void run() {
//                    oxygen(getName());
//                }
//            };
//            tO.start();
//        }
        for (int i = 0; i < 3; i++) {
            Thread tH = new Thread("" + i) {
                @Override
                public void run() {
                    hydrogen(getName());
                }
            };
            tH.start();
        }
        for (int j = 0; j < 1; j++) {
            Thread tO = new Thread("" + j) {
                @Override
                public void run() {
                    oxygen(getName());
                }
            };
            tO.start();
        }
        try {
            Thread.sleep(5000);
        } catch ( Exception e) {

        }
        System.out.println("Combined H is still bigger than 0: " + combinedH);
        while (combinedH > 0) {
            try {
                Thread.sleep(5000);
                System.out.println("Combined H is still bigger than 0: " + combinedH);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
