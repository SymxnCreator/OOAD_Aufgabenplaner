package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Interfaces.INotify;
import sample.Models.Task;

import java.io.IOException;

public class TaskController implements INotify
{


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToStatisken(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/StatistikenView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEinstellungen(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("EinstellungenView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void addTask_OnAction(ActionEvent event)
    {

    }

    @Override
    public void notifiy(Task task)
    {

    }
}
