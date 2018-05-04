package AddUser;

import Main.Main;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.sqlite.SQLiteException;

import java.io.IOException;
import java.sql.Statement;

public class AddUserController {

    public TextField nameField;
    public TextField surnameField;
    public TextField idUserField;
    public Label nameLabel;
    public Label surnameLabel;
    public Label idLabel;
    public Button addButton;
    public Button escapeButton;
    public Label messageLabel;


    public void initialize()
    {
        allAnimation();
    }

    public void addUser(ActionEvent actionEvent) {
        //messageLabel.setVisible(false);

        try
        {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String idUser = idUserField.getText();
            if(name.equals("") || surname.equals("") || idUser.equals(""))
            {
                throw new IllegalArgumentException();
            }
            String query = "INSERT INTO users(name, surname, StudentID) VALUES ('" +
                    name + "', '" + surname + "', '" + idUser + "')";
            Statement statement = Main.getStatementDB();
            statement.executeUpdate(query);
            messageAnimation("Успешно добавлено");
        }
        catch (IllegalArgumentException e) {
            messageAnimation("Неверно указаны данные");
        }
        catch (SQLiteException e) {
            messageAnimation("Пользователь с таким ID уже существует!");
        }
        catch (Exception e) {
            messageAnimation("Неизвестная ошибка");
        }

    }

    public void goToBack() {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../Main/MainPage.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void allAnimation()
    {
        animationForNode(nameField, 65);
        animationForNode(surnameField, 125);
        animationForNode(idUserField, 188);
        animationForNode(nameLabel, 69);
        animationForNode(surnameLabel, 129);
        animationForNode(idLabel, 192);
        animationForNode(addButton, 254);
        animationForNode(escapeButton, 341);
    }

    private void animationForNode(Node node, int setToY)
    {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), node);
        tt.setByY(0);
        tt.setToY(setToY);
        tt.play();
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
