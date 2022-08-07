package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Enums.NotificationTime;
import sample.Enums.TaskPriority;
import sample.Models.Task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Steuerklasse der NewTaskView.fxml
 */
public class NewTaskPresenter implements Initializable
{
    @FXML
    Label title_Label;
    @FXML
    Button AddOrUpdateTask_Button;
    @FXML
    TextField title_TextField;
    @FXML
    DatePicker endDate_DatePicker;
    @FXML
    TextField endTime_TextField;
    @FXML
    ChoiceBox notificationTime_ChoiceBox;
    @FXML
    ChoiceBox priority_ChoiceBox;
    @FXML
    TextArea note_TextArea;

    private Task passedTask;
    private boolean isInEdetingMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if (this.passedTask == null)
        {
            return;
        }

        this.title_TextField.setText(this.passedTask.getTitle());
        this.endDate_DatePicker.setValue(this.passedTask.getEndDate().toLocalDate());
        this.note_TextArea.setText(this.passedTask.getNote());

        switch (this.passedTask.getPriority())
        {
            case Low:
                priority_ChoiceBox.setValue("Niedrig");
                break;
            case Medium:
                priority_ChoiceBox.setValue("Mittel");
                break;
            case High:
                priority_ChoiceBox.setValue("Hoch");
                break;
            default:
                break;
        }

        switch (this.passedTask.getNotificationTime())
        {
            case Never:
                notificationTime_ChoiceBox.setValue("Keine Erinnerung");
                break;
            case Hour:
                notificationTime_ChoiceBox.setValue("1 Stunde vorher");
                break;
            case Day:
                notificationTime_ChoiceBox.setValue("1 Tag vorher");
                break;
            case Week:
                notificationTime_ChoiceBox.setValue("1 Woche vorher");
                break;
        }

        this.title_Label.setText("Aufgabe bearbeiten");
        this.AddOrUpdateTask_Button.setText("Aufgabe aktualisieren");
    }

    /**
     * Empängt eine Aufgabe, um die Daten dieser beim Öffnen des Fensters anzuzeigen.
     * @param task Die anzuzeigende Aufgabe.
     */
    public void passTask(Task task)
    {
        this.passedTask = task;
        this.isInEdetingMode = true;
    }

    /**
     * Fügt der MainView.fxml eine neue Aufgabe hinzu und schließt das aktuelle Fenster..
     */
    @FXML
    public void addTask_OnAction(ActionEvent event) throws IOException
    {
        String title = title_TextField.getText();
        LocalDateTime endDate = endDate_DatePicker.getValue().atStartOfDay();
        NotificationTime notificationTime = NotificationTime.Never;
        TaskPriority priority = TaskPriority.Medium;
        String note = note_TextArea.getText();

        switch (priority_ChoiceBox.getValue().toString())
        {
            case "Niedrig":
                priority = TaskPriority.Low;
                break;
            case "Mittel":
                priority = TaskPriority.Medium;
                break;
            case "Hoch":
                priority = TaskPriority.High;
                break;
            default:
                break;
        }

        switch (notificationTime_ChoiceBox.getValue().toString())
        {
            case "1 Stunde vorher":
                notificationTime = NotificationTime.Hour;
                break;
            case "1 Tag vorher":
                notificationTime = NotificationTime.Day;
                break;
            case "1 Woche vorher":
                notificationTime = NotificationTime.Week;
                break;
        }

        // Übergebene Aufgabe wird aktualisiert
        if (this.isInEdetingMode)
        {
            this.passedTask.setTitle(title);
            this.passedTask.setPriority(priority);
            this.passedTask.setEndDate(endDate);
            this.passedTask.setNotificationTime(notificationTime);
            this.passedTask.setNote(note);
            PresenterLocator.taskPresenter.refreshTasks();
        }
        // Neue Aufgabe wird erstellt
        else
        {
            Task task = new Task(title, priority, endDate, notificationTime, note, false);
            PresenterLocator.taskPresenter.addTask(task);
        }

        // Fenster schließen
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
