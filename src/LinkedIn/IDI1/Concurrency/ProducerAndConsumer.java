package LinkedIn.Concurrency;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ProducerAndConsumer {

    private static int END_OF_QUEUE = -666;

    private static class Producer implements Runnable{
        private int bufferCapacity;
        private List<Integer> dataToSent;
        private Queue<Integer> buffer;
        private volatile boolean isShutdown = false;

        public Producer(List<Integer> dataToSent, Queue<Integer> buffer, int bufferCapacity) {
            this.bufferCapacity = bufferCapacity;
            this.buffer = buffer;
            this.dataToSent = dataToSent;
        }

        @Override
        public void run() {
            for (int data : dataToSent) {
                if (isShutdown) {
                    break;
                }
                // don't produce special message
                if (data == END_OF_QUEUE) {
                    System.out.println("Skipping message: " + data + " because it's a special message in our system. Eat me");
                    continue;
                }

                synchronized (buffer) {
                    // critical session
                    if (buffer.size() == bufferCapacity) {
                        try {
                            /**
                             * Buffer is full; wait for any consumer to remove something out of the buffer
                             */
                            buffer.wait();
                        } catch (Exception e) {
                        }
                    }

                    // ok, this producer has some head room to produce data
                    buffer.offer(data);
                    buffer.notify();

                // END of critical session
                }
            }

            // send special message to stop the corresponding consumer
            synchronized (buffer) {
                // critical session
                if (buffer.size() == bufferCapacity) {
                    try {
                        /**
                         * Buffer is full; wait for any consumer to remove something out of the buffer
                         */
                        buffer.wait();
                    } catch (Exception e) {
                    }
                }

                // ok, this producer has some head room to produce data
                buffer.offer(END_OF_QUEUE);
                buffer.notify();

                // END of critical session
            }
        }

        public void shutdown() {
            isShutdown = true;
        }
    }

    private static class Consumer implements Runnable {
        private int threadId;
        private Queue<Integer> buffer;
        private volatile boolean isShutdown = false;

        public Consumer(Queue<Integer> buffer, int threadId) {
            this.threadId = threadId;
            this.buffer = buffer;
        }
        @Override
        public void run() {
            while (!isShutdown) {
                synchronized (buffer) {
                    if (buffer.size() == 0) {
                        try {
                            /**
                             * Buffer is empty; wait for any producer to remove something out of the buffer
                             */
                            buffer.wait();
                        } catch (Exception e) {
                        }
                    }

                    if (isShutdown) {
                        break;
                    }

                    // ok, consumer has something to consume now
                    Integer data = buffer.poll();
                    if (data == null) {
                        throw new RuntimeException("This shouldn't happen");
                    }
                    buffer.notify();

                    if (data == END_OF_QUEUE) {
                        System.out.println("Consume[" + threadId + "]: END_OF_QUEUE");
                        break;
                    } else {
                        // do whatever you want with the data
                        System.out.println("Consume[" + threadId + "]: " + data);
                    }

                // END of critical session
                }
            }
        }

        public void shutdown() {
            isShutdown = true;
        }
    }



    public static void main(String[] args) {
//        Queue<Integer> buffer = new ArrayDeque<>();
//        Queue<Integer> buffer1 = new ArrayDeque<>();
//        Queue<Integer> buffer2 = new ArrayDeque<>();
//        Queue<Integer> buffer3 = new ArrayDeque<>();
        int K = 10;
        int bufferSize = 3;
        List<Integer> dataToBeProduced = new ArrayList<>(K);
        for (int i = 0; i < K; i++) {
            dataToBeProduced.add(i);
        }

        // graceful shutdown one producer and one consumer
//        // create a producer to produce these shit
//        Producer producer = new Producer(dataToBeProduced, buffer, bufferSize);
//
//        // create a consumer to consume
//        Consumer consumer = new Consumer(buffer, 0);

//        Thread producerThread = new Thread(producer);
//        Thread consumerThread = new Thread(consumer);
//
//        Thread producerThread2 = new Thread(producer);
//
//        producerThread.start();
//        producerThread2.start();
//        consumerThread.start();
//        try {
//            Thread.sleep(10000);
//        } catch (Exception e) {
//
//        }
//
//        consumer.shutdown();
//        producer.shutdown();
//
//        consumerThread.interrupt();
//        producerThread.interrupt();
        // END of "graceful shutdown one producer and one consumer"


        // graceful shutdown multiple producers and multiple consumers
        int producerThreadNum = 3;
        int consumerThreadNum = 3;
        if (producerThreadNum != consumerThreadNum) {
            throw new RuntimeException("WTF?????");
        }
        Thread[] producerThreads = new Thread[producerThreadNum];
        Consumer[] consumerTasks = new Consumer[consumerThreadNum];
        Thread[] consumerThreads = new Thread[consumerThreadNum];
        Queue<Integer>[] bufferList = new Queue[producerThreadNum];

        for (int i = 0; i < producerThreadNum; i++) {
            bufferList[i] = new ArrayDeque<>();
            Producer producer = new Producer(dataToBeProduced, bufferList[i], bufferSize);
            consumerTasks[i] = new Consumer(bufferList[i], i);

            producerThreads[i] = new Thread(producer);
            consumerThreads[i] = new Thread(consumerTasks[i]);
        }

        // start everything
        for (int i = 0; i < producerThreadNum; i++) {
            producerThreads[i].start();
            consumerThreads[i].start();
        }

        // waif for all producers to complete their job
        for (int i = 0; i < producerThreadNum; i++) {
            try {
                producerThreads[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // with special END_OF_QUEUE message, consumer can gracefully shutdown themselves
        for (int i = 0; i < consumerThreadNum; i++) {
            try {
                consumerThreads[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        // check whether all queues are empty
        while (true) {
            boolean queueEmpty = true;
            for (int i = 0; i < producerThreadNum; i++) {
                if (!bufferList[i].isEmpty()) {
                    queueEmpty = false;
                }
            }
            if (queueEmpty) {
                break;
            } else {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }
            }
        }

        // all queue empty now; shutdown all the consumers
        for (int i = 0; i < consumerThreadNum; i++) {
            consumerTasks[i].shutdown();
            consumerThreads[i].interrupt();
        }
        */
    }
}
