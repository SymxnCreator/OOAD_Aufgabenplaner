package sample.Services;

import sample.Interfaces.INotify;
import sample.Models.TaskList;

import java.util.List;

public class NotificationService
{
    private INotify consumer;

    public void setEventConsumer(INotify consumer)
    {
        this.consumer = consumer;
    }

    public void run(List<TaskList> lists)
    {

    }
}
