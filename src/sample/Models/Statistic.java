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

    static int getSumTasksDone(TaskList taskList) { //in TaskLists rein????
        int counter = 0;
        for (Task task : taskList.getTasks()) {
            if (task.isFinished()) counter++;
        }
        return counter;
    }

    static TaskList getMostSuccessfulGroup(ArrayList<TaskList> taskLists) {
        if (taskLists.size() == 0) return null;

        TaskList tl1 = taskLists.get(0);
        double tl1TasksDoneInPercent = getSumTasksDoneInPercent(tl1);

        for (int i = 1; i < taskLists.size(); i++) {
            TaskList tl2 = taskLists.get(i);
            double tl2TasksDoneInPercent = getSumTasksDoneInPercent(tl2);

            if (tl1TasksDoneInPercent < tl2TasksDoneInPercent) {
                tl1 = tl2;
                tl1TasksDoneInPercent = tl2TasksDoneInPercent;
            }
        }

        return tl1;
    }

    static double getSumTasksDoneInPercent(TaskList taskList) {
        return (getSumTasksDone(taskList) * 100.0) / taskList.getTasks().size();
    }
}
