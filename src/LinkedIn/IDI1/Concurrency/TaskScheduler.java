package LinkedIn.IDI1.Concurrency;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface DelayedScheduler {
    // non-blocking call
    void delayedRun(long timeToRun, TaskScheduler.MyRunnable task);
}

public class TaskScheduler implements DelayedScheduler {

    class TaskToRun implements Comparable<TaskToRun> {
        Long time;
        MyRunnable task;
        TaskToRun(long time, MyRunnable task) {
            this.task = task;
            this.time = time;
        }

        @Override
        public int compareTo(TaskToRun t) {
            return this.time.compareTo(t.time);
        }
    }

    private PriorityQueue<TaskToRun> minHeap;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition executeTask = lock.newCondition();

    BackgroudTask backgroundTask;

    public void delayedRun(long timeToRun, MyRunnable task) {
        lock.lock();
        try {
            if (minHeap.isEmpty() || timeToRun < minHeap.peek().time) {
                minHeap.offer(new TaskToRun(timeToRun, task));
                executeTask.signal();
            } else {
                minHeap.offer(new TaskToRun(timeToRun, task));
                notEmpty.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static class BackgroudTask implements Runnable {
        private boolean isRunning = true;
        private Lock lock;
        private PriorityQueue<TaskToRun> minHeap;
        private Condition executeTask;

        public BackgroudTask(Lock lock, PriorityQueue<TaskToRun> minHeap, Condition executeTask) {
            this.lock = lock;
            this.minHeap = minHeap;
            this.executeTask = executeTask;
        }
        public void stop() {
            isRunning = false;
        }

        @Override
        public void run() {
            while (isRunning) {
                lock.lock();
                try {
                    while (minHeap.isEmpty()) {
                        System.out.println("heap is empty, await...");
                        executeTask.await();
                        if (!isRunning) {
                            System.out.println("isRunning is false");
                            return;
                        }
                    }
                    TaskToRun earliestTask = minHeap.peek();
                    System.out.println("peek current task scheduled " + earliestTask.task.getTaskNumber());
                    long curTime = System.currentTimeMillis();
                    long diff = earliestTask.time - curTime;
                    if (diff > 0) {
                        // new task signal current await, proceed to get new task
                        System.out.println("await to execute task " + earliestTask.task.getTaskNumber());
                        if (executeTask.await(diff, TimeUnit.MILLISECONDS)) {
                            System.out.println("Awake by another task");
                            continue;
                        }
                        System.out.println("waited enough time " + diff);
                    }
                    minHeap.poll();
                    earliestTask.task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    TaskScheduler() {
        minHeap = new PriorityQueue<>();
        backgroundTask = new BackgroudTask(lock, minHeap, executeTask);
        Thread background = new Thread(backgroundTask);
        background.start();
    }

    public static void main(String[] args) {
        TaskScheduler test = new TaskScheduler();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        test.delayedRun(System.currentTimeMillis() + 3000, new MyRunnable(1));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        test.delayedRun(System.currentTimeMillis() + 1000, new MyRunnable(2));
        test.delayedRun(System.currentTimeMillis() + 1000, new MyRunnable(3));

        // wait until all task finishes
        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
        test.backgroundTask.stop();
        test.lock.lock();
        test.executeTask.signalAll();
        test.lock.unlock();
    }

    public static class MyRunnable implements Runnable {
        private int taskNumber;
        MyRunnable (int number) {
            this.taskNumber = number;
        }
        @Override
        public void run() {
            System.out.println("Task " + taskNumber + " is running");
        }
        public int getTaskNumber() {
            return this.taskNumber;
        }
    }
}
