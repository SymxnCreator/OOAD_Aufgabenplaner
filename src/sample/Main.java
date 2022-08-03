package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Models.Task;
import sample.Models.TaskList;
import sample.Services.StorageService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));
        primaryStage.setTitle("Aufgabenplaner");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException
    {
        launch(args);

        System.out.println("Hallo");

        TaskList taskList = new TaskList("Meine Aufgaben");
        Task task1 = new Task("Sauber machen", LocalDate.now(), false);
        Task task2 = new Task("Wäsche waschen", LocalDate.now(), true);
        taskList.add(task1);
        taskList.add(task2);

        StorageService.saveTaskList(taskList);
        StorageService.addTask("Meine Aufgaben", task1);

        List<TaskList> lists = StorageService.getTaskLists();

        for (TaskList list : lists)
        {
            System.out.println(list.getName());

            for (Task task : list.getTasks())
            {
                System.out.println(task.getTitle());
            }
        }
    }
}
