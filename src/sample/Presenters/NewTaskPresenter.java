package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Enums.NotificationTime;
import sample.Enums.TaskPriority;
import sample.Models.Task;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Steuerklasse der NewTaskView.fxml
 */
public class NewTaskPresenter
{
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

    public void passTask(Task task)
    {

    }

    /**
     * Fügt der MainView.fxml eine neue Aufgabe hinzu und schließt das aktuelle Fenster..
     */
    @FXML
    public void addTask_OnAction(ActionEvent event) throws IOException
    {
        String title = title_TextField.getText();
        LocalDate endDate = endDate_DatePicker.getValue();
        NotificationTime notificationTime = NotificationTime.Day;
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

        Task task = new Task(title, priority, note);
        PresenterLocator.taskPresenter.addTask(task);

        // Fenster schließen
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
