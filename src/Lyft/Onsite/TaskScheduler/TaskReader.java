package Lyft.Onsite.TaskScheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskReader {
    List<Task> tasks;
    TaskReader() {
        this.tasks = new ArrayList<>();
    }

    public void read(String filename) {
        try {
            BufferedReader sc = new BufferedReader(new FileReader(filename));
            int totalJobNumber = Integer.parseInt(sc.readLine());
            for (int i = 0; i < totalJobNumber; i++) {
                String[] line = sc.readLine().split(" ");
                tasks.add(new Task(line[0], line[1], line[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTaskPool() {
        Collections.sort(tasks);
        return tasks;
    }
}
