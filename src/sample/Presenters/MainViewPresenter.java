package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Interfaces.INotify;
import sample.Models.Task;
import sample.Models.TaskList;
import sample.Services.StorageService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Steuerklasse der MainView.fxml
 */
public class MainViewPresenter implements INotify, Initializable
{
    /**
     * Name der Standard-Aufgabenliste, die erstellt wird, falls keine Listen vorhanden sind.
     */
    private final String DEFAULT_TASK_LIST_NAME = "Meine Aufgaben";

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    @FXML
    ListView<TaskList> TaskGroups_ListView;

    @FXML
    ListView<Task> Tasks_ListView;

    @FXML
    Label taskListTitle;

    @FXML
    ChoiceBox filter_ChoiceBox;

    @FXML
    GridPane rootPane;

    @Override
    public void notifiy(Task task)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erinnerung");
        alert.setContentText("Aufgabe ist fällig: " + task.getTitle());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        PresenterLocator.taskPresenter = this;

        TaskGroups_ListView.setCellFactory(o -> new ListCell<TaskList>()
        {
            @Override
            protected void updateItem(TaskList item, boolean empty)
            {
                super.updateItem(item, empty);

                if (item == null)
                {
                    setText("");
                }
                else
                {
                    setText(item.getName());
                }
            }
        });

