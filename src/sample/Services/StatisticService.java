package sample.Services;

import sample.Models.TaskList;

import java.util.ArrayList;

/**
 * Eine Klasse, die für das Berechnen von Statistiken zuständig ist.
 */
public class StatisticService
{
    /**
     * Berechnet, wie viele Aufgaben der Benutzer noch nicht erledigt hat.
     * @param taskLists Die Aufgabenlisten.
     * @return Gibt die Anzahl zurück.
     */
    public static int sumAllTasksNotDone(ArrayList<TaskList> taskLists)
    {
        int counter = 0;

        for (TaskList taskList : taskLists)
        {
            counter += taskList.sumTasksNotDone();
        }

        return counter;
    }

    /**
     * Berechnet, wie viele Aufgaben der Benutzer bereits erledigt hat.
     * @param taskLists Die Aufgabenlisten.
     * @return Gibt die Anzahl zurück.
     */
    public static int sumAllTasksDone(ArrayList<TaskList> taskLists)
    {
        int counter = 0;

        for (TaskList taskList : taskLists)
        {
            counter += taskList.sumTasksDone();
        }

        return counter;
    }

    static TaskList getMostSuccessfulList(ArrayList<TaskList> taskLists)
    {
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
