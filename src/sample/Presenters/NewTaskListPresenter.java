package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Steuerklasse der NewTaskListView.fxml
 * @author Simon Schnitker
 */
public class NewTaskListPresenter
{
    @FXML
    TextField listName_TextField;

    /**
     * Fügt eine neue Aufgabenliste der MainView hinzu und schließt das Fenster.
     */
    @FXML
    public void createTask_OnAction(ActionEvent event) throws IOException
    {
        if (listName_TextField.getText() == null || listName_TextField.getText().isBlank())
        {
            return;
        }

        PresenterLocator.taskPresenter.createTaskList(listName_TextField.getText());

        // Fenster schließen
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Bricht die Aktion ab.
     */
    @FXML
    public void cancel_OnAction(ActionEvent event)
    {
        // Fenster schließen
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
