package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Interfaces.TaskNotificatable;
import sample.Main;
import sample.Models.Task;
import sample.Models.TaskList;
import sample.Services.NotificationService;
import sample.Services.StorageService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Steuerklasse der MainView.fxml
 */
public class MainViewPresenter implements TaskNotificatable, Initializable
{
    /**
     * Name der Standard-Aufgabenliste, die erstellt wird, falls keine Listen vorhanden sind.
     */
    private final String DEFAULT_TASK_LIST_NAME = "Meine Aufgaben";
    private Stage primaryStage;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    @FXML
    ListView<TaskList> taskLists_ListView;

    @FXML
    ListView<Task> tasks_ListView;

    @FXML
    ListView<Task> notifications_ListView;

    @FXML
    Label taskListTitle_Label;

    @FXML
    ChoiceBox filter_ChoiceBox;

    @FXML
    ListView<Task> tasksDone_ListView;

    @Override
    public void notifiy(Task task)
    {
        this.notifications_ListView.getItems().add(task);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        PresenterLocator.taskPresenter = this;

        notifications_ListView.setCellFactory(o -> new ListCell<Task>()
        {
            @Override
            protected void updateItem(Task item, boolean empty)
            {
                super.updateItem(item, empty);

                if (item == null)
                {
                    setText("");
                }
                else
                {
                    setText(item.getTitle());
                }
            }
        });

        taskLists_ListView.setCellFactory(o -> new ListCell<TaskList>()
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

        setCellFactory(tasks_ListView);
        setCellFactory(tasksDone_ListView);

        // Aufgabenlisten laden
        loadTaskLists();

        // Standardliste hinzufügen, wenn keine Listen vorhanden sind
        if (this.taskLists_ListView.getItems().size() == 0)
        {
            addDefaultList();
        }

        // Erste Liste selektieren
        selectTaskList(taskLists_ListView.getItems().get(0));

        // Erinnerungsprozess starten
        NotificationService.run(this, taskLists_ListView.getItems());
    }

    /**
     * Erstellt die einzelnen Aufgabezellen der Listen.
     */
    public void setCellFactory(ListView<Task> listView) {
        listView.setCellFactory(o -> new ListCell<Task>()
        {
            private final CheckBox checkBox = new CheckBox();
            private final Label title_Label = new Label();
            private final Label endDate_Label = new Label();
            private final Button editTask_Button = new Button("Bearbeiten");
            private final Button deleteTask_Button = new Button("Löschen");
            private final  HBox hBox = new HBox(editTask_Button, deleteTask_Button);
            private final GridPane gridPane = new GridPane();
            {
                checkBox.setAlignment(Pos.CENTER);
                gridPane.setPadding(new Insets(8,12,8,12));
                checkBox.setPadding(new Insets(0,12,0,0));
                title_Label.setPadding(new Insets(0,12,0,0));
                title_Label.getStyleClass().add("TaskTitle");
                endDate_Label.setPadding(new Insets(0,12,0,0));
                endDate_Label.getStyleClass().add("TaskEndDate");
                editTask_Button.getStyleClass().add("BlueBtn");
                deleteTask_Button.getStyleClass().add("BlueBtn");
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(8);

                gridPane.add(checkBox, 0, 0, 1, 2);
                gridPane.add(title_Label, 1, 0);
                gridPane.add(endDate_Label, 1, 1);
                gridPane.add(hBox, 2, 0, 1, 2);

                ColumnConstraints c1 = new ColumnConstraints();
                c1.setHgrow(Priority.NEVER);
                ColumnConstraints c2 = new ColumnConstraints();
                c2.setHgrow(Priority.ALWAYS);
                gridPane.getColumnConstraints().addAll(c1, c2);

                editTask_Button.setOnAction(event ->
                {
                    if (primaryStage == null || !primaryStage.isShowing()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/NewTaskView.fxml"));
                        NewTaskPresenter presenter = new NewTaskPresenter();
                        presenter.passTask(getItem());
                        loader.setController(presenter);

                        Parent root = null;
                        try
                        {
                            root = loader.load();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        primaryStage = new Stage();
                        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/sample/Views/Resources/calendar.png")));
                        primaryStage.setResizable(false);
                        primaryStage.setTitle("Aufgabe bearbeiten");
                        primaryStage.setScene(scene);
                        primaryStage.initModality(Modality.NONE);
                        primaryStage.show();
                    } else {
                        primaryStage.toFront();
                    }

                });

                deleteTask_Button.setOnAction(event ->
                {
                    Task task = getItem();
                    listView.getItems().remove(task);
                    TaskList selectedList = taskLists_ListView.getSelectionModel().getSelectedItem();
                    selectedList.remove(task);
                    notifications_ListView.getItems().remove(task);

                    try
                    {
                        StorageService.saveTaskList(taskLists_ListView.getSelectionModel().getSelectedItem());
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

                    if (task.isFinished())
                    {
                        notifications_ListView.getItems().remove(task);

                        tasksDone_ListView.getItems().add(task);
                        tasks_ListView.getItems().remove(task);
                    } else {
                        tasksDone_ListView.getItems().remove(task);
                        tasks_ListView.getItems().add(task);
                    }
                    filterTasks_OnAction(event);


                    try
                    {
                        StorageService.saveTaskList(taskLists_ListView.getSelectionModel().getSelectedItem());
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

                if (item == null)
                {
                    setGraphic(null);
                    setText("");
                }
                else
                {
                    setGraphic(gridPane);
                    title_Label.setText(item.getTitle());

                    if (item.getEndDate() == null || item.getEndDate() == LocalDateTime.MIN)
                    {
                        endDate_Label.setText("Kein Enddatum");
                    }
                    else
                    {
                        LocalDateTime dateTime = item.getEndDate();

                        endDate_Label.setText("Fällig am " + dateTime.getDayOfMonth() + "." + dateTime.getMonthValue() + "." + dateTime.getYear() + " um " + dateTime.getHour() + ":" + dateTime.getMinute() + " Uhr");
                    }

                    checkBox.setSelected(item.isFinished());
                }
            }
        });
    }

    /**
     * Wechselt die Hauptansicht zu den Statistiken.
     */
    @FXML
    public void switchToStatisticsView_OnAction(ActionEvent event)throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/StatisticsView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/sample/Views/Resources/calendar.png")));
        stage.setResizable(false);
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
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/sample/Views/Resources/calendar.png")));
        stage.setResizable(false);
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
        if (primaryStage == null || !primaryStage.isShowing()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/NewTaskView.fxml"));
            loader.setController(new NewTaskPresenter());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage = new Stage();
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/sample/Views/Resources/calendar.png")));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Neue Aufgabe");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.NONE);
            primaryStage.show();
        } else {
            primaryStage.toFront();
        }
    }

    /**
     * Öffnet ein Fenster, um eine neue Liste anzulegen.
     */
    @FXML
    public void createTaskList_OnAction(ActionEvent event) throws IOException
    {
        if (primaryStage == null || !primaryStage.isShowing()) {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/NewTaskListView.fxml"));
            Scene scene = new Scene(root);
            primaryStage = new Stage();
            primaryStage.setTitle("Neue Liste");

            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/sample/Views/Resources/calendar.png")));
            primaryStage.setResizable(false);

            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.NONE);
            primaryStage.show();
        } else {
            primaryStage.toFront();
        }
    }

    /**
     * Wird ausgelöst, wenn der Filter-Modus zum Sortieren der Aufgaben geändert wurde.
     */
    @FXML
    public void filterTasks_OnAction(ActionEvent event)
    {
        if (filter_ChoiceBox.getValue().toString().contentEquals("Nach Datum"))
        {
            for (Task task : tasks_ListView.getItems())
            {
                if (task.getEndDate() == null)
                {
                    task.setEndDate(LocalDateTime.MIN);
                }
            }

            Collections.sort(tasks_ListView.getItems(), (x, y) -> x.getEndDate().compareTo(y.getEndDate()));
        }
        else
        {
            Collections.sort(tasks_ListView.getItems(), (x, y) -> x.getPriority().compareTo(y.getPriority()));
        }
    }

    /**
     * Zeigt die Aufgaben der angeklickten Liste auf der UI an.
     */
    @FXML
    public void GroupListView_Clicked(MouseEvent event)
    {
        TaskList selectedList = taskLists_ListView.getSelectionModel().getSelectedItem();
        selectTaskList(selectedList);
    }

    /**
     * Löscht die selektierte Aufgabenliste auf dem Computer, wenn die "Entfernen"-Taste gedrückt wurde.
     */
    @FXML
    public void GroupListView_OnKeyReleased(KeyEvent event)
    {
        if (event.getCode() != KeyCode.DELETE || taskLists_ListView.getItems().size() == 1)
        {
            return;
        }

        TaskList selectedList = taskLists_ListView.getSelectionModel().getSelectedItem();

        // Liste auf dem Computer und lokal löschen
        boolean deleted = StorageService.deleteTaskList(selectedList);

        if (deleted)
        {
            taskLists_ListView.getItems().remove(selectedList);

            // Erste Liste selektieren
            selectTaskList(taskLists_ListView.getItems().get(0));
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
     * @param task Die hinzuzufügende Aufgabe.
     * @throws IOException
     */
    public void addTask(Task task) throws IOException
    {
        TaskList selectedList = taskLists_ListView.getSelectionModel().getSelectedItem();
        selectedList.add(task);
        tasks_ListView.getItems().add(task);

        // Liste auf dem Computer speichern
        StorageService.saveTaskList(selectedList);
    }

    /**
     * Aktualisiert die Aufgaben in der ListView und lokal auf dem Computer.
     * Wird von der Klasse "NewTaskPresenter" aufgerufen, wenn die Daten einer Aufgabe bearbeitet wurden.
     * @throws IOException
     */
    public void refreshTasks() throws IOException
    {
        tasks_ListView.refresh();

        // Liste auf dem Computer speichern
        TaskList selectedList = taskLists_ListView.getSelectionModel().getSelectedItem();
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
        for (TaskList taskList : this.taskLists_ListView.getItems())
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
        taskLists_ListView.getItems().add(list);

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
            this.taskLists_ListView.getItems().addAll(taskLists);
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
        this.taskLists_ListView.getItems().add(defaultTaskList);

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
        taskLists_ListView.getSelectionModel().select(list);
        taskListTitle_Label.setText(list.getName());

        // Aufgaben der Liste anzeigen
        tasks_ListView.getItems().clear();
        tasksDone_ListView.getItems().clear();

        for (Task task : list.getTasks()) {
            if (task.isFinished()) {
                tasksDone_ListView.getItems().add(task);
            } else {
                tasks_ListView.getItems().add(task);
            }
        }
    }
}
