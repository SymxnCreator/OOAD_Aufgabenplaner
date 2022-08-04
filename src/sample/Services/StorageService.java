package sample.Services;

import sample.Enums.TaskPriority;
import sample.Models.Task;
import sample.Models.TaskList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Eine Service-Klasse, die für das permanente Speichern von Benutzer-erstellten Daten zuständig ist.
 */
public class StorageService
{
    /**
     * Der Wurzelordner auf dem Computer, in dem die Aufgabenlisten gespeichert werden.
     */
    private static final String TASK_LIST_PATH = "TaskLists/";

    static
    {
        File rootFolder = new File(TASK_LIST_PATH);

        if (rootFolder.exists() == false)
        {
            rootFolder.mkdir();
        }
    }

    /**
     * Löscht eine Liste auf dem Computer.
     * @param listName Der Name der Liste.
     * @return Gibt zurück, ob das Löschen erfolgreich war.
     */
    public static boolean deleteTaskList(String listName)
    {
        return new File(TASK_LIST_PATH + listName).delete();
    }

    /**
     * Fügt eine Liste eine Aufgabe hinzu.
     * @param listName Den Name der Liste, in der die Aufgabe gespeichert werden soll.
     * @param task Die zu-speichernde Aufgabe.
     * @return Gibt zurück, ob das Speichern erfolgreich war.
     * @throws IOException
     */
    public static boolean addTask(String listName, Task task) throws IOException {
        File file = new File(TASK_LIST_PATH + listName);

        if (file.exists() == false)
        {
            return false;
        }

        FileWriter writer = new FileWriter(file, true);

        writer.append(task.getTitle() + ";" + task.getPriority() + ";" + task.getEndDate() + ";" + task.getNotificationDate()
                + ";" + task.getNote() + ";" + task.isFinished() + "\n");

        writer.close();

        return true;
    }

    /**
     * Speichert eine Aufgabe innerhalb einer Liste auf dem Computer.
     * @param task Die zu-löschende Aufgabe.
     * @return
     */
    public static boolean deleteTask(Task task)
    {
        return false;
    }

    /**
     * Gibt alle auf dem Computer gespeicherten Listen zurück.
     * @throws FileNotFoundException
     */
    public static List<TaskList> getTaskLists() throws FileNotFoundException
    {
        ArrayList<TaskList> taskLists = new ArrayList<>();
        File rootFolder = new File(TASK_LIST_PATH);

        for (File file : rootFolder.listFiles())
        {
            TaskList list = new TaskList(file.getName());
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine())
            {
                String[] data = reader.nextLine().split(";");

                String title = data[0];
                TaskPriority priority = TaskPriority.valueOf(data[1]);

                LocalDate endDate = null;
                if (!data[2].equals("null")) endDate = LocalDate.parse(data[2]);

                LocalDate notificationDate = null;
                if (!data[3].equals("null")) notificationDate = LocalDate.parse(data[3]);

                String note = null;
                if (!data[4].equals("null")) note = data[4];

                boolean isFinished = Boolean.parseBoolean(data[5]);


                list.add(new Task(title, priority, endDate, notificationDate, note, isFinished));
            }

            taskLists.add(list);
        }

        return taskLists;
    }

    /**
     * Speichert eine Liste auf dem Computer.
     * @param list Die zu speichernde Liste.
     * @throws IOException
     */
    public static void saveTaskList(TaskList list) throws IOException
    {
        File file = new File(TASK_LIST_PATH + list.getName());

        if (file.exists())
        {
            file.delete();
        }

        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        for (Task task : list.getTasks())
        {
            addTask(list.getName(), task);
            //writer.append(task.getTitle() + ";" + task.getEndDate() + ";" + task.isFinished() + "\n");
        }

        writer.close();
    }
}
