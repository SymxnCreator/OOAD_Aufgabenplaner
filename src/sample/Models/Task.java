package sample.Models;

import sample.Enums.NotificationTime;
import sample.Enums.TaskPriority;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Definiert den Aufbau einer Aufgabe.
 * @author Simon Schnitker, Megan Diekmann
 */
public class Task
{
    /**
     * Gibt den Titel der Aufgabe an.
     */
    private String title;

    /**
     * Gibt die Wichtigkeit der Aufgabe an.
     */
    private TaskPriority priority;

    /**
     * Gibt an, wann die Aufgabe fällig ist.
     */
    private LocalDate endDate;

    /**
     * Gibt an, wann der Nutzer an die Aufgabe erinnert wird.
     */
    private NotificationTime notificationTime;

    /**
     * Gibt die Notiz/Beschreibung der Aufgabe an.
     */
    private String note;

    /**
     * Gibt an, ob die Aufgabe erledigt wurde oder nicht.
     */
    private boolean isFinished;

    /**
     * Gibt an, ob der Benutzer vom Benachrichtigungssystem über diese Aufgabe informiert wurde.
     * Dient dazu, damit der Benutzer während der laufenden Sitzung nur einmal erinnert wird.
     */
    private boolean hasUserNotified;

    /**
     * Konstruktor zum Erstellen einer Aufgabe.
     * @param title Der Name der Aufgabe.
     * @param priority Die Wichtigkeit der Aufgabe.
     * @param endDate Das Datum, wann die Aufgabe fällig ist.
     * @param notificationTime Das Datum, wann die Erinnerung der Aufgabe ist.
     * @param note Die Notiz der Aufgabe.
     * @param isFinished Ist die Aufgabe erledigt?
     */
    public Task(String title, TaskPriority priority, LocalDate endDate, NotificationTime notificationTime, String note, boolean isFinished)
    {
        this.title = title;
        this.priority = priority;
        this.endDate = endDate;
        this.notificationTime = notificationTime;
        this.note = note;
        this.isFinished = isFinished;
        this.hasUserNotified = false;
    }

    public LocalDate getNotificationDate()
    {
        if (endDate == null)
        {
            return LocalDate.MIN;
        }

        switch (this.notificationTime) {
            case Hour: {
                return endDate.minus(1, ChronoUnit.HOURS);
            }
            case Day: {
                return endDate.minusDays(1);
            }
            case Week: {
                return endDate.minusWeeks(1);
            }
            default:
                return LocalDate.MIN;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public NotificationTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(NotificationTime notificationTime)
    {
        this.notificationTime = notificationTime;
    }

    public String getNote() {
        return note;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean hasUserNotified() {
        return hasUserNotified;
    }

    public void setHasUserNotified(boolean hasUserNotified) {
        this.hasUserNotified = hasUserNotified;
    }
}
