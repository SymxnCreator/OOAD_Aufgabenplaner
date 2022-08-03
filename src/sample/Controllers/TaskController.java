package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Interfaces.INotify;
import sample.Models.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskController implements INotify, Initializable
{


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<Button> GroupListView;
    private Button openNewTastWindow;

    Button[] groups = {new Button("Meine Aufgaben")};

    @Override
    public void notifiy(Task task)
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GroupListView.getItems().addAll(groups);


    }

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

    public void openNewTastWindow(ActionEvent event) throws IOException {

        System.out.println("Click");
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/NewTastView.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Neue Aufgabe Erstellen");
        primaryStage.setScene(scene);

        primaryStage.initModality(Modality.NONE);
        primaryStage.show();

    }


    @FXML
    private void addTask_OnAction(ActionEvent event)
    {

    }




}
