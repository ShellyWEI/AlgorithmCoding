package Lyft.Onsite.TaskScheduler;

import java.util.List;
import java.util.PriorityQueue;
/**
 * Assumption:
 * 1. 24 hours schedule, job start time and finish time must be this same day
 * 2. input time is valid, would not deal with unformatted case like start time 1199
 * */
public class TaskScheduler {
    PriorityQueue<Task> finishFirstMinHeap;

    public TaskScheduler() {
        // return those task with smallest endtime,
        // if two task has same end time, poll workerID is smaller one
        this.finishFirstMinHeap = new PriorityQueue<>((o1, o2) -> {
            if (o1.getEndTime() == o2.getEndTime()) {
                return o1.getWorker().compareTo(o2.getWorker());
            }
            return o1.getEndTime() < o2.getEndTime() ? -1 : 1;
        });
    }

    private void scheduleTask(List<Task> tasks) {
        WorkerPool workerPool = new WorkerPool();
        for (Task task : tasks) {
            // current task starts even later than previous finish task
            // meas task has been finished and worker assigned to that task is free;
            while (!finishFirstMinHeap.isEmpty() && task.getStartTime() >= finishFirstMinHeap.peek().getEndTime()) {
                Task finishedTask = finishFirstMinHeap.poll();
                workerPool.putFreeWorkerToPool(finishedTask.getWorker());
            }
            task.setWorker(workerPool.getFreeWorkerFromPool());
            finishFirstMinHeap.offer(task);
            printTaskInfo(task);
        }
        System.out.println("Max workers needed: " + workerPool.getMaxNeededWorker());
    }

    private void printTaskInfo(Task task) {
        System.out.println(task.getJobID() + " starts at " + task.getStartTime() + " ends at " + task.getEndTime() + " is done by " + task.getWorker());
    }

    public static void main(String[] args) {
        TaskReader taskReader = new TaskReader();
        taskReader.read(System.getProperty("user.dir") + "/src/Lyft/Onsite/TaskScheduler/taskSchedulerSample.txt");
        TaskScheduler taskScheduler = new TaskScheduler();
        taskScheduler.scheduleTask(taskReader.getTaskPool());
    }
}
