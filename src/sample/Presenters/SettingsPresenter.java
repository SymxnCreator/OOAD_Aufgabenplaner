package sample.Presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Steuerklasse der SettingsView.fxml
 * @author Dirk Dresselhaus
 */
public class SettingsPresenter
{
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    TextArea textToSend_TextArea;
    @FXML
    Label MsgSend_Label;

    /**
     * Wechselt die Hauptansicht zur MainView (Aufgabenübersicht).
     * @author Dirk Dresselhaus
     */
    @FXML
    public void switchToMainView_OnAction(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/MainView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sendet den Entwicklern die vom Bentutzer-eingegebene Nachricht.
     * (Diese Funktion dient nur zur Visualisierung)
     * @param event
     * @author Dirk Dresselhaus
     */
    @FXML
    public void send_OnAction(ActionEvent event) throws InterruptedException
    {
        this.textToSend_TextArea.setText("");
        this.MsgSend_Label.setVisible(true);
    }
}
