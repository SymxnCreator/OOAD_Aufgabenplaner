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
     */
    public boolean add(Task task)
    {
        return this.tasks.add(task);
    }

    /**
     * Löscht eine Aufgabe aus der Liste
     * @param task Die zulöschende Aufgabe.
     */
    public boolean remove(Task task) {
        return this.tasks.remove(task);
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
