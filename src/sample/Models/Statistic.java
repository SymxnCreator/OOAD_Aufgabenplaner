package sample.Models;

import java.util.ArrayList;

public class Statistic {

    static int getSumAllTasksNotDone(ArrayList<TaskList> taskLists) {
        int counter = 0;
        for (TaskList tl : taskLists) {
            counter += getSumTasksNotDone(tl);
        }
        return counter;
    }

    static int getSumAllTasksDone(ArrayList<TaskList> taskLists) {
        int counter = 0;
        for (TaskList tl : taskLists) {
            counter += getSumTasksDone(tl);
        }
        return counter;
    }

    static int getSumTasksNotDone(TaskList taskList) {
        int counter = 0;
        for (Task task : taskList.getTasks()) {
            if (!task.isFinished()) counter++;
        }
        return counter;
    }

    static int getSumTasksDone(TaskList taskList) {
        int counter = 0;
        for (Task task : taskList.getTasks()) {
            if (task.isFinished()) counter++;
        }
        return counter;
    }
}
