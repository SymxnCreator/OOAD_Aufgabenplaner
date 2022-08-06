package sample.Services;

import sample.Interfaces.INotify;
import sample.Models.Task;
import sample.Models.TaskList;

import java.time.LocalDate;
import java.util.List;

public class NotificationService
{
    public static void run(INotify consumer, List<TaskList> lists)
    {
        if (consumer == null)
        {
            return;
        }

        new Thread(() ->
        {
            for (TaskList taskList : lists)
            {
                System.out.println(taskList);
                for (Task task : taskList.getTasks())
                {
                    System.out.println(task);
                }
            }

            try
            {
                System.out.println("aaa");
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });
    }
}
