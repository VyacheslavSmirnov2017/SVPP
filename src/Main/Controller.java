package Main;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {
    public Button runTestBtn;
    public Button resultsBtn;
    public Button addUsersBtn;
    public Button cancelBtn;



    public void initialize()
    {
        allAnimation();
    }
    public void runTest(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Test/InputUserName.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void results(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Result/ResultFrame.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addUsers(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../AddUser/AddUserFrame.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Main.stage.close();
    }


    private void allAnimation()
    {
        animationForNode(runTestBtn, 125);
        animationForNode(resultsBtn, 188);
        animationForNode(addUsersBtn, 247);
        animationForNode(cancelBtn, 306);
    }

    private void animationForNode(Node node, int setToY)
    {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), node);
        tt.setByY(0);
        tt.setToY(setToY);
        tt.play();
    }
}
