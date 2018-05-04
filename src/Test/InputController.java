package Test;

import Main.Main;
import Result.Result;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class InputController {

    private static String filePath;
    public ComboBox<String> box;
    public Label idLabel;
    public Label nameLabel;
    public Label surnameLabel;
    public Button nextButton;
    public Label testLabel;
    public Button escapeButton;
    public Label messageLabel;


    public static String getFilePath() {
        return filePath;
    }

    public TextField idField;
    public TextField surnameField;
    public TextField nameField;
    public Button goTestButton;
    private Result result = Result.getInstance();


    @FXML
    public void initialize()
    {
        allAnimation();
        File myFolder = new File("Documents");
        File[] files = myFolder.listFiles();
        String[] titlesOfTest = fileAsString(files);
        ObservableList<String> options = FXCollections.observableArrayList(titlesOfTest);

        box.setItems(options);
        box.getSelectionModel().selectFirst();

        nameField.setEditable(false);
        surnameField.setEditable(false);
        goTestButton.setVisible(false);

    }
    public void next() {
        messageLabel.setVisible(false);
        try {

            String id = idField.getText();
            if(id.equals(""))
            {
                throw new NullPointerException();
            }
            String query = "Select Name, Surname from users where studentID = " + id;
            Statement statement = Main.getStatementDB();
            ResultSet rs = statement.executeQuery(query);
            if(rs.isClosed())
            {
                throw new IllegalArgumentException();
            }
            idField.setEditable(false);
            nameField.setText(rs.getString("name"));
            surnameField.setText(rs.getString("surname"));
            goTestButton.setVisible(true);
        }catch (NullPointerException e) {
            messageAnimation("Не введено ID пользователя");
        }
        catch (IllegalArgumentException e) {
            messageAnimation("Пользователь с указанным ID не найден");
        }
        catch (Exception e) {
            messageAnimation("Неизвестная ошибка");
        }
    }

    public void goTest() {
        filePath = "Documents/" + String.valueOf(box.getValue()) + ".xml";
        result.setTestName(String.valueOf(box.getValue()));
        result.setStudentID(idField.getText());
        surnameField.setText(filePath);
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TestFrame.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] fileAsString(File[] files)
    {
        String[] tmp = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            tmp[i] = files[i].getName().substring(0, files[i].getName().length()-4);
        }

        return tmp;
    }

    private void allAnimation()
    {
        animationForNode(box, 274);
        animationForNode(idField, 52);
        animationForNode(idLabel, 56);
        animationForNode(nameLabel, 134);
        animationForNode(nameField, 130);
        animationForNode(surnameLabel, 212);
        animationForNode(surnameField, 208);
        animationForNode(nextButton, 344);
        animationForNode(testLabel, 278);
        animationForNode(escapeButton, 344);

    }

    private void animationForNode(Node node, int setToY)
    {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), node);
        tt.setByY(0);
        tt.setToY(setToY);
        tt.play();
    }

    public void escape(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../Main/MainPage.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void messageAnimation(String message)
    {
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), messageLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }
}
