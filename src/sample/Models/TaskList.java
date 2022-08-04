package sample.Models;

import java.util.ArrayList;

public class TaskList
{
    /**
     * Gibt den Namen der Liste an.
     */
    private String name;

    /**
     * Gibt die Aufgaben der Liste an.
     */
    private ArrayList<Task> tasks;

    public TaskList(String name)
    {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    /**
     * Fügt der Liste eine neue Aufgabe hinzu.
     * @param task Die hinzuzufügende Aufgabe.
     * @return Gibt bei Erfolg true zurück
     */
    public boolean add(Task task)
    {
        return this.tasks.add(task);
    }

    /**
     * Löscht eine Aufgabe aus der Liste
     * @param task Die zulöschende Aufgabe.
     * @return Gibt bei Erfolg true zurück
     */
    public boolean remove(Task task) {
        return this.tasks.remove(task);
    }

    /**
     * Berechnet die Summe aller erledigten Aufgaben.
     * @return Die Summe aller erledigten Aufgaben
     */
    public int sumTasksDone() {
        int counter = 0;
        for (Task task : tasks) {
            if (task.isFinished()) counter++;
        }
        return counter;
    }

    /**
     * Berechnet die Summe aller unerledigten Aufgaben.
     * @return Die Summe aller unerledigten Aufgaben
     */
    public int sumTasksNotDone() {
        int counter = 0;
        for (Task task : tasks) {
            if (!task.isFinished()) counter++;
        }
        return counter;
    }

    public double sumTasksDoneInPercent() {
        return (sumTasksDone() * 100.0) / tasks.size();
    }

    public double sumTasksNotDoneInPercent() {
        return (sumTasksNotDone() * 100.0) / tasks.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
