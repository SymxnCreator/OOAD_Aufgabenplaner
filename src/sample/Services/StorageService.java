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
import java.util.Scanner;

/**
 * Eine Service-Klasse, die für das permanente Speichern von Benutzer-erstellten Daten zuständig ist.
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

                LocalDate endDate = null;
                if (!data[2].equals("null")) endDate = LocalDate.parse(data[2]);

                LocalDate notificationDate = null;
                if (!data[3].equals("null")) notificationDate = LocalDate.parse(data[3]);

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
            writer.append(task.getTitle() + ";" + task.getPriority() + ";" + task.getEndDate() + ";" + task.getNotificationDate()
                    + ";" + task.getNote() + ";" + task.isFinished() + "\n");
        }

        writer.close();
    }

    /**
     * Löscht eine Liste auf dem Computer.
     * @param list Die Liste.
     * @return Gibt zurück, ob das Löschen erfolgreich war.
     */
    public static boolean deleteTaskList(TaskList list)
    {
        return new File(TASK_LISTS_PATH + list.getName()).delete();
    }
}
