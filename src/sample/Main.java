package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Views/Resources/calendar.png")));

        primaryStage.setResizable(false);

        primaryStage.setTitle("Aufgabenplaner");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
}
