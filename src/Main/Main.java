package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main extends Application {

    private static Statement stat;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        File file = new File("kalambur.mp3");
        Media pick = new Media(file.toURI().toString());
        MediaPlayer player = new MediaPlayer(pick);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        stage = primaryStage;
        stage.setMinHeight(550);
        stage.setMaxHeight(550);
        stage.setMaxWidth(600);
        stage.setMinWidth(600);
        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
        player.play();

    }

    public static Statement getStatementDB()
    {
        if(stat == null)
        {
            try {

                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");
                stat = connection.createStatement();
                return stat;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stat;
    }





    public static void main(String[] args) {
        launch(args);
    }
}