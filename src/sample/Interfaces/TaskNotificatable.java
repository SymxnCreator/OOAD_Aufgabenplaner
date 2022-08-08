package sample.Interfaces;

import sample.Models.Task;

/**
 * Eine Schnittstelle, die anderen Klassen implementieren können, damit diese Erinnerungen von Aufgaben empfangen können.
 * @author Simon Schnitker
 */
public interface TaskNotificatable
{
    /**
     * Informiert darüber, dass eine Aufgabe bald fällig wird.
     * @param task Die Aufgabe.
     */
    void notifiy(Task task);
}
