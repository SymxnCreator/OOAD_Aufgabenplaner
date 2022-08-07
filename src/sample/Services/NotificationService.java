package sample.Services;

import sample.Enums.NotificationTime;
import sample.Interfaces.Notificatable;
import sample.Models.Task;
import sample.Models.TaskList;

import java.time.LocalDate;
import java.util.List;

public class NotificationService
{
    public static void run(Notificatable consumer, List<TaskList> lists)
    {
        if (consumer == null)
        {
            return;
        }

        new Thread(() ->
        {
            while (true)
            {
                for (TaskList taskList : lists)
                {
                    for (Task task : taskList.getTasks())
                    {
                        if (task.getNotificationTime() != NotificationTime.Never && task.hasUserNotified() == false && LocalDate.now().compareTo(task.getNotificationDate()) > 0)
                        {
                            task.setHasUserNotified(true);
                            System.out.println(task.getTitle());
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
        }).start();
    }
}
