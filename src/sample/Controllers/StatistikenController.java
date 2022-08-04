package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StatistikenController
{
    @FXML
    private Label NumberOfTasksDone;
    @FXML
    private Label NumberOfTasksNotDone;
    @FXML
    private Label NumberOfDeletedTasks;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void switchToMainView(ActionEvent event)throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/MainView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
