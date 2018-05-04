package Result;

import Main.Main;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class ResultController {

    public TableView<Result> tableView;
    public TableColumn<Result, String> studentIdColumn;
    public TableColumn<Result, String> testNameColumn;
    public TableColumn<Result, Double> resultColumn;
    public TableColumn<Result, String> dateColumn;
    public TextField idField;
    public TextField nameSurnameField;
    public Label enterNumberLabel;
    public Button findButton;
    public Button escapeButton;
    public Label nameSurnameLabel;
    public Label messageLabel;

    private ObservableList<Result> list = FXCollections.observableArrayList();


    @FXML
    public void initialize()
    {
        allAnimation();
        tableView.setItems(list);
        studentIdColumn.setCellValueFactory(cell -> cell.getValue().studentIDProperty());
        testNameColumn.setCellValueFactory(cell -> cell.getValue().testNameProperty());
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        dateColumn.setCellValueFactory(cell -> cell.getValue().dateProperty());

        try {

            String query = "SELECT StudentID,\n" +
                    "       TestName,\n" +
                    "       Result,\n" +
                    "       Date\n" +
                    "  FROM users,\n" +
                    "       results where users.StudentId = results.UserID";
            Statement statement = Main.getStatementDB();

            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                String studID = String.valueOf(rs.getInt("StudentID"));
                String testName = rs.getString("TestName");
                double result = rs.getDouble("Result");
                String date = rs.getString("Date");
                list.add(new Result(studID, testName, result, date));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToBack(ActionEvent actionEvent) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../Main/MainPage.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void find(ActionEvent actionEvent) {
        list.clear();
        String idTmp = idField.getText();
        String name;
        String surname;
        String queryGetNameSurname = "SELECT \n" +
                "       Name,\n" +
                "       Surname\n" +
                "       \n" +
                "  FROM Users where StudentID = '" + idTmp + "'";
        try {
            Statement statement = Main.getStatementDB();
            ResultSet rs = statement.executeQuery(queryGetNameSurname);
            surname = rs.getString("Surname");
            name = rs.getString("name");
            nameSurnameField.setText(name + " " + surname);
        }
        catch (SQLException e) {
            messageAnimation("Неверный ID пользователя");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String queryGetData = "SELECT \n" +
                "       UserID,\n" +
                "       TestName,\n" +
                "       Result,\n" +
                "       Date\n" +
                "  FROM Results where UserID = '" + idTmp + "'";
        try {
            Statement statement = Main.getStatementDB();
            ResultSet rs = statement.executeQuery(queryGetData);
            while(rs.next())
            {
                String studID = String.valueOf(rs.getInt("UserID"));
                String testName = rs.getString("TestName");
                double result = rs.getDouble("Result");
                String date = rs.getString("Date");
                list.add(new Result(studID, testName, result, date));
            }
            tableView.refresh();
        } catch (Exception e) {
            messageAnimation("Неверный ID пользователя");
        }

    }


    private void allAnimation()
    {
        animationForNode(tableView, 140);
        animationForNode(idField, 59);
        animationForNode(enterNumberLabel, 63);
        animationForNode(findButton, 59);
        animationForNode(nameSurnameField, 99);
        animationForNode(nameSurnameLabel, 103);
        animationForNode(escapeButton, 380);
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
