package sample.Models;

import sample.Enums.NotificationTime;
import sample.Enums.TaskPriority;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;

public class Task
{
    private int id;

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
     * (Optional)
     */
    private LocalDate endDate;

    /**
     * Gibt an, wann der Nutzer an die Aufgabe erinnert wird.
     * (Optional)
     */
    private LocalDate notificationDate;

    /**
     * Gibt die Notiz der Aufgabe an.
     * (Optional)
     */
    private String note;

    /**
     * Gibt an, ob die Aufgabe erledigt wurde oder nicht.
     */
    private boolean isFinished;


    /**
     * Konstruktor zur Erstellung einer neuen Aufgabe
     * @param title Der Name der Aufgabe.
     * @param priority Die Wichtigkeit der Aufgabe.
     */
    public Task(String title, TaskPriority priority)
    {
        this.title = title;
        this.priority = priority;
        this.isFinished = false;
    }

    /**
     * Konstruktor zur Wiederherstellung der Aufgaben
     * @param title Der Name der Aufgabe.
     * @param priority Die Wichtigkeit der Aufgabe.
     * @param endDate Das Datum, wann die Aufgabe fällig ist.
     * @param notificationDate Das Datum, wann die Erinnerung der Aufgabe ist.
     * @param note Die Notiz der Aufgabe.
     * @param isFinished Ist die Aufgabe erledigt?
     */
    public Task(String title, TaskPriority priority, LocalDate endDate, LocalDate notificationDate, String note, boolean isFinished)
    {
        this.title = title;
        this.priority = priority;
        this.endDate = endDate;
        this.notificationDate = notificationDate;
        this.note = note;
        this.isFinished = isFinished;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(NotificationTime notificationTime) {
        if (endDate != null) {
            switch (notificationTime) {
                case Hour: {
                    this.notificationDate = endDate.minus(1, ChronoUnit.HOURS);
                    break;
                }
                case Day: {
                    this.notificationDate = endDate.minusDays(1);
                    break;
                }
                case Week: {
                    this.notificationDate = endDate.minusWeeks(1);
                    break;
                }
                default: break;
            }
        }
    }

    public String getNote() {
        return note;
    }
}
