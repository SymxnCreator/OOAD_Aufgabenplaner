package sample.Services;

import sample.Enums.NotificationTime;
import sample.Interfaces.Notificatable;
import sample.Models.Task;
import sample.Models.TaskList;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Eine Service-Klasse, die für das Senden von Erinnerungen zuständig ist.
 * @author Simon Schnitker
 */
public class NotificationService
{
    /**
     * Startet den Erinnerungsdienst.
     * @param consumer Die Klasse, die benachrichtigt werden soll, wenn eine Aufgabe ansteht.
     * @param lists Die Aufgabenlisten.
     */
    public static void run(Notificatable consumer, List<TaskList> lists)
    {
        if (consumer == null)
        {
            return;
        }

        Thread thread = new Thread(() ->
        {
            while (true)
            {
                for (TaskList taskList : lists)
                {
                    for (Task task : taskList.getTasks())
                    {
                        if (task.getNotificationTime() != NotificationTime.Never && task.hasUserNotified() == false && LocalDateTime.now().compareTo(task.getNotificationDate()) > 0)
                        {
                            task.setHasUserNotified(true);
                            consumer.notifiy(task);
                        }
                    }
                }

                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}
