package Lyft.Onsite.TaskScheduler;

public class Task implements Comparable<Task> {
    private String jobID;
    private int startTime;
    private int endTime;
    private String worker;

    public Task(String id, String startTime, String duration) {
        this.jobID = id;
        this.startTime = Integer.valueOf(startTime);
        this.endTime = convertTime(this.startTime, Integer.parseInt(duration));
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getJobID() {
        return jobID;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWorker() {
        return worker;
    }

    private int convertTime(int startTime, int duration) {
        int hour = startTime / 100;
        int minute = startTime % 100;
        int addDuration = minute + duration;
        while (addDuration >= 60) {
            addDuration -= 60;
            hour++;
        }
        return hour * 100 + addDuration;
    }

    @Override
    public int compareTo(Task another) {
        return this.startTime < another.startTime ? -1 : 1;
    }
}