        Tasks_ListView.setCellFactory(o -> new ListCell<Task>()
        {
            private final CheckBox checkBox = new CheckBox();
            private final ProgressBar progressBar = new ProgressBar();
            private final Label title_Label = new Label();
            private final Label endDate_Label = new Label();
            private final Button editTask_Button = new Button("Bearbeiten");
            private final Button deleteTask_Button = new Button("Löschen");
            private final HBox content = new HBox(checkBox, title_Label, endDate_Label, editTask_Button, deleteTask_Button);
            {
                deleteTask_Button.setOnAction(event ->
                {
                    Task task = getItem();
                    Tasks_ListView.getItems().remove(task);
                    TaskList selectedList = TaskGroups_ListView.getSelectionModel().getSelectedItem();
                    selectedList.remove(task);

                    try
                    {
                        StorageService.saveTaskList(TaskGroups_ListView.getSelectionModel().getSelectedItem());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });

                checkBox.setOnAction(event ->
                {
                    Task task = getItem();
                    task.setFinished(checkBox.isSelected());

                    try
                    {
                        StorageService.saveTaskList(TaskGroups_ListView.getSelectionModel().getSelectedItem());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Task item, boolean empty)
            {
                super.updateItem(item, empty);
                progressBar.progressProperty().unbind();

                if (item == null)
                {
                    setGraphic(null);
                    setText("");
                }
                else
                {
                    setGraphic(content);
                    title_Label.setText(item.getTitle());

                    if (item.getEndDate() != null)
                    {
                        endDate_Label.setText("Fällig am " + item.getEndDate());
                    }

                    checkBox.setSelected(item.isFinished());
                    //progressBar.progressProperty().bind(item.progressProperty());

                }
            }
        });

        // Aufgabenlisten laden
        loadTaskLists();

        // Standardliste hinzufügen, wenn keine Listen vorhanden sind
        if (this.TaskGroups_ListView.getItems().size() == 0)
        {
            addDefaultList();
        }

        // Erste Liste selektieren
        selectTaskList(TaskGroups_ListView.getItems().get(0));

        //NotificationService.run(this, TaskGroups_ListView.getItems());
    }

    /**
     * Wechselt die Hauptansicht zu den Statistiken.
     */
    @FXML
    public void switchToStatisticsView_OnAction(ActionEvent event)throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/StatisticsView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wechselt die Hauptansicht zu den Einstellungen.
     */
    @FXML
    public void switchToSettingsView_OnAction(ActionEvent event)throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/SettingsView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Öffnet ein Fenster, um eine neue Aufgabe anzulegen.
     */
    @FXML
    public void openNewTaskWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/NewTaskView.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Neue Aufgabe");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.NONE);
        primaryStage.show();
    }

    /**
     * Öffnet ein Fenster, um eine neue Liste anzulegen.
     */
    @FXML
    public void createTaskList_OnAction(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/NewTaskListView.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Neue Liste");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.NONE);
        primaryStage.show();
    }

    /**
     * Wird ausgelöst, wenn der Filter-Modus zum Sortieren der Aufgaben geändert wurde.
     */
    @FXML
    public void filterTasks_OnAction(ActionEvent event)
    {

    }

    /**
     * Zeigt die Aufgaben der angeklickten Liste auf der UI an.
     */
    @FXML
    public void GroupListView_Clicked(MouseEvent event)
    {
        TaskList selectedList = TaskGroups_ListView.getSelectionModel().getSelectedItem();
        selectTaskList(selectedList);
    }

    /**
     * Löscht die selektierte Aufgabenliste auf dem Computer, wenn die "Entfernen"-Taste gedrückt wurde.
     */
    @FXML
    public void GroupListView_OnKeyReleased(KeyEvent event)
    {
        if (event.getCode() != KeyCode.DELETE)
        {
            return;
        }

        TaskList selectedList = TaskGroups_ListView.getSelectionModel().getSelectedItem();

        // Liste auf dem Computer und lokal löschen
        boolean deleted = StorageService.deleteTaskList(selectedList);

        if (deleted)
        {
            TaskGroups_ListView.getItems().remove(selectedList);

            // Erste Liste selektieren
            selectTaskList(TaskGroups_ListView.getItems().get(0));
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Die Liste konnte nicht gelöscht werden.");
            alert.show();
        }
    }

    /**
     * Fügt die übergebene Aufgabe der aktuell-ausgewählten Aufgabenliste hinzu.
     * Wird von der Klasse "NewTaskPresenter" aufgerufen.
     * @param task
     */
    public void addTask(Task task) throws IOException
    {
        TaskList selectedList = TaskGroups_ListView.getSelectionModel().getSelectedItem();
        selectedList.add(task);
        Tasks_ListView.getItems().add(task);

        // Liste auf dem Computer speichern
        StorageService.saveTaskList(selectedList);
    }

    /**
     * Fügt eine neue Aufgabenliste hinzu.
     * Wird von der Klasse "NewGroupPresenter" aufgerufen.
     * @param name Der Name der Liste.
     */
    public void createTaskList(String name) throws IOException
    {
        // Prüfen, ob bereits eine Liste mit diesem Namen existiert
        for (TaskList taskList : this.TaskGroups_ListView.getItems())
        {
            if (taskList.getName().contentEquals(name))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Eine Liste mit diesem Namen ist bereits vorhanden.");
                alert.show();
                return;
            }
        }

        TaskList list = new TaskList(name);
        TaskGroups_ListView.getItems().add(list);

        // Liste auf dem Computer speichern
        StorageService.saveTaskList(list);

        // Erstellte Liste selektieren
        selectTaskList(list);
    }

    /**
     * Lädt die auf dem Computer gespeicherten Aufgabenlisten und selektiert die Erste.
     */
    private void loadTaskLists()
    {
        try
        {
            // Gespeicherte Aufgabenlisten laden
            ArrayList<TaskList> taskLists = StorageService.getTaskLists();
            this.TaskGroups_ListView.getItems().addAll(taskLists);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Fehler beim Laden der Aufgabenlisten.");
        }
    }

    /**
     * Fügt eine Standard-Aufgabenliste hinzu.
     */
    private void addDefaultList()
    {
        TaskList defaultTaskList = new TaskList(DEFAULT_TASK_LIST_NAME);
        this.TaskGroups_ListView.getItems().add(defaultTaskList);

        try
        {
            StorageService.saveTaskList(defaultTaskList);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Selektiert eine Aufgabeliste und bereitet die UI dafür auf.
     * @param list Die Liste.
     */
    private void selectTaskList(TaskList list)
    {
        TaskGroups_ListView.getSelectionModel().select(list);
        taskListTitle.setText(list.getName());

        // Aufgaben der Liste anzeigen
        Tasks_ListView.getItems().clear();
        Tasks_ListView.getItems().addAll(list.getTasks());
    }
}
