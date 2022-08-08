package sample.Services;

import sample.Enums.NotificationTime;
import sample.Interfaces.TaskNotificatable;
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
     * @param consumer Die Klasse, die benachrichtigt werden soll, wenn eine Aufgabe bald fällig wird.
     * @param lists Die Aufgabenlisten.
     * @author Simon Schnitker
     */
    public static void run(TaskNotificatable consumer, List<TaskList> lists)
    {
        if (consumer == null || lists == null)
        {
            System.out.println("NotificationService wurde abgebrochen.");
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
                        // Wenn benachrichtigt werden soll, die Aufgabe nicht bereits erledigt ist, der Benutzer noch nicht erinnert wurde und die Benachrichtigungszeit erreicht wurde
                        if (task.getNotificationTime() != NotificationTime.Never && task.isFinished() == false && task.hasUserNotified() == false && LocalDateTime.now().compareTo(task.getNotificationDate()) > 0)
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
