package sample.Services;

import sample.Enums.NotificationTime;
import sample.Enums.TaskPriority;
import sample.Models.Task;
import sample.Models.TaskList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Eine Service-Klasse, die für das permanente Speichern von Benutzer-erstellten Daten zuständig ist.
 * @author Simon Schnitker, Megan Diekmann
 */
public class StorageService
{
    /**
     * Der Wurzelordner auf dem Computer, in dem die Aufgabenlisten gespeichert werden.
     */
    private static final String TASK_LISTS_PATH = "TaskLists/";

    static
    {
        File rootFolder = new File(TASK_LISTS_PATH);

        if (rootFolder.exists() == false)
        {
            rootFolder.mkdir();
        }
    }

    /**
     * Gibt alle auf dem Computer gespeicherten Listen zurück.
     * @throws FileNotFoundException
     * @author Megan Diekmann
     */
    public static ArrayList<TaskList> getTaskLists() throws FileNotFoundException
    {
        ArrayList<TaskList> taskLists = new ArrayList<>();
        File rootFolder = new File(TASK_LISTS_PATH);

        for (File file : rootFolder.listFiles())
        {
            TaskList list = new TaskList(file.getName());
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine())
            {
                String[] data = reader.nextLine().split(";");

                String title = data[0];

                TaskPriority priority = TaskPriority.valueOf(data[1]);

                LocalDateTime endDate = null;
                if (!data[2].equals("null")) endDate = LocalDateTime.parse(data[2]);

                NotificationTime notificationDate = NotificationTime.valueOf(data[3]);

                String note = null;
                if (!data[4].equals("null")) note = data[4];

                boolean isFinished = Boolean.parseBoolean(data[5]);

                list.add(new Task(title, priority, endDate, notificationDate, note, isFinished));
            }

            reader.close();
            taskLists.add(list);
        }

        return taskLists;
    }

    /**
     * Speichert eine Liste auf dem Computer.
     * @param list Die zu speichernde Liste.
     * @throws IOException
     * @auther Simon Schnitker
     */
    public static void saveTaskList(TaskList list) throws IOException
    {
        File file = new File(TASK_LISTS_PATH + list.getName());

        if (file.exists())
        {
            file.delete();
        }

        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        for (Task task : list.getTasks())
        {
            writer.append(task.getTitle() + ";" + task.getPriority() + ";" + task.getEndDate() + ";" + task.getNotificationTime()
                    + ";" + task.getNote() + ";" + task.isFinished() + "\n");
        }

        writer.close();
    }

    /**
     * Löscht eine Liste auf dem Computer.
     * @param list Die zu-löschende Liste.
     * @return Gibt zurück, ob das Löschen erfolgreich war.
     * @auther Simon Schnitker
     */
    public static boolean deleteTaskList(TaskList list)
    {
        return new File(TASK_LISTS_PATH + list.getName()).delete();
    }
}
