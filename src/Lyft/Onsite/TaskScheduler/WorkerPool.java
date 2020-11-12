package Lyft.Onsite.TaskScheduler;

import java.util.PriorityQueue;

public class WorkerPool {
    private PriorityQueue<String> freeWorker;
    private int startWorkerID;
    private int maxWorkerNeed;

    public WorkerPool() {
        freeWorker = new PriorityQueue<>();
        this.startWorkerID = 1;
    }

    public void putFreeWorkerToPool(String worker) {
        freeWorker.offer(worker);
    }

    public String getFreeWorkerFromPool() {
        if (freeWorker.isEmpty()) {
            return addExtraWorker();
        } else {
            return freeWorker.poll();
        }
    }

    private String addExtraWorker() {
        maxWorkerNeed++;
        return "M" + startWorkerID++;
    }

    public int getMaxNeededWorker() {
        return maxWorkerNeed;
    }
}
