package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Enums.TaskPriority;
import sample.Models.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTastController
{
    @FXML
    TextField title;
    @FXML
    DatePicker endDate;
    @FXML
    ChoiceBox notificationTime;
    @FXML
    ChoiceBox priority;
    @FXML
    TextArea note;

    @FXML
    private void addTask_OnAction(ActionEvent event)
    {
        Task task = new Task(title.getText(), TaskPriority.High);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        TaskController taskController = loader.getController();
        taskController.addTask(task);

        // Fenster schließen
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
