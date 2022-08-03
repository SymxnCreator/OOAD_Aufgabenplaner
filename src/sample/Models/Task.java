package sample.Models;

import java.time.LocalDate;
import java.util.Date;

public class Task
{
    private int id;

    /**
     * Gibt den Titel der Aufgabe an.
     */
    private String title;

    /**
     * Gibt an, wann die Aufgabe fällig ist. Test
     */
    private LocalDate endDate;

    /**
     * Gibt an, ob die Aufgabe erledigt wurde oder nicht.
     */
    private boolean isFinished;

    public Task(String title, LocalDate endDate, boolean isFinished)
    {
        this.title = title;
        this.endDate = endDate;
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
}
