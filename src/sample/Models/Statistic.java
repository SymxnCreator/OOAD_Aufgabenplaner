package sample.Models;

import java.util.ArrayList;

public class Statistic {

    static int sumAllTasksNotDone(ArrayList<TaskList> taskLists) {
        int counter = 0;
        for (TaskList tl : taskLists) {
            counter += tl.sumTasksNotDone();
        }
        return counter;
    }

    static int sumAllTasksDone(ArrayList<TaskList> taskLists) {
        int counter = 0;
        for (TaskList tl : taskLists) {
            counter += tl.sumTasksDone();
        }
        return counter;
    }

    static TaskList getMostSuccessfulGroup(ArrayList<TaskList> taskLists) {
        if (taskLists.size() == 0) return null;

        TaskList tl1 = taskLists.get(0);
        double tl1TasksDoneInPercent = tl1.sumTasksDoneInPercent();

        for (int i = 1; i < taskLists.size(); i++) {
            TaskList tl2 = taskLists.get(i);
            double tl2TasksDoneInPercent = tl2.sumTasksDoneInPercent();

            if (tl1TasksDoneInPercent < tl2TasksDoneInPercent) {
                tl1 = tl2;
                tl1TasksDoneInPercent = tl2TasksDoneInPercent;
            }
        }

        return tl1;
    }
}
