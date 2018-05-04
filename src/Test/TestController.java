package Test;

import Result.Result;
import Main.Main;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestController{

    public Label timeLabel;
    public Label messageLabel;
    private ToggleGroup group = new ToggleGroup();
    private int questionNumber = -1;
    private int rightCount = 0;
    private double result = 0;
    public Button finish;
    public Button next;
    public RadioButton answer1RB;
    public RadioButton answer2RB;
    public RadioButton answer3RB;
    public RadioButton answer4RB;
    public Label taskLabel;

    private QuestionList list = new QuestionList(InputController.getFilePath());

    private Integer time = list.size()*60;


    public void initialize()
    {
        answer1RB.setToggleGroup(group);
        answer2RB.setToggleGroup(group);
        answer3RB.setToggleGroup(group);
        answer4RB.setToggleGroup(group);
        setNewQuestionInScene();
        timeLabel.textProperty().bind(task.messageProperty());

        Thread threadTime = new Thread(task);
        threadTime.setDaemon(true);
        threadTime.start();
    }

    public void nextQuestion() {
        if(time <= 0)
        {
            finishAction();
        }else{
            try{
                checkAsk();
                if(questionNumber == (list.size()-1))
                {
                    finishAction();
                }else
                {
                    setNewQuestionInScene();
                }
            }catch(NullPointerException e){messageAnimation("Не выбран вариант ответа");}
        }

    }

    private void setNewQuestionInScene()
    {
        questionNumber++;
        Question question = list.get(questionNumber);
        taskLabel.setText(question.getTask());
        answer1RB.setText(question.getAnswers().get(0));
        answer2RB.setText(question.getAnswers().get(1));
        answer3RB.setText(question.getAnswers().get(2));
        answer4RB.setText(question.getAnswers().get(3));
    }

    public void finishAction() {
        result = rightCount * 100/list.size();
        inputResultInDataBase();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Тест закончен");
        alert.setContentText("Ваш результат: " + result + "%");
        alert.showAndWait();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../Main/MainPage.fxml"));
            Main.stage.setScene(new Scene(root, 600, 550));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkAsk()
    {
        if(group.getSelectedToggle() == null)
        {
            throw new NullPointerException();
        }
        else{
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            Question questionTmp = list.get(questionNumber);
            if (selectedRadioButton.getText().equals(questionTmp.getRightAnswer()))
            {
                ++rightCount;
            }
            selectedRadioButton.setSelected(false);
        }

    }

    public void inputResultInDataBase()
    {
        Result resTmp = Result.getInstance();
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        resTmp.setResult(result);
        resTmp.setDate(formatForDateNow.format(date));


        try {
            String query = "Insert Into results (UserId, testName, Result, Date)" +
                    " Values('" + resTmp.getStudentID() + "', '" +
                    resTmp.getTestName() + "', " + resTmp.getResult() + ", '" + resTmp.getDate() + "')";
            Statement statement = Main.getStatementDB();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Task<Integer> task = new Task<Integer>() {
        @Override
        protected Integer call() throws Exception {
            for (; time >= 0; time--) {
                if (isCancelled()) {
                    break;
                }
                Thread.sleep(1000);
                updateMessage("Осталось времени " + time + " секунд");
            }
            return time;
        }
    };

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
