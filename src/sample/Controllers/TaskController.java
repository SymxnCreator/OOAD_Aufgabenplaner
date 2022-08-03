package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.Interfaces.INotify;
import sample.Models.Task;

public class TaskController implements INotify
{
    @FXML
    private void addTask_OnAction(ActionEvent event)
    {

    }

    @Override
    public void notifiy(Task task)
    {

    }
}
