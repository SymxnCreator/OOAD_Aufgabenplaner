package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Models.TaskList;
import sample.Services.StatisticService;
import sample.Services.StorageService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Steuerklasse der StatisticsView.fxml
 * @author Megan Diekmann
 */
public class StatisticsPresenter implements Initializable
{
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Label NumberOfTasksDone_Label;
    @FXML
    private Label NumberOfTasksNotDone_Label;
    @FXML
    private Label MostSuccessfulList_Label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ArrayList<TaskList> taskLists = new ArrayList<>();

        try
        {
            taskLists = StorageService.getTaskLists();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        NumberOfTasksDone_Label.setText(String.valueOf(StatisticService.sumAllTasksDone(taskLists)));
        NumberOfTasksNotDone_Label.setText(String.valueOf(StatisticService.sumAllTasksNotDone(taskLists)));
        MostSuccessfulList_Label.setText(StatisticService.getMostSuccessfulList(taskLists).getName());
    }

    /**
     * Wechselt die Hauptansicht zur MainView (Aufgabenübersicht).
     * @author Megan Diekmann
     */
    @FXML
    public void switchToMainView_OnAction(ActionEvent event)throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/MainView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
